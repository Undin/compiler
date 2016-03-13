package com.warrior.compiler

import org.bytedeco.javacpp.LLVM
import org.bytedeco.javacpp.PointerPointer

/**
 * Created by warrior on 07.03.16.
 */
sealed class Type {
    object BOOL : Type() {
        override fun toLLVMType(): LLVM.LLVMTypeRef = LLVM.LLVMInt1Type()
    }
    object I32 : Type() {
        override fun toLLVMType(): LLVM.LLVMTypeRef = LLVM.LLVMInt32Type();
    }
    class Fn(val argsTypes: List<Type>, val returnType: Type) : Type() {
        override fun toLLVMType(): LLVM.LLVMTypeRef {
            val llvmArgsTypes = argsTypes.map { it.toLLVMType() }.toTypedArray()
            return LLVM.LLVMFunctionType(returnType.toLLVMType(), PointerPointer(*llvmArgsTypes), argsTypes.size, 0)
        }
    }

    abstract fun toLLVMType(): LLVM.LLVMTypeRef
}

fun String.toType(): Type {
    return when (this) {
        "bool" -> Type.BOOL
        "i32" -> Type.I32
        else -> throw IllegalArgumentException("string is not type")
    }
}
