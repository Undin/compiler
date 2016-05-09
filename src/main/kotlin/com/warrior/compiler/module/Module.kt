package com.warrior.compiler.module

import com.warrior.compiler.ASTNode
import com.warrior.compiler.SymbolTable
import com.warrior.compiler.Type
import com.warrior.compiler.expression.*
import com.warrior.compiler.expression.Binary.Arithmetic
import com.warrior.compiler.expression.MemoryExpr.Variable
import com.warrior.compiler.module.Module.Operation.*
import com.warrior.compiler.statement.Statement
import com.warrior.compiler.statement.Statement.*
import com.warrior.compiler.validation.ErrorMessage
import com.warrior.compiler.validation.ErrorType.FUNCTION_IS_ALREADY_DECLARED
import com.warrior.compiler.validation.Result
import com.warrior.compiler.validation.Result.Error
import com.warrior.compiler.validation.Result.Ok
import com.warrior.compiler.validation.fold
import org.antlr.v4.runtime.ParserRuleContext
import org.bytedeco.javacpp.LLVM.*
import java.util.*

/**
 * Created by warrior on 19.03.16.
 */
class Module(ctx: ParserRuleContext, val globals: List<GlobalDeclaration>, val functions: List<Function>) : ASTNode(ctx) {

    companion object {
        private val EMPTY_CTX = ParserRuleContext()
        private val ACCUMULATOR_NAME = ".acc"
    }

    fun generateCode(module: LLVMModuleRef, builder: LLVMBuilderRef, useTailRecOptimization: Boolean = false) {
        val finalFunctions = functions.map {
            if (useTailRecOptimization) { it.eliminateTailRecursion() } else { Pair(it, false) }
        }

        val symbolTable = SymbolTable<LLVMValueRef>()
        globals.forEach { it.generateCode(module, builder, symbolTable) }
        finalFunctions.forEach { it.first.prototype.generateCode(module) }
        declarePrintf(module)
        declareScanf(module)
        finalFunctions.forEach { it.first.generateCode(module, builder, symbolTable, it.second) }
    }

    private fun declarePrintf(module: LLVMModuleRef) {
        val i8Pointer = LLVMPointerType(LLVMInt8Type(), 0)
        val fnType = LLVMFunctionType(LLVMInt32Type(), i8Pointer, 1, 1)
        val fn = LLVMAddFunction(module, "printf", fnType)
        LLVMSetFunctionCallConv(fn, LLVMCCallConv)
    }

    private fun declareScanf(module: LLVMModuleRef) {
        val i8Pointer = LLVMPointerType(LLVMInt8Type(), 0)
        val fnType = LLVMFunctionType(LLVMInt32Type(), i8Pointer, 1, 1)
        val fn = LLVMAddFunction(module, "scanf", fnType)
        LLVMSetFunctionCallConv(fn, LLVMCCallConv)
    }

    fun validate(): Result {
        val symbolTable = SymbolTable<Type>()
        val globalResult = globals
            .map { it.validate(symbolTable) }
            .fold()

        val functionMap = HashMap<String, Type.Fn>()
        val prototypeResult = functions.map {
            val proto = it.prototype
            if (proto.name in functionMap) {
                val message = "'${proto.getText()}': function '${proto.name}' is already declared"
                Error(ErrorMessage(FUNCTION_IS_ALREADY_DECLARED, message, proto.start(), proto.end()))
            } else {
                functionMap[proto.name] = it.getType()
                Ok
            }
        }
        .fold()
        val functionsResult = functions
                .map { it.validate(functionMap, symbolTable) }
                .fold()
        return globalResult + prototypeResult + functionsResult
    }

    fun Function.analyze(): AnalyzeInfo = body.analyze(prototype.name)

    private fun Function.eliminateTailRecursion(): Pair<Function, Boolean> {
        val name = prototype.name
        val analyzeInfo = analyze()
        val op = analyzeInfo.op
        if (!analyzeInfo.hasImplicitTailCalls || op == NONE) {
            return Pair(this, analyzeInfo.hasExplicitTailCalls)
        }
        val newBody = body.transformBlock(name, op)
        val newFunction = Function(EMPTY_CTX, prototype, newBody)
        newFunction.accDeclaration = AssignDecl(EMPTY_CTX, ACCUMULATOR_NAME, op.expressionType(), op.neutralElement())
        return Pair(newFunction, true)
    }

    private fun Statement.analyze(name: String): AnalyzeInfo = when (this) {
        is Block -> statements
                .map { it.analyze(name) }
                .fold(AnalyzeInfo.NEUTRAL, AnalyzeInfo::plus)
        is If -> thenBlock.analyze(name)
        is IfElse -> thenBlock.analyze(name) + elseBlock.analyze(name)
        is While -> bodyBlock.analyze(name)
        is Return -> {
            if (expr.isRecursiveCall(name)) {
                AnalyzeInfo(true, false, NONE)
            } else {
                val op = expr.implicitTailCallOperation(name)
                AnalyzeInfo(false, op != NONE, op)
            }
        }
        else -> AnalyzeInfo.NEUTRAL
    }

    private fun Block.transformBlock(name: String, op: Operation): Block {
        val transformedStatements = this.statements.flatMap { it.transform(name, op) }
        return Block(EMPTY_CTX, transformedStatements)
    }

    private fun Statement.transform(name: String, op: Operation): List<Statement> = when (this) {
        is Block -> listOf(transformBlock(name, op))
        is If -> {
            val thenBlock = thenBlock.transformBlock(name, op)
            listOf(If(EMPTY_CTX, condition, thenBlock))
        }
        is IfElse -> {
            val thenBlock = thenBlock.transformBlock(name, op)
            val elseBlock = elseBlock.transformBlock(name, op)
            listOf(IfElse(EMPTY_CTX, condition, thenBlock, elseBlock))
        }
        is While -> {
            val bodyBlock = bodyBlock.transformBlock(name, op)
            listOf(While(EMPTY_CTX, condition, bodyBlock))
        }
        is Return -> transformReturn(name, op)
        else -> listOf(this)
    }

    private fun Return.transformReturn(name: String, op: Operation): List<Statement> {
        return if (expr.isRecursiveCall(name)) {
            listOf(Return(EMPTY_CTX, expr))
        } else {
            val accVar = Variable(EMPTY_CTX, ACCUMULATOR_NAME)
            accVar.type = op.expressionType()
            if (expr.implicitTailCallOperation(name) != NONE) {
                if (expr !is Binary) {
                    throw IllegalStateException("unreachable state")
                }
                val call: Expr
                val e: Expr
                if (expr.lhs.isRecursiveCall(name)) {
                    call = expr.lhs
                    e = expr.rhs
                } else {
                    call = expr.rhs
                    e = expr.lhs
                }
                val assign = Assign(EMPTY_CTX, ACCUMULATOR_NAME, op.expression(accVar, e))
                val ret = Return(EMPTY_CTX, call)
                listOf(assign, ret)
            } else {
                val expr = op.expression(accVar, expr)
                listOf(Return(EMPTY_CTX, expr))
            }
        }
    }

    private fun Expr.isRecursiveCall(name: String): Boolean = this is Call && fnName == name

    private fun Expr.implicitTailCallOperation(name: String): Operation {
        return if (this is Binary.Arithmetic && (lhs.isRecursiveCall(name) || rhs.isRecursiveCall(name))) {
            when (op) {
                Arithmetic.Operation.ADD -> ADD
                Arithmetic.Operation.MUL -> MUL
                else -> NONE
            }
        } else {
            NONE
        }
    }

    data class AnalyzeInfo(
            val hasExplicitTailCalls: Boolean,
            val hasImplicitTailCalls: Boolean,
            val op: Operation) {

        companion object {
            val NEUTRAL = AnalyzeInfo(false, false, NONE)
        }

        operator fun plus(other: AnalyzeInfo): AnalyzeInfo {
            val mergedOp = if (hasImplicitTailCalls && other.hasImplicitTailCalls) {
                if (op == other.op) { op } else { NONE }
            } else if (hasExplicitTailCalls) { op } else { other.op }

            return AnalyzeInfo(hasExplicitTailCalls || other.hasExplicitTailCalls,
                    hasImplicitTailCalls || other.hasImplicitTailCalls, mergedOp)
        }
    }

    enum class Operation {
        NONE {
            override fun expression(lhs: Expr, rhs: Expr): Expr = throw UnsupportedOperationException()
            override fun expressionType(): Type = throw UnsupportedOperationException()
            override fun neutralElement(): Expr = throw UnsupportedOperationException()
        },
        ADD {
            override fun expression(lhs: Expr, rhs: Expr): Expr = expressionInternal {
                add(EMPTY_CTX, lhs, rhs)
            }
            override fun expressionType(): Type = Type.I32
            override fun neutralElement(): Expr = I32(EMPTY_CTX, 0)

        },
        MUL {
            override fun expression(lhs: Expr, rhs: Expr): Expr = expressionInternal {
                mul(EMPTY_CTX, lhs, rhs)
            }
            override fun expressionType(): Type = Type.I32
            override fun neutralElement(): Expr = I32(EMPTY_CTX, 1)
        };

        abstract fun expression(lhs: Expr, rhs: Expr): Expr
        abstract fun expressionType(): Type
        abstract fun neutralElement(): Expr

        protected fun expressionInternal(block: () -> Expr): Expr {
            val expr = block()
            expr.type = expressionType()
            return expr
        }
    }
}
