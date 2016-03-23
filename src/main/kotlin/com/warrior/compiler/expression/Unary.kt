package com.warrior.compiler.expression

import com.warrior.compiler.*
import com.warrior.compiler.validation.ErrorType.*
import com.warrior.compiler.validation.ErrorMessage
import com.warrior.compiler.validation.Fn
import com.warrior.compiler.validation.Result
import com.warrior.compiler.validation.TypedValue
import org.antlr.v4.runtime.ParserRuleContext
import org.bytedeco.javacpp.LLVM

/**
 * Created by warrior on 09.03.16.
 */

class Not(ctx: ParserRuleContext, val expr: Expr) : Expr(ctx) {
    override fun generateCode(module: LLVM.LLVMModuleRef, builder: LLVM.LLVMBuilderRef, symbolTable: SymbolTable): LLVM.LLVMValueRef {
        val exprValue = expr.generateCode(module, builder, symbolTable)
        return LLVM.LLVMBuildNot(builder, exprValue, "not")
    }

    override fun getType(functions: Map<String, Type.Fn>, variables: Map<String, Type>): Type = Type.Bool

    override fun validate(functions: Map<String, Type.Fn>, variables: Map<String, Type>): Result {
        val exprResult = expr.validate(functions, variables)
        val exprType = expr.getType(functions, variables)
        val result = if (exprType != Type.Bool && exprType != Type.Unknown) {
            val message = "expression '${expr.getText()}' must have 'bool' type"
            Result.Error(ErrorMessage(TYPE_MISMATCH, message, expr.start(), expr.end()))
        } else {
            Result.Ok
        }
        return exprResult + result;
    }

    override fun calculate(functions: Map<String, Fn>, variables: Map<String, TypedValue>): TypedValue.BoolValue {
        val exprValue = expr.calculate(functions, variables)
        if (exprValue is TypedValue.BoolValue) {
            return TypedValue.BoolValue(!exprValue.value)
        } else {
            throw IllegalStateException("Expression must have bool type")
        }
    }

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is Not) {
            return false
        }
        return expr == other.expr
    }

    override fun hashCode(): Int = expr.hashCode()
    override fun toString(): String = "!($expr)"
}

class UnaryMinus(ctx: ParserRuleContext, val expr: Expr) : Expr(ctx) {
    override fun generateCode(module: LLVM.LLVMModuleRef, builder: LLVM.LLVMBuilderRef, symbolTable: SymbolTable): LLVM.LLVMValueRef {
        val exprValue = expr.generateCode(module, builder, symbolTable)
        return LLVM.LLVMBuildNeg(builder, exprValue, "unaryMinus")
    }

    override fun getType(functions: Map<String, Type.Fn>, variables: Map<String, Type>): Type = Type.I32

    override fun validate(functions: Map<String, Type.Fn>, variables: Map<String, Type>): Result {
        val exprResult = expr.validate(functions, variables)
        val exprType = expr.getType(functions, variables)
        val result = if (exprType != Type.I32 && exprType != Type.Unknown) {
            val message = "expression '${expr.getText()}' must have 'i32' type"
            Result.Error(ErrorMessage(TYPE_MISMATCH, message, expr.start(), expr.end()))
        } else {
            Result.Ok
        }
        return exprResult + result;
    }

    override fun calculate(functions: Map<String, Fn>, variables: Map<String, TypedValue>): TypedValue.IntValue {
        val exprValue = expr.calculate(functions, variables)
        if (exprValue is TypedValue.IntValue) {
            return TypedValue.IntValue(-exprValue.value)
        } else {
            throw IllegalStateException("Expression must have int type")
        }
    }

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is UnaryMinus) {
            return false
        }
        return expr == other.expr
    }

    override fun hashCode(): Int = expr.hashCode()
    override fun toString(): String = "-($expr)"
}
