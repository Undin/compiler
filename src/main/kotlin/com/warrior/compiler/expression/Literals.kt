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
import java.util.*

/**
 * Created by warrior on 09.03.16.
 */
class Bool(ctx: ParserRuleContext, val value: Boolean) : Expr(ctx) {

    init {
        type = Bool
    }

    override fun generateCode(module: LLVMModuleRef, builder: LLVMBuilderRef,
                              symbolTable: SymbolTable<LLVMValueRef>): LLVMValueRef = generateConstValue()
    override fun generateConstValue(): LLVMValueRef = LLVMConstInt(LLVMInt1Type(), if (value) 1 else 0, 0)
    override fun determineTypeInternal(functions: Map<String, Type.Fn>, variables: SymbolTable<Type>): Type = Bool
    override fun validate(functions: Map<String, Type.Fn>, variables: SymbolTable<Type>): Result = Ok
    override fun calculate(functions: Map<String, Fn>, variables: Map<String, TypedValue>): TypedValue.BoolValue = TypedValue.BoolValue(value)
    override fun isConstant(): Boolean = true

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

    init {
        type = I32
    }

    override fun generateCode(module: LLVMModuleRef, builder: LLVMBuilderRef,
                              symbolTable: SymbolTable<LLVMValueRef>): LLVMValueRef = generateConstValue()
    override fun generateConstValue(): LLVMValueRef = LLVMConstInt(LLVMInt32Type(), value.toLong(), 1)
    override fun determineTypeInternal(functions: Map<String, Type.Fn>, variables: SymbolTable<Type>): Type = I32
    override fun validate(functions: Map<String, Type.Fn>, variables: SymbolTable<Type>): Result = Ok
    override fun calculate(functions: Map<String, Fn>, variables: Map<String, TypedValue>): TypedValue.IntValue = TypedValue.IntValue(value)
    override fun isConstant(): Boolean = true

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

        override fun generateConstValue(): LLVMValueRef {
            val values = elements.map { it.generateConstValue() }.toTypedArray()
            return LLVMConstStruct(PointerPointer(*values), values.size, 0)
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

        override fun isConstant(): Boolean = elements.all { it.isConstant() }

        override fun propagateType(type: Type) {
            val expectedType = Collections.nCopies(elements.size, "_").joinToString(prefix = "(", postfix = ")")
            if (type !is Type.Tuple || type.elementsTypes.size != elements.size) {
                throw IllegalArgumentException("type must be '$expectedType' but found '$type'")
            }
            this.type = type
            for ((i, t) in type.elementsTypes.withIndex()) {
                elements[i].propagateType(t)
            }
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

        override fun generateConstValue(): LLVMValueRef {
            val values = elements.map { it.generateConstValue() }.toTypedArray()
            val elementType = (type as Type.Array).elementType.toLLVMType()
            return LLVMConstArray(elementType, PointerPointer(*values), values.size)
        }

        override fun determineTypeInternal(functions: Map<String, Type.Fn>, variables: SymbolTable<Type>): Type {
            val types = elements.map { it.determineType(functions, variables) }
            val determinedTypes = types.filter { it.isDetermined() }
            return if (determinedTypes.isEmpty() || determinedTypes.size != types.size || types.any { !it.match(determinedTypes[0]) }) {
                Type.Array(Unknown, elements.size)
            } else {
                Type.Array(types[0], elements.size)
            }
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

        override fun isConstant(): Boolean = elements.all { it.isConstant() }

        override fun propagateType(type: Type) {
            val expectedType = "[_; ${elements.size}]"
            if (type !is Type.Array || type.size != elements.size) {
                throw IllegalArgumentException("type must be '$expectedType' but found '$type'")
            }
            this.type = type
            for (e in elements) {
                e.propagateType(type.elementType)
            }
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

        override fun generateConstValue(): LLVMValueRef {
            val values = Array(size) { elementValue.generateConstValue() }
            val elementType = (type as Type.Array).elementType.toLLVMType()
            return LLVMConstArray(elementType, PointerPointer(*values), values.size)
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

        override fun isConstant(): Boolean = elementValue.isConstant()

        override fun propagateType(type: Type) {
            val expectedType = "[_; $size]"
            if (type !is Type.Array || type.size != size) {
                throw IllegalArgumentException("type must be '$expectedType' but found '$type'")
            }
            this.type = type
            elementValue.propagateType(type.elementType)
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
}
