package com.warrior.compiler.statement

import com.warrior.compiler.ASTNode
import com.warrior.compiler.Compiler
import com.warrior.compiler.SymbolTable
import com.warrior.compiler.Type
import com.warrior.compiler.expression.Expr
import com.warrior.compiler.validation.*
import com.warrior.compiler.validation.ErrorType.*
import com.warrior.compiler.validation.Result.Error
import com.warrior.compiler.validation.Result.Ok
import org.antlr.v4.runtime.ParserRuleContext
import org.bytedeco.javacpp.LLVM.*
import org.bytedeco.javacpp.PointerPointer
import java.util.*

/**
 * Created by warrior on 13.03.16.
 */
sealed class Statement(ctx: ParserRuleContext) : ASTNode(ctx) {

    class Block(ctx: ParserRuleContext, val statements: List<Statement>) : Statement(ctx) {
        override fun generateCode(module: LLVMModuleRef, builder: LLVMBuilderRef,
                                  symbolTable: SymbolTable<LLVMValueRef>, returnBlock: ReturnBlock?) {
            val localSymbolTable = SymbolTable(symbolTable)

            for (st in statements) {
                st.generateCode(module, builder, localSymbolTable, returnBlock)
                if (st.isTerminalStatement()) {
                    break;
                }
            }
        }

        override fun hasReturnStatement(): Boolean = statements.any { it.hasReturnStatement() }
        override fun isTerminalStatement(): Boolean = statements.any { it.isTerminalStatement() }

        override fun interpret(env: MutableMap<String, TypedValue>, functions: Map<String, Fn>,
                               input: MutableList<TypedValue>): List<TypedValue> {
            val localEnv = HashMap(env)
            val out = ArrayList<TypedValue>()
            for (st in statements) {
                out += st.interpret(localEnv, functions, input)
                if (st.isTerminalStatement()) {
                    break;
                }
            }
            update(env, localEnv)
            return out
        }

        override fun validate(functions: Map<String, Type.Fn>, variables: SymbolTable<Type>,
                              fnName: String): Result {
            val localSymbolTable = SymbolTable(variables)
            val result = statements
                    .map { it.validate(functions, localSymbolTable, fnName) }
                    .fold()
            return result
        }

        private fun <K, V> update(oldMap: MutableMap<K, V>, newMap: Map<K, V>) {
            for ((name, v) in oldMap) {
                val value = newMap[name]
                if (value != null) {
                    oldMap[name] = value
                }
            }
        }
    }

    class ExpressionStatement(ctx: ParserRuleContext, val expr: Expr) : Statement(ctx) {
        override fun generateCode(module: LLVMModuleRef, builder: LLVMBuilderRef,
                                  symbolTable: SymbolTable<LLVMValueRef>, returnBlock: ReturnBlock?) {
            expr.generateCode(module, builder, symbolTable)
        }

        override fun interpret(env: MutableMap<String, TypedValue>, functions: Map<String, Fn>,
                               input: MutableList<TypedValue>): List<TypedValue> {
            expr.calculate(functions, env)
            return listOf()
        }

        override fun validate(functions: Map<String, Type.Fn>, variables: SymbolTable<Type>,
                              fnName: String): Result = expr.validate(functions, variables)
    }

    class Assign(ctx: ParserRuleContext, val name: String, val expr: Expr) : Statement(ctx) {
        override fun generateCode(module: LLVMModuleRef, builder: LLVMBuilderRef,
                                  symbolTable: SymbolTable<LLVMValueRef>, returnBlock: ReturnBlock?) {
            val ref = symbolTable[name] ?: throw IllegalStateException("variable '$name' is not declared");
            val value = expr.generateCode(module, builder, symbolTable)
            LLVMBuildStore(builder, value, ref)
        }

        override fun interpret(env: MutableMap<String, TypedValue>, functions: Map<String, Fn>,
                               input: MutableList<TypedValue>): List<TypedValue> {
            if (name !in env) {
                throw IllegalStateException("variable '$name' is not declared");
            }
            val value = expr.calculate(functions, env)
            env[name] = value
            return listOf();
        }

        override fun validate(functions: Map<String, Type.Fn>,
                              variables: SymbolTable<Type>,
                              fnName: String): Result {
            val exprResult = expr.validate(functions, variables)
            val varType = variables[name]
            val result = if (varType == null) {
                val message = "'${getText()}': variable '$name' is not declared"
                Error(ErrorMessage(UNDECLARED_VARIABLE, message, start(), end()))
            } else {
                val exprType = expr.getType(functions, variables)
                if (exprType != Type.Unknown && !varType.match(exprType)) {
                    val message = "'${getText()}': variable and expression types don't match"
                    Error(ErrorMessage(TYPE_MISMATCH, message, start(), end()))
                } else {
                    Ok
                }
            }
            return exprResult + result
        }
    }

    class AssignDecl(ctx: ParserRuleContext, val name: String, val type: Type?, val expr: Expr?) : Statement(ctx) {
        override fun generateCode(module: LLVMModuleRef, builder: LLVMBuilderRef,
                                  symbolTable: SymbolTable<LLVMValueRef>, returnBlock: ReturnBlock?) {
            if (name in symbolTable.getLocal()) {
                throw IllegalStateException("$name is already declared")
            }

            val value: LLVMValueRef
            if (type == null) {
                if (expr == null) {
                    throw IllegalArgumentException("can't determine type of variable '$name'")
                }
                value = expr.generateCode(module, builder, symbolTable)
            } else {
                value = expr?.generateCode(module, builder, symbolTable) ?: LLVMConstInt(type.toLLVMType(), 0L, 1)
            }

            val ref = LLVMBuildAlloca(builder, LLVMTypeOf(value), name)
            LLVMBuildStore(builder, value, ref)

            symbolTable.putLocal(name, ref)
        }

        override fun interpret(env: MutableMap<String, TypedValue>, functions: Map<String, Fn>,
                               input: MutableList<TypedValue>): List<TypedValue> {
            if (name in env) {
                throw IllegalStateException("variable '$name' is already declared");
            }

            env[name] = if (expr != null) {
                expr.calculate(functions, env)
            } else {
                when (type) {
                    is Type.Bool -> TypedValue.BoolValue(false)
                    is Type.I32 -> TypedValue.IntValue(0)
                    else -> throw IllegalStateException("Unknown variable type")
                }
            }
            return listOf()
        }

        override fun validate(functions: Map<String, Type.Fn>, variables: SymbolTable<Type>,
                              fnName: String): Result {
            val exprResult = expr?.validate(functions, variables) ?: Ok
            if (name in variables.getLocal()) {
                val message = "'${getText()}': variable '$name' is already declared"
                return exprResult + Error(ErrorMessage(VARIABLE_IS_ALREADY_DECLARED, message, start(), end()))
            }

            val variableType: Type
            val exprType: Type
            if (type == null) {
                if (expr == null) {
                    variables.putLocal(name, Type.Unknown)
                    val message = "'${getText()}': can't determine type of variable '$name'"
                    return exprResult + Error(ErrorMessage(UNKNOWN_VARIABLE_TYPE, message, start(), end()))
                }
                exprType = expr.getType(functions, variables)
                variableType = exprType

            } else {
                variableType = type
                exprType = expr?.getType(functions, variables) ?: type
            }
            variables.putLocal(name, variableType)

            val result = if (exprType != Type.Unknown && !variableType.match(exprType)) {
                val message = "'${getText()}': variable and expression types don't match"
                Error(ErrorMessage(TYPE_MISMATCH, message, start(), end()))
            } else {
                Ok
            }
            return exprResult + result
        }
    }

    class If(ctx: ParserRuleContext, val condition: Expr, val thenBlock: Block) : Statement(ctx) {
        override fun generateCode(module: LLVMModuleRef, builder: LLVMBuilderRef,
                                  symbolTable: SymbolTable<LLVMValueRef>, returnBlock: ReturnBlock?) {
            val value = condition.generateCode(module, builder, symbolTable)
            val fn = getCurrentFunction(builder)

            val thenBasicBlock = LLVMAppendBasicBlock(fn, "if_true")
            val mergeBasicBlock = LLVMAppendBasicBlock(fn, "if_merge")

            // create conditional jump
            LLVMBuildCondBr(builder, value, thenBasicBlock, mergeBasicBlock)

            // generate code for 'then' block
            LLVMPositionBuilderAtEnd(builder, thenBasicBlock)
            thenBlock.generateCode(module, builder, symbolTable, returnBlock)

            // if 'then' block hasn't terminal statement
            if (!thenBlock.isTerminalStatement()) {
                // create unconditional jump to 'merge' block
                LLVMBuildBr(builder, mergeBasicBlock)
            }

            // move builder position to 'merge' block
            LLVMPositionBuilderAtEnd(builder, mergeBasicBlock)
        }

        override fun hasReturnStatement(): Boolean = thenBlock.hasReturnStatement()

        override fun interpret(env: MutableMap<String, TypedValue>, functions: Map<String, Fn>,
                               input: MutableList<TypedValue>): List<TypedValue> {
            val value = condition.calculate(functions, env) as TypedValue.BoolValue
            return if (value.value) {
                thenBlock.interpret(env, functions, input)
            } else {
                listOf()
            }
        }

        override fun validate(functions: Map<String, Type.Fn>, variables: SymbolTable<Type>,
                              fnName: String): Result {
            val conditionResult = condition.validate(functions, variables)
            val conditionType = condition.getType(functions, variables)
            val typeResult = if (!conditionType.match(Type.Bool)) {
                val message = "expression '${condition.getText()}' must have 'bool' type"
                Error(ErrorMessage(TYPE_MISMATCH, message, condition.start(), condition.end()))
            } else {
                Ok
            }
            val thenBlockResult = thenBlock.validate(functions, variables, fnName)
            return conditionResult + typeResult + thenBlockResult
        }
    }

    class IfElse(ctx: ParserRuleContext, val condition: Expr, val thenBlock: Block, val elseBlock: Block) : Statement(ctx) {
        override fun generateCode(module: LLVMModuleRef, builder: LLVMBuilderRef,
                                  symbolTable: SymbolTable<LLVMValueRef>, returnBlock: ReturnBlock?) {
            val value = condition.generateCode(module, builder, symbolTable)
            val fn = getCurrentFunction(builder)

            val thenBasicBlock = LLVMAppendBasicBlock(fn, "if_true")
            val elseBasicBlock = LLVMAppendBasicBlock(fn, "if_false")
            val mergeBasicBlock = LLVMAppendBasicBlock(fn, "if_merge")

            // create conditional jump
            LLVMBuildCondBr(builder, value, thenBasicBlock, elseBasicBlock)

            // generate code for 'then' block
            LLVMPositionBuilderAtEnd(builder, thenBasicBlock)
            thenBlock.generateCode(module, builder, symbolTable, returnBlock)

            var removeMergeBlock = true
            // if 'then' block isn't terminal statement
            if (!thenBlock.isTerminalStatement()) {
                removeMergeBlock = false
                // create unconditional jump to 'merge' block
                LLVMBuildBr(builder, mergeBasicBlock)
            }

            // generate code for 'else' block
            LLVMPositionBuilderAtEnd(builder, elseBasicBlock)
            elseBlock.generateCode(module, builder, symbolTable, returnBlock)

            // if 'else' block isn't terminal statement
            if (!elseBlock.isTerminalStatement()) {
                removeMergeBlock = false
                // create unconditional jump to 'merge' block
                LLVMBuildBr(builder, mergeBasicBlock)
            }

            if (removeMergeBlock) {
                // remove 'merge' block because it is unreachable
                LLVMRemoveBasicBlockFromParent(mergeBasicBlock)
            } else {
                // move builder position to 'merge' block
                LLVMPositionBuilderAtEnd(builder, mergeBasicBlock)
            }
        }

        override fun hasReturnStatement(): Boolean = thenBlock.hasReturnStatement() || elseBlock.hasReturnStatement()
        override fun isTerminalStatement(): Boolean = thenBlock.isTerminalStatement() && elseBlock.isTerminalStatement()

        override fun interpret(env: MutableMap<String, TypedValue>, functions: Map<String, Fn>,
                               input: MutableList<TypedValue>): List<TypedValue> {
            val value = condition.calculate(functions, env) as TypedValue.BoolValue
            return if (value.value) {
                thenBlock.interpret(env, functions, input)
            } else {
                elseBlock.interpret(env, functions, input)
            }
        }

        override fun validate(functions: Map<String, Type.Fn>, variables: SymbolTable<Type>,
                              fnName: String): Result {
            val conditionResult = condition.validate(functions, variables)
            val conditionType = condition.getType(functions, variables)
            val typeResult = if (!conditionType.match(Type.Bool)) {
                val message = "expression '${condition.getText()}' must have 'bool' type"
                Error(ErrorMessage(TYPE_MISMATCH, message, condition.start(), condition.end()))
            } else {
                Ok
            }
            val thenBlockResult = thenBlock.validate(functions, variables, fnName)
            val elseBlockResult = elseBlock.validate(functions, variables, fnName)
            return conditionResult + typeResult + thenBlockResult + elseBlockResult
        }
    }

    class While(ctx: ParserRuleContext, val condition: Expr, val bodyBlock: Block) : Statement(ctx) {
        override fun generateCode(module: LLVMModuleRef, builder: LLVMBuilderRef,
                                  symbolTable: SymbolTable<LLVMValueRef>, returnBlock: ReturnBlock?) {
            val fn = getCurrentFunction(builder)

            val condBasicBlock = LLVMAppendBasicBlock(fn, "while_cond")
            val bodyBasicBlock = LLVMAppendBasicBlock(fn, "while_body")
            val nextBasicBlock = LLVMAppendBasicBlock(fn, "while_next")

            // create unconditional jump to 'condition' block
            LLVMBuildBr(builder, condBasicBlock)

            // generate code for 'condition' block
            LLVMPositionBuilderAtEnd(builder, condBasicBlock)
            val value = condition.generateCode(module, builder, symbolTable)
            // create conditional jump
            LLVMBuildCondBr(builder, value, bodyBasicBlock, nextBasicBlock)

            // generate code for 'body' block
            LLVMPositionBuilderAtEnd(builder, bodyBasicBlock)
            bodyBlock.generateCode(module, builder, symbolTable, returnBlock)

            // if 'body' block isn't terminal statement
            if (!bodyBlock.isTerminalStatement()) {
                // create unconditional jump to 'condition' block
                LLVMBuildBr(builder, condBasicBlock)
            }

            // move builder position to 'next' block
            LLVMPositionBuilderAtEnd(builder, nextBasicBlock)
        }

        override fun hasReturnStatement(): Boolean = bodyBlock.hasReturnStatement()

        override fun interpret(env: MutableMap<String, TypedValue>, functions: Map<String, Fn>,
                               input: MutableList<TypedValue>): List<TypedValue> {
            val output = ArrayList<TypedValue>()
            while ((condition.calculate(functions, env) as TypedValue.BoolValue).value) {
                output += bodyBlock.interpret(env, functions, input)
            }
            return output
        }

        override fun validate(functions: Map<String, Type.Fn>, variables: SymbolTable<Type>,
                              fnName: String): Result {
            val conditionResult = condition.validate(functions, variables)
            val conditionType = condition.getType(functions, variables)
            val typeResult = if (!conditionType.match(Type.Bool)) {
                val message = "expression '${condition.getText()}' must have 'bool' type"
                Error(ErrorMessage(TYPE_MISMATCH, message, condition.start(), condition.end()))
            } else {
                Ok
            }
            val bodyBlockResult = bodyBlock.validate(functions, variables, fnName)
            return conditionResult + typeResult + bodyBlockResult
        }
    }

    class Return(ctx: ParserRuleContext, val expr: Expr) : Statement(ctx) {
        override fun generateCode(module: LLVMModuleRef, builder: LLVMBuilderRef,
                                  symbolTable: SymbolTable<LLVMValueRef>, returnBlock: ReturnBlock?) {
            val value = expr.generateCode(module, builder, symbolTable)
            if (returnBlock != null) {
                LLVMBuildStore(builder, value, returnBlock.retValueRef)
                LLVMBuildBr(builder, returnBlock.block)
            } else {
                LLVMBuildRet(builder, value)
            }
        }

        override fun hasReturnStatement(): Boolean = true
        override fun isTerminalStatement(): Boolean = true

        override fun interpret(env: MutableMap<String, TypedValue>, functions: Map<String, Fn>,
                               input: MutableList<TypedValue>): List<TypedValue> {
            expr.calculate(functions, env)
            return listOf()
        }

        override fun validate(functions: Map<String, Type.Fn>,
                              variables: SymbolTable<Type>,
                              fnName: String): Result {
            val exprResult = expr.validate(functions, variables)
            val type = expr.getType(functions, variables)
            val returnType = functions[fnName]!!.returnType
            val result = if (!type.match(returnType)) {
                val message = "'${getText()}': expression has '$type' but expected '$returnType'"
                Error(ErrorMessage(TYPE_MISMATCH, message, start(), end()))
            } else {
                Ok
            }
            return exprResult + result
        }
    }

    class Print(ctx: ParserRuleContext, val expr: Expr, val newLine: Boolean) : Statement(ctx) {
        override fun generateCode(module: LLVMModuleRef, builder: LLVMBuilderRef,
                                  symbolTable: SymbolTable<LLVMValueRef>, returnBlock: ReturnBlock?) {
            val s = if (newLine) Compiler.getStrLn(builder) else Compiler.getStr(builder)
            val value = expr.generateCode(module, builder, symbolTable)
            val printfFn = LLVMGetNamedFunction(module, "printf")
            val args = arrayOf(s, value)
            LLVMBuildCall(builder, printfFn, PointerPointer(*args), args.size, "printCall")
        }

        override fun interpret(env: MutableMap<String, TypedValue>, functions: Map<String, Fn>,
                               input: MutableList<TypedValue>): List<TypedValue> {
            val value = expr.calculate(functions, env)
            return listOf(value)
        }

        override fun validate(functions: Map<String, Type.Fn>, variables: SymbolTable<Type>,
                              fnName: String): Result = expr.validate(functions, variables)
    }

    class Read(ctx: ParserRuleContext, val varName: String) : Statement(ctx) {
        override fun generateCode(module: LLVMModuleRef, builder: LLVMBuilderRef,
                                  symbolTable: SymbolTable<LLVMValueRef>, returnBlock: ReturnBlock?) {
            val ref = symbolTable[varName] ?: throw IllegalStateException("variable '$varName' isn't declared")

            val s = Compiler.getStr(builder)
            val scanfFn = LLVMGetNamedFunction(module, "scanf")
            val args = arrayOf(s, ref)
            LLVMBuildCall(builder, scanfFn, PointerPointer(*args), args.size, "readCall")
        }

        override fun interpret(env: MutableMap<String, TypedValue>, functions: Map<String, Fn>,
                               input: MutableList<TypedValue>): List<TypedValue> {
            if (varName !in env) {
                throw IllegalStateException("variable '$varName' isn't declared")
            }
            env[varName] = input.removeAt(0)
            return listOf()
        }

        override fun validate(functions: Map<String, Type.Fn>, variables: SymbolTable<Type>,
                              fnName: String): Result {
            if (varName !in variables) {
                val message = "'${getText()}': variable '$varName' is not declared"
                return Error(ErrorMessage(UNDECLARED_VARIABLE, message, start(), end()))
            }
            return Ok
        }
    }

    open fun hasReturnStatement(): Boolean = false
    open fun isTerminalStatement(): Boolean = false

    abstract fun generateCode(module: LLVMModuleRef, builder: LLVMBuilderRef,
                     symbolTable: SymbolTable<LLVMValueRef>, returnBlock: ReturnBlock?): Unit
    abstract fun interpret(env: MutableMap<String, TypedValue> = HashMap(),
                           functions: Map<String, Fn> = HashMap(),
                           input: MutableList<TypedValue> = ArrayList()): List<TypedValue>
    abstract fun validate(functions: Map<String, Type.Fn> = emptyMap(),
                          variables: SymbolTable<Type> = SymbolTable(),
                          fnName: String): Result

    protected fun getCurrentFunction(builder: LLVMBuilderRef): LLVMValueRef {
        val currentBlock = LLVMGetInsertBlock(builder)
        return LLVMGetBasicBlockParent(currentBlock)
    }
}

data class ReturnBlock(val block: LLVMBasicBlockRef, val retValueRef: LLVMValueRef)
