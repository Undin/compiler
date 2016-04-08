// Generated from /Users/warrior/Programming/compiler/src/main/java/com/warrior/compiler/Grammar.g4 by ANTLR 4.5.1
package com.warrior.compiler;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link GrammarParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface GrammarVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link GrammarParser#module}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitModule(GrammarParser.ModuleContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#globalDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGlobalDeclaration(GrammarParser.GlobalDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#functionDefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionDefinition(GrammarParser.FunctionDefinitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#prototype}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrototype(GrammarParser.PrototypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#typedArguments}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypedArguments(GrammarParser.TypedArgumentsContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#typedArgument}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypedArgument(GrammarParser.TypedArgumentContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType(GrammarParser.TypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#simpleType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimpleType(GrammarParser.SimpleTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#tupleType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTupleType(GrammarParser.TupleTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#arrayType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayType(GrammarParser.ArrayTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(GrammarParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(GrammarParser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#exprStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprStatement(GrammarParser.ExprStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#assign}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssign(GrammarParser.AssignContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#assignDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignDeclaration(GrammarParser.AssignDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#ifStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfStatement(GrammarParser.IfStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#ifElseStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfElseStatement(GrammarParser.IfElseStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#whileStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileStatement(GrammarParser.WhileStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#returnStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnStatement(GrammarParser.ReturnStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#print}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrint(GrammarParser.PrintContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#read}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRead(GrammarParser.ReadContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(GrammarParser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimary(GrammarParser.PrimaryContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#functionCall}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionCall(GrammarParser.FunctionCallContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#variable}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariable(GrammarParser.VariableContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#arguments}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArguments(GrammarParser.ArgumentsContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#intLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIntLiteral(GrammarParser.IntLiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#boolLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBoolLiteral(GrammarParser.BoolLiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#tupleLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTupleLiteral(GrammarParser.TupleLiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#seqArrayLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSeqArrayLiteral(GrammarParser.SeqArrayLiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#repeatArrayLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRepeatArrayLiteral(GrammarParser.RepeatArrayLiteralContext ctx);
}