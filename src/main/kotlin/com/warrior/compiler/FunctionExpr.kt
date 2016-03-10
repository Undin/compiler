package com.warrior.compiler

import org.bytedeco.javacpp.LLVM

/**
 * Created by warrior on 10.03.16.
 */
class FunctionExpr(val prototype: PrototypeExpr, val expr: Expr) : Expr {
    override fun getType(): Type {
        throw UnsupportedOperationException()
    }

    override fun generateCode(module: LLVM.LLVMModuleRef, builder: LLVM.LLVMBuilderRef): LLVM.LLVMValueRef {
        val fn = prototype.generateCode(module, builder)
        // create basic block
        val entry = LLVM.LLVMAppendBasicBlock(fn, "entry")
        LLVM.LLVMPositionBuilderAtEnd(builder, entry)
        // create return expression
        val retValue = expr.generateCode(module, builder)
        LLVM.LLVMBuildRet(builder, retValue)
        // verify function
        LLVM.LLVMVerifyFunction(fn, LLVM.LLVMAbortProcessAction)
        return fn
    }
}