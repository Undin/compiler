package com.warrior.compiler

import org.antlr.v4.runtime.ParserRuleContext
import org.bytedeco.javacpp.LLVM
import org.bytedeco.javacpp.PointerPointer

/**
 * Created by warrior on 07.03.16.
 */
sealed class Type() : ASTNode(emptyContext) {

    companion object {
        val emptyContext = ParserRuleContext()
    }

    object Unknown : Type() {
        override fun toLLVMType(): LLVM.LLVMTypeRef = throw UnsupportedOperationException()
    }

    object Bool : Type() {
        override fun toLLVMType(): LLVM.LLVMTypeRef = LLVM.LLVMInt1Type()
    }

    object I32 : Type() {
        override fun toLLVMType(): LLVM.LLVMTypeRef = LLVM.LLVMInt32Type();
    }

    class Tuple(val elementsTypes: List<Type>) : Type() {
        override fun toLLVMType(): LLVM.LLVMTypeRef {
            val llvmTypes = elementsTypes.map { it.toLLVMType() }.toTypedArray()
            return LLVM.LLVMStructType(PointerPointer(*llvmTypes), llvmTypes.size, 0)
        }
    }

    class Array(val elementType: Type, val size: Int) : Type() {
        override fun toLLVMType(): LLVM.LLVMTypeRef = LLVM.LLVMArrayType(elementType.toLLVMType(), size)
    }

    class Fn(val argsTypes: List<Type>, val returnType: Type) : Type() {
        override fun toLLVMType(): LLVM.LLVMTypeRef {
            val llvmArgsTypes = argsTypes.map { it.toLLVMType() }.toTypedArray()
            return LLVM.LLVMFunctionType(returnType.toLLVMType(), PointerPointer(*llvmArgsTypes), argsTypes.size, 0)
        }
    }

    abstract fun toLLVMType(): LLVM.LLVMTypeRef

    fun isDetermined(): Boolean = when (this) {
        Unknown -> false
        Bool -> true
        I32 -> true
        is Tuple -> elementsTypes.all { it.isDetermined() }
        is Array -> elementType.isDetermined()
        is Fn -> true
    }

    fun match(expectedType: Type): Boolean {
        if (this == expectedType || this == Unknown) {
            return true;
        }
        return when (this) {
            is Tuple -> expectedType is Tuple && elementsTypes
                    .mapIndexed { i, type -> type.match(expectedType.elementsTypes[i]) }
                    .all { it }
            is Array -> expectedType is Array &&
                    size == expectedType.size &&
                    elementType.match(expectedType.elementType)
            else -> false
        }
    }

    fun isPrimitive(): Boolean = when (this) {
        Unknown -> throw UnsupportedOperationException()
        Bool -> true
        I32 -> true
        is Tuple -> false
        is Array -> false
        is Fn -> false
    }

    override fun toString(): String = when (this) {
        Unknown -> "unknown"
        Bool -> "bool"
        I32 -> "i32"
        is Tuple -> elementsTypes.joinToString(prefix = "(", postfix = ")")
        is Array -> "[$elementType; $size]"
        is Fn -> argsTypes.joinToString(prefix = "(", postfix = ") -> $returnType")
    }

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is Type) {
            return false
        }
        return when (other) {
            is Unknown -> this is Unknown
            is Bool -> this is Bool
            is I32 -> this is I32
            is Tuple -> this is Tuple && elementsTypes == other.elementsTypes
            is Array -> this is Array && size == other.size && elementType == other.elementType
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
