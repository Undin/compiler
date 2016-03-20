// Generated from /Users/warrior/Programming/compiler/src/main/java/com/warrior/compiler/Grammar.g4 by ANTLR 4.5.1
package com.warrior.compiler;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class GrammarParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.5.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, IntLiteral=8, 
		BoolLiteral=9, Identifier=10, LPAREN=11, RPAREN=12, LBRACE=13, RBRACE=14, 
		LBRACK=15, RBRACK=16, COLON=17, SEMICOLON=18, COMMA=19, DOT=20, ARROW=21, 
		GT=22, LT=23, BANG=24, EQUAL=25, LE=26, GE=27, NOTEQUAL=28, AND=29, OR=30, 
		ADD=31, SUB=32, MUL=33, DIV=34, MOD=35, ASSIGN=36, WS=37, COMMENT=38, 
		LINE_COMMENT=39;
	public static final int
		RULE_module = 0, RULE_functionDefinition = 1, RULE_prototype = 2, RULE_typedArguments = 3, 
		RULE_typedArgument = 4, RULE_type = 5, RULE_statement = 6, RULE_block = 7, 
		RULE_exprStatement = 8, RULE_assign = 9, RULE_assignDeclaration = 10, 
		RULE_ifStatement = 11, RULE_ifElseStatement = 12, RULE_whileStatement = 13, 
		RULE_returnStatement = 14, RULE_print = 15, RULE_expression = 16, RULE_primary = 17, 
		RULE_functionCall = 18, RULE_variable = 19, RULE_arguments = 20, RULE_intLiteral = 21, 
		RULE_boolLiteral = 22;
	public static final String[] ruleNames = {
		"module", "functionDefinition", "prototype", "typedArguments", "typedArgument", 
		"type", "statement", "block", "exprStatement", "assign", "assignDeclaration", 
		"ifStatement", "ifElseStatement", "whileStatement", "returnStatement", 
		"print", "expression", "primary", "functionCall", "variable", "arguments", 
		"intLiteral", "boolLiteral"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'fn'", "'if'", "'else'", "'while'", "'return'", "'print'", "'println'", 
		null, null, null, "'('", "')'", "'{'", "'}'", "'['", "']'", "':'", "';'", 
		"','", "'.'", "'->'", "'>'", "'<'", "'!'", "'=='", "'<='", "'>='", "'!='", 
		"'&&'", "'||'", "'+'", "'-'", "'*'", "'/'", "'%'", "'='"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, "IntLiteral", "BoolLiteral", 
		"Identifier", "LPAREN", "RPAREN", "LBRACE", "RBRACE", "LBRACK", "RBRACK", 
		"COLON", "SEMICOLON", "COMMA", "DOT", "ARROW", "GT", "LT", "BANG", "EQUAL", 
		"LE", "GE", "NOTEQUAL", "AND", "OR", "ADD", "SUB", "MUL", "DIV", "MOD", 
		"ASSIGN", "WS", "COMMENT", "LINE_COMMENT"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "Grammar.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public GrammarParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class ModuleContext extends ParserRuleContext {
		public List<FunctionDefinitionContext> functionDefinition() {
			return getRuleContexts(FunctionDefinitionContext.class);
		}
		public FunctionDefinitionContext functionDefinition(int i) {
			return getRuleContext(FunctionDefinitionContext.class,i);
		}
		public ModuleContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_module; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammarVisitor ) return ((GrammarVisitor<? extends T>)visitor).visitModule(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ModuleContext module() throws RecognitionException {
		ModuleContext _localctx = new ModuleContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_module);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(47); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(46);
				functionDefinition();
				}
				}
				setState(49); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==T__0 );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FunctionDefinitionContext extends ParserRuleContext {
		public PrototypeContext prototype() {
			return getRuleContext(PrototypeContext.class,0);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public FunctionDefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionDefinition; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammarVisitor ) return ((GrammarVisitor<? extends T>)visitor).visitFunctionDefinition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctionDefinitionContext functionDefinition() throws RecognitionException {
		FunctionDefinitionContext _localctx = new FunctionDefinitionContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_functionDefinition);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(51);
			match(T__0);
			setState(52);
			prototype();
			setState(53);
			block();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PrototypeContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(GrammarParser.Identifier, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TypedArgumentsContext typedArguments() {
			return getRuleContext(TypedArgumentsContext.class,0);
		}
		public PrototypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_prototype; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammarVisitor ) return ((GrammarVisitor<? extends T>)visitor).visitPrototype(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PrototypeContext prototype() throws RecognitionException {
		PrototypeContext _localctx = new PrototypeContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_prototype);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(55);
			match(Identifier);
			setState(56);
			match(LPAREN);
			setState(58);
			_la = _input.LA(1);
			if (_la==Identifier) {
				{
				setState(57);
				typedArguments();
				}
			}

			setState(60);
			match(RPAREN);
			setState(61);
			match(ARROW);
			setState(62);
			type();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypedArgumentsContext extends ParserRuleContext {
		public List<TypedArgumentContext> typedArgument() {
			return getRuleContexts(TypedArgumentContext.class);
		}
		public TypedArgumentContext typedArgument(int i) {
			return getRuleContext(TypedArgumentContext.class,i);
		}
		public TypedArgumentsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typedArguments; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammarVisitor ) return ((GrammarVisitor<? extends T>)visitor).visitTypedArguments(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypedArgumentsContext typedArguments() throws RecognitionException {
		TypedArgumentsContext _localctx = new TypedArgumentsContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_typedArguments);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(64);
			typedArgument();
			setState(69);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(65);
				match(COMMA);
				setState(66);
				typedArgument();
				}
				}
				setState(71);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypedArgumentContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(GrammarParser.Identifier, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TypedArgumentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typedArgument; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammarVisitor ) return ((GrammarVisitor<? extends T>)visitor).visitTypedArgument(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypedArgumentContext typedArgument() throws RecognitionException {
		TypedArgumentContext _localctx = new TypedArgumentContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_typedArgument);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(72);
			match(Identifier);
			setState(73);
			match(COLON);
			setState(74);
			type();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(GrammarParser.Identifier, 0); }
		public TypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammarVisitor ) return ((GrammarVisitor<? extends T>)visitor).visitType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeContext type() throws RecognitionException {
		TypeContext _localctx = new TypeContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_type);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(76);
			match(Identifier);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StatementContext extends ParserRuleContext {
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public ExprStatementContext exprStatement() {
			return getRuleContext(ExprStatementContext.class,0);
		}
		public AssignContext assign() {
			return getRuleContext(AssignContext.class,0);
		}
		public AssignDeclarationContext assignDeclaration() {
			return getRuleContext(AssignDeclarationContext.class,0);
		}
		public IfStatementContext ifStatement() {
			return getRuleContext(IfStatementContext.class,0);
		}
		public IfElseStatementContext ifElseStatement() {
			return getRuleContext(IfElseStatementContext.class,0);
		}
		public WhileStatementContext whileStatement() {
			return getRuleContext(WhileStatementContext.class,0);
		}
		public ReturnStatementContext returnStatement() {
			return getRuleContext(ReturnStatementContext.class,0);
		}
		public PrintContext print() {
			return getRuleContext(PrintContext.class,0);
		}
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammarVisitor ) return ((GrammarVisitor<? extends T>)visitor).visitStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_statement);
		try {
			setState(87);
			switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(78);
				block();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(79);
				exprStatement();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(80);
				assign();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(81);
				assignDeclaration();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(82);
				ifStatement();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(83);
				ifElseStatement();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(84);
				whileStatement();
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(85);
				returnStatement();
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(86);
				print();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BlockContext extends ParserRuleContext {
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public BlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_block; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammarVisitor ) return ((GrammarVisitor<? extends T>)visitor).visitBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BlockContext block() throws RecognitionException {
		BlockContext _localctx = new BlockContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_block);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(89);
			match(LBRACE);
			setState(93);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__1) | (1L << T__3) | (1L << T__4) | (1L << T__5) | (1L << T__6) | (1L << IntLiteral) | (1L << BoolLiteral) | (1L << Identifier) | (1L << LPAREN) | (1L << LBRACE) | (1L << BANG) | (1L << ADD) | (1L << SUB))) != 0)) {
				{
				{
				setState(90);
				statement();
				}
				}
				setState(95);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(96);
			match(RBRACE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExprStatementContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ExprStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_exprStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammarVisitor ) return ((GrammarVisitor<? extends T>)visitor).visitExprStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExprStatementContext exprStatement() throws RecognitionException {
		ExprStatementContext _localctx = new ExprStatementContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_exprStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(98);
			expression(0);
			setState(99);
			match(SEMICOLON);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AssignContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(GrammarParser.Identifier, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public AssignContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assign; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammarVisitor ) return ((GrammarVisitor<? extends T>)visitor).visitAssign(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssignContext assign() throws RecognitionException {
		AssignContext _localctx = new AssignContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_assign);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(101);
			match(Identifier);
			setState(102);
			match(ASSIGN);
			setState(103);
			expression(0);
			setState(104);
			match(SEMICOLON);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AssignDeclarationContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(GrammarParser.Identifier, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public AssignDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assignDeclaration; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammarVisitor ) return ((GrammarVisitor<? extends T>)visitor).visitAssignDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssignDeclarationContext assignDeclaration() throws RecognitionException {
		AssignDeclarationContext _localctx = new AssignDeclarationContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_assignDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(106);
			match(Identifier);
			setState(107);
			match(COLON);
			setState(108);
			type();
			setState(111);
			_la = _input.LA(1);
			if (_la==ASSIGN) {
				{
				setState(109);
				match(ASSIGN);
				setState(110);
				expression(0);
				}
			}

			setState(113);
			match(SEMICOLON);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IfStatementContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public IfStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ifStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammarVisitor ) return ((GrammarVisitor<? extends T>)visitor).visitIfStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IfStatementContext ifStatement() throws RecognitionException {
		IfStatementContext _localctx = new IfStatementContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_ifStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(115);
			match(T__1);
			setState(116);
			match(LPAREN);
			setState(117);
			expression(0);
			setState(118);
			match(RPAREN);
			setState(119);
			block();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IfElseStatementContext extends ParserRuleContext {
		public BlockContext thenBlock;
		public BlockContext elseBlock;
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public List<BlockContext> block() {
			return getRuleContexts(BlockContext.class);
		}
		public BlockContext block(int i) {
			return getRuleContext(BlockContext.class,i);
		}
		public IfElseStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ifElseStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammarVisitor ) return ((GrammarVisitor<? extends T>)visitor).visitIfElseStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IfElseStatementContext ifElseStatement() throws RecognitionException {
		IfElseStatementContext _localctx = new IfElseStatementContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_ifElseStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(121);
			match(T__1);
			setState(122);
			match(LPAREN);
			setState(123);
			expression(0);
			setState(124);
			match(RPAREN);
			setState(125);
			((IfElseStatementContext)_localctx).thenBlock = block();
			setState(126);
			match(T__2);
			setState(127);
			((IfElseStatementContext)_localctx).elseBlock = block();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class WhileStatementContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public WhileStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_whileStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammarVisitor ) return ((GrammarVisitor<? extends T>)visitor).visitWhileStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WhileStatementContext whileStatement() throws RecognitionException {
		WhileStatementContext _localctx = new WhileStatementContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_whileStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(129);
			match(T__3);
			setState(130);
			match(LPAREN);
			setState(131);
			expression(0);
			setState(132);
			match(RPAREN);
			setState(133);
			block();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ReturnStatementContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ReturnStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_returnStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammarVisitor ) return ((GrammarVisitor<? extends T>)visitor).visitReturnStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ReturnStatementContext returnStatement() throws RecognitionException {
		ReturnStatementContext _localctx = new ReturnStatementContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_returnStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(135);
			match(T__4);
			setState(136);
			expression(0);
			setState(137);
			match(SEMICOLON);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PrintContext extends ParserRuleContext {
		public Token name;
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public PrintContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_print; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammarVisitor ) return ((GrammarVisitor<? extends T>)visitor).visitPrint(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PrintContext print() throws RecognitionException {
		PrintContext _localctx = new PrintContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_print);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(139);
			((PrintContext)_localctx).name = _input.LT(1);
			_la = _input.LA(1);
			if ( !(_la==T__5 || _la==T__6) ) {
				((PrintContext)_localctx).name = (Token)_errHandler.recoverInline(this);
			} else {
				consume();
			}
			setState(140);
			match(LPAREN);
			setState(141);
			expression(0);
			setState(142);
			match(RPAREN);
			setState(143);
			match(SEMICOLON);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionContext extends ParserRuleContext {
		public ExpressionContext left;
		public Token unaryOp;
		public Token op;
		public ExpressionContext right;
		public Token cmpOp;
		public Token boolOp;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public PrimaryContext primary() {
			return getRuleContext(PrimaryContext.class,0);
		}
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammarVisitor ) return ((GrammarVisitor<? extends T>)visitor).visitExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		return expression(0);
	}

	private ExpressionContext expression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExpressionContext _localctx = new ExpressionContext(_ctx, _parentState);
		ExpressionContext _prevctx = _localctx;
		int _startState = 32;
		enterRecursionRule(_localctx, 32, RULE_expression, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(151);
			switch (_input.LA(1)) {
			case ADD:
			case SUB:
				{
				setState(146);
				((ExpressionContext)_localctx).unaryOp = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==ADD || _la==SUB) ) {
					((ExpressionContext)_localctx).unaryOp = (Token)_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(147);
				((ExpressionContext)_localctx).left = expression(8);
				}
				break;
			case BANG:
				{
				setState(148);
				((ExpressionContext)_localctx).unaryOp = match(BANG);
				setState(149);
				((ExpressionContext)_localctx).left = expression(7);
				}
				break;
			case IntLiteral:
			case BoolLiteral:
			case Identifier:
			case LPAREN:
				{
				setState(150);
				primary();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(173);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,8,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(171);
					switch ( getInterpreter().adaptivePredict(_input,7,_ctx) ) {
					case 1:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						_localctx.left = _prevctx;
						_localctx.left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(153);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(154);
						((ExpressionContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << MUL) | (1L << DIV) | (1L << MOD))) != 0)) ) {
							((ExpressionContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						} else {
							consume();
						}
						setState(155);
						((ExpressionContext)_localctx).right = expression(7);
						}
						break;
					case 2:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						_localctx.left = _prevctx;
						_localctx.left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(156);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(157);
						((ExpressionContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==ADD || _la==SUB) ) {
							((ExpressionContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						} else {
							consume();
						}
						setState(158);
						((ExpressionContext)_localctx).right = expression(6);
						}
						break;
					case 3:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						_localctx.left = _prevctx;
						_localctx.left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(159);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(160);
						((ExpressionContext)_localctx).cmpOp = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << GT) | (1L << LT) | (1L << LE) | (1L << GE))) != 0)) ) {
							((ExpressionContext)_localctx).cmpOp = (Token)_errHandler.recoverInline(this);
						} else {
							consume();
						}
						setState(161);
						((ExpressionContext)_localctx).right = expression(5);
						}
						break;
					case 4:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						_localctx.left = _prevctx;
						_localctx.left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(162);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(163);
						((ExpressionContext)_localctx).cmpOp = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==EQUAL || _la==NOTEQUAL) ) {
							((ExpressionContext)_localctx).cmpOp = (Token)_errHandler.recoverInline(this);
						} else {
							consume();
						}
						setState(164);
						((ExpressionContext)_localctx).right = expression(4);
						}
						break;
					case 5:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						_localctx.left = _prevctx;
						_localctx.left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(165);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(166);
						((ExpressionContext)_localctx).boolOp = match(AND);
						setState(167);
						((ExpressionContext)_localctx).right = expression(3);
						}
						break;
					case 6:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						_localctx.left = _prevctx;
						_localctx.left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(168);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(169);
						((ExpressionContext)_localctx).boolOp = match(OR);
						setState(170);
						((ExpressionContext)_localctx).right = expression(2);
						}
						break;
					}
					} 
				}
				setState(175);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,8,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class PrimaryContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public IntLiteralContext intLiteral() {
			return getRuleContext(IntLiteralContext.class,0);
		}
		public BoolLiteralContext boolLiteral() {
			return getRuleContext(BoolLiteralContext.class,0);
		}
		public FunctionCallContext functionCall() {
			return getRuleContext(FunctionCallContext.class,0);
		}
		public VariableContext variable() {
			return getRuleContext(VariableContext.class,0);
		}
		public PrimaryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_primary; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammarVisitor ) return ((GrammarVisitor<? extends T>)visitor).visitPrimary(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PrimaryContext primary() throws RecognitionException {
		PrimaryContext _localctx = new PrimaryContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_primary);
		try {
			setState(184);
			switch ( getInterpreter().adaptivePredict(_input,9,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(176);
				match(LPAREN);
				setState(177);
				expression(0);
				setState(178);
				match(RPAREN);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(180);
				intLiteral();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(181);
				boolLiteral();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(182);
				functionCall();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(183);
				variable();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FunctionCallContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(GrammarParser.Identifier, 0); }
		public ArgumentsContext arguments() {
			return getRuleContext(ArgumentsContext.class,0);
		}
		public FunctionCallContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionCall; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammarVisitor ) return ((GrammarVisitor<? extends T>)visitor).visitFunctionCall(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctionCallContext functionCall() throws RecognitionException {
		FunctionCallContext _localctx = new FunctionCallContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_functionCall);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(186);
			match(Identifier);
			setState(187);
			match(LPAREN);
			setState(189);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << IntLiteral) | (1L << BoolLiteral) | (1L << Identifier) | (1L << LPAREN) | (1L << BANG) | (1L << ADD) | (1L << SUB))) != 0)) {
				{
				setState(188);
				arguments();
				}
			}

			setState(191);
			match(RPAREN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VariableContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(GrammarParser.Identifier, 0); }
		public VariableContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variable; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammarVisitor ) return ((GrammarVisitor<? extends T>)visitor).visitVariable(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VariableContext variable() throws RecognitionException {
		VariableContext _localctx = new VariableContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_variable);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(193);
			match(Identifier);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ArgumentsContext extends ParserRuleContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public ArgumentsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arguments; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammarVisitor ) return ((GrammarVisitor<? extends T>)visitor).visitArguments(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArgumentsContext arguments() throws RecognitionException {
		ArgumentsContext _localctx = new ArgumentsContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_arguments);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(195);
			expression(0);
			setState(200);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(196);
				match(COMMA);
				setState(197);
				expression(0);
				}
				}
				setState(202);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IntLiteralContext extends ParserRuleContext {
		public TerminalNode IntLiteral() { return getToken(GrammarParser.IntLiteral, 0); }
		public IntLiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_intLiteral; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammarVisitor ) return ((GrammarVisitor<? extends T>)visitor).visitIntLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IntLiteralContext intLiteral() throws RecognitionException {
		IntLiteralContext _localctx = new IntLiteralContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_intLiteral);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(203);
			match(IntLiteral);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BoolLiteralContext extends ParserRuleContext {
		public TerminalNode BoolLiteral() { return getToken(GrammarParser.BoolLiteral, 0); }
		public BoolLiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_boolLiteral; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammarVisitor ) return ((GrammarVisitor<? extends T>)visitor).visitBoolLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BoolLiteralContext boolLiteral() throws RecognitionException {
		BoolLiteralContext _localctx = new BoolLiteralContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_boolLiteral);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(205);
			match(BoolLiteral);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 16:
			return expression_sempred((ExpressionContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expression_sempred(ExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 6);
		case 1:
			return precpred(_ctx, 5);
		case 2:
			return precpred(_ctx, 4);
		case 3:
			return precpred(_ctx, 3);
		case 4:
			return precpred(_ctx, 2);
		case 5:
			return precpred(_ctx, 1);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3)\u00d2\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\3\2\6\2\62"+
		"\n\2\r\2\16\2\63\3\3\3\3\3\3\3\3\3\4\3\4\3\4\5\4=\n\4\3\4\3\4\3\4\3\4"+
		"\3\5\3\5\3\5\7\5F\n\5\f\5\16\5I\13\5\3\6\3\6\3\6\3\6\3\7\3\7\3\b\3\b\3"+
		"\b\3\b\3\b\3\b\3\b\3\b\3\b\5\bZ\n\b\3\t\3\t\7\t^\n\t\f\t\16\ta\13\t\3"+
		"\t\3\t\3\n\3\n\3\n\3\13\3\13\3\13\3\13\3\13\3\f\3\f\3\f\3\f\3\f\5\fr\n"+
		"\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\r\3\16\3\16\3\16\3\16\3\16\3\16\3\16"+
		"\3\16\3\17\3\17\3\17\3\17\3\17\3\17\3\20\3\20\3\20\3\20\3\21\3\21\3\21"+
		"\3\21\3\21\3\21\3\22\3\22\3\22\3\22\3\22\3\22\5\22\u009a\n\22\3\22\3\22"+
		"\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22"+
		"\3\22\3\22\7\22\u00ae\n\22\f\22\16\22\u00b1\13\22\3\23\3\23\3\23\3\23"+
		"\3\23\3\23\3\23\3\23\5\23\u00bb\n\23\3\24\3\24\3\24\5\24\u00c0\n\24\3"+
		"\24\3\24\3\25\3\25\3\26\3\26\3\26\7\26\u00c9\n\26\f\26\16\26\u00cc\13"+
		"\26\3\27\3\27\3\30\3\30\3\30\2\3\"\31\2\4\6\b\n\f\16\20\22\24\26\30\32"+
		"\34\36 \"$&(*,.\2\7\3\2\b\t\3\2!\"\3\2#%\4\2\30\31\34\35\4\2\33\33\36"+
		"\36\u00d5\2\61\3\2\2\2\4\65\3\2\2\2\69\3\2\2\2\bB\3\2\2\2\nJ\3\2\2\2\f"+
		"N\3\2\2\2\16Y\3\2\2\2\20[\3\2\2\2\22d\3\2\2\2\24g\3\2\2\2\26l\3\2\2\2"+
		"\30u\3\2\2\2\32{\3\2\2\2\34\u0083\3\2\2\2\36\u0089\3\2\2\2 \u008d\3\2"+
		"\2\2\"\u0099\3\2\2\2$\u00ba\3\2\2\2&\u00bc\3\2\2\2(\u00c3\3\2\2\2*\u00c5"+
		"\3\2\2\2,\u00cd\3\2\2\2.\u00cf\3\2\2\2\60\62\5\4\3\2\61\60\3\2\2\2\62"+
		"\63\3\2\2\2\63\61\3\2\2\2\63\64\3\2\2\2\64\3\3\2\2\2\65\66\7\3\2\2\66"+
		"\67\5\6\4\2\678\5\20\t\28\5\3\2\2\29:\7\f\2\2:<\7\r\2\2;=\5\b\5\2<;\3"+
		"\2\2\2<=\3\2\2\2=>\3\2\2\2>?\7\16\2\2?@\7\27\2\2@A\5\f\7\2A\7\3\2\2\2"+
		"BG\5\n\6\2CD\7\25\2\2DF\5\n\6\2EC\3\2\2\2FI\3\2\2\2GE\3\2\2\2GH\3\2\2"+
		"\2H\t\3\2\2\2IG\3\2\2\2JK\7\f\2\2KL\7\23\2\2LM\5\f\7\2M\13\3\2\2\2NO\7"+
		"\f\2\2O\r\3\2\2\2PZ\5\20\t\2QZ\5\22\n\2RZ\5\24\13\2SZ\5\26\f\2TZ\5\30"+
		"\r\2UZ\5\32\16\2VZ\5\34\17\2WZ\5\36\20\2XZ\5 \21\2YP\3\2\2\2YQ\3\2\2\2"+
		"YR\3\2\2\2YS\3\2\2\2YT\3\2\2\2YU\3\2\2\2YV\3\2\2\2YW\3\2\2\2YX\3\2\2\2"+
		"Z\17\3\2\2\2[_\7\17\2\2\\^\5\16\b\2]\\\3\2\2\2^a\3\2\2\2_]\3\2\2\2_`\3"+
		"\2\2\2`b\3\2\2\2a_\3\2\2\2bc\7\20\2\2c\21\3\2\2\2de\5\"\22\2ef\7\24\2"+
		"\2f\23\3\2\2\2gh\7\f\2\2hi\7&\2\2ij\5\"\22\2jk\7\24\2\2k\25\3\2\2\2lm"+
		"\7\f\2\2mn\7\23\2\2nq\5\f\7\2op\7&\2\2pr\5\"\22\2qo\3\2\2\2qr\3\2\2\2"+
		"rs\3\2\2\2st\7\24\2\2t\27\3\2\2\2uv\7\4\2\2vw\7\r\2\2wx\5\"\22\2xy\7\16"+
		"\2\2yz\5\20\t\2z\31\3\2\2\2{|\7\4\2\2|}\7\r\2\2}~\5\"\22\2~\177\7\16\2"+
		"\2\177\u0080\5\20\t\2\u0080\u0081\7\5\2\2\u0081\u0082\5\20\t\2\u0082\33"+
		"\3\2\2\2\u0083\u0084\7\6\2\2\u0084\u0085\7\r\2\2\u0085\u0086\5\"\22\2"+
		"\u0086\u0087\7\16\2\2\u0087\u0088\5\20\t\2\u0088\35\3\2\2\2\u0089\u008a"+
		"\7\7\2\2\u008a\u008b\5\"\22\2\u008b\u008c\7\24\2\2\u008c\37\3\2\2\2\u008d"+
		"\u008e\t\2\2\2\u008e\u008f\7\r\2\2\u008f\u0090\5\"\22\2\u0090\u0091\7"+
		"\16\2\2\u0091\u0092\7\24\2\2\u0092!\3\2\2\2\u0093\u0094\b\22\1\2\u0094"+
		"\u0095\t\3\2\2\u0095\u009a\5\"\22\n\u0096\u0097\7\32\2\2\u0097\u009a\5"+
		"\"\22\t\u0098\u009a\5$\23\2\u0099\u0093\3\2\2\2\u0099\u0096\3\2\2\2\u0099"+
		"\u0098\3\2\2\2\u009a\u00af\3\2\2\2\u009b\u009c\f\b\2\2\u009c\u009d\t\4"+
		"\2\2\u009d\u00ae\5\"\22\t\u009e\u009f\f\7\2\2\u009f\u00a0\t\3\2\2\u00a0"+
		"\u00ae\5\"\22\b\u00a1\u00a2\f\6\2\2\u00a2\u00a3\t\5\2\2\u00a3\u00ae\5"+
		"\"\22\7\u00a4\u00a5\f\5\2\2\u00a5\u00a6\t\6\2\2\u00a6\u00ae\5\"\22\6\u00a7"+
		"\u00a8\f\4\2\2\u00a8\u00a9\7\37\2\2\u00a9\u00ae\5\"\22\5\u00aa\u00ab\f"+
		"\3\2\2\u00ab\u00ac\7 \2\2\u00ac\u00ae\5\"\22\4\u00ad\u009b\3\2\2\2\u00ad"+
		"\u009e\3\2\2\2\u00ad\u00a1\3\2\2\2\u00ad\u00a4\3\2\2\2\u00ad\u00a7\3\2"+
		"\2\2\u00ad\u00aa\3\2\2\2\u00ae\u00b1\3\2\2\2\u00af\u00ad\3\2\2\2\u00af"+
		"\u00b0\3\2\2\2\u00b0#\3\2\2\2\u00b1\u00af\3\2\2\2\u00b2\u00b3\7\r\2\2"+
		"\u00b3\u00b4\5\"\22\2\u00b4\u00b5\7\16\2\2\u00b5\u00bb\3\2\2\2\u00b6\u00bb"+
		"\5,\27\2\u00b7\u00bb\5.\30\2\u00b8\u00bb\5&\24\2\u00b9\u00bb\5(\25\2\u00ba"+
		"\u00b2\3\2\2\2\u00ba\u00b6\3\2\2\2\u00ba\u00b7\3\2\2\2\u00ba\u00b8\3\2"+
		"\2\2\u00ba\u00b9\3\2\2\2\u00bb%\3\2\2\2\u00bc\u00bd\7\f\2\2\u00bd\u00bf"+
		"\7\r\2\2\u00be\u00c0\5*\26\2\u00bf\u00be\3\2\2\2\u00bf\u00c0\3\2\2\2\u00c0"+
		"\u00c1\3\2\2\2\u00c1\u00c2\7\16\2\2\u00c2\'\3\2\2\2\u00c3\u00c4\7\f\2"+
		"\2\u00c4)\3\2\2\2\u00c5\u00ca\5\"\22\2\u00c6\u00c7\7\25\2\2\u00c7\u00c9"+
		"\5\"\22\2\u00c8\u00c6\3\2\2\2\u00c9\u00cc\3\2\2\2\u00ca\u00c8\3\2\2\2"+
		"\u00ca\u00cb\3\2\2\2\u00cb+\3\2\2\2\u00cc\u00ca\3\2\2\2\u00cd\u00ce\7"+
		"\n\2\2\u00ce-\3\2\2\2\u00cf\u00d0\7\13\2\2\u00d0/\3\2\2\2\16\63<GY_q\u0099"+
		"\u00ad\u00af\u00ba\u00bf\u00ca";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}