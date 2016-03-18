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

sealed class TypedValue {
    class BoolValue(val value: Boolean) : TypedValue()

    class IntValue(val value: Int) : TypedValue(), Comparable<IntValue> {
        operator fun plus(other: IntValue): IntValue = IntValue(value + other.value)
        operator fun minus(other: IntValue): IntValue = IntValue(value - other.value)
        operator fun times(other: IntValue): IntValue = IntValue(value * other.value)
        operator fun div(other: IntValue): IntValue = IntValue(value / other.value)
        operator fun mod(other: IntValue): IntValue = IntValue(value % other.value)

        override operator fun compareTo(other: IntValue): Int = value.compareTo(other.value)
    }

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is TypedValue) {
            return false
        }
        return when (other) {
            is BoolValue -> this is BoolValue && value == other.value
            is IntValue -> this is IntValue && value == other.value
            else -> false
        }
    }

    override fun hashCode(): Int = when (this) {
        is BoolValue -> value.hashCode()
        is IntValue -> value.hashCode()
    }

    override fun toString(): String = when (this) {
        is BoolValue -> value.toString()
        is IntValue -> value.toString()
    }
}
