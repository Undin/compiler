package com.warrior.compiler

import org.bytedeco.javacpp.LLVM

/**
 * Created by warrior on 09.03.16.
 */
class Bool(val value: Boolean) : BoolExpr {
    override fun generateCode(module: LLVM.LLVMModuleRef, builder: LLVM.LLVMBuilderRef, symbolTable: SymbolTable): LLVM.LLVMValueRef {
        return LLVM.LLVMConstInt(LLVM.LLVMInt1Type(), if (value) 1 else 0, 0)
    }
}
class I32(val value: Int) : IntExpr {
    override fun generateCode(module: LLVM.LLVMModuleRef, builder: LLVM.LLVMBuilderRef, symbolTable: SymbolTable): LLVM.LLVMValueRef {
        return LLVM.LLVMConstInt(LLVM.LLVMInt32Type(), value.toLong(), 1)
    }
}
