package com.warrior.compiler

import org.bytedeco.javacpp.LLVM
import org.bytedeco.javacpp.Pointer

/**
 * Created by warrior on 07.03.16.
 */
interface ASTNode<out V : Pointer> {
    fun generateCode(module: LLVM.LLVMModuleRef, builder: LLVM.LLVMBuilderRef, symbolTable: SymbolTable): V

//    fun validate(): Unit
}
