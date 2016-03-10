package com.warrior.compiler

import com.warrior.compiler.GrammarLexer.*
import java.util.*

/**
 * Created by warrior on 08.03.16.
 */
class ASTVisitor : GrammarBaseVisitor<Expr>() {

    override fun visitFunctionCall(ctx: GrammarParser.FunctionCallContext): Expr {
        val name = ctx.Identifier().text
        val args = ctx.arguments()
                ?.expression()
                ?.map { visitExpression(it) }
                ?.toList()
                ?: Collections.emptyList()
        return CallExpr(name, args)
    }

    override fun visitFunctionDefinition(ctx: GrammarParser.FunctionDefinitionContext): Expr {
        val prototype = visitPrototype(ctx.prototype())
        val expr = visitExpression(ctx.expression())
        return FunctionExpr(prototype, expr)
    }

    override fun visitPrototype(ctx: GrammarParser.PrototypeContext): PrototypeExpr {
        val name = ctx.Identifier().text
        val args = ctx.typedArguments()
                ?.typedArgument()
                ?.map { PrototypeExpr.Arg(it.Identifier().text, it.type().text.toType()) }
                ?.toList()
                ?: Collections.emptyList()
        val type = ctx.type().text.toType();
        return PrototypeExpr(name, args, type)
    }


    override fun visitPrimary(ctx: GrammarParser.PrimaryContext): Expr {
        if (ctx.expression() != null) {
            return visitExpression(ctx.expression())
        }
        return super.visitPrimary(ctx)
    }

    override fun visitExpression(ctx: GrammarParser.ExpressionContext): Expr {
        if (ctx.primary() != null) {
            return super.visitExpression(ctx)
        }
        val leftExpr = visit(ctx.left)
        if (ctx.unaryOp != null) {
            return unaryExpr(ctx.unaryOp.type, leftExpr)
        }
        val rightExpr = visit(ctx.right)
        if (ctx.op != null) {
            return arithmeticExpr(ctx.op.type, leftExpr, rightExpr)
        }
        if (ctx.boolOp != null) {
            return boolExpr(ctx.boolOp.type, leftExpr, rightExpr)
        }
        if (ctx.cmpOp != null) {
            return cmpExpr(ctx.cmpOp.type, leftExpr, rightExpr)
        }
        throw IllegalStateException("unreachable state")
    }

    private fun cmpExpr(opType: Int, leftExpr: Expr, rightExpr: Expr): CmpExpr {
        val op = when (opType) {
            EQUAL -> CmpExpr.Operation.EQUAL
            NOTEQUAL -> CmpExpr.Operation.NOT_EQUAL
            LT -> CmpExpr.Operation.LESS
            LE -> CmpExpr.Operation.LESS_OR_EQUAL
            GT -> CmpExpr.Operation.GREATER
            GE -> CmpExpr.Operation.GREATER_OR_EQUAL
            else -> throw IllegalStateException("unreachable state")
        }
        return CmpExpr(op, leftExpr, rightExpr)
    }

    private fun boolExpr(opType: Int, leftExpr: Expr, rightExpr: Expr): BinaryBoolExpr {
        val op = when (opType) {
            AND -> BinaryBoolExpr.Operation.AND
            OR -> BinaryBoolExpr.Operation.OR
            else -> throw IllegalStateException("unreachable state")
        }
        return BinaryBoolExpr(op, leftExpr, rightExpr)
    }

    private fun arithmeticExpr(opType: Int, leftExpr: Expr, rightExpr: Expr): ArithmeticExpr {
        val op = when (opType) {
            ADD -> ArithmeticExpr.Operation.ADD
            SUB -> ArithmeticExpr.Operation.SUB
            MUL -> ArithmeticExpr.Operation.MUL
            DIV -> ArithmeticExpr.Operation.DIV
            MOD -> ArithmeticExpr.Operation.MOD
            else -> throw IllegalStateException("unreachable state")
        }
        return ArithmeticExpr(op, leftExpr, rightExpr)
    }

    private fun unaryExpr(opType: Int, expr: Expr): Expr {
        return when (opType) {
            BANG -> Not(expr)
            ADD -> expr
            SUB -> UnaryMinus(expr)
            else -> throw IllegalStateException("unreachable state")
        }
    }

    override fun visitIntLiteral(ctx: GrammarParser.IntLiteralContext): Expr {
        return I32(ctx.text.toInt())
    }

    override fun visitBoolLiteral(ctx: GrammarParser.BoolLiteralContext): Expr {
        return Bool(ctx.text.toBoolean())
    }
}