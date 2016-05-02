package com.warrior.compiler

import com.warrior.compiler.GrammarLexer.*
import com.warrior.compiler.expression.*
import com.warrior.compiler.expression.AggregateLiteral.*
import com.warrior.compiler.expression.Binary.*
import com.warrior.compiler.expression.Call.*
import com.warrior.compiler.expression.MemoryExpr.*
import com.warrior.compiler.module.*
import com.warrior.compiler.module.Function
import com.warrior.compiler.statement.*
import com.warrior.compiler.statement.Statement.*
import java.util.*

/**
 * Created by warrior on 08.03.16.
 */
class ASTVisitor : GrammarBaseVisitor<ASTNode>() {

    override fun visitModule(ctx: GrammarParser.ModuleContext): Module {
        val globals = ctx.globalDeclaration().map { visitGlobalDeclaration(it) }
        val functions = ctx.functionDefinition().map { visitFunctionDefinition(it) }
        return Module(ctx, globals, functions)
    }

    override fun visitFunctionDefinition(ctx: GrammarParser.FunctionDefinitionContext): Function {
        val prototype = if (ctx.prototype() != null) {
            visitPrototype(ctx.prototype())
        } else if (ctx.extensionPrototype() != null) {
            visitExtensionPrototype(ctx.extensionPrototype())
        } else {
            throw IllegalStateException("unreachable state")
        }
        val body = visitBlock(ctx.block())
        return Function(ctx, prototype, body)
    }

    override fun visitPrototype(ctx: GrammarParser.PrototypeContext): Prototype {
        val name = ctx.Identifier().text
        val args = ctx.typedArguments()
                ?.typedArgument()
                ?.map { Prototype.Arg(it.Identifier().text, visitType(it.type())) }
                ?: Collections.emptyList()
        val type = visitType(ctx.type())
        return Prototype.SimplePrototype(ctx, name, args, type)
    }

    override fun visitExtensionPrototype(ctx: GrammarParser.ExtensionPrototypeContext): Prototype {
        if (ctx.self.text != "self") {
            throw InputMismatchException("first param of extension function must be 'self' but found '${ctx.self.text}'")
        }
        val fnName = ctx.fnName.text
        val extendedType = visitType(ctx.extendedType)
        val args = ctx.typedArguments()
                ?.typedArgument()
                ?.map { Prototype.Arg(it.Identifier().text, visitType(it.type())) }
                ?: Collections.emptyList()
        val returnType = visitType(ctx.returnType)
        return Prototype.ExtensionPrototype(ctx, fnName, extendedType, args, returnType)
    }

    override fun visitGlobalDeclaration(ctx: GrammarParser.GlobalDeclarationContext): GlobalDeclaration {
        val name = ctx.Identifier().text
        val type = ctx.type()?.let { visitType(it) }
        val expr = visitExpression(ctx.expression())
        return GlobalDeclaration(ctx, name, type, expr)
    }

    // Statements

    override fun visitStatement(ctx: GrammarParser.StatementContext): Statement {
        return super.visitStatement(ctx) as Statement
    }

    override fun visitReturnStatement(ctx: GrammarParser.ReturnStatementContext): Return {
        val expr = visitExpression(ctx.expression())
        return Return(ctx, expr)
    }

    override fun visitBlock(ctx: GrammarParser.BlockContext): Statement.Block {
        val statements = ctx.statement().map { visitStatement(it) }.toList()
        return Block(ctx, statements)
    }

    override fun visitIfElseStatement(ctx: GrammarParser.IfElseStatementContext): IfElse {
        val condExpr = visitExpression(ctx.expression())
        val thenBlock = visitBlock(ctx.thenBlock)
        val elseBlock = visitBlock(ctx.elseBlock)
        return IfElse(ctx, condExpr, thenBlock, elseBlock)
    }

    override fun visitIfStatement(ctx: GrammarParser.IfStatementContext): If {
        val condExpr = visitExpression(ctx.expression())
        val thenBlock = visitBlock(ctx.block())
        return If(ctx, condExpr, thenBlock)
    }

    override fun visitWhileStatement(ctx: GrammarParser.WhileStatementContext): While {
        val condExpr = visitExpression(ctx.expression())
        val bodyBlock = visitBlock(ctx.block())
        return While(ctx, condExpr, bodyBlock)
    }

    override fun visitExprStatement(ctx: GrammarParser.ExprStatementContext): ExpressionStatement {
        return ExpressionStatement(ctx, visitExpression(ctx.expression()))
    }

    override fun visitAssign(ctx: GrammarParser.AssignContext): Assign {
        val name = ctx.Identifier().text
        val expr = visitExpression(ctx.expression())
        return Assign(ctx, name, expr)
    }

    override fun visitSetTupleElement(ctx: GrammarParser.SetTupleElementContext): SetTupleElement {
        val tupleExpr = visitExpression(ctx.tuple)
        val index = visitIntLiteral(ctx.index).value
        val valueExpr = visitExpression(ctx.value)
        return SetTupleElement(ctx, tupleExpr, index, valueExpr)
    }

    override fun visitSetArrayElement(ctx: GrammarParser.SetArrayElementContext): SetArrayElement {
        val arrayExpr = visitExpression(ctx.array)
        val indexExpr = visitExpression(ctx.index)
        val valueExpr = visitExpression(ctx.value)
        return SetArrayElement(ctx, arrayExpr, indexExpr, valueExpr)
    }

    override fun visitAssignDeclaration(ctx: GrammarParser.AssignDeclarationContext): AssignDecl {
        val name = ctx.Identifier().text
        val type = ctx.type()?.let { visitType(ctx.type()) }
        val expr = visitExpression(ctx.expression())
        return AssignDecl(ctx, name, type, expr)
    }

    override fun visitDestructiveDeclaration(ctx: GrammarParser.DestructiveDeclarationContext): DestructiveDeclaration {
        val names = ctx.Identifier().map { it.text }
        val expr = visitExpression(ctx.expression())
        return DestructiveDeclaration(ctx, names, expr)
    }

    override fun visitPrint(ctx: GrammarParser.PrintContext): Print {
        val expr = visitExpression(ctx.expression())
        val newLine = when (ctx.name.text) {
            "print" -> false
            "println" -> true
            else -> throw IllegalStateException("unreachable state")
        }
        return Print(ctx, expr, newLine)
    }

    override fun visitRead(ctx: GrammarParser.ReadContext): Read {
        val varName = ctx.Identifier().text
        return Read(ctx, varName)
    }

    // Expressions

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
        if (ctx.`object` != null) {
            val objectExpr = visitExpression(ctx.`object`)
            val name = ctx.Identifier().text
            val args = ctx.arguments()
                    ?.expression()
                    ?.map { visitExpression(it) }
                    ?: Collections.emptyList()
            return ExtensionCall(ctx, objectExpr, name, args)
        }
        if (ctx.tuple != null) {
            val tuple = visitExpression(ctx.tuple)
            val index = visitIntLiteral(ctx.tupleIndex)
            return TupleElement(ctx, tuple, index.value)
        }
        if (ctx.array != null) {
            val array = visitExpression(ctx.array)
            val index = visitExpression(ctx.arrayIndex)
            return ArrayElement(ctx, array, index)
        }

        val leftExpr = visitExpression(ctx.left)
        if (ctx.unaryOp != null) {
            return unaryExpr(ctx, ctx.unaryOp.type, leftExpr)
        }
        val rightExpr = visitExpression(ctx.right)
        if (ctx.op != null) {
            return arithmeticExpr(ctx, ctx.op.type, leftExpr, rightExpr)
        }
        if (ctx.cmpOp != null) {
            return cmpExpr(ctx, ctx.cmpOp.type, leftExpr, rightExpr)
        }
        if (ctx.equalOp != null) {
            return equalExpr(ctx, ctx.equalOp.type, leftExpr, rightExpr)
        }
        if (ctx.boolOp != null) {
            return boolExpr(ctx, ctx.boolOp.type, leftExpr, rightExpr)
        }
        throw IllegalStateException("unreachable state")
    }

    private fun equalExpr(ctx: GrammarParser.ExpressionContext, opType: Int, leftExpr: Expr, rightExpr: Expr): Equal {
        return when (opType) {
            EQUAL -> eq(ctx, leftExpr, rightExpr)
            NOTEQUAL -> ne(ctx, leftExpr, rightExpr)
            else -> throw IllegalStateException("unreachable state")
        }
    }

    private fun cmpExpr(ctx: GrammarParser.ExpressionContext, opType: Int, leftExpr: Expr, rightExpr: Expr): Cmp {
        return when (opType) {
            LT -> lt(ctx, leftExpr, rightExpr)
            LE -> le(ctx, leftExpr, rightExpr)
            GT -> gt(ctx, leftExpr, rightExpr)
            GE -> ge(ctx, leftExpr, rightExpr)
            else -> throw IllegalStateException("unreachable state")
        }
    }

    private fun boolExpr(ctx: GrammarParser.ExpressionContext, opType: Int, leftExpr: Expr, rightExpr: Expr): Logic {
        return when (opType) {
            AND -> and(ctx, leftExpr, rightExpr)
            OR -> or(ctx, leftExpr, rightExpr)
            else -> throw IllegalStateException("unreachable state")
        }
    }

    private fun arithmeticExpr(ctx: GrammarParser.ExpressionContext, opType: Int, leftExpr: Expr, rightExpr: Expr): Arithmetic {
        return when (opType) {
            ADD -> add(ctx, leftExpr, rightExpr)
            SUB -> sub(ctx, leftExpr, rightExpr)
            MUL -> mul(ctx, leftExpr, rightExpr)
            DIV -> div(ctx, leftExpr, rightExpr)
            MOD -> mod(ctx, leftExpr, rightExpr)
            else -> throw IllegalStateException("unreachable state")
        }
    }

    private fun unaryExpr(ctx: GrammarParser.ExpressionContext, opType: Int, expr: Expr): Expr {
        return when (opType) {
            BANG -> Not(ctx, expr)
            ADD -> expr
            SUB -> UnaryMinus(ctx, expr)
            else -> throw IllegalStateException("unreachable state")
        }
    }

    override fun visitVariable(ctx: GrammarParser.VariableContext): Variable = Variable(ctx, ctx.text)

    override fun visitFunctionCall(ctx: GrammarParser.FunctionCallContext): Call {
        val name = ctx.Identifier().text
        val args = ctx.arguments()
                ?.expression()
                ?.map { visitExpression(it) }
                ?: Collections.emptyList()
        return SimpleCall(ctx, name, args)
    }

    // Literals

    override fun visitIntLiteral(ctx: GrammarParser.IntLiteralContext): I32 = I32(ctx, ctx.text.toInt())

    override fun visitBoolLiteral(ctx: GrammarParser.BoolLiteralContext): Bool = Bool(ctx, ctx.text.toBoolean())

    override fun visitTupleLiteral(ctx: GrammarParser.TupleLiteralContext): Tuple {
        val elements = ctx.expression().map { visitExpression(it) }
        return Tuple(ctx, elements)
    }

    override fun visitSeqArrayLiteral(ctx: GrammarParser.SeqArrayLiteralContext): SeqArray {
        val elements = ctx.expression().map { visitExpression(it) }
        return SeqArray(ctx, elements)
    }

    override fun visitRepeatArrayLiteral(ctx: GrammarParser.RepeatArrayLiteralContext): RepeatArray {
        val elementValue = visitExpression(ctx.expression())
        val size = visitIntLiteral(ctx.intLiteral()).value
        return RepeatArray(ctx, elementValue, size)
    }

    // Types

    override fun visitType(ctx: GrammarParser.TypeContext): Type {
        ctx.simpleType()?.let { return visitSimpleType(it) }
        ctx.tupleType()?.let { return visitTupleType(it) }
        ctx.arrayType()?.let { return visitArrayType(it) }
        throw IllegalStateException("unexpected type name")
    }

    override fun visitSimpleType(ctx: GrammarParser.SimpleTypeContext): Type = ctx.Identifier().text.toType()

    override fun visitTupleType(ctx: GrammarParser.TupleTypeContext): Type.Tuple {
        val elementTypes = ctx.type().map { visitType(it) }
        return Type.Tuple(elementTypes)
    }

    override fun visitArrayType(ctx: GrammarParser.ArrayTypeContext): Type.Array {
        val elementType = visitType(ctx.type())
        val size = ctx.intLiteral().text.toInt()
        return Type.Array(elementType, size)
    }
}
