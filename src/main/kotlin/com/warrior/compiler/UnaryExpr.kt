package com.warrior.compiler

import org.bytedeco.javacpp.LLVM

/**
 * Created by warrior on 09.03.16.
 */

class Not(val expr: Expr) : BoolExpr {
    override fun generateCode(module: LLVM.LLVMModuleRef, builder: LLVM.LLVMBuilderRef): LLVM.LLVMValueRef {
        val exprValue = expr.generateCode(module, builder)
        return LLVM.LLVMBuildNot(builder, exprValue, "not")
    }
}

class UnaryMinus(val expr: Expr) : IntExpr {
    override fun generateCode(module: LLVM.LLVMModuleRef, builder: LLVM.LLVMBuilderRef): LLVM.LLVMValueRef {
        val exprValue = expr.generateCode(module, builder)
        return LLVM.LLVMBuildNeg(builder, exprValue, "unaryMinus")
    }
}