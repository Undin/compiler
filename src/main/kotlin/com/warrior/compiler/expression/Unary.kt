package com.warrior.compiler.expression

import com.warrior.compiler.Fn
import com.warrior.compiler.SymbolTable
import com.warrior.compiler.TypedValue
import org.bytedeco.javacpp.LLVM

/**
 * Created by warrior on 09.03.16.
 */

class Not(val expr: Expr) : BoolExpr {
    override fun generateCode(module: LLVM.LLVMModuleRef, builder: LLVM.LLVMBuilderRef, symbolTable: SymbolTable): LLVM.LLVMValueRef {
        val exprValue = expr.generateCode(module, builder, symbolTable)
        return LLVM.LLVMBuildNot(builder, exprValue, "not")
    }

    override fun calculate(env: Map<String, TypedValue>, functions: Map<String, Fn>): TypedValue.BoolValue {
        val exprValue = expr.calculate(env, functions)
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

class UnaryMinus(val expr: Expr) : IntExpr {
    override fun generateCode(module: LLVM.LLVMModuleRef, builder: LLVM.LLVMBuilderRef, symbolTable: SymbolTable): LLVM.LLVMValueRef {
        val exprValue = expr.generateCode(module, builder, symbolTable)
        return LLVM.LLVMBuildNeg(builder, exprValue, "unaryMinus")
    }

    override fun calculate(env: Map<String, TypedValue>, functions: Map<String, Fn>): TypedValue.IntValue {
        val exprValue = expr.calculate(env, functions)
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
