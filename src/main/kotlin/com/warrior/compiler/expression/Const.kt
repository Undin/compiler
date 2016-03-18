package com.warrior.compiler.expression

import com.warrior.compiler.SymbolTable
import com.warrior.compiler.TypedValue
import org.bytedeco.javacpp.LLVM

/**
 * Created by warrior on 09.03.16.
 */
class Bool(val value: Boolean) : BoolExpr {
    override fun generateCode(module: LLVM.LLVMModuleRef, builder: LLVM.LLVMBuilderRef, symbolTable: SymbolTable): LLVM.LLVMValueRef {
        return LLVM.LLVMConstInt(LLVM.LLVMInt1Type(), if (value) 1 else 0, 0)
    }

    override fun calculate(env: Map<String, TypedValue>): TypedValue.BoolValue = TypedValue.BoolValue(value)

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is Bool) {
            return false
        }
        return value == other.value
    }

    override fun hashCode(): Int = value.hashCode()
    override fun toString(): String = value.toString()
}

class I32(val value: Int) : IntExpr {
    override fun generateCode(module: LLVM.LLVMModuleRef, builder: LLVM.LLVMBuilderRef, symbolTable: SymbolTable): LLVM.LLVMValueRef {
        return LLVM.LLVMConstInt(LLVM.LLVMInt32Type(), value.toLong(), 1)
    }

    override fun calculate(env: Map<String, TypedValue>): TypedValue.IntValue = TypedValue.IntValue(value)
    override fun equals(other: Any?): Boolean {
        if (other == null || other !is I32) {
            return false
        }
        return value == other.value
    }

    override fun hashCode(): Int = value
    override fun toString(): String = value.toString()
}
