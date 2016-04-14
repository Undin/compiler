package com.warrior.compiler.expression

import com.warrior.compiler.SymbolTable
import com.warrior.compiler.Type
import com.warrior.compiler.validation.*
import com.warrior.compiler.validation.ErrorType.UNDECLARED_VARIABLE
import org.antlr.v4.runtime.ParserRuleContext
import org.bytedeco.javacpp.LLVM.*
import org.bytedeco.javacpp.PointerPointer

/**
 * Created by warrior on 10.03.16.
 */
sealed class MemoryExpr(ctx: ParserRuleContext) : Expr(ctx) {

    class Variable(ctx: ParserRuleContext, val name: String) : MemoryExpr(ctx) {
        override fun generateCode(module: LLVMModuleRef, builder: LLVMBuilderRef,
                                  symbolTable: SymbolTable<LLVMValueRef>): LLVMValueRef {
            val variableRef = symbolTable[name] ?: throw IllegalStateException("variable '$name' isn't declared")
            return returnValue(builder, variableRef)
        }

        override fun determineTypeInternal(functions: Map<String, Type.Fn>, variables: SymbolTable<Type>): Type {
            return variables[name] ?: Type.Unknown
        }

        override fun validate(functions: Map<String, Type.Fn>, variables: SymbolTable<Type>): Result {
            return if (name in variables) {
                Result.Ok
            } else {
                val message = "variable '$name' is not declared"
                Result.Error(ErrorMessage(UNDECLARED_VARIABLE, message, start(), end()))
            }
        }

        override fun calculate(functions: Map<String, Fn>, variables: Map<String, TypedValue>): TypedValue {
            return variables[name] ?: throw IllegalStateException("variable '$name' isn't declared")
        }
    }

    class TupleElement(ctx: ParserRuleContext, val tupleExpr: Expr, val index: Int) : MemoryExpr(ctx) {
        override fun generateCode(module: LLVMModuleRef, builder: LLVMBuilderRef, symbolTable: SymbolTable<LLVMValueRef>): LLVMValueRef {
            val tupleValue = tupleExpr.generateCode(module, builder, symbolTable)
            val valuePtr = LLVMBuildStructGEP(builder, tupleValue, index, "")
            return returnValue(builder, valuePtr)
        }

        override fun validate(functions: Map<String, Type.Fn>, variables: SymbolTable<Type>): Result {
            var result = tupleExpr.validate(functions, variables)

            val type = tupleExpr.determineType(functions, variables)
            if (!(type == Type.Unknown || type is Type.Tuple)) {
                val message = "'${getText()}': '${tupleExpr.getText()}' must be tuple"
                result += Result.Error(ErrorMessage(ErrorType.TYPE_MISMATCH, message, tupleExpr.start(), tupleExpr.end()))
            }

            if (type is Type.Tuple && index >= type.elementsTypes.size) {
                val message = "'${getText()}': attempted out-of-bounds tuple index '$index' on type '$type'"
                result += Result.Error(ErrorMessage(ErrorType.INDEX_OUT_OF_RANGE, message, start(), end()))
            }

            return result
        }

        override fun determineTypeInternal(functions: Map<String, Type.Fn>, variables: SymbolTable<Type>): Type {
            val tupleType = tupleExpr.determineType(functions, variables)
            if (tupleType !is Type.Tuple || index >= tupleType.elementsTypes.size) {
                return Type.Unknown
            }
            return tupleType.elementsTypes[index]
        }

        override fun calculate(functions: Map<String, Fn>, variables: Map<String, TypedValue>): TypedValue {
            val tupleValue = tupleExpr.calculate(functions, variables)
            if (tupleValue !is TypedValue.TupleValue) {
                throw IllegalStateException("'$tupleExpr' must be tuple")
            }
            return tupleValue[index]
        }
    }

    class ArrayElement(ctx: ParserRuleContext, val arrayExpr: Expr, val indexExpr: Expr) : MemoryExpr(ctx) {
        override fun generateCode(module: LLVMModuleRef, builder: LLVMBuilderRef, symbolTable: SymbolTable<LLVMValueRef>): LLVMValueRef {
            val arrayValue = arrayExpr.generateCode(module, builder, symbolTable)
            val indexValue = indexExpr.generateCode(module, builder, symbolTable)
            val zero = LLVMConstInt(LLVMInt32Type(), 0L, 1)
            val valuePtr = LLVMBuildInBoundsGEP(builder, arrayValue, PointerPointer(*arrayOf(zero, indexValue)), 2, "")
            return returnValue(builder, valuePtr)
        }

        override fun validate(functions: Map<String, Type.Fn>, variables: SymbolTable<Type>): Result {
            var result = arrayExpr.validate(functions, variables)
            result += indexExpr.validate(functions, variables)

            val arrayType = arrayExpr.determineType(functions, variables)
            if (!(arrayType == Type.Unknown || arrayType is Type.Array)) {
                val message = "'${getText()}': '${arrayExpr.getText()}' must be array"
                result += Result.Error(ErrorMessage(ErrorType.TYPE_MISMATCH, message, arrayExpr.start(), arrayExpr.end()))
            }

            val indexType = indexExpr.determineType(functions, variables)
            if (!indexType.match(Type.I32)) {
                val message = "'${getText()}': array index '${indexType.getText()}' must have 'i32' type"
                result += Result.Error(ErrorMessage(ErrorType.TYPE_MISMATCH, message, indexExpr.start(), indexExpr.end()))
            }

            return result
        }

        override fun determineTypeInternal(functions: Map<String, Type.Fn>, variables: SymbolTable<Type>): Type {
            val arrayType = arrayExpr.determineType(functions, variables)
            if (arrayType !is Type.Array) {
                return Type.Unknown
            }
            return arrayType.elementType
        }

        override fun calculate(functions: Map<String, Fn>, variables: Map<String, TypedValue>): TypedValue {
            val arrayValue = arrayExpr.calculate(functions, variables) as TypedValue.ArrayValue
            val indexValue = indexExpr.calculate(functions, variables) as TypedValue.IntValue
            return arrayValue[indexValue]
        }
    }

    override fun isConstant(): Boolean = false

    protected fun returnValue(builder: LLVMBuilderRef, exprValue: LLVMValueRef): LLVMValueRef {
        return if (type.isPrimitive()) {
            LLVMBuildLoad(builder, exprValue, "")
        } else {
            exprValue
        }
    }

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is MemoryExpr) {
            return false
        }
        return when (other) {
            is Variable -> this is Variable && name == other.name
            is TupleElement -> this is TupleElement && tupleExpr == other.tupleExpr && index == other.index
            is ArrayElement -> this is ArrayElement && arrayExpr == other.arrayExpr && indexExpr == other.indexExpr
            else -> false
        }
    }

    override fun hashCode(): Int {
        return when (this) {
            is Variable -> name.hashCode()
            is TupleElement -> 31 * tupleExpr.hashCode() + index.hashCode()
            is ArrayElement -> 31 * arrayExpr.hashCode() + indexExpr.hashCode()
        }
    }

    override fun toString(): String = when (this) {
        is Variable -> name
        is TupleElement -> "$tupleExpr.$index"
        is ArrayElement -> "$arrayExpr[$indexExpr]"
    }
}

