package com.warrior.compiler.expression

import com.warrior.compiler.SymbolTable
import com.warrior.compiler.Type
import com.warrior.compiler.VariableAttrs
import com.warrior.compiler.expression.Binary.*
import com.warrior.compiler.expression.Binary.Arithmetic.Operation.*
import com.warrior.compiler.expression.Binary.Cmp.Operation.*
import com.warrior.compiler.expression.Binary.Equal.Operation.*
import com.warrior.compiler.expression.Binary.Logic.Operation.*
import com.warrior.compiler.validation.ErrorMessage
import com.warrior.compiler.validation.ErrorType.TYPE_MISMATCH
import com.warrior.compiler.validation.Fn
import com.warrior.compiler.validation.Result
import com.warrior.compiler.validation.Result.Error
import com.warrior.compiler.validation.Result.Ok
import com.warrior.compiler.validation.TypedValue
import org.antlr.v4.runtime.ParserRuleContext
import org.bytedeco.javacpp.LLVM.*
import org.bytedeco.javacpp.PointerPointer
import java.util.*

/**
 * Created by warrior on 07.03.16.
 */
sealed class Binary(ctx: ParserRuleContext, val opcode: Int, val name: String, val lhs: Expr, val rhs: Expr) : Expr(ctx) {

    class Logic(ctx: ParserRuleContext, val op: Operation, lhs: Expr, rhs: Expr) :
            Binary(ctx, op.opcode, op.name.toLowerCase(), lhs, rhs) {

        enum class Operation(val opcode: Int) {
            AND(LLVMAnd),
            OR(LLVMOr);

            override fun toString(): String = when (this) {
                AND -> "&&"
                OR -> "||"
            }
        }

        override fun generateCode(module: LLVMModuleRef, builder: LLVMBuilderRef,
                                  symbolTable: SymbolTable<VariableAttrs>): LLVMValueRef {
            val currentBlock = LLVMGetInsertBlock(builder)
            val fn = LLVMGetBasicBlockParent(currentBlock)

            val rhsBlock = LLVMAppendBasicBlock(fn, "rhs")
            val joinBlock = LLVMAppendBasicBlock(fn, "join")

            val lhsResult = lhs.generateCode(module, builder, symbolTable)

            when (op) {
                // if 'and' and lhsResult == false jump to join block
                AND -> LLVMBuildCondBr(builder, lhsResult, rhsBlock, joinBlock)
                // if 'or' and lhsResult == true jump to join block
                OR -> LLVMBuildCondBr(builder, lhsResult, joinBlock, rhsBlock)
            }

            // calculate rhs and operation result
            LLVMPositionBuilderAtEnd(builder, rhsBlock)
            val rhsResult = rhs.generateCode(module, builder, symbolTable)
            val exprResult = doOperation(builder, lhsResult, rhsResult)
            LLVMBuildBr(builder, joinBlock)

            LLVMPositionBuilderAtEnd(builder, joinBlock)
            // join results
            val phi = LLVMBuildPhi(builder, LLVMInt1Type(), "res")
            val incomingValues = PointerPointer(*arrayOf(lhsResult, exprResult))
            val incomingBlocks = PointerPointer(*arrayOf(currentBlock, rhsBlock))
            LLVMAddIncoming(phi, incomingValues, incomingBlocks, 2)

            return phi
        }

        override fun getType(functions: Map<String, Type.Fn>, variables: SymbolTable<Type>): Type = Type.Bool

        override fun expectedArgType(): Type = Type.Bool

        override fun calculate(functions: Map<String, Fn>, variables: Map<String, TypedValue>): TypedValue.BoolValue {
            val left = lhs.calculate(functions, variables)
            val right = rhs.calculate(functions, variables)
            if (left is TypedValue.BoolValue && right is TypedValue.BoolValue) {
                return when (op) {
                    AND -> TypedValue.BoolValue(left.value && right.value)
                    OR -> TypedValue.BoolValue(left.value || right.value)
                }
            } else {
                throw IllegalStateException("Incompatible types: expressions must have bool type")
            }
        }
    }

    class Arithmetic(ctx: ParserRuleContext, val op: Operation, lhs: Expr, rhs: Expr) :
            Binary(ctx, op.opcode, op.name.toLowerCase(), lhs, rhs) {

        enum class Operation(val opcode: Int) {
            ADD(LLVMAdd),
            SUB(LLVMSub),
            MUL(LLVMMul),
            DIV(LLVMSDiv),
            MOD(LLVMSRem);

            override fun toString(): String = when (this) {
                ADD -> "+"
                SUB -> "-"
                MUL -> "*"
                DIV -> "/"
                MOD -> "%"
            }
        }

        override fun getType(functions: Map<String, Type.Fn>, variables: SymbolTable<Type>): Type = Type.I32

        override fun expectedArgType(): Type = Type.I32

        override fun calculate(functions: Map<String, Fn>, variables: Map<String, TypedValue>): TypedValue.IntValue {
            val left = lhs.calculate(functions, variables)
            val right = rhs.calculate(functions, variables)
            if (left is TypedValue.IntValue && right is TypedValue.IntValue) {
                return when (op) {
                    ADD -> left + right
                    SUB -> left - right
                    MUL -> left * right
                    DIV -> left / right
                    MOD -> left % right
                }
            } else {
                throw IllegalStateException("Incompatible types: expressions must have int type")
            }
        }
    }

    class Equal(ctx: ParserRuleContext, val op: Operation, lhs: Expr, rhs: Expr) :
            Binary(ctx, op.opcode, op.name.toLowerCase(), lhs, rhs) {

        enum class Operation(val opcode: Int) {
            EQUAL(LLVMIntEQ),
            NOT_EQUAL(LLVMIntNE);

            override fun toString(): String = when (this) {
                EQUAL -> "=="
                NOT_EQUAL -> "!="
            }
        }

        override fun doOperation(builder: LLVMBuilderRef, left: LLVMValueRef, right: LLVMValueRef): LLVMValueRef {
            return LLVMBuildICmp(builder, opcode, left, right, name)
        }

        override fun getType(functions: Map<String, Type.Fn>, variables: SymbolTable<Type>): Type = Type.Bool

        override fun doValidate(functions: Map<String, Type.Fn>, variables: SymbolTable<Type>): List<ErrorMessage> {
            val lhsType = lhs.getType(functions, variables)
            val rhsType = rhs.getType(functions, variables)
            if (lhsType != rhsType && lhsType != Type.Unknown && rhsType != Type.Unknown) {
                val message = "'${lhs.getText()}' and '${rhs.getText()}' have different types"
                return listOf(ErrorMessage(TYPE_MISMATCH, message, start(), end()))
            }
            return listOf()
        }

        override fun expectedArgType(): Type {
            throw UnsupportedOperationException()
        }

        override fun calculate(functions: Map<String, Fn>, variables: Map<String, TypedValue>): TypedValue.BoolValue {
            val left = lhs.calculate(functions, variables)
            val right = rhs.calculate(functions, variables)
            if (left.javaClass != right.javaClass) {
                throw IllegalStateException("Incompatible types: you can not compare expression with different types")
            }
            val result = when (op) {
                EQUAL -> left == right
                NOT_EQUAL -> left != right
            }
            return TypedValue.BoolValue(result)
        }
    }

    class Cmp(ctx: ParserRuleContext, val op: Operation, lhs: Expr, rhs: Expr) :
            Binary(ctx, op.opcode, op.name.toLowerCase(), lhs, rhs) {

        enum class Operation(val opcode: Int) {
            LESS(LLVMIntSLT),
            LESS_OR_EQUAL(LLVMIntSLE),
            GREATER(LLVMIntSGT),
            GREATER_OR_EQUAL(LLVMIntSGE);

            override fun toString(): String = when (this) {
                LESS -> "<"
                LESS_OR_EQUAL -> "<="
                GREATER -> ">"
                GREATER_OR_EQUAL -> ">="
            }
        }

        override fun doOperation(builder: LLVMBuilderRef, left: LLVMValueRef, right: LLVMValueRef): LLVMValueRef {
            return LLVMBuildICmp(builder, opcode, left, right, name)
        }

        override fun getType(functions: Map<String, Type.Fn>, variables: SymbolTable<Type>): Type = Type.Bool

        override fun expectedArgType(): Type = Type.I32

        override fun calculate(functions: Map<String, Fn>, variables: Map<String, TypedValue>): TypedValue.BoolValue {
            val left = lhs.calculate(functions, variables)
            val right = rhs.calculate(functions, variables)

            if (left !is TypedValue.IntValue || right !is TypedValue.IntValue) {
                throw IllegalStateException("Incompatible types: You can not compare non i32 expressions")
            }
            val result = when (op) {
                LESS -> left < right
                LESS_OR_EQUAL -> left <= right
                GREATER -> left > right
                GREATER_OR_EQUAL -> left >= right
            }
            return TypedValue.BoolValue(result)
        }
    }

    override fun generateCode(module: LLVMModuleRef, builder: LLVMBuilderRef, symbolTable: SymbolTable<VariableAttrs>): LLVMValueRef {
        val left = lhs.generateCode(module, builder, symbolTable)
        val right = rhs.generateCode(module, builder, symbolTable)
        return doOperation(builder, left, right)
    }

    open protected fun doOperation(builder: LLVMBuilderRef, left: LLVMValueRef, right: LLVMValueRef): LLVMValueRef {
        return LLVMBuildBinOp(builder, opcode, left, right, name)
    }

    override fun validate(functions: Map<String, Type.Fn>, variables: SymbolTable<Type>): Result {
        val lhsResult = lhs.validate(functions, variables)
        val rhsResult = rhs.validate(functions, variables)
        val messages = doValidate(functions, variables)
        val result = if (messages.isEmpty()) {
            Ok
        } else {
            Error(messages)
        }
        return lhsResult + rhsResult + result
    }

    open protected fun doValidate(functions: Map<String, Type.Fn>, variables: SymbolTable<Type>): List<ErrorMessage> {
        val lhsType = lhs.getType(functions, variables)
        val rhsType = rhs.getType(functions, variables)
        val expectedType = expectedArgType()
        val messages = ArrayList<ErrorMessage>()

        if (!lhsType.match(expectedType)) {
            val msg = "expression '${lhs.getText()}' must have $expectedType type"
            messages.add(ErrorMessage(TYPE_MISMATCH, msg, lhs.start(), lhs.end()))
        }
        if (!rhsType.match(expectedType)) {
            val msg = "expression '${rhs.getText()}' must have $expectedType type"
            messages.add(ErrorMessage(TYPE_MISMATCH, msg, rhs.start(), rhs.end()))
        }
        return messages
    }

    protected abstract fun expectedArgType(): Type

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
        is Logic -> "$lhs $op $rhs"
        is Arithmetic -> "$lhs $op $rhs"
        is Equal -> "$lhs $op $rhs"
        is Cmp -> "$lhs $op $rhs"
    }
}

fun and(ctx: ParserRuleContext, lhs: Expr, rhs: Expr) = Logic(ctx, AND, lhs, rhs)
fun or(ctx: ParserRuleContext, lhs: Expr, rhs: Expr) = Logic(ctx, OR, lhs, rhs)
fun add(ctx: ParserRuleContext, lhs: Expr, rhs: Expr) = Arithmetic(ctx, ADD, lhs, rhs)
fun sub(ctx: ParserRuleContext, lhs: Expr, rhs: Expr) = Arithmetic(ctx, SUB, lhs, rhs)
fun mul(ctx: ParserRuleContext, lhs: Expr, rhs: Expr) = Arithmetic(ctx, MUL, lhs, rhs)
fun div(ctx: ParserRuleContext, lhs: Expr, rhs: Expr) = Arithmetic(ctx, DIV, lhs, rhs)
fun mod(ctx: ParserRuleContext, lhs: Expr, rhs: Expr) = Arithmetic(ctx, MOD, lhs, rhs)
fun eq(ctx: ParserRuleContext, lhs: Expr, rhs: Expr) = Equal(ctx, EQUAL, lhs, rhs)
fun ne(ctx: ParserRuleContext, lhs: Expr, rhs: Expr) = Equal(ctx, NOT_EQUAL, lhs, rhs)
fun lt(ctx: ParserRuleContext, lhs: Expr, rhs: Expr) = Cmp(ctx, LESS, lhs, rhs)
fun le(ctx: ParserRuleContext, lhs: Expr, rhs: Expr) = Cmp(ctx, LESS_OR_EQUAL, lhs, rhs)
fun gt(ctx: ParserRuleContext, lhs: Expr, rhs: Expr) = Cmp(ctx, GREATER, lhs, rhs)
fun ge(ctx: ParserRuleContext, lhs: Expr, rhs: Expr) = Cmp(ctx, GREATER_OR_EQUAL, lhs, rhs)
