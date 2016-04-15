package com.warrior.compiler.statement

import com.warrior.compiler.ASTNode
import com.warrior.compiler.Compiler
import com.warrior.compiler.SymbolTable
import com.warrior.compiler.Type
import com.warrior.compiler.Type.*
import com.warrior.compiler.expression.AggregateLiteral
import com.warrior.compiler.expression.Expr
import com.warrior.compiler.validation.*
import com.warrior.compiler.validation.ErrorType.*
import com.warrior.compiler.validation.Fn
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
                              fnName: String): Result {
            expr.determineType(functions, variables)
            return expr.validate(functions, variables)
        }
    }

    class Assign(ctx: ParserRuleContext, val name: String, val expr: Expr) : Statement(ctx) {
        override fun generateCode(module: LLVMModuleRef, builder: LLVMBuilderRef,
                                  symbolTable: SymbolTable<LLVMValueRef>, returnBlock: ReturnBlock?) {
            val ref = symbolTable[name] ?: throw IllegalStateException("variable '$name' is not declared");
            val value = loadValue(expr, module, builder, symbolTable)
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
                val exprType = expr.determineType(functions, variables)
                if (exprType != Unknown && !varType.match(exprType)) {
                    val message = "'${getText()}': variable and expression types don't match"
                    Error(ErrorMessage(TYPE_MISMATCH, message, start(), end()))
                } else {
                    Ok
                }
            }
            return exprResult + result
        }
    }

    class SetTupleElement(ctx: ParserRuleContext, val tupleExpr: Expr, val index: Int, val valueExpr: Expr) : Statement(ctx) {
        override fun generateCode(module: LLVMModuleRef, builder: LLVMBuilderRef,
                                  symbolTable: SymbolTable<LLVMValueRef>, returnBlock: ReturnBlock?) {
            val tupleValue = tupleExpr.generateCode(module, builder, symbolTable)
            val value = loadValue(valueExpr, module, builder, symbolTable)
            val elementPtr = LLVMBuildStructGEP(builder, tupleValue, index, "")
            LLVMBuildStore(builder, value, elementPtr)
        }

        override fun interpret(env: MutableMap<String, TypedValue>, functions: Map<String, Fn>,
                               input: MutableList<TypedValue>): List<TypedValue> {
            val tupleValue = tupleExpr.calculate(functions, env)
            if (tupleValue !is TypedValue.TupleValue) {
                throw IllegalStateException("'$tupleExpr' must be tuple")
            }
            val value = valueExpr.calculate(functions, env)
            tupleValue[index] = value
            return emptyList()
        }

        override fun validate(functions: Map<String, Type.Fn>, variables: SymbolTable<Type>,
                              fnName: String): Result {
            var result = tupleExpr.validate(functions, variables)
            result += valueExpr.validate(functions, variables)

            val tupleType = tupleExpr.determineType(functions, variables)
            val valueType = valueExpr.determineType(functions, variables)

            if (!(tupleType == Unknown || tupleType is Tuple)) {
                val message = "'${getText()}': '${tupleExpr.getText()}' must have 'tuple' type"
                result += Error(ErrorMessage(TYPE_MISMATCH, message, tupleExpr.start(), tupleExpr.end()))
            }
            if (tupleType is Tuple) {
                if (index >= tupleType.elementsTypes.size) {
                    val message = "'${getText()}': attempted out-of-bounds tuple index '$index' on type '$tupleType'"
                    result += Error(ErrorMessage(INDEX_OUT_OF_RANGE, message, start(), end()))
                } else {
                    val elemType = tupleType.elementsTypes[index]
                    if (!(elemType == Unknown || valueType.match(elemType))) {
                        val message = "'${getText()}': '${valueExpr.getText()}' must have '$elemType' type"
                        result += Error(ErrorMessage(TYPE_MISMATCH, message, valueExpr.start(), valueExpr.end()))
                    }
                }
            }
            return result
        }
    }

    class SetArrayElement(ctx: ParserRuleContext, val arrayExpr: Expr, val indexExpr: Expr, val valueExpr: Expr) : Statement(ctx) {
        override fun generateCode(module: LLVMModuleRef, builder: LLVMBuilderRef,
                                  symbolTable: SymbolTable<LLVMValueRef>, returnBlock: ReturnBlock?) {
            val arrayValue = arrayExpr.generateCode(module, builder, symbolTable)
            val index = indexExpr.generateCode(module, builder, symbolTable)
            val value = loadValue(valueExpr, module, builder, symbolTable)

            val zero = LLVMConstInt(LLVMInt32Type(), 0L, 1)
            val elementPtr = LLVMBuildInBoundsGEP(builder, arrayValue, PointerPointer(*arrayOf(zero, index)), 2, "");

            LLVMBuildStore(builder, value, elementPtr)
        }

        override fun interpret(env: MutableMap<String, TypedValue>, functions: Map<String, Fn>,
                               input: MutableList<TypedValue>): List<TypedValue> {
            val arrayValue = arrayExpr.calculate(functions, env)
            if (arrayValue !is TypedValue.ArrayValue) {
                throw IllegalStateException("'$arrayExpr' must be tuple")
            }
            val indexValue = indexExpr.calculate(functions, env)
            if (indexValue !is TypedValue.IntValue) {
                throw IllegalStateException("'$arrayExpr' must be i32")
            }
            val value = valueExpr.calculate(functions, env)
            arrayValue[indexValue] = value
            return emptyList()
        }

        override fun validate(functions: Map<String, Type.Fn>, variables: SymbolTable<Type>,
                              fnName: String): Result {
            var result = arrayExpr.validate(functions, variables)
            result += indexExpr.validate(functions, variables)
            result += valueExpr.validate(functions, variables)

            val arrayType = arrayExpr.determineType(functions, variables)
            val indexType = indexExpr.determineType(functions, variables)
            val valueType = valueExpr.determineType(functions, variables)

            if (!(arrayType == Unknown || arrayType is Type.Array)) {
                val message = "'${getText()}': '${arrayExpr.getText()}' must have 'array' type"
                result += Error(ErrorMessage(TYPE_MISMATCH, message, arrayExpr.start(), arrayExpr.end()))
            }
            if (!indexType.match(I32)) {
                val message = "'${getText()}': '${indexExpr.getText()}' must have 'i32' type"
                result += Error(ErrorMessage(TYPE_MISMATCH, message, indexExpr.start(), indexExpr.end()))
            }

            if (arrayType is Type.Array) {
                val elementType = arrayType.elementType
                if (!(elementType == Unknown || valueType.match(elementType))) {
                    val message = "'${getText()}': '${valueExpr.getText()}' must have '$elementType' type"
                    result += Error(ErrorMessage(TYPE_MISMATCH, message, valueExpr.start(), valueExpr.end()))
                }
            }

            return result
        }
    }

    protected fun loadValue(expr: Expr, module: LLVMModuleRef, builder: LLVMBuilderRef,
                          symbolTable: SymbolTable<LLVMValueRef>): LLVMValueRef {
        val exprValue = expr.generateCode(module, builder, symbolTable)
        return if (expr.type.isPrimitive()) {
            exprValue
        } else {
            LLVMBuildLoad(builder, exprValue, "")
        }
    }

    class AssignDecl(ctx: ParserRuleContext, val name: String, val type: Type?, val expr: Expr) : Statement(ctx) {
        override fun generateCode(module: LLVMModuleRef, builder: LLVMBuilderRef,
                                  symbolTable: SymbolTable<LLVMValueRef>, returnBlock: ReturnBlock?) {
            if (name in symbolTable.getLocal()) {
                throw IllegalStateException("$name is already declared")
            }

            val ref = LLVMBuildAlloca(builder, expr.type.toLLVMType(), name)

            if (expr is AggregateLiteral) {
                expr.pointer = ref
                expr.generateCode(module, builder, symbolTable)
            } else {
                val exprValue = expr.generateCode(module, builder, symbolTable)
                val value = if (expr.type.isPrimitive()) {
                    exprValue
                } else {
                    LLVMBuildLoad(builder, exprValue, "")
                }
                LLVMBuildStore(builder, value, ref)
            }

            symbolTable.putLocal(name, ref)
        }

        override fun interpret(env: MutableMap<String, TypedValue>, functions: Map<String, Fn>,
                               input: MutableList<TypedValue>): List<TypedValue> {
            if (name in env) {
                throw IllegalStateException("variable '$name' is already declared");
            }

            env[name] = expr.calculate(functions, env)
            return listOf()
        }

        override fun validate(functions: Map<String, Type.Fn>, variables: SymbolTable<Type>,
                              fnName: String): Result {
            var result = expr.validate(functions, variables)

            if (name in variables.getLocal()) {
                val message = "'${getText()}': variable '$name' is already declared"
                return result + Error(ErrorMessage(VARIABLE_IS_ALREADY_DECLARED, message, start(), end()))
            }

            val exprType = expr.determineType(functions, variables)

            val variableType = if (type is Tuple && type.elementsTypes.size == 1) {
                val message = "'${getText()}': tuples with one elements are not supported"
                result += Error(ErrorMessage(ONE_LENGTH_TUPLE, message, start(), end()))
                Unknown
            } else {
                type ?: exprType
            }

            if (exprType != Unknown && !variableType.match(exprType)) {
                val message = "'${getText()}': variable and expression types don't match"
                result += Error(ErrorMessage(TYPE_MISMATCH, message, start(), end()))
            }

            if (name !in variables.getLocal()) {
                variables.putLocal(name, variableType)
            }

            return result
        }
    }

    class DestructiveDeclaration(ctx: ParserRuleContext, val names: List<String>, val expr: Expr) : Statement(ctx) {
        override fun generateCode(module: LLVMModuleRef, builder: LLVMBuilderRef, symbolTable: SymbolTable<LLVMValueRef>, returnBlock: ReturnBlock?) {
            val exprType = expr.type
            if (exprType !is Tuple) {
                throw IllegalStateException("'${expr.getText()}' must have tuple tuple")
            }
            val tupleValue = expr.generateCode(module, builder, symbolTable)
            exprType.elementsTypes.forEachIndexed { i, type ->
                val name = names[i]
                val variableRef = LLVMBuildAlloca(builder, type.toLLVMType(), name)
                val valuePtr = LLVMBuildStructGEP(builder, tupleValue, i, "")
                val value = LLVMBuildLoad(builder, valuePtr, "")
                LLVMBuildStore(builder, value, variableRef)
                symbolTable.putLocal(name, variableRef)
            }
        }

        override fun interpret(env: MutableMap<String, TypedValue>, functions: Map<String, Fn>, input: MutableList<TypedValue>): List<TypedValue> {
            val tupleValue = expr.calculate(functions, env) as TypedValue.TupleValue
            tupleValue.elements.forEachIndexed { i, elementValue ->
                val name = names[i]
                if (name in env) {
                    throw IllegalStateException("variable '$name' is already declared");
                }
                env[name] = elementValue
            }
            return emptyList()
        }

        override fun validate(functions: Map<String, Type.Fn>, variables: SymbolTable<Type>, fnName: String): Result {
            var result = expr.validate(functions, variables)
            var exprType = expr.determineType(functions, variables)
            val expectedTypeString = Collections.nCopies(names.size, "_").joinToString(prefix = "(", postfix = ")")
            if (exprType != Unknown && exprType !is Tuple || exprType is Tuple && exprType.elementsTypes.size != names.size) {
                val message = "'${getText()}': '${expr.getText()}' must have '$expectedTypeString' type"
                result += Error(ErrorMessage(TYPE_MISMATCH, message, expr.start(), expr.end()))
            }

            for ((index, name) in names.withIndex()) {
                if (name in variables) {
                    val message = "'${getText()}': variable '$name' is already declared"
                    result += Error(ErrorMessage(VARIABLE_IS_ALREADY_DECLARED, message, start(), end()))
                } else {
                    val variableType = if (exprType is Tuple && exprType.elementsTypes.size > index) {
                        exprType.elementsTypes[index]
                    } else {
                        Unknown
                    }
                    variables.putLocal(name, variableType)
                }
            }
            return result
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
            val conditionType = condition.determineType(functions, variables)
            val typeResult = if (!conditionType.match(Bool)) {
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
            val conditionType = condition.determineType(functions, variables)
            val typeResult = if (!conditionType.match(Bool)) {
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
            val conditionType = condition.determineType(functions, variables)
            val typeResult = if (!conditionType.match(Bool)) {
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

        var returnValuePtr: LLVMValueRef? = null;

        override fun generateCode(module: LLVMModuleRef, builder: LLVMBuilderRef,
                                  symbolTable: SymbolTable<LLVMValueRef>, returnBlock: ReturnBlock?) {
            if (expr is AggregateLiteral) {
                expr.pointer = returnBlock?.retValueRef ?: returnValuePtr!!
            }
            val exprValue = expr.generateCode(module, builder, symbolTable)
            if (returnBlock != null) {
                if (expr.type.isPrimitive()) {
                    LLVMBuildStore(builder, exprValue, returnBlock.retValueRef)
                } else {
                    saveNonPrimitiveValue(builder, exprValue, returnBlock.retValueRef)
                }
                LLVMBuildBr(builder, returnBlock.block)
            } else {
                if (expr.type.isPrimitive()) {
                    LLVMBuildRet(builder, exprValue)
                } else {
                    if (returnValuePtr == null) {
                        throw IllegalStateException("There is not return value ptr")
                    } else {
                        if (expr !is AggregateLiteral) {
                            saveNonPrimitiveValue(builder, exprValue, returnValuePtr!!)
                        }
                        LLVMBuildRetVoid(builder)
                    }
                }
            }
        }

        private fun saveNonPrimitiveValue(builder: LLVMBuilderRef, srcPtr: LLVMValueRef, dstPtr: LLVMValueRef) {
            val value = LLVMBuildLoad(builder, srcPtr, "")
            LLVMBuildStore(builder, value, dstPtr)
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
            val type = expr.determineType(functions, variables)
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
                              fnName: String): Result {
            expr.determineType(functions, variables)
            return expr.validate(functions, variables)
        }
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
