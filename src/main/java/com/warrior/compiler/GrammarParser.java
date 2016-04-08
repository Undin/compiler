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
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		IntLiteral=10, BoolLiteral=11, Identifier=12, LPAREN=13, RPAREN=14, LBRACE=15, 
		RBRACE=16, LBRACK=17, RBRACK=18, COLON=19, SEMICOLON=20, COMMA=21, DOT=22, 
		ARROW=23, GT=24, LT=25, BANG=26, EQUAL=27, LE=28, GE=29, NOTEQUAL=30, 
		AND=31, OR=32, ADD=33, SUB=34, MUL=35, DIV=36, MOD=37, ASSIGN=38, WS=39, 
		COMMENT=40, LINE_COMMENT=41;
	public static final int
		RULE_module = 0, RULE_globalDeclaration = 1, RULE_functionDefinition = 2, 
		RULE_prototype = 3, RULE_typedArguments = 4, RULE_typedArgument = 5, RULE_type = 6, 
		RULE_simpleType = 7, RULE_tupleType = 8, RULE_arrayType = 9, RULE_statement = 10, 
		RULE_block = 11, RULE_exprStatement = 12, RULE_assign = 13, RULE_assignDeclaration = 14, 
		RULE_ifStatement = 15, RULE_ifElseStatement = 16, RULE_whileStatement = 17, 
		RULE_returnStatement = 18, RULE_print = 19, RULE_read = 20, RULE_expression = 21, 
		RULE_primary = 22, RULE_functionCall = 23, RULE_variable = 24, RULE_arguments = 25, 
		RULE_intLiteral = 26, RULE_boolLiteral = 27, RULE_tupleLiteral = 28, RULE_arrayLiteral = 29;
	public static final String[] ruleNames = {
		"module", "globalDeclaration", "functionDefinition", "prototype", "typedArguments", 
		"typedArgument", "type", "simpleType", "tupleType", "arrayType", "statement", 
		"block", "exprStatement", "assign", "assignDeclaration", "ifStatement", 
		"ifElseStatement", "whileStatement", "returnStatement", "print", "read", 
		"expression", "primary", "functionCall", "variable", "arguments", "intLiteral", 
		"boolLiteral", "tupleLiteral", "arrayLiteral"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'let'", "'fn'", "'if'", "'else'", "'while'", "'return'", "'print'", 
		"'println'", "'read'", null, null, null, "'('", "')'", "'{'", "'}'", "'['", 
		"']'", "':'", "';'", "','", "'.'", "'->'", "'>'", "'<'", "'!'", "'=='", 
		"'<='", "'>='", "'!='", "'&&'", "'||'", "'+'", "'-'", "'*'", "'/'", "'%'", 
		"'='"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, "IntLiteral", 
		"BoolLiteral", "Identifier", "LPAREN", "RPAREN", "LBRACE", "RBRACE", "LBRACK", 
		"RBRACK", "COLON", "SEMICOLON", "COMMA", "DOT", "ARROW", "GT", "LT", "BANG", 
		"EQUAL", "LE", "GE", "NOTEQUAL", "AND", "OR", "ADD", "SUB", "MUL", "DIV", 
		"MOD", "ASSIGN", "WS", "COMMENT", "LINE_COMMENT"
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
		public List<GlobalDeclarationContext> globalDeclaration() {
			return getRuleContexts(GlobalDeclarationContext.class);
		}
		public GlobalDeclarationContext globalDeclaration(int i) {
			return getRuleContext(GlobalDeclarationContext.class,i);
		}
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
			setState(62); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				setState(62);
				switch (_input.LA(1)) {
				case T__0:
					{
					setState(60);
					globalDeclaration();
					}
					break;
				case T__1:
					{
					setState(61);
					functionDefinition();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(64); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==T__0 || _la==T__1 );
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

	public static class GlobalDeclarationContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(GrammarParser.Identifier, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public IntLiteralContext intLiteral() {
			return getRuleContext(IntLiteralContext.class,0);
		}
		public BoolLiteralContext boolLiteral() {
			return getRuleContext(BoolLiteralContext.class,0);
		}
		public GlobalDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_globalDeclaration; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammarVisitor ) return ((GrammarVisitor<? extends T>)visitor).visitGlobalDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final GlobalDeclarationContext globalDeclaration() throws RecognitionException {
		GlobalDeclarationContext _localctx = new GlobalDeclarationContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_globalDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(66);
			match(T__0);
			setState(67);
			match(Identifier);
			setState(70);
			_la = _input.LA(1);
			if (_la==COLON) {
				{
				setState(68);
				match(COLON);
				setState(69);
				type();
				}
			}

			setState(77);
			_la = _input.LA(1);
			if (_la==ASSIGN) {
				{
				setState(72);
				match(ASSIGN);
				setState(75);
				switch (_input.LA(1)) {
				case IntLiteral:
					{
					setState(73);
					intLiteral();
					}
					break;
				case BoolLiteral:
					{
					setState(74);
					boolLiteral();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
			}

			setState(79);
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
		enterRule(_localctx, 4, RULE_functionDefinition);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(81);
			match(T__1);
			setState(82);
			prototype();
			setState(83);
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
		enterRule(_localctx, 6, RULE_prototype);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(85);
			match(Identifier);
			setState(86);
			match(LPAREN);
			setState(88);
			_la = _input.LA(1);
			if (_la==Identifier) {
				{
				setState(87);
				typedArguments();
				}
			}

			setState(90);
			match(RPAREN);
			setState(91);
			match(ARROW);
			setState(92);
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
		enterRule(_localctx, 8, RULE_typedArguments);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(94);
			typedArgument();
			setState(99);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(95);
				match(COMMA);
				setState(96);
				typedArgument();
				}
				}
				setState(101);
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
		enterRule(_localctx, 10, RULE_typedArgument);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(102);
			match(Identifier);
			setState(103);
			match(COLON);
			setState(104);
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
		public SimpleTypeContext simpleType() {
			return getRuleContext(SimpleTypeContext.class,0);
		}
		public TupleTypeContext tupleType() {
			return getRuleContext(TupleTypeContext.class,0);
		}
		public ArrayTypeContext arrayType() {
			return getRuleContext(ArrayTypeContext.class,0);
		}
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
		enterRule(_localctx, 12, RULE_type);
		try {
			setState(109);
			switch (_input.LA(1)) {
			case Identifier:
				enterOuterAlt(_localctx, 1);
				{
				setState(106);
				simpleType();
				}
				break;
			case LPAREN:
				enterOuterAlt(_localctx, 2);
				{
				setState(107);
				tupleType();
				}
				break;
			case LBRACK:
				enterOuterAlt(_localctx, 3);
				{
				setState(108);
				arrayType();
				}
				break;
			default:
				throw new NoViableAltException(this);
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

	public static class SimpleTypeContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(GrammarParser.Identifier, 0); }
		public SimpleTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_simpleType; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammarVisitor ) return ((GrammarVisitor<? extends T>)visitor).visitSimpleType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SimpleTypeContext simpleType() throws RecognitionException {
		SimpleTypeContext _localctx = new SimpleTypeContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_simpleType);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(111);
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

	public static class TupleTypeContext extends ParserRuleContext {
		public List<TypeContext> type() {
			return getRuleContexts(TypeContext.class);
		}
		public TypeContext type(int i) {
			return getRuleContext(TypeContext.class,i);
		}
		public TupleTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tupleType; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammarVisitor ) return ((GrammarVisitor<? extends T>)visitor).visitTupleType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TupleTypeContext tupleType() throws RecognitionException {
		TupleTypeContext _localctx = new TupleTypeContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_tupleType);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(113);
			match(LPAREN);
			setState(122);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << Identifier) | (1L << LPAREN) | (1L << LBRACK))) != 0)) {
				{
				setState(114);
				type();
				setState(119);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(115);
					match(COMMA);
					setState(116);
					type();
					}
					}
					setState(121);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(124);
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

	public static class ArrayTypeContext extends ParserRuleContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public IntLiteralContext intLiteral() {
			return getRuleContext(IntLiteralContext.class,0);
		}
		public ArrayTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arrayType; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammarVisitor ) return ((GrammarVisitor<? extends T>)visitor).visitArrayType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArrayTypeContext arrayType() throws RecognitionException {
		ArrayTypeContext _localctx = new ArrayTypeContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_arrayType);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(126);
			match(LBRACK);
			setState(127);
			type();
			setState(128);
			match(SEMICOLON);
			setState(129);
			intLiteral();
			setState(130);
			match(RBRACK);
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
		public ReadContext read() {
			return getRuleContext(ReadContext.class,0);
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
		enterRule(_localctx, 20, RULE_statement);
		try {
			setState(142);
			switch ( getInterpreter().adaptivePredict(_input,10,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(132);
				block();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(133);
				exprStatement();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(134);
				assign();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(135);
				assignDeclaration();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(136);
				ifStatement();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(137);
				ifElseStatement();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(138);
				whileStatement();
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(139);
				returnStatement();
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(140);
				print();
				}
				break;
			case 10:
				enterOuterAlt(_localctx, 10);
				{
				setState(141);
				read();
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
		enterRule(_localctx, 22, RULE_block);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(144);
			match(LBRACE);
			setState(148);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__2) | (1L << T__4) | (1L << T__5) | (1L << T__6) | (1L << T__7) | (1L << T__8) | (1L << IntLiteral) | (1L << BoolLiteral) | (1L << Identifier) | (1L << LPAREN) | (1L << LBRACE) | (1L << LBRACK) | (1L << BANG) | (1L << ADD) | (1L << SUB))) != 0)) {
				{
				{
				setState(145);
				statement();
				}
				}
				setState(150);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(151);
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
		enterRule(_localctx, 24, RULE_exprStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(153);
			expression(0);
			setState(154);
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
		enterRule(_localctx, 26, RULE_assign);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(156);
			match(Identifier);
			setState(157);
			match(ASSIGN);
			setState(158);
			expression(0);
			setState(159);
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
		enterRule(_localctx, 28, RULE_assignDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(161);
			match(T__0);
			setState(162);
			match(Identifier);
			setState(165);
			_la = _input.LA(1);
			if (_la==COLON) {
				{
				setState(163);
				match(COLON);
				setState(164);
				type();
				}
			}

			setState(169);
			_la = _input.LA(1);
			if (_la==ASSIGN) {
				{
				setState(167);
				match(ASSIGN);
				setState(168);
				expression(0);
				}
			}

			setState(171);
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
		enterRule(_localctx, 30, RULE_ifStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(173);
			match(T__2);
			setState(174);
			match(LPAREN);
			setState(175);
			expression(0);
			setState(176);
			match(RPAREN);
			setState(177);
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
		enterRule(_localctx, 32, RULE_ifElseStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(179);
			match(T__2);
			setState(180);
			match(LPAREN);
			setState(181);
			expression(0);
			setState(182);
			match(RPAREN);
			setState(183);
			((IfElseStatementContext)_localctx).thenBlock = block();
			setState(184);
			match(T__3);
			setState(185);
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
		enterRule(_localctx, 34, RULE_whileStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(187);
			match(T__4);
			setState(188);
			match(LPAREN);
			setState(189);
			expression(0);
			setState(190);
			match(RPAREN);
			setState(191);
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
		enterRule(_localctx, 36, RULE_returnStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(193);
			match(T__5);
			setState(194);
			expression(0);
			setState(195);
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
		enterRule(_localctx, 38, RULE_print);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(197);
			((PrintContext)_localctx).name = _input.LT(1);
			_la = _input.LA(1);
			if ( !(_la==T__6 || _la==T__7) ) {
				((PrintContext)_localctx).name = (Token)_errHandler.recoverInline(this);
			} else {
				consume();
			}
			setState(198);
			match(LPAREN);
			setState(199);
			expression(0);
			setState(200);
			match(RPAREN);
			setState(201);
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

	public static class ReadContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(GrammarParser.Identifier, 0); }
		public ReadContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_read; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammarVisitor ) return ((GrammarVisitor<? extends T>)visitor).visitRead(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ReadContext read() throws RecognitionException {
		ReadContext _localctx = new ReadContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_read);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(203);
			match(T__8);
			setState(204);
			match(LPAREN);
			setState(205);
			match(Identifier);
			setState(206);
			match(RPAREN);
			setState(207);
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
		public Token equalOp;
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
		int _startState = 42;
		enterRecursionRule(_localctx, 42, RULE_expression, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(215);
			switch (_input.LA(1)) {
			case ADD:
			case SUB:
				{
				setState(210);
				((ExpressionContext)_localctx).unaryOp = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==ADD || _la==SUB) ) {
					((ExpressionContext)_localctx).unaryOp = (Token)_errHandler.recoverInline(this);
				} else {
					consume();
				}
				setState(211);
				((ExpressionContext)_localctx).left = expression(8);
				}
				break;
			case BANG:
				{
				setState(212);
				((ExpressionContext)_localctx).unaryOp = match(BANG);
				setState(213);
				((ExpressionContext)_localctx).left = expression(7);
				}
				break;
			case IntLiteral:
			case BoolLiteral:
			case Identifier:
			case LPAREN:
			case LBRACK:
				{
				setState(214);
				primary();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(237);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,16,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(235);
					switch ( getInterpreter().adaptivePredict(_input,15,_ctx) ) {
					case 1:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						_localctx.left = _prevctx;
						_localctx.left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(217);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(218);
						((ExpressionContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << MUL) | (1L << DIV) | (1L << MOD))) != 0)) ) {
							((ExpressionContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						} else {
							consume();
						}
						setState(219);
						((ExpressionContext)_localctx).right = expression(7);
						}
						break;
					case 2:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						_localctx.left = _prevctx;
						_localctx.left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(220);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(221);
						((ExpressionContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==ADD || _la==SUB) ) {
							((ExpressionContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						} else {
							consume();
						}
						setState(222);
						((ExpressionContext)_localctx).right = expression(6);
						}
						break;
					case 3:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						_localctx.left = _prevctx;
						_localctx.left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(223);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(224);
						((ExpressionContext)_localctx).cmpOp = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << GT) | (1L << LT) | (1L << LE) | (1L << GE))) != 0)) ) {
							((ExpressionContext)_localctx).cmpOp = (Token)_errHandler.recoverInline(this);
						} else {
							consume();
						}
						setState(225);
						((ExpressionContext)_localctx).right = expression(5);
						}
						break;
					case 4:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						_localctx.left = _prevctx;
						_localctx.left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(226);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(227);
						((ExpressionContext)_localctx).equalOp = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==EQUAL || _la==NOTEQUAL) ) {
							((ExpressionContext)_localctx).equalOp = (Token)_errHandler.recoverInline(this);
						} else {
							consume();
						}
						setState(228);
						((ExpressionContext)_localctx).right = expression(4);
						}
						break;
					case 5:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						_localctx.left = _prevctx;
						_localctx.left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(229);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(230);
						((ExpressionContext)_localctx).boolOp = match(AND);
						setState(231);
						((ExpressionContext)_localctx).right = expression(3);
						}
						break;
					case 6:
						{
						_localctx = new ExpressionContext(_parentctx, _parentState);
						_localctx.left = _prevctx;
						_localctx.left = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(232);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(233);
						((ExpressionContext)_localctx).boolOp = match(OR);
						setState(234);
						((ExpressionContext)_localctx).right = expression(2);
						}
						break;
					}
					} 
				}
				setState(239);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,16,_ctx);
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
		public TupleLiteralContext tupleLiteral() {
			return getRuleContext(TupleLiteralContext.class,0);
		}
		public ArrayLiteralContext arrayLiteral() {
			return getRuleContext(ArrayLiteralContext.class,0);
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
		enterRule(_localctx, 44, RULE_primary);
		try {
			setState(250);
			switch ( getInterpreter().adaptivePredict(_input,17,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(240);
				match(LPAREN);
				setState(241);
				expression(0);
				setState(242);
				match(RPAREN);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(244);
				intLiteral();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(245);
				boolLiteral();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(246);
				tupleLiteral();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(247);
				arrayLiteral();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(248);
				functionCall();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(249);
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
		enterRule(_localctx, 46, RULE_functionCall);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(252);
			match(Identifier);
			setState(253);
			match(LPAREN);
			setState(255);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << IntLiteral) | (1L << BoolLiteral) | (1L << Identifier) | (1L << LPAREN) | (1L << LBRACK) | (1L << BANG) | (1L << ADD) | (1L << SUB))) != 0)) {
				{
				setState(254);
				arguments();
				}
			}

			setState(257);
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
		enterRule(_localctx, 48, RULE_variable);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(259);
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
		enterRule(_localctx, 50, RULE_arguments);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(261);
			expression(0);
			setState(266);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(262);
				match(COMMA);
				setState(263);
				expression(0);
				}
				}
				setState(268);
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
		enterRule(_localctx, 52, RULE_intLiteral);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(269);
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
		enterRule(_localctx, 54, RULE_boolLiteral);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(271);
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

	public static class TupleLiteralContext extends ParserRuleContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TupleLiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tupleLiteral; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammarVisitor ) return ((GrammarVisitor<? extends T>)visitor).visitTupleLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TupleLiteralContext tupleLiteral() throws RecognitionException {
		TupleLiteralContext _localctx = new TupleLiteralContext(_ctx, getState());
		enterRule(_localctx, 56, RULE_tupleLiteral);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(273);
			match(LPAREN);
			setState(282);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << IntLiteral) | (1L << BoolLiteral) | (1L << Identifier) | (1L << LPAREN) | (1L << LBRACK) | (1L << BANG) | (1L << ADD) | (1L << SUB))) != 0)) {
				{
				setState(274);
				expression(0);
				setState(279);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(275);
					match(COMMA);
					setState(276);
					expression(0);
					}
					}
					setState(281);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(284);
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

	public static class ArrayLiteralContext extends ParserRuleContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public ArrayLiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arrayLiteral; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammarVisitor ) return ((GrammarVisitor<? extends T>)visitor).visitArrayLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArrayLiteralContext arrayLiteral() throws RecognitionException {
		ArrayLiteralContext _localctx = new ArrayLiteralContext(_ctx, getState());
		enterRule(_localctx, 58, RULE_arrayLiteral);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(286);
			match(LBRACK);
			setState(295);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << IntLiteral) | (1L << BoolLiteral) | (1L << Identifier) | (1L << LPAREN) | (1L << LBRACK) | (1L << BANG) | (1L << ADD) | (1L << SUB))) != 0)) {
				{
				setState(287);
				expression(0);
				setState(292);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(288);
					match(COMMA);
					setState(289);
					expression(0);
					}
					}
					setState(294);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(297);
			match(RBRACK);
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
		case 21:
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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3+\u012e\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\3\2\3\2\6"+
		"\2A\n\2\r\2\16\2B\3\3\3\3\3\3\3\3\5\3I\n\3\3\3\3\3\3\3\5\3N\n\3\5\3P\n"+
		"\3\3\3\3\3\3\4\3\4\3\4\3\4\3\5\3\5\3\5\5\5[\n\5\3\5\3\5\3\5\3\5\3\6\3"+
		"\6\3\6\7\6d\n\6\f\6\16\6g\13\6\3\7\3\7\3\7\3\7\3\b\3\b\3\b\5\bp\n\b\3"+
		"\t\3\t\3\n\3\n\3\n\3\n\7\nx\n\n\f\n\16\n{\13\n\5\n}\n\n\3\n\3\n\3\13\3"+
		"\13\3\13\3\13\3\13\3\13\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\5\f\u0091"+
		"\n\f\3\r\3\r\7\r\u0095\n\r\f\r\16\r\u0098\13\r\3\r\3\r\3\16\3\16\3\16"+
		"\3\17\3\17\3\17\3\17\3\17\3\20\3\20\3\20\3\20\5\20\u00a8\n\20\3\20\3\20"+
		"\5\20\u00ac\n\20\3\20\3\20\3\21\3\21\3\21\3\21\3\21\3\21\3\22\3\22\3\22"+
		"\3\22\3\22\3\22\3\22\3\22\3\23\3\23\3\23\3\23\3\23\3\23\3\24\3\24\3\24"+
		"\3\24\3\25\3\25\3\25\3\25\3\25\3\25\3\26\3\26\3\26\3\26\3\26\3\26\3\27"+
		"\3\27\3\27\3\27\3\27\3\27\5\27\u00da\n\27\3\27\3\27\3\27\3\27\3\27\3\27"+
		"\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\3\27\7\27\u00ee"+
		"\n\27\f\27\16\27\u00f1\13\27\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3\30\3"+
		"\30\3\30\5\30\u00fd\n\30\3\31\3\31\3\31\5\31\u0102\n\31\3\31\3\31\3\32"+
		"\3\32\3\33\3\33\3\33\7\33\u010b\n\33\f\33\16\33\u010e\13\33\3\34\3\34"+
		"\3\35\3\35\3\36\3\36\3\36\3\36\7\36\u0118\n\36\f\36\16\36\u011b\13\36"+
		"\5\36\u011d\n\36\3\36\3\36\3\37\3\37\3\37\3\37\7\37\u0125\n\37\f\37\16"+
		"\37\u0128\13\37\5\37\u012a\n\37\3\37\3\37\3\37\2\3, \2\4\6\b\n\f\16\20"+
		"\22\24\26\30\32\34\36 \"$&(*,.\60\62\64\668:<\2\7\3\2\t\n\3\2#$\3\2%\'"+
		"\4\2\32\33\36\37\4\2\35\35  \u013a\2@\3\2\2\2\4D\3\2\2\2\6S\3\2\2\2\b"+
		"W\3\2\2\2\n`\3\2\2\2\fh\3\2\2\2\16o\3\2\2\2\20q\3\2\2\2\22s\3\2\2\2\24"+
		"\u0080\3\2\2\2\26\u0090\3\2\2\2\30\u0092\3\2\2\2\32\u009b\3\2\2\2\34\u009e"+
		"\3\2\2\2\36\u00a3\3\2\2\2 \u00af\3\2\2\2\"\u00b5\3\2\2\2$\u00bd\3\2\2"+
		"\2&\u00c3\3\2\2\2(\u00c7\3\2\2\2*\u00cd\3\2\2\2,\u00d9\3\2\2\2.\u00fc"+
		"\3\2\2\2\60\u00fe\3\2\2\2\62\u0105\3\2\2\2\64\u0107\3\2\2\2\66\u010f\3"+
		"\2\2\28\u0111\3\2\2\2:\u0113\3\2\2\2<\u0120\3\2\2\2>A\5\4\3\2?A\5\6\4"+
		"\2@>\3\2\2\2@?\3\2\2\2AB\3\2\2\2B@\3\2\2\2BC\3\2\2\2C\3\3\2\2\2DE\7\3"+
		"\2\2EH\7\16\2\2FG\7\25\2\2GI\5\16\b\2HF\3\2\2\2HI\3\2\2\2IO\3\2\2\2JM"+
		"\7(\2\2KN\5\66\34\2LN\58\35\2MK\3\2\2\2ML\3\2\2\2NP\3\2\2\2OJ\3\2\2\2"+
		"OP\3\2\2\2PQ\3\2\2\2QR\7\26\2\2R\5\3\2\2\2ST\7\4\2\2TU\5\b\5\2UV\5\30"+
		"\r\2V\7\3\2\2\2WX\7\16\2\2XZ\7\17\2\2Y[\5\n\6\2ZY\3\2\2\2Z[\3\2\2\2[\\"+
		"\3\2\2\2\\]\7\20\2\2]^\7\31\2\2^_\5\16\b\2_\t\3\2\2\2`e\5\f\7\2ab\7\27"+
		"\2\2bd\5\f\7\2ca\3\2\2\2dg\3\2\2\2ec\3\2\2\2ef\3\2\2\2f\13\3\2\2\2ge\3"+
		"\2\2\2hi\7\16\2\2ij\7\25\2\2jk\5\16\b\2k\r\3\2\2\2lp\5\20\t\2mp\5\22\n"+
		"\2np\5\24\13\2ol\3\2\2\2om\3\2\2\2on\3\2\2\2p\17\3\2\2\2qr\7\16\2\2r\21"+
		"\3\2\2\2s|\7\17\2\2ty\5\16\b\2uv\7\27\2\2vx\5\16\b\2wu\3\2\2\2x{\3\2\2"+
		"\2yw\3\2\2\2yz\3\2\2\2z}\3\2\2\2{y\3\2\2\2|t\3\2\2\2|}\3\2\2\2}~\3\2\2"+
		"\2~\177\7\20\2\2\177\23\3\2\2\2\u0080\u0081\7\23\2\2\u0081\u0082\5\16"+
		"\b\2\u0082\u0083\7\26\2\2\u0083\u0084\5\66\34\2\u0084\u0085\7\24\2\2\u0085"+
		"\25\3\2\2\2\u0086\u0091\5\30\r\2\u0087\u0091\5\32\16\2\u0088\u0091\5\34"+
		"\17\2\u0089\u0091\5\36\20\2\u008a\u0091\5 \21\2\u008b\u0091\5\"\22\2\u008c"+
		"\u0091\5$\23\2\u008d\u0091\5&\24\2\u008e\u0091\5(\25\2\u008f\u0091\5*"+
		"\26\2\u0090\u0086\3\2\2\2\u0090\u0087\3\2\2\2\u0090\u0088\3\2\2\2\u0090"+
		"\u0089\3\2\2\2\u0090\u008a\3\2\2\2\u0090\u008b\3\2\2\2\u0090\u008c\3\2"+
		"\2\2\u0090\u008d\3\2\2\2\u0090\u008e\3\2\2\2\u0090\u008f\3\2\2\2\u0091"+
		"\27\3\2\2\2\u0092\u0096\7\21\2\2\u0093\u0095\5\26\f\2\u0094\u0093\3\2"+
		"\2\2\u0095\u0098\3\2\2\2\u0096\u0094\3\2\2\2\u0096\u0097\3\2\2\2\u0097"+
		"\u0099\3\2\2\2\u0098\u0096\3\2\2\2\u0099\u009a\7\22\2\2\u009a\31\3\2\2"+
		"\2\u009b\u009c\5,\27\2\u009c\u009d\7\26\2\2\u009d\33\3\2\2\2\u009e\u009f"+
		"\7\16\2\2\u009f\u00a0\7(\2\2\u00a0\u00a1\5,\27\2\u00a1\u00a2\7\26\2\2"+
		"\u00a2\35\3\2\2\2\u00a3\u00a4\7\3\2\2\u00a4\u00a7\7\16\2\2\u00a5\u00a6"+
		"\7\25\2\2\u00a6\u00a8\5\16\b\2\u00a7\u00a5\3\2\2\2\u00a7\u00a8\3\2\2\2"+
		"\u00a8\u00ab\3\2\2\2\u00a9\u00aa\7(\2\2\u00aa\u00ac\5,\27\2\u00ab\u00a9"+
		"\3\2\2\2\u00ab\u00ac\3\2\2\2\u00ac\u00ad\3\2\2\2\u00ad\u00ae\7\26\2\2"+
		"\u00ae\37\3\2\2\2\u00af\u00b0\7\5\2\2\u00b0\u00b1\7\17\2\2\u00b1\u00b2"+
		"\5,\27\2\u00b2\u00b3\7\20\2\2\u00b3\u00b4\5\30\r\2\u00b4!\3\2\2\2\u00b5"+
		"\u00b6\7\5\2\2\u00b6\u00b7\7\17\2\2\u00b7\u00b8\5,\27\2\u00b8\u00b9\7"+
		"\20\2\2\u00b9\u00ba\5\30\r\2\u00ba\u00bb\7\6\2\2\u00bb\u00bc\5\30\r\2"+
		"\u00bc#\3\2\2\2\u00bd\u00be\7\7\2\2\u00be\u00bf\7\17\2\2\u00bf\u00c0\5"+
		",\27\2\u00c0\u00c1\7\20\2\2\u00c1\u00c2\5\30\r\2\u00c2%\3\2\2\2\u00c3"+
		"\u00c4\7\b\2\2\u00c4\u00c5\5,\27\2\u00c5\u00c6\7\26\2\2\u00c6\'\3\2\2"+
		"\2\u00c7\u00c8\t\2\2\2\u00c8\u00c9\7\17\2\2\u00c9\u00ca\5,\27\2\u00ca"+
		"\u00cb\7\20\2\2\u00cb\u00cc\7\26\2\2\u00cc)\3\2\2\2\u00cd\u00ce\7\13\2"+
		"\2\u00ce\u00cf\7\17\2\2\u00cf\u00d0\7\16\2\2\u00d0\u00d1\7\20\2\2\u00d1"+
		"\u00d2\7\26\2\2\u00d2+\3\2\2\2\u00d3\u00d4\b\27\1\2\u00d4\u00d5\t\3\2"+
		"\2\u00d5\u00da\5,\27\n\u00d6\u00d7\7\34\2\2\u00d7\u00da\5,\27\t\u00d8"+
		"\u00da\5.\30\2\u00d9\u00d3\3\2\2\2\u00d9\u00d6\3\2\2\2\u00d9\u00d8\3\2"+
		"\2\2\u00da\u00ef\3\2\2\2\u00db\u00dc\f\b\2\2\u00dc\u00dd\t\4\2\2\u00dd"+
		"\u00ee\5,\27\t\u00de\u00df\f\7\2\2\u00df\u00e0\t\3\2\2\u00e0\u00ee\5,"+
		"\27\b\u00e1\u00e2\f\6\2\2\u00e2\u00e3\t\5\2\2\u00e3\u00ee\5,\27\7\u00e4"+
		"\u00e5\f\5\2\2\u00e5\u00e6\t\6\2\2\u00e6\u00ee\5,\27\6\u00e7\u00e8\f\4"+
		"\2\2\u00e8\u00e9\7!\2\2\u00e9\u00ee\5,\27\5\u00ea\u00eb\f\3\2\2\u00eb"+
		"\u00ec\7\"\2\2\u00ec\u00ee\5,\27\4\u00ed\u00db\3\2\2\2\u00ed\u00de\3\2"+
		"\2\2\u00ed\u00e1\3\2\2\2\u00ed\u00e4\3\2\2\2\u00ed\u00e7\3\2\2\2\u00ed"+
		"\u00ea\3\2\2\2\u00ee\u00f1\3\2\2\2\u00ef\u00ed\3\2\2\2\u00ef\u00f0\3\2"+
		"\2\2\u00f0-\3\2\2\2\u00f1\u00ef\3\2\2\2\u00f2\u00f3\7\17\2\2\u00f3\u00f4"+
		"\5,\27\2\u00f4\u00f5\7\20\2\2\u00f5\u00fd\3\2\2\2\u00f6\u00fd\5\66\34"+
		"\2\u00f7\u00fd\58\35\2\u00f8\u00fd\5:\36\2\u00f9\u00fd\5<\37\2\u00fa\u00fd"+
		"\5\60\31\2\u00fb\u00fd\5\62\32\2\u00fc\u00f2\3\2\2\2\u00fc\u00f6\3\2\2"+
		"\2\u00fc\u00f7\3\2\2\2\u00fc\u00f8\3\2\2\2\u00fc\u00f9\3\2\2\2\u00fc\u00fa"+
		"\3\2\2\2\u00fc\u00fb\3\2\2\2\u00fd/\3\2\2\2\u00fe\u00ff\7\16\2\2\u00ff"+
		"\u0101\7\17\2\2\u0100\u0102\5\64\33\2\u0101\u0100\3\2\2\2\u0101\u0102"+
		"\3\2\2\2\u0102\u0103\3\2\2\2\u0103\u0104\7\20\2\2\u0104\61\3\2\2\2\u0105"+
		"\u0106\7\16\2\2\u0106\63\3\2\2\2\u0107\u010c\5,\27\2\u0108\u0109\7\27"+
		"\2\2\u0109\u010b\5,\27\2\u010a\u0108\3\2\2\2\u010b\u010e\3\2\2\2\u010c"+
		"\u010a\3\2\2\2\u010c\u010d\3\2\2\2\u010d\65\3\2\2\2\u010e\u010c\3\2\2"+
		"\2\u010f\u0110\7\f\2\2\u0110\67\3\2\2\2\u0111\u0112\7\r\2\2\u01129\3\2"+
		"\2\2\u0113\u011c\7\17\2\2\u0114\u0119\5,\27\2\u0115\u0116\7\27\2\2\u0116"+
		"\u0118\5,\27\2\u0117\u0115\3\2\2\2\u0118\u011b\3\2\2\2\u0119\u0117\3\2"+
		"\2\2\u0119\u011a\3\2\2\2\u011a\u011d\3\2\2\2\u011b\u0119\3\2\2\2\u011c"+
		"\u0114\3\2\2\2\u011c\u011d\3\2\2\2\u011d\u011e\3\2\2\2\u011e\u011f\7\20"+
		"\2\2\u011f;\3\2\2\2\u0120\u0129\7\23\2\2\u0121\u0126\5,\27\2\u0122\u0123"+
		"\7\27\2\2\u0123\u0125\5,\27\2\u0124\u0122\3\2\2\2\u0125\u0128\3\2\2\2"+
		"\u0126\u0124\3\2\2\2\u0126\u0127\3\2\2\2\u0127\u012a\3\2\2\2\u0128\u0126"+
		"\3\2\2\2\u0129\u0121\3\2\2\2\u0129\u012a\3\2\2\2\u012a\u012b\3\2\2\2\u012b"+
		"\u012c\7\24\2\2\u012c=\3\2\2\2\32@BHMOZeoy|\u0090\u0096\u00a7\u00ab\u00d9"+
		"\u00ed\u00ef\u00fc\u0101\u010c\u0119\u011c\u0126\u0129";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}