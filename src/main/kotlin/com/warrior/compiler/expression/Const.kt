package com.warrior.compiler.expression

import com.warrior.compiler.*
import com.warrior.compiler.validation.Fn
import com.warrior.compiler.validation.Result
import com.warrior.compiler.validation.TypedValue
import org.antlr.v4.runtime.ParserRuleContext
import org.bytedeco.javacpp.LLVM

/**
 * Created by warrior on 09.03.16.
 */
class Bool(ctx: ParserRuleContext, val value: Boolean) : Expr(ctx) {
    override fun generateCode(module: LLVM.LLVMModuleRef, builder: LLVM.LLVMBuilderRef, symbolTable: SymbolTable): LLVM.LLVMValueRef {
        return LLVM.LLVMConstInt(LLVM.LLVMInt1Type(), if (value) 1 else 0, 0)
    }

    override fun getType(functions: Map<String, Type.Fn>, variables: Map<String, Type>): Type = Type.Bool
    override fun validate(functions: Map<String, Type.Fn>, variables: Map<String, Type>): Result = Result.Ok
    override fun calculate(functions: Map<String, Fn>, variables: Map<String, TypedValue>): TypedValue.BoolValue = TypedValue.BoolValue(value)

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is Bool) {
            return false
        }
        return value == other.value
    }

    override fun hashCode(): Int = value.hashCode()
    override fun toString(): String = value.toString()
}

class I32(ctx: ParserRuleContext, val value: Int) : Expr(ctx) {
    override fun generateCode(module: LLVM.LLVMModuleRef, builder: LLVM.LLVMBuilderRef, symbolTable: SymbolTable): LLVM.LLVMValueRef {
        return LLVM.LLVMConstInt(LLVM.LLVMInt32Type(), value.toLong(), 1)
    }

    override fun getType(functions: Map<String, Type.Fn>, variables: Map<String, Type>): Type = Type.I32
    override fun validate(functions: Map<String, Type.Fn>, variables: Map<String, Type>): Result = Result.Ok
    override fun calculate(functions: Map<String, Fn>, variables: Map<String, TypedValue>): TypedValue.IntValue = TypedValue.IntValue(value)

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is I32) {
            return false
        }
        return value == other.value
    }

    override fun hashCode(): Int = value
    override fun toString(): String = value.toString()
}
