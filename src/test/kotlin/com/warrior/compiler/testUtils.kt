package com.warrior.compiler

import com.warrior.compiler.expression.Expr
import com.warrior.compiler.statement.Statement
import com.warrior.compiler.module.Function
import com.warrior.compiler.module.GlobalDeclaration
import com.warrior.compiler.module.Module
import com.warrior.compiler.module.Prototype
import com.warrior.compiler.validation.ErrorMessage
import com.warrior.compiler.validation.ErrorType
import com.warrior.compiler.validation.Result
import org.antlr.v4.runtime.ANTLRInputStream
import org.antlr.v4.runtime.CommonTokenStream

/**
 * Created by warrior on 24.03.16.
 */
fun parseExpr(expr: String): Expr {
    val tree = parser(expr).expression();
    val visitor = ASTVisitor()
    return visitor.visitExpression(tree)
}

fun parseStatement(statement: String): Statement {
    val tree = parser(statement).statement();
    val visitor = ASTVisitor()
    return visitor.visitStatement(tree)
}

fun parsePrototype(prototype: String): Prototype {
    val tree = parser(prototype).prototype();
    val visitor = ASTVisitor()
    return visitor.visitPrototype(tree)
}

fun parseFunction(function: String): Function {
    val tree = parser(function).functionDefinition();
    val visitor = ASTVisitor()
    return visitor.visitFunctionDefinition(tree)
}

fun parseGlobalDeclaration(global: String): GlobalDeclaration {
    val tree = parser(global).globalDeclaration();
    val visitor = ASTVisitor()
    return visitor.visitGlobalDeclaration(tree)
}

fun parseModule(module: String): Module {
    val tree = parser(module).module();
    val visitor = ASTVisitor()
    return visitor.visitModule(tree)
}

private fun parser(str: String): GrammarParser {
    val stream = ANTLRInputStream(str);
    val lexer = GrammarLexer(stream);
    val tokens = CommonTokenStream(lexer);
    return GrammarParser(tokens);
}

fun error(vararg errorType: ErrorType): Result.Error {
    val messages = errorType.map { ErrorMessage(it, "", Position(0, 0), Position(0, 0)) }
    return Result.Error(messages)
}
