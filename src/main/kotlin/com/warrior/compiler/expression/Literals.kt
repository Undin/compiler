package com.warrior.compiler.expression

import com.warrior.compiler.SymbolTable
import com.warrior.compiler.Type
import com.warrior.compiler.Type.*
import com.warrior.compiler.validation.*
import com.warrior.compiler.validation.ErrorType.TYPE_MISMATCH
import com.warrior.compiler.validation.Fn
import com.warrior.compiler.validation.Result.Error
import com.warrior.compiler.validation.Result.Ok
import org.antlr.v4.runtime.ParserRuleContext
import org.bytedeco.javacpp.LLVM.*
import org.bytedeco.javacpp.PointerPointer

/**
 * Created by warrior on 09.03.16.
 */
class Bool(ctx: ParserRuleContext, val value: Boolean) : Expr(ctx) {
    override fun generateCode(module: LLVMModuleRef, builder: LLVMBuilderRef,
                              symbolTable: SymbolTable<LLVMValueRef>): LLVMValueRef {
        return LLVMConstInt(LLVMInt1Type(), if (value) 1 else 0, 0)
    }

    override fun determineTypeInternal(functions: Map<String, Type.Fn>, variables: SymbolTable<Type>): Type = Bool
    override fun validate(functions: Map<String, Type.Fn>, variables: SymbolTable<Type>): Result = Ok
    override fun calculate(functions: Map<String, Fn>, variables: Map<String, TypedValue>): TypedValue.BoolValue = TypedValue.BoolValue(value)

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is Bool) {
            return false
        }
        return value == other.value
    }

    override fun hashCode(): Int = value.hashCode()
    override fun toString(): String = value.toString()
}

class I32(ctx: ParserRuleContext, val value: Int) : Expr(ctx) {
    override fun generateCode(module: LLVMModuleRef, builder: LLVMBuilderRef,
                              symbolTable: SymbolTable<LLVMValueRef>): LLVMValueRef {
        return LLVMConstInt(LLVMInt32Type(), value.toLong(), 1)
    }

    override fun determineTypeInternal(functions: Map<String, Type.Fn>, variables: SymbolTable<Type>): Type = I32
    override fun validate(functions: Map<String, Type.Fn>, variables: SymbolTable<Type>): Result = Ok
    override fun calculate(functions: Map<String, Fn>, variables: Map<String, TypedValue>): TypedValue.IntValue = TypedValue.IntValue(value)

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is I32) {
            return false
        }
        return value == other.value
    }

    override fun hashCode(): Int = value
    override fun toString(): String = value.toString()
}

class Tuple(ctx: ParserRuleContext, val elements: List<Expr>) : Expr(ctx) {
    override fun generateCode(module: LLVMModuleRef, builder: LLVMBuilderRef, symbolTable: SymbolTable<LLVMValueRef>): LLVMValueRef {
        val values = elements.map { it.generateCode(module, builder, symbolTable) }
        val tupleRef = LLVMBuildAlloca(builder, type.toLLVMType(), "tuple")

        values.forEachIndexed { i, value ->
            val elementPtr = LLVMBuildStructGEP(builder, tupleRef, i, "e$i")
            LLVMBuildStore(builder, value, elementPtr)
        }

        return tupleRef
    }

    override fun validate(functions: Map<String, Type.Fn>, variables: SymbolTable<Type>): Result =
            elements.map { it.validate(functions, variables) }.fold()

    override fun calculate(functions: Map<String, Fn>, variables: Map<String, TypedValue>): TypedValue {
        val values = elements.map { it.calculate(functions, variables) }
        return TypedValue.TupleValue(values)
    }

    override fun determineTypeInternal(functions: Map<String, Type.Fn>, variables: SymbolTable<Type>): Type {
        val types = elements.map { it.determineType(functions, variables) }
        return Tuple(types)
    }

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is Tuple) {
            return false
        }
        return elements == other.elements
    }

    override fun hashCode(): Int = elements.hashCode()
    override fun toString(): String = elements.joinToString(prefix = "(", postfix = ")")
}

class Array(ctx: ParserRuleContext, val elements: List<Expr>) : Expr(ctx) {
    override fun generateCode(module: LLVMModuleRef, builder: LLVMBuilderRef, symbolTable: SymbolTable<LLVMValueRef>): LLVMValueRef {
        val values = elements.map { it.generateCode(module, builder, symbolTable) }
        val type = LLVMArrayType(LLVMTypeOf(values[0]), values.size)
        val arrayRef = LLVMBuildArrayAlloca(builder, type, null, "array")
        val zero = LLVMConstInt(LLVMInt32Type(), 0L, 1)

        values.forEachIndexed { i, value ->
            val index = LLVMConstInt(LLVMInt32Type(), i.toLong(), 1)
            val elementPtr = LLVMBuildInBoundsGEP(builder, arrayRef, PointerPointer(*arrayOf(zero, index)), 2, "e$i");
            LLVMBuildStore(builder, value, elementPtr)
        }

        return arrayRef
    }

    override fun determineTypeInternal(functions: Map<String, Type.Fn>, variables: SymbolTable<Type>): Type {
        val types = elements.map { it.determineType(functions, variables) }
        val t = types.firstOrNull { it != Unknown } ?: return Unknown
        if (types.any { it != t }) {
            return Unknown
        }
        return Type.Array(t, elements.size)
    }

    override fun validate(functions: Map<String, Type.Fn>, variables: SymbolTable<Type>): Result {
        val elementsResult = elements
                .map { it.validate(functions, variables) }
                .fold()
        val knownTypes = elements.map { it.determineType(functions, variables) }
                .filter { it != Unknown }
        val result = if (knownTypes.isNotEmpty() && knownTypes.any { it != knownTypes[0] }) {
            val message = "'${getText()}': all elements must have same type"
            Error(ErrorMessage(TYPE_MISMATCH, message, start(), end()))
        } else {
            Ok
        }
        return elementsResult + result
    }

    override fun calculate(functions: Map<String, Fn>, variables: Map<String, TypedValue>): TypedValue.ArrayValue {
        val elementsValues = elements.map { it.calculate(functions, variables) }
        return TypedValue.ArrayValue(elementsValues)
    }

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is Array) {
            return false
        }
        return elements == other.elements
    }

    override fun hashCode(): Int = elements.hashCode()
    override fun toString(): String = elements.joinToString(prefix = "[", postfix = "]")
}
