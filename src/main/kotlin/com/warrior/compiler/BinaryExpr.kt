package com.warrior.compiler

import org.bytedeco.javacpp.LLVM

/**
 * Created by warrior on 07.03.16.
 */
abstract class BinaryExpr(val opcode: Int, val name: String, val lhs: Expr, val rhs: Expr) : Expr {
    override fun generateCode(module: LLVM.LLVMModuleRef, builder: LLVM.LLVMBuilderRef, symbolTable: SymbolTable): LLVM.LLVMValueRef {
        val left = lhs.generateCode(module, builder, symbolTable)
        val right = rhs.generateCode(module, builder, symbolTable)
        return LLVM.LLVMBuildBinOp(builder, opcode, left, right, name)
    }
}

class BinaryBoolExpr(val op: Operation, lhs: Expr, rhs: Expr) :
        BinaryExpr(op.opcode, op.name.toLowerCase(), lhs, rhs), BoolExpr {
    enum class Operation(val opcode: Int) {
        AND(LLVM.LLVMAnd),
        OR(LLVM.LLVMOr)
    }
}

class CmpExpr(val op: Operation, lhs: Expr, rhs: Expr) :
        BinaryExpr(op.opcode, op.name.toLowerCase(), lhs, rhs), BoolExpr {
    enum class Operation(val opcode: Int) {
        EQUAL(LLVM.LLVMIntEQ),
        NOT_EQUAL(LLVM.LLVMIntNE),
        LESS(LLVM.LLVMIntSLT),
        LESS_OR_EQUAL(LLVM.LLVMIntSLE),
        GREATER(LLVM.LLVMIntSGT),
        GREATER_OR_EQUAL(LLVM.LLVMIntSGE)
    }
}

class ArithmeticExpr(val op: Operation, lhs: Expr, rhs: Expr) :
        BinaryExpr(op.opcode, op.name.toLowerCase(), lhs, rhs), IntExpr {
    enum class Operation(val opcode: Int) {
        ADD(LLVM.LLVMAdd),
        SUB(LLVM.LLVMSub),
        MUL(LLVM.LLVMMul),
        DIV(LLVM.LLVMSDiv),
        MOD(LLVM.LLVMSRem)
    }
}
