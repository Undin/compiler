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
    override fun isLValue(): Boolean = false

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
    override fun isLValue(): Boolean = false

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is I32) {
            return false
        }
        return value == other.value
    }

    override fun hashCode(): Int = value
    override fun toString(): String = value.toString()
}

sealed class AggregateLiteral(ctx: ParserRuleContext) : Expr(ctx) {

    var pointer: LLVMValueRef? = null

    class Tuple(ctx: ParserRuleContext, val elements: List<Expr>) : AggregateLiteral(ctx) {
        override fun generateCode(module: LLVMModuleRef, builder: LLVMBuilderRef, symbolTable: SymbolTable<LLVMValueRef>): LLVMValueRef {
            val tupleRef = pointer ?: LLVMBuildAlloca(builder, type.toLLVMType(), "tuple")

            elements.forEachIndexed { i, expr ->
                val elementPtr = LLVMBuildStructGEP(builder, tupleRef, i, "")
                if (expr is AggregateLiteral) {
                    expr.pointer = elementPtr
                    expr.generateCode(module, builder, symbolTable)
                } else {
                    val value = expr.generateCode(module, builder, symbolTable)
                    LLVMBuildStore(builder, value, elementPtr)
                }
            }

            return tupleRef
        }

        override fun validate(functions: Map<String, Type.Fn>, variables: SymbolTable<Type>): Result =
                elements.map { it.validate(functions, variables) }.fold()

        override fun calculate(functions: Map<String, Fn>, variables: Map<String, TypedValue>): TypedValue {
            val values = elements.map { it.calculate(functions, variables) }.toMutableList()
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

    class SeqArray(ctx: ParserRuleContext, val elements: List<Expr>) : AggregateLiteral(ctx) {
        override fun generateCode(module: LLVMModuleRef, builder: LLVMBuilderRef, symbolTable: SymbolTable<LLVMValueRef>): LLVMValueRef {
            val arrayRef = pointer ?: LLVMBuildAlloca(builder, type.toLLVMType(), "seqArray")
            val zero = LLVMConstInt(LLVMInt32Type(), 0L, 1)

            elements.forEachIndexed { i, expr ->
                val index = LLVMConstInt(LLVMInt32Type(), i.toLong(), 1)
                val elementPtr = LLVMBuildInBoundsGEP(builder, arrayRef, PointerPointer(*arrayOf(zero, index)), 2, "");
                if (expr is AggregateLiteral) {
                    expr.pointer = elementPtr
                    expr.generateCode(module, builder, symbolTable)
                } else {
                    val value = expr.generateCode(module, builder, symbolTable)
                    LLVMBuildStore(builder, value, elementPtr)
                }
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
            val types = elements.map { it.determineType(functions, variables) }
            val determinedTypes = types.filter { it.isDetermined() }
            val result = if (determinedTypes.isNotEmpty() && types.any { !it.match(determinedTypes[0]) }) {
                val message = "'${getText()}': all elements must have same type"
                Error(ErrorMessage(TYPE_MISMATCH, message, start(), end()))
            } else {
                Ok
            }
            return elementsResult + result
        }

        override fun calculate(functions: Map<String, Fn>, variables: Map<String, TypedValue>): TypedValue.ArrayValue {
            val elementsValues = elements.map { it.calculate(functions, variables) }.toMutableList()
            return TypedValue.ArrayValue(elementsValues)
        }

        override fun equals(other: Any?): Boolean {
            if (other == null || other !is SeqArray) {
                return false
            }
            return elements == other.elements
        }

        override fun hashCode(): Int = elements.hashCode()
        override fun toString(): String = elements.joinToString(prefix = "[", postfix = "]")
    }

    class RepeatArray(ctx: ParserRuleContext, val elementValue: Expr, val size: Int) : AggregateLiteral(ctx) {
        override fun generateCode(module: LLVMModuleRef, builder: LLVMBuilderRef, symbolTable: SymbolTable<LLVMValueRef>): LLVMValueRef {
            val arrayRef = pointer ?: LLVMBuildAlloca(builder, type.toLLVMType(), "repeatArray")

            val exprValue = elementValue.generateCode(module, builder, symbolTable)
            val value = if (elementValue.type.isPrimitive()) {
                exprValue
            } else {
                LLVMBuildLoad(builder, exprValue, "")
            }
            if (size == 0) {
                return arrayRef
            }

            val zero = LLVMConstInt(LLVMInt32Type(), 0L, 1)
            if (size == 1) {
                val elementRef = LLVMBuildInBoundsGEP(builder, arrayRef, PointerPointer(*arrayOf(zero, zero)), 2, "")
                LLVMBuildStore(builder, value, elementRef)
            } else {
                val currentBlock = LLVMGetInsertBlock(builder)
                val fn = LLVMGetBasicBlockParent(currentBlock)
                val repeatBasicBlock = LLVMAppendBasicBlock(fn, "repeat")
                val nextBasicBlock = LLVMAppendBasicBlock(fn, "next")
                LLVMBuildBr(builder, repeatBasicBlock)

                // fill array
                LLVMPositionBuilderAtEnd(builder, repeatBasicBlock)
                val index = LLVMBuildPhi(builder, LLVMInt32Type(), "i")
                val elementRef = LLVMBuildInBoundsGEP(builder, arrayRef, PointerPointer(*arrayOf(zero, index)), 2, "")
                LLVMBuildStore(builder, value, elementRef)
                val increaseIndex = LLVMBuildAdd(builder, index, LLVMConstInt(LLVMInt32Type(), 1L, 1), "")

                val incomingValues = PointerPointer(*arrayOf(zero, increaseIndex))
                val incomingBlocks = PointerPointer(*arrayOf(currentBlock, repeatBasicBlock))
                LLVMAddIncoming(index, incomingValues, incomingBlocks, 2)

                val sizeValue = LLVMConstInt(LLVMInt32Type(), size.toLong(), 1)
                val cmp = LLVMBuildICmp(builder, LLVMIntSLT, increaseIndex, sizeValue, "")
                LLVMBuildCondBr(builder, cmp, repeatBasicBlock, nextBasicBlock)

                // next code
                LLVMPositionBuilderAtEnd(builder, nextBasicBlock)
            }
            return arrayRef
        }

        override fun validate(functions: Map<String, Type.Fn>, variables: SymbolTable<Type>): Result =
                elementValue.validate(functions, variables)

        override fun calculate(functions: Map<String, Fn>, variables: Map<String, TypedValue>): TypedValue {
            val value = elementValue.calculate(functions, variables)
            return TypedValue.ArrayValue(value, size)
        }

        override fun determineTypeInternal(functions: Map<String, Type.Fn>, variables: SymbolTable<Type>): Type {
            val elementType = elementValue.determineType(functions, variables)
            return Type.Array(elementType, size)
        }

        override fun equals(other: Any?): Boolean {
            if (other == null || other !is RepeatArray) {
                return false
            }
            return size == other.size && elementValue == other.elementValue
        }

        override fun hashCode(): Int {
            var result = elementValue.hashCode()
            result += 31 * result + size
            return result
        }

        override fun toString(): String = "[$elementValue; $size]"
    }

    override fun isLValue(): Boolean = false
}
