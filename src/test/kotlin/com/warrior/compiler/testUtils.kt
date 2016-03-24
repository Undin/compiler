package com.warrior.compiler

import com.warrior.compiler.expression.Expr
import com.warrior.compiler.statement.Statement
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
