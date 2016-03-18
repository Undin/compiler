package com.warrior.compiler.expression

import com.warrior.compiler.ASTVisitor
import com.warrior.compiler.GrammarLexer
import com.warrior.compiler.GrammarParser
import org.antlr.v4.runtime.ANTLRInputStream
import org.antlr.v4.runtime.CommonTokenStream

/**
 * Created by warrior on 18.03.16.
 */

fun parseExpr(expr: String): Expr {
    val stream = ANTLRInputStream(expr);
    val lexer = GrammarLexer(stream);
    val tokens = CommonTokenStream(lexer);
    val parser = GrammarParser(tokens);
    val tree = parser.expression();
    val visitor = ASTVisitor()
    return visitor.visitExpression(tree)
}
