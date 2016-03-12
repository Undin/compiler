package com.warrior.compiler

import org.bytedeco.javacpp.LLVM

/**
 * Created by warrior on 07.03.16.
 */
enum class Type {
    BOOL {
        override fun getLLVMType(): LLVM.LLVMTypeRef = LLVM.LLVMInt1Type()
    },
    I32 {
        override fun getLLVMType(): LLVM.LLVMTypeRef = LLVM.LLVMInt32Type();
    };

    abstract fun getLLVMType(): LLVM.LLVMTypeRef
}

fun String.toType(): Type {
    return when (this) {
        "bool" -> Type.BOOL
        "i32" -> Type.I32
        else -> throw IllegalArgumentException("string is not type")
    }
}
