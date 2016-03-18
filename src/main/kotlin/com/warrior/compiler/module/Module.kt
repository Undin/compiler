package com.warrior.compiler.module

import com.warrior.compiler.ASTNode
import org.bytedeco.javacpp.LLVM

/**
 * Created by warrior on 19.03.16.
 */
class Module(val functions: List<Function>) : ASTNode {

    fun generateCode(module: LLVM.LLVMModuleRef, builder: LLVM.LLVMBuilderRef) {
        functions.forEach { it.prototype.generateCode(module) }
        functions.forEach { it.generateCode(module, builder) }
    }
}
