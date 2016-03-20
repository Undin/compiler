package com.warrior.compiler.module

import com.warrior.compiler.ASTNode
import org.bytedeco.javacpp.LLVM.*

/**
 * Created by warrior on 19.03.16.
 */
class Module(val functions: List<Function>) : ASTNode {

    fun generateCode(module: LLVMModuleRef, builder: LLVMBuilderRef) {
        functions.forEach { it.prototype.generateCode(module) }
        declarePrintf(module)
        functions.forEach { it.generateCode(module, builder) }
    }

    private fun declarePrintf(module: LLVMModuleRef) {
        val i8Pointer = LLVMPointerType(LLVMInt8Type(), 0);
        val fnType = LLVMFunctionType(LLVMInt32Type(), i8Pointer, 1, 1);
        val fn = LLVMAddFunction(module, "printf", fnType)
        LLVMSetFunctionCallConv(fn, LLVMCCallConv)
    }
}
