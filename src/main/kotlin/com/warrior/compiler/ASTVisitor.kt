package com.warrior.compiler

import com.warrior.compiler.GrammarLexer.*
import com.warrior.compiler.expression.*
import com.warrior.compiler.expression.Binary.*
import com.warrior.compiler.statement.*
import com.warrior.compiler.module.Function
import com.warrior.compiler.module.Prototype
import com.warrior.compiler.module.Module
import java.util.*

/**
 * Created by warrior on 08.03.16.
 */
class ASTVisitor : GrammarBaseVisitor<ASTNode>() {

    override fun visitModule(ctx: GrammarParser.ModuleContext): Module {
        val functions = ctx.functionDefinition()
                .map { visitFunctionDefinition(it) }
                .toList()
        return Module(functions)
    }

    override fun visitStatement(ctx: GrammarParser.StatementContext): Statement {
        return super.visitStatement(ctx) as Statement
    }

    override fun visitReturnStatement(ctx: GrammarParser.ReturnStatementContext): Return {
        val expr = visitExpression(ctx.expression())
        return Return(expr)
    }

    override fun visitBlock(ctx: GrammarParser.BlockContext): Block {
        val statements = ctx.statement().map { visitStatement(it) }.toList()
        return Block(statements)
    }

    override fun visitIfElseStatement(ctx: GrammarParser.IfElseStatementContext): IfElse {
        val condExpr = visitExpression(ctx.expression())
        val thenBlock = visitBlock(ctx.thenBlock)
        val elseBlock = visitBlock(ctx.elseBlock)
        return IfElse(condExpr, thenBlock, elseBlock)
    }

    override fun visitIfStatement(ctx: GrammarParser.IfStatementContext): If {
        val condExpr = visitExpression(ctx.expression())
        val thenBlock = visitBlock(ctx.block())
        return If(condExpr, thenBlock)
    }

    override fun visitWhileStatement(ctx: GrammarParser.WhileStatementContext): While {
        val condExpr = visitExpression(ctx.expression())
        val bodyBlock = visitBlock(ctx.block())
        return While(condExpr, bodyBlock)
    }

    override fun visitExprStatement(ctx: GrammarParser.ExprStatementContext): ExpressionStatement {
        return ExpressionStatement(visitExpression(ctx.expression()))
    }

    override fun visitAssign(ctx: GrammarParser.AssignContext): Assign {
        val name = ctx.Identifier().text
        val expr = visitExpression(ctx.expression())
        return Assign(name, expr)
    }

    override fun visitAssignDeclaration(ctx: GrammarParser.AssignDeclarationContext): AssignDecl {
        val name = ctx.Identifier().text
        val type = ctx.type().text.toType()
        val expr = if (ctx.expression() != null) {
            visitExpression(ctx.expression())
        } else {
            null
        }
        return AssignDecl(name, type, expr)
    }

    override fun visitVariable(ctx: GrammarParser.VariableContext): Variable {
        return Variable(ctx.text)
    }

    override fun visitFunctionCall(ctx: GrammarParser.FunctionCallContext): Call {
        val name = ctx.Identifier().text
        val args = ctx.arguments()
                ?.expression()
                ?.map { visitExpression(it) }
                ?.toList()
                ?: Collections.emptyList()
        return Call(name, args)
    }

    override fun visitFunctionDefinition(ctx: GrammarParser.FunctionDefinitionContext): Function {
        val prototype = visitPrototype(ctx.prototype())
        val body = visitBlock(ctx.block())
        return Function(prototype, body)
    }

    override fun visitPrototype(ctx: GrammarParser.PrototypeContext): Prototype {
        val name = ctx.Identifier().text
        val args = ctx.typedArguments()
                ?.typedArgument()
                ?.map { Prototype.Arg(it.Identifier().text, it.type().text.toType()) }
                ?.toList()
                ?: Collections.emptyList()
        val type = ctx.type().text.toType();
        return Prototype(name, args, type)
    }

    override fun visitPrint(ctx: GrammarParser.PrintContext): Print {
        val expr = visitExpression(ctx.expression())
        val newLine = when (ctx.name.text) {
            "print" -> false
            "println" -> true
            else -> throw IllegalStateException("unreachable state")
        }
        return Print(expr, newLine)
    }

    override fun visitPrimary(ctx: GrammarParser.PrimaryContext): Expr {
        if (ctx.expression() != null) {
            return visitExpression(ctx.expression())
        }
        return super.visitPrimary(ctx) as Expr
    }

    override fun visitExpression(ctx: GrammarParser.ExpressionContext): Expr {
        if (ctx.primary() != null) {
            return visitPrimary(ctx.primary())
        }
        val leftExpr = visitExpression(ctx.left)
        if (ctx.unaryOp != null) {
            return unaryExpr(ctx.unaryOp.type, leftExpr)
        }
        val rightExpr = visitExpression(ctx.right)
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

    private fun cmpExpr(opType: Int, leftExpr: Expr, rightExpr: Expr): Cmp {
        return when (opType) {
            EQUAL -> eq(leftExpr, rightExpr)
            NOTEQUAL -> ne(leftExpr, rightExpr)
            LT -> lt(leftExpr, rightExpr)
            LE -> le(leftExpr, rightExpr)
            GT -> gt(leftExpr, rightExpr)
            GE -> ge(leftExpr, rightExpr)
            else -> throw IllegalStateException("unreachable state")
        }
    }

    private fun boolExpr(opType: Int, leftExpr: Expr, rightExpr: Expr): BinaryBool {
        return when (opType) {
            AND -> and(leftExpr, rightExpr)
            OR -> or(leftExpr, rightExpr)
            else -> throw IllegalStateException("unreachable state")
        }
    }

    private fun arithmeticExpr(opType: Int, leftExpr: Expr, rightExpr: Expr): Arithmetic {
        return when (opType) {
            ADD -> add(leftExpr, rightExpr)
            SUB -> sub(leftExpr, rightExpr)
            MUL -> mul(leftExpr, rightExpr)
            DIV -> div(leftExpr, rightExpr)
            MOD -> mod(leftExpr, rightExpr)
            else -> throw IllegalStateException("unreachable state")
        }
    }

    private fun unaryExpr(opType: Int, expr: Expr): Expr {
        return when (opType) {
            BANG -> Not(expr)
            ADD -> expr
            SUB -> UnaryMinus(expr)
            else -> throw IllegalStateException("unreachable state")
        }
    }

    override fun visitIntLiteral(ctx: GrammarParser.IntLiteralContext): I32 {
        return I32(ctx.text.toInt())
    }

    override fun visitBoolLiteral(ctx: GrammarParser.BoolLiteralContext): Bool {
        return Bool(ctx.text.toBoolean())
    }
}
