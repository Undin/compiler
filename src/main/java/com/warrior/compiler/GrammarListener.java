// Generated from /Users/warrior/Programming/Compiler/src/main/java/com/warrior/compiler/lexer/Grammar.g4 by ANTLR 4.5.1
package com.warrior.compiler;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link GrammarParser}.
 */
public interface GrammarListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link GrammarParser#root}.
	 * @param ctx the parse tree
	 */
	void enterRoot(GrammarParser.RootContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#root}.
	 * @param ctx the parse tree
	 */
	void exitRoot(GrammarParser.RootContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#functionDefinition}.
	 * @param ctx the parse tree
	 */
	void enterFunctionDefinition(GrammarParser.FunctionDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#functionDefinition}.
	 * @param ctx the parse tree
	 */
	void exitFunctionDefinition(GrammarParser.FunctionDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#prototype}.
	 * @param ctx the parse tree
	 */
	void enterPrototype(GrammarParser.PrototypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#prototype}.
	 * @param ctx the parse tree
	 */
	void exitPrototype(GrammarParser.PrototypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#arguments}.
	 * @param ctx the parse tree
	 */
	void enterArguments(GrammarParser.ArgumentsContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#arguments}.
	 * @param ctx the parse tree
	 */
	void exitArguments(GrammarParser.ArgumentsContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#argument}.
	 * @param ctx the parse tree
	 */
	void enterArgument(GrammarParser.ArgumentContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#argument}.
	 * @param ctx the parse tree
	 */
	void exitArgument(GrammarParser.ArgumentContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#type}.
	 * @param ctx the parse tree
	 */
	void enterType(GrammarParser.TypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#type}.
	 * @param ctx the parse tree
	 */
	void exitType(GrammarParser.TypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(GrammarParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(GrammarParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#primary}.
	 * @param ctx the parse tree
	 */
	void enterPrimary(GrammarParser.PrimaryContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#primary}.
	 * @param ctx the parse tree
	 */
	void exitPrimary(GrammarParser.PrimaryContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#intLiteral}.
	 * @param ctx the parse tree
	 */
	void enterIntLiteral(GrammarParser.IntLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#intLiteral}.
	 * @param ctx the parse tree
	 */
	void exitIntLiteral(GrammarParser.IntLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#boolLiteral}.
	 * @param ctx the parse tree
	 */
	void enterBoolLiteral(GrammarParser.BoolLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#boolLiteral}.
	 * @param ctx the parse tree
	 */
	void exitBoolLiteral(GrammarParser.BoolLiteralContext ctx);
}