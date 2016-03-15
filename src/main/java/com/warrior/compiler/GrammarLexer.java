// Generated from /Users/warrior/Programming/Compiler/src/main/java/com/warrior/compiler/Grammar.g4 by ANTLR 4.5.1
package com.warrior.compiler;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class GrammarLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.5.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, Identifier=3, IntLiteral=4, BoolLiteral=5, LPAREN=6, RPAREN=7, 
		LBRACE=8, RBRACE=9, LBRACK=10, RBRACK=11, COLON=12, SEMICOLON=13, COMMA=14, 
		DOT=15, ARROW=16, GT=17, LT=18, BANG=19, EQUAL=20, LE=21, GE=22, NOTEQUAL=23, 
		AND=24, OR=25, ADD=26, SUB=27, MUL=28, DIV=29, MOD=30, ASSIGN=31, WS=32, 
		COMMENT=33, LINE_COMMENT=34;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "T__1", "Identifier", "IntLiteral", "BoolLiteral", "Letter", "Digit", 
		"NonZeroDigit", "LPAREN", "RPAREN", "LBRACE", "RBRACE", "LBRACK", "RBRACK", 
		"COLON", "SEMICOLON", "COMMA", "DOT", "ARROW", "GT", "LT", "BANG", "EQUAL", 
		"LE", "GE", "NOTEQUAL", "AND", "OR", "ADD", "SUB", "MUL", "DIV", "MOD", 
		"ASSIGN", "WS", "COMMENT", "LINE_COMMENT"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'fn'", "'if'", null, null, null, "'('", "')'", "'{'", "'}'", "'['", 
		"']'", "':'", "';'", "','", "'.'", "'->'", "'>'", "'<'", "'!'", "'=='", 
		"'<='", "'>='", "'!='", "'&&'", "'||'", "'+'", "'-'", "'*'", "'/'", "'%'", 
		"'='"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, "Identifier", "IntLiteral", "BoolLiteral", "LPAREN", 
		"RPAREN", "LBRACE", "RBRACE", "LBRACK", "RBRACK", "COLON", "SEMICOLON", 
		"COMMA", "DOT", "ARROW", "GT", "LT", "BANG", "EQUAL", "LE", "GE", "NOTEQUAL", 
		"AND", "OR", "ADD", "SUB", "MUL", "DIV", "MOD", "ASSIGN", "WS", "COMMENT", 
		"LINE_COMMENT"
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


	public GrammarLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Grammar.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2$\u00d1\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\3\2\3\2\3\2\3\3\3\3\3\3\3\4\3\4\3"+
		"\4\7\4W\n\4\f\4\16\4Z\13\4\3\5\3\5\3\5\7\5_\n\5\f\5\16\5b\13\5\5\5d\n"+
		"\5\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\5\6o\n\6\3\7\3\7\3\b\3\b\3\t\3"+
		"\t\3\n\3\n\3\13\3\13\3\f\3\f\3\r\3\r\3\16\3\16\3\17\3\17\3\20\3\20\3\21"+
		"\3\21\3\22\3\22\3\23\3\23\3\24\3\24\3\24\3\25\3\25\3\26\3\26\3\27\3\27"+
		"\3\30\3\30\3\30\3\31\3\31\3\31\3\32\3\32\3\32\3\33\3\33\3\33\3\34\3\34"+
		"\3\34\3\35\3\35\3\35\3\36\3\36\3\37\3\37\3 \3 \3!\3!\3\"\3\"\3#\3#\3$"+
		"\6$\u00b3\n$\r$\16$\u00b4\3$\3$\3%\3%\3%\3%\7%\u00bd\n%\f%\16%\u00c0\13"+
		"%\3%\3%\3%\3%\3%\3&\3&\3&\3&\7&\u00cb\n&\f&\16&\u00ce\13&\3&\3&\3\u00be"+
		"\2\'\3\3\5\4\7\5\t\6\13\7\r\2\17\2\21\2\23\b\25\t\27\n\31\13\33\f\35\r"+
		"\37\16!\17#\20%\21\'\22)\23+\24-\25/\26\61\27\63\30\65\31\67\329\33;\34"+
		"=\35?\36A\37C E!G\"I#K$\3\2\7\5\2C\\aac|\3\2\62;\3\2\63;\5\2\13\f\17\17"+
		"\"\"\4\2\f\f\17\17\u00d5\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2"+
		"\2\2\13\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2"+
		"\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2"+
		"\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2"+
		"\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2"+
		"\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2\2\2\2E\3\2\2\2\2G\3\2\2\2\2I\3\2\2\2\2"+
		"K\3\2\2\2\3M\3\2\2\2\5P\3\2\2\2\7S\3\2\2\2\tc\3\2\2\2\13n\3\2\2\2\rp\3"+
		"\2\2\2\17r\3\2\2\2\21t\3\2\2\2\23v\3\2\2\2\25x\3\2\2\2\27z\3\2\2\2\31"+
		"|\3\2\2\2\33~\3\2\2\2\35\u0080\3\2\2\2\37\u0082\3\2\2\2!\u0084\3\2\2\2"+
		"#\u0086\3\2\2\2%\u0088\3\2\2\2\'\u008a\3\2\2\2)\u008d\3\2\2\2+\u008f\3"+
		"\2\2\2-\u0091\3\2\2\2/\u0093\3\2\2\2\61\u0096\3\2\2\2\63\u0099\3\2\2\2"+
		"\65\u009c\3\2\2\2\67\u009f\3\2\2\29\u00a2\3\2\2\2;\u00a5\3\2\2\2=\u00a7"+
		"\3\2\2\2?\u00a9\3\2\2\2A\u00ab\3\2\2\2C\u00ad\3\2\2\2E\u00af\3\2\2\2G"+
		"\u00b2\3\2\2\2I\u00b8\3\2\2\2K\u00c6\3\2\2\2MN\7h\2\2NO\7p\2\2O\4\3\2"+
		"\2\2PQ\7k\2\2QR\7h\2\2R\6\3\2\2\2SX\5\r\7\2TW\5\r\7\2UW\5\17\b\2VT\3\2"+
		"\2\2VU\3\2\2\2WZ\3\2\2\2XV\3\2\2\2XY\3\2\2\2Y\b\3\2\2\2ZX\3\2\2\2[d\7"+
		"\62\2\2\\`\5\21\t\2]_\5\17\b\2^]\3\2\2\2_b\3\2\2\2`^\3\2\2\2`a\3\2\2\2"+
		"ad\3\2\2\2b`\3\2\2\2c[\3\2\2\2c\\\3\2\2\2d\n\3\2\2\2ef\7v\2\2fg\7t\2\2"+
		"gh\7w\2\2ho\7g\2\2ij\7h\2\2jk\7c\2\2kl\7n\2\2lm\7u\2\2mo\7g\2\2ne\3\2"+
		"\2\2ni\3\2\2\2o\f\3\2\2\2pq\t\2\2\2q\16\3\2\2\2rs\t\3\2\2s\20\3\2\2\2"+
		"tu\t\4\2\2u\22\3\2\2\2vw\7*\2\2w\24\3\2\2\2xy\7+\2\2y\26\3\2\2\2z{\7}"+
		"\2\2{\30\3\2\2\2|}\7\177\2\2}\32\3\2\2\2~\177\7]\2\2\177\34\3\2\2\2\u0080"+
		"\u0081\7_\2\2\u0081\36\3\2\2\2\u0082\u0083\7<\2\2\u0083 \3\2\2\2\u0084"+
		"\u0085\7=\2\2\u0085\"\3\2\2\2\u0086\u0087\7.\2\2\u0087$\3\2\2\2\u0088"+
		"\u0089\7\60\2\2\u0089&\3\2\2\2\u008a\u008b\7/\2\2\u008b\u008c\7@\2\2\u008c"+
		"(\3\2\2\2\u008d\u008e\7@\2\2\u008e*\3\2\2\2\u008f\u0090\7>\2\2\u0090,"+
		"\3\2\2\2\u0091\u0092\7#\2\2\u0092.\3\2\2\2\u0093\u0094\7?\2\2\u0094\u0095"+
		"\7?\2\2\u0095\60\3\2\2\2\u0096\u0097\7>\2\2\u0097\u0098\7?\2\2\u0098\62"+
		"\3\2\2\2\u0099\u009a\7@\2\2\u009a\u009b\7?\2\2\u009b\64\3\2\2\2\u009c"+
		"\u009d\7#\2\2\u009d\u009e\7?\2\2\u009e\66\3\2\2\2\u009f\u00a0\7(\2\2\u00a0"+
		"\u00a1\7(\2\2\u00a18\3\2\2\2\u00a2\u00a3\7~\2\2\u00a3\u00a4\7~\2\2\u00a4"+
		":\3\2\2\2\u00a5\u00a6\7-\2\2\u00a6<\3\2\2\2\u00a7\u00a8\7/\2\2\u00a8>"+
		"\3\2\2\2\u00a9\u00aa\7,\2\2\u00aa@\3\2\2\2\u00ab\u00ac\7\61\2\2\u00ac"+
		"B\3\2\2\2\u00ad\u00ae\7\'\2\2\u00aeD\3\2\2\2\u00af\u00b0\7?\2\2\u00b0"+
		"F\3\2\2\2\u00b1\u00b3\t\5\2\2\u00b2\u00b1\3\2\2\2\u00b3\u00b4\3\2\2\2"+
		"\u00b4\u00b2\3\2\2\2\u00b4\u00b5\3\2\2\2\u00b5\u00b6\3\2\2\2\u00b6\u00b7"+
		"\b$\2\2\u00b7H\3\2\2\2\u00b8\u00b9\7\61\2\2\u00b9\u00ba\7,\2\2\u00ba\u00be"+
		"\3\2\2\2\u00bb\u00bd\13\2\2\2\u00bc\u00bb\3\2\2\2\u00bd\u00c0\3\2\2\2"+
		"\u00be\u00bf\3\2\2\2\u00be\u00bc\3\2\2\2\u00bf\u00c1\3\2\2\2\u00c0\u00be"+
		"\3\2\2\2\u00c1\u00c2\7,\2\2\u00c2\u00c3\7\61\2\2\u00c3\u00c4\3\2\2\2\u00c4"+
		"\u00c5\b%\2\2\u00c5J\3\2\2\2\u00c6\u00c7\7\61\2\2\u00c7\u00c8\7\61\2\2"+
		"\u00c8\u00cc\3\2\2\2\u00c9\u00cb\n\6\2\2\u00ca\u00c9\3\2\2\2\u00cb\u00ce"+
		"\3\2\2\2\u00cc\u00ca\3\2\2\2\u00cc\u00cd\3\2\2\2\u00cd\u00cf\3\2\2\2\u00ce"+
		"\u00cc\3\2\2\2\u00cf\u00d0\b&\2\2\u00d0L\3\2\2\2\13\2VX`cn\u00b4\u00be"+
		"\u00cc\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}