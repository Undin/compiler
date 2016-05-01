package com.warrior.compiler.expression

import com.warrior.compiler.SymbolTable
import com.warrior.compiler.Type
import com.warrior.compiler.Type.Unknown
import com.warrior.compiler.validation.*
import com.warrior.compiler.validation.ErrorType.*
import com.warrior.compiler.validation.Result.Error
import org.antlr.v4.runtime.ParserRuleContext
import org.bytedeco.javacpp.LLVM.*
import org.bytedeco.javacpp.PointerPointer

/**
 * Created by warrior on 10.03.16.
 */
sealed class Call(ctx: ParserRuleContext, val fnName: String, val args: List<Expr>) : Expr(ctx) {

    class SimpleCall(ctx: ParserRuleContext, fnName: String, args: List<Expr>) : Call(ctx, fnName, args) {

        override fun validate(functions: Map<String, Type.Fn>, variables: SymbolTable<Type>): Result {
            var result = args
                    .map { it.validate(functions, variables) }
                    .fold()

            val fnType = functions[fnName]
            if (fnType == null) {
                val message = "'${getText()}': function '$fnName' is not declared"
                result += Error(ErrorMessage(UNDECLARED_FUNCTION, message, start(), end()))
            } else {
                if (args.size != fnType.argsTypes.size) {
                    val message = "'${getText()}': you must pass ${fnType.argsTypes.size} args to function '$fnName'"
                    result += Error(ErrorMessage(WRONG_ARGS_NUMBER, message, start(), end()))
                }
                val minSize = Math.min(args.size, fnType.argsTypes.size)
                for ((i, arg) in args.slice(0 until minSize).withIndex()) {
                    val type = arg.determineType(functions, variables)
                    if (!type.match(fnType.argsTypes[i])) {
                        val message = "'${getText()}': expression '${arg.getText()}' must have '$type' type";
                        result += Error(ErrorMessage(TYPE_MISMATCH, message, arg.start(), arg.end()))
                    }
                }
            }
            return result
        }

        override fun equals(other: Any?): Boolean {
            if (other == null || other !is SimpleCall) {
                return false
            }
            return fnName == other.fnName && args == other.args
        }

        override fun hashCode(): Int{
            var result = fnName.hashCode()
            result += 31 * result + args.hashCode()
            return result
        }

        override fun toString(): String{
            return "$fnName(${args.joinToString()})"
        }
    }

    class ExtensionCall(ctx: ParserRuleContext, val objectExpr: Expr, fnName: String, val lastArgs: List<Expr>) : Call(ctx, fnName, listOf(objectExpr) + lastArgs) {

        override fun validate(functions: Map<String, Type.Fn>, variables: SymbolTable<Type>): Result {
            var result = args.map { it.validate(functions, variables) }.fold()

            val fnType = functions[fnName]
            if (fnType == null) {
                val message = "'${getText()}': function '$fnName' is not declared"
                result += Error(ErrorMessage(UNDECLARED_FUNCTION, message, start(), end()))
            } else {
                val objectType = objectExpr.determineType(functions, variables)
                val argsTypes = lastArgs.map { it.determineType(functions, variables) }

                if (!fnType.isExtension) {
                    val message = "'${getText()}': function '$fnName' must be extension function"
                    result += Error(ErrorMessage(NOT_EXTENSION_FUNCTION, message, start(), end()))
                } else if (fnType.argsTypes[0] != objectType) {
                    val message = "'${getText()}': expression '${objectExpr.getText()}' must have '${fnType.argsTypes[0]}' type " +
                            "but found '$objectType'"
                    result += Error(ErrorMessage(TYPE_MISMATCH, message, objectExpr.start(), objectExpr.end()))
                } else {
                    if (lastArgs.size + 1 != fnType.argsTypes.size) {
                        val message = "'${getText()}': you must pass ${fnType.argsTypes.size - 1} args to function '$fnName'"
                        result += Error(ErrorMessage(WRONG_ARGS_NUMBER, message, start(), end()))
                    }
                    val minSize = Math.min(lastArgs.size, fnType.argsTypes.size - 1)
                    for ((i, argType) in argsTypes.slice(0 until minSize).withIndex()) {
                        val expectedType = fnType.argsTypes[i + 1]
                        if (!argType.match(expectedType)) {
                            val arg = lastArgs[i]
                            val message = "'${getText()}': expression '${arg.getText()}' must have '$expectedType' type";
                            result += Error(ErrorMessage(TYPE_MISMATCH, message, arg.start(), arg.end()))
                        }
                    }
                }
            }

            return result
        }

        override fun equals(other: Any?): Boolean {
            if (other == null || other !is ExtensionCall) {
                return false
            }
            return fnName == other.fnName && objectExpr == other.objectExpr && lastArgs == other.lastArgs
        }

        override fun hashCode(): Int {
            var result = fnName.hashCode()
            result += 31 * result + objectExpr.hashCode()
            result += 31 * result + lastArgs.hashCode()
            return result
        }

        override fun toString(): String = "$objectExpr.$fnName(${lastArgs.joinToString()})"
    }

    override fun generateCode(module: LLVMModuleRef, builder: LLVMBuilderRef, symbolTable: SymbolTable<LLVMValueRef>): LLVMValueRef {
        val fn = LLVMGetNamedFunction(module, fnName)
        var argsValues = args.map { arg ->
            val exprValue = arg.generateCode(module, builder, symbolTable)
            return@map if (arg.type.isPrimitive()) {
                exprValue
            } else {
                LLVMBuildLoad(builder, exprValue, "")
            }
        }.toTypedArray()
        return if (type.isPrimitive()) {
            LLVMBuildCall(builder, fn, PointerPointer(*argsValues), argsValues.size, "${fnName}Call")
        } else {
            val result = LLVMBuildAlloca(builder, type.toLLVMType(), "${fnName}Call")
            argsValues += result
            LLVMBuildCall(builder, fn, PointerPointer(*argsValues), argsValues.size, "")
            result
        }
    }

    override fun determineTypeInternal(functions: Map<String, Type.Fn>, variables: SymbolTable<Type>): Type =
            functions[fnName]?.returnType ?: Unknown

    override fun calculate(functions: Map<String, Fn>, variables: Map<String, TypedValue>): TypedValue {
        val fn = functions[fnName]!!
        val arguments = args.map { it.calculate(functions, variables) }.toList()
        return fn(arguments)
    }

    override fun isConstant(): Boolean = false
}
