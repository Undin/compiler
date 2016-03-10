package com.warrior.compiler

import org.bytedeco.javacpp.LLVM
import org.bytedeco.javacpp.PointerPointer

/**
 * Created by warrior on 09.03.16.
 */
class PrototypeExpr(val name: String, val args: List<Arg>, val returnType: Type) : Expr {
    data class Arg(val name: String, val type: Type)

    override fun getType(): Type {
        throw UnsupportedOperationException()
    }

    override fun generateCode(module: LLVM.LLVMModuleRef, builder: LLVM.LLVMBuilderRef): LLVM.LLVMValueRef {
        val argsTypes = args.map { it.type.getLLVMType() }.toTypedArray()
        val fnType = LLVM.LLVMFunctionType(returnType.getLLVMType(), PointerPointer(*argsTypes), argsTypes.size, 0)
        val fn = LLVM.LLVMAddFunction(module, name, fnType)
        LLVM.LLVMSetFunctionCallConv(fn, LLVM.LLVMCCallConv)
        return fn
    }
}