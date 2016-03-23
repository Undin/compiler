package com.warrior.compiler.statement

import com.warrior.compiler.*
import com.warrior.compiler.expression.Expr
import com.warrior.compiler.validation.Fn
import com.warrior.compiler.validation.TypedValue
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
                                  symbolTable: SymbolTable, returnBlock: ReturnBlock?) {
            val localSymbolTable = SymbolTable(symbolTable)

            for (st in statements) {
                st.generateCode(module, builder, localSymbolTable, returnBlock)
                if (st.isTerminalStatement()) {
                    break;
                }
            }
            for ((name, value) in symbolTable.variables) {
                val value = localSymbolTable.variables[name]
                if (value != null) {
                    symbolTable.variables[name] = value
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
            for ((name, value) in env) {
                val value = localEnv[name]
                if (value != null) {
                    env[name] = value
                }
            }
            return out
        }
    }

    class ExpressionStatement(ctx: ParserRuleContext, val expr: Expr) : Statement(ctx) {
        override fun generateCode(module: LLVMModuleRef, builder: LLVMBuilderRef,
                                  symbolTable: SymbolTable, returnBlock: ReturnBlock?) {
            expr.generateCode(module, builder, symbolTable)
        }

        override fun interpret(env: MutableMap<String, TypedValue>, functions: Map<String, Fn>,
                               input: MutableList<TypedValue>): List<TypedValue> {
            expr.calculate(functions, env)
            return listOf()
        }
    }

    class Assign(ctx: ParserRuleContext, val name: String, val expr: Expr) : Statement(ctx) {
        override fun generateCode(module: LLVMModuleRef, builder: LLVMBuilderRef,
                                  symbolTable: SymbolTable, returnBlock: ReturnBlock?) {
            val v = symbolTable.variables[name] ?: throw IllegalStateException("variable '$name' is not declared");
            val value = expr.generateCode(module, builder, symbolTable)
            LLVMBuildStore(builder, value, v.ref)
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
    }

    class AssignDecl(ctx: ParserRuleContext, val name: String, val type: Type, val expr: Expr?) : Statement(ctx) {
        override fun generateCode(module: LLVMModuleRef, builder: LLVMBuilderRef,
                                  symbolTable: SymbolTable, returnBlock: ReturnBlock?) {
            if (name in symbolTable.variables) {
                throw IllegalStateException("$name is already declared")
            }
            val ref = LLVMBuildAlloca(builder, type.toLLVMType(), name)
            symbolTable.variables[name] = VariableAttrs(name, type, ref)
            if (expr != null) {
                val value = expr.generateCode(module, builder, symbolTable)
                LLVMBuildStore(builder, value, ref)
            }
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
    }

    class If(ctx: ParserRuleContext, val condition: Expr, val thenBlock: Block) : Statement(ctx) {
        override fun generateCode(module: LLVMModuleRef, builder: LLVMBuilderRef,
                                  symbolTable: SymbolTable, returnBlock: ReturnBlock?) {
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
    }

    class IfElse(ctx: ParserRuleContext, val condition: Expr, val thenBlock: Block, val elseBlock: Block) : Statement(ctx) {
        override fun generateCode(module: LLVMModuleRef, builder: LLVMBuilderRef,
                                  symbolTable: SymbolTable, returnBlock: ReturnBlock?) {
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
    }

    class While(ctx: ParserRuleContext, val condition: Expr, val bodyBlock: Block) : Statement(ctx) {
        override fun generateCode(module: LLVMModuleRef, builder: LLVMBuilderRef,
                                  symbolTable: SymbolTable, returnBlock: ReturnBlock?) {
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
    }

    class Return(ctx: ParserRuleContext, val expr: Expr) : Statement(ctx) {
        override fun generateCode(module: LLVMModuleRef, builder: LLVMBuilderRef,
                                  symbolTable: SymbolTable, returnBlock: ReturnBlock?) {
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
    }

    class Print(ctx: ParserRuleContext, val expr: Expr, val newLine: Boolean) : Statement(ctx) {

        companion object {
            private var str: LLVMValueRef? = null
            private var strLn: LLVMValueRef? = null

            private fun getStr(builder: LLVMBuilderRef): LLVMValueRef {
                if (str == null) {
                    str = LLVMBuildGlobalStringPtr(builder, "%d", "str")
                }
                return str!!
            }

            private fun getStrLn(builder: LLVMBuilderRef): LLVMValueRef {
                if (strLn == null) {
                    strLn = LLVMBuildGlobalStringPtr(builder, "%d\n", "strLn")
                }
                return strLn!!
            }
        }

        override fun generateCode(module: LLVMModuleRef, builder: LLVMBuilderRef, symbolTable: SymbolTable, returnBlock: ReturnBlock?) {
            val s = if (newLine) getStrLn(builder) else getStr(builder)
            val value = expr.generateCode(module, builder, symbolTable)
            val printfFn = LLVMGetNamedFunction(module, "printf")
            val args = arrayOf(s, value)
            LLVMBuildCall(builder, printfFn, PointerPointer(*args), args.size, "writeCall")
        }

        override fun interpret(env: MutableMap<String, TypedValue>, functions: Map<String, Fn>,
                               input: MutableList<TypedValue>): List<TypedValue> {
            val value = expr.calculate(functions, env)
            return listOf(value)
        }
    }

    class Read(ctx: ParserRuleContext, val varName: String) : Statement(ctx) {

        companion object {
            private var str: LLVMValueRef? = null

            private fun getStr(builder: LLVMBuilderRef): LLVMValueRef {
                if (str == null) {
                    str = LLVMBuildGlobalStringPtr(builder, "%d", "str")
                }
                return str!!
            }
        }

        override fun generateCode(module: LLVMModuleRef, builder: LLVMBuilderRef, symbolTable: SymbolTable, returnBlock: ReturnBlock?) {
            val variable = symbolTable.variables[varName] ?: throw IllegalStateException("variable '$varName' isn't declared")

            val s = getStr(builder)
            val scanfFn = LLVMGetNamedFunction(module, "scanf")
            val args = arrayOf(s, variable.ref)
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
    }

    open fun hasReturnStatement(): Boolean = false
    open fun isTerminalStatement(): Boolean = false

    abstract fun generateCode(module: LLVMModuleRef, builder: LLVMBuilderRef,
                     symbolTable: SymbolTable, returnBlock: ReturnBlock?): Unit
    abstract fun interpret(env: MutableMap<String, TypedValue> = HashMap(),
                           functions: Map<String, Fn> = HashMap(),
                           input: MutableList<TypedValue> = ArrayList()): List<TypedValue>

    protected fun getCurrentFunction(builder: LLVMBuilderRef): LLVMValueRef {
        val currentBlock = LLVMGetInsertBlock(builder)
        return LLVMGetBasicBlockParent(currentBlock)
    }
}

data class ReturnBlock(val block: LLVMBasicBlockRef, val retValueRef: LLVMValueRef)
