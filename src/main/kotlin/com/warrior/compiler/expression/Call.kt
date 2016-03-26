package com.warrior.compiler.expression

import com.warrior.compiler.SymbolTable
import com.warrior.compiler.Type
import com.warrior.compiler.VariableAttrs
import com.warrior.compiler.validation.ErrorMessage
import com.warrior.compiler.validation.ErrorType.*
import com.warrior.compiler.validation.Fn
import com.warrior.compiler.validation.Result
import com.warrior.compiler.validation.TypedValue
import org.antlr.v4.runtime.ParserRuleContext
import org.bytedeco.javacpp.LLVM
import org.bytedeco.javacpp.PointerPointer
import java.util.*

/**
 * Created by warrior on 10.03.16.
 */
class Call(ctx: ParserRuleContext, val fnName: String, val args: List<Expr>) : Expr(ctx) {
    override fun generateCode(module: LLVM.LLVMModuleRef, builder: LLVM.LLVMBuilderRef,
                              symbolTable: SymbolTable<VariableAttrs>): LLVM.LLVMValueRef {
        val fn = LLVM.LLVMGetNamedFunction(module, fnName)
        val argsValues = args.map { it.generateCode(module, builder, symbolTable) }.toTypedArray()
        return LLVM.LLVMBuildCall(builder, fn, PointerPointer(*argsValues), argsValues.size, "${fnName}Call")
    }

    override fun getType(functions: Map<String, Type.Fn>, variables: SymbolTable<Type>): Type {
        return functions[fnName]?.returnType ?: Type.Unknown
    }

    override fun validate(functions: Map<String, Type.Fn>, variables: SymbolTable<Type>): Result {
        val argsResults = args
                .map { it.validate(functions, variables) }
                .toList()

        val messages = ArrayList<ErrorMessage>()
        if (fnName !in functions) {
            val msg = "'${getText()}': function '$fnName' is not declared"
            messages.add(ErrorMessage(UNDECLARED_FUNCTION, msg, start(), end()))
        } else {
            val fnType = functions[fnName]!!
            if (args.size != fnType.argsTypes.size) {
                val msg = "'${getText()}': you must pass ${fnType.argsTypes.size} args to function '$fnName'"
                messages.add(ErrorMessage(WRONG_ARGS_NUMBER, msg, start(), end()))
            }
            for ((i, arg) in args.slice(0..Math.min(args.size, fnType.argsTypes.size) - 1).withIndex()) {
                val type = arg.getType(functions, variables)
                if (!type.match(fnType.argsTypes[i])) {
                    val msg = "expression '${arg.getText()}' must have '$type' type";
                    messages.add(ErrorMessage(TYPE_MISMATCH, msg, arg.start(), arg.end()))
                }
            }
        }
        val result = if (messages.isEmpty()) {
            Result.Ok
        } else {
            Result.Error(messages)
        }
        return argsResults.fold(Result.Ok, Result::plus) + result
    }

    override fun calculate(functions: Map<String, Fn>, variables: Map<String, TypedValue>): TypedValue {
        val fn = functions[fnName]!!
        val arguments = args.map { it.calculate(functions, variables) }.toList()
        return fn(arguments)
    }

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is Call) {
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
