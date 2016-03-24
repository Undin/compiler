package com.warrior.compiler

import org.bytedeco.javacpp.LLVM
import org.bytedeco.javacpp.PointerPointer

/**
 * Created by warrior on 07.03.16.
 */
sealed class Type {
    object Unknown : Type() {
        override fun toLLVMType(): LLVM.LLVMTypeRef {
            throw UnsupportedOperationException()
        }
    }

    object Bool : Type() {
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

    fun match(expectedType: Type): Boolean = this == expectedType || this == Unknown

    override fun toString(): String = when (this) {
        Unknown -> "unknown"
        Bool -> "bool"
        I32 -> "i32"
        is Type.Fn -> argsTypes.joinToString(prefix = "(", postfix = ") -> $returnType")
    }

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is Type) {
            return false
        }
        return when (other) {
            is Unknown -> this is Unknown
            is Bool -> this is Bool
            is I32 -> this is I32
            is Fn -> this is Fn && argsTypes == other.argsTypes && returnType == other.returnType
            else -> false
        }
    }
}

fun String.toType(): Type {
    return when (this) {
        "bool" -> Type.Bool
        "i32" -> Type.I32
        else -> throw IllegalArgumentException("$this is not type")
    }
}
