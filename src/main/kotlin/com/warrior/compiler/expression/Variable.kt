package com.warrior.compiler.expression

import com.warrior.compiler.*
import com.warrior.compiler.validation.*
import com.warrior.compiler.validation.ErrorType.*
import org.antlr.v4.runtime.ParserRuleContext
import org.bytedeco.javacpp.LLVM

/**
 * Created by warrior on 10.03.16.
 */
class Variable(ctx: ParserRuleContext, val name: String) : Expr(ctx) {
    override fun generateCode(module: LLVM.LLVMModuleRef, builder: LLVM.LLVMBuilderRef, symbolTable: SymbolTable): LLVM.LLVMValueRef {
        val ref = symbolTable.variables[name]!!.ref
        return LLVM.LLVMBuildLoad(builder, ref, name)
    }

    override fun getType(functions: Map<String, Type.Fn>, variables: Map<String, Type>): Type {
        return variables[name] ?: Type.Unknown
    }

    override fun validate(functions: Map<String, Type.Fn>, variables: Map<String, Type>): Result {
        return if (name in variables) {
            Result.Ok
        } else {
            val message = "variable '$name' is not declared"
            Result.Error(ErrorMessage(UNDECLARED_VARIABLE, message, start(), end()))
        }
    }

    override fun calculate(functions: Map<String, Fn>, variables: Map<String, TypedValue>): TypedValue {
        return variables[name] ?: throw IllegalStateException("variable '$name' isn't declared")
    }

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is Variable) {
            return false
        }
        return name == other.name
    }

    override fun hashCode(): Int = name.hashCode()
    override fun toString(): String = name
}
