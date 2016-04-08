package com.warrior.compiler.expression

import com.warrior.compiler.SymbolTable
import com.warrior.compiler.Type
import com.warrior.compiler.validation.ErrorMessage
import com.warrior.compiler.validation.ErrorType.UNDECLARED_VARIABLE
import com.warrior.compiler.validation.Fn
import com.warrior.compiler.validation.Result
import com.warrior.compiler.validation.TypedValue
import org.antlr.v4.runtime.ParserRuleContext
import org.bytedeco.javacpp.LLVM.*

/**
 * Created by warrior on 10.03.16.
 */
class Variable(ctx: ParserRuleContext, val name: String) : Expr(ctx) {
    override fun generateCode(module: LLVMModuleRef, builder: LLVMBuilderRef,
                              symbolTable: SymbolTable<LLVMValueRef>): LLVMValueRef {
        val variableRef = symbolTable[name] ?: throw IllegalStateException("variable '$name' isn't declared")
        return LLVMBuildLoad(builder, variableRef, name)
    }

    override fun determineTypeInternal(functions: Map<String, Type.Fn>, variables: SymbolTable<Type>): Type {
        return variables[name] ?: Type.Unknown
    }

    override fun validate(functions: Map<String, Type.Fn>, variables: SymbolTable<Type>): Result {
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

    override fun isLValue(): Boolean = true

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is Variable) {
            return false
        }
        return name == other.name
    }

    override fun hashCode(): Int = name.hashCode()
    override fun toString(): String = name
}
