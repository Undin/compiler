package com.warrior.compiler.expression

import com.warrior.compiler.Fn
import com.warrior.compiler.SymbolTable
import com.warrior.compiler.TypedValue
import com.warrior.compiler.expression.Binary.*
import org.bytedeco.javacpp.LLVM

/**
 * Created by warrior on 07.03.16.
 */
sealed class Binary(val opcode: Int, val name: String, val lhs: Expr, val rhs: Expr) : Expr {

    class BinaryBool(val op: Operation, lhs: Expr, rhs: Expr) :
            Binary(op.opcode, op.name.toLowerCase(), lhs, rhs), BoolExpr {
        enum class Operation(val opcode: Int) {
            AND(LLVM.LLVMAnd),
            OR(LLVM.LLVMOr);

            override fun toString(): String = when (this) {
                AND -> "&&"
                OR -> "||"
            }
        }

        override fun calculate(env: Map<String, TypedValue>, functions: Map<String, Fn>): TypedValue.BoolValue {
            val left = lhs.calculate(env, functions)
            val right = rhs.calculate(env, functions)
            if (left is TypedValue.BoolValue && right is TypedValue.BoolValue) {
                return when (op) {
                    Operation.AND -> TypedValue.BoolValue(left.value && right.value)
                    Operation.OR -> TypedValue.BoolValue(left.value || right.value)
                }
            } else {
                throw IllegalStateException("Incompatible types: expressions must have bool type")
            }
        }
    }

    class Arithmetic(val op: Operation, lhs: Expr, rhs: Expr) :
            Binary(op.opcode, op.name.toLowerCase(), lhs, rhs), IntExpr {

        enum class Operation(val opcode: Int) {
            ADD(LLVM.LLVMAdd),
            SUB(LLVM.LLVMSub),
            MUL(LLVM.LLVMMul),
            DIV(LLVM.LLVMSDiv),
            MOD(LLVM.LLVMSRem);

            override fun toString(): String = when (this) {
                ADD -> "+"
                SUB -> "-"
                MUL -> "*"
                DIV -> "/"
                MOD -> "%"
            }
        }

        override fun calculate(env: Map<String, TypedValue>, functions: Map<String, Fn>): TypedValue.IntValue {
            val left = lhs.calculate(env, functions)
            val right = rhs.calculate(env, functions)
            if (left is TypedValue.IntValue && right is TypedValue.IntValue) {
                return when (op) {
                    Operation.ADD -> left + right
                    Operation.SUB -> left - right
                    Operation.MUL -> left * right
                    Operation.DIV -> left / right
                    Operation.MOD -> left % right
                }
            } else {
                throw IllegalStateException("Incompatible types: expressions must have int type")
            }
        }
    }

    class Cmp(val op: Operation, lhs: Expr, rhs: Expr) :
            Binary(op.opcode, op.name.toLowerCase(), lhs, rhs), BoolExpr {

        enum class Operation(val opcode: Int) {
            EQUAL(LLVM.LLVMIntEQ),
            NOT_EQUAL(LLVM.LLVMIntNE),
            LESS(LLVM.LLVMIntSLT),
            LESS_OR_EQUAL(LLVM.LLVMIntSLE),
            GREATER(LLVM.LLVMIntSGT),
            GREATER_OR_EQUAL(LLVM.LLVMIntSGE);

            override fun toString(): String = when (this) {
                EQUAL -> "=="
                NOT_EQUAL -> "!="
                LESS -> "<"
                LESS_OR_EQUAL -> "<="
                GREATER -> ">"
                GREATER_OR_EQUAL -> ">="
            }
        }

        override fun doOperation(builder: LLVM.LLVMBuilderRef, left: LLVM.LLVMValueRef, right: LLVM.LLVMValueRef): LLVM.LLVMValueRef {
            return LLVM.LLVMBuildICmp(builder, opcode, left, right, name)
        }

        override fun calculate(env: Map<String, TypedValue>, functions: Map<String, Fn>): TypedValue.BoolValue {
            val left = lhs.calculate(env, functions)
            val right = rhs.calculate(env, functions)

            if (left is TypedValue.IntValue && right is TypedValue.IntValue) {
                val result = when (op) {
                    Operation.EQUAL -> left == right
                    Operation.NOT_EQUAL -> left != right
                    Operation.LESS -> left < right
                    Operation.LESS_OR_EQUAL -> left <= right
                    Operation.GREATER -> left > right
                    Operation.GREATER_OR_EQUAL -> left >= right
                }
                return TypedValue.BoolValue(result)
            }

            if (left is TypedValue.BoolValue && right is TypedValue.BoolValue) {
                val result = when (op) {
                    Operation.EQUAL -> left == right
                    Operation.NOT_EQUAL -> left != right
                    else -> throw IllegalStateException("Incompatible types: You can not compare bool expressions")
                }
                return TypedValue.BoolValue(result)
            }

            throw IllegalStateException("Incompatible types")
        }
    }

    override fun generateCode(module: LLVM.LLVMModuleRef, builder: LLVM.LLVMBuilderRef, symbolTable: SymbolTable): LLVM.LLVMValueRef {
        val left = lhs.generateCode(module, builder, symbolTable)
        val right = rhs.generateCode(module, builder, symbolTable)
        return doOperation(builder, left, right)
    }

    open protected fun doOperation(builder: LLVM.LLVMBuilderRef, left: LLVM.LLVMValueRef, right: LLVM.LLVMValueRef): LLVM.LLVMValueRef {
        return LLVM.LLVMBuildBinOp(builder, opcode, left, right, name)
    }

    override fun equals(other: Any?): Boolean {
        if (other == null || other.javaClass != javaClass) {
            return false
        }
        other as Binary
        return opcode == other.opcode &&
                lhs == other.lhs &&
                rhs == other.rhs
    }

    override fun hashCode(): Int {
        var result = opcode
        result += 31 * result + name.hashCode()
        result += 31 * result + lhs.hashCode()
        result += 31 * result + rhs.hashCode()
        return result
    }

    override fun toString(): String = when (this) {
        is BinaryBool -> "$lhs $op $rhs"
        is Arithmetic -> "$lhs $op $rhs"
        is Cmp -> "$lhs $op $rhs"
    }
}

fun and(lhs: Expr, rhs: Expr) = BinaryBool(BinaryBool.Operation.AND, lhs, rhs)
fun or(lhs: Expr, rhs: Expr) = BinaryBool(BinaryBool.Operation.OR, lhs, rhs)
fun add(lhs: Expr, rhs: Expr) = Arithmetic(Arithmetic.Operation.ADD, lhs, rhs)
fun sub(lhs: Expr, rhs: Expr) = Arithmetic(Arithmetic.Operation.SUB, lhs, rhs)
fun mul(lhs: Expr, rhs: Expr) = Arithmetic(Arithmetic.Operation.MUL, lhs, rhs)
fun div(lhs: Expr, rhs: Expr) = Arithmetic(Arithmetic.Operation.DIV, lhs, rhs)
fun mod(lhs: Expr, rhs: Expr) = Arithmetic(Arithmetic.Operation.MOD, lhs, rhs)
fun eq(lhs: Expr, rhs: Expr) = Cmp(Cmp.Operation.EQUAL, lhs, rhs)
fun ne(lhs: Expr, rhs: Expr) = Cmp(Cmp.Operation.NOT_EQUAL, lhs, rhs)
fun lt(lhs: Expr, rhs: Expr) = Cmp(Cmp.Operation.LESS, lhs, rhs)
fun le(lhs: Expr, rhs: Expr) = Cmp(Cmp.Operation.LESS_OR_EQUAL, lhs, rhs)
fun gt(lhs: Expr, rhs: Expr) = Cmp(Cmp.Operation.GREATER, lhs, rhs)
fun ge(lhs: Expr, rhs: Expr) = Cmp(Cmp.Operation.GREATER_OR_EQUAL, lhs, rhs)
