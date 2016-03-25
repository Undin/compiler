package com.warrior.compiler

import org.antlr.v4.runtime.ParserRuleContext

/**
 * Created by warrior on 07.03.16.
 */
abstract class ASTNode(val ctx: ParserRuleContext) {

    fun getText(): String = ctx.text

    fun start(): Position {
        val token = ctx.getStart();
        return Position(token.line, token.charPositionInLine)
    }

    fun end(): Position {
        val token = ctx.getStop();
        return Position(token.line, token.charPositionInLine + token.text.length)
    }
}

data class Position(val line: Int, val linePosition: Int) {
    override fun toString(): String = "($line:$linePosition)"
}
