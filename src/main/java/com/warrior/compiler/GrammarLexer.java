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
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, Identifier=6, IntLiteral=7, BoolLiteral=8, 
		LPAREN=9, RPAREN=10, LBRACE=11, RBRACE=12, LBRACK=13, RBRACK=14, COLON=15, 
		SEMICOLON=16, COMMA=17, DOT=18, ARROW=19, GT=20, LT=21, BANG=22, EQUAL=23, 
		LE=24, GE=25, NOTEQUAL=26, AND=27, OR=28, ADD=29, SUB=30, MUL=31, DIV=32, 
		MOD=33, ASSIGN=34, WS=35, COMMENT=36, LINE_COMMENT=37;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "T__1", "T__2", "T__3", "T__4", "Identifier", "IntLiteral", "BoolLiteral", 
		"Letter", "Digit", "NonZeroDigit", "LPAREN", "RPAREN", "LBRACE", "RBRACE", 
		"LBRACK", "RBRACK", "COLON", "SEMICOLON", "COMMA", "DOT", "ARROW", "GT", 
		"LT", "BANG", "EQUAL", "LE", "GE", "NOTEQUAL", "AND", "OR", "ADD", "SUB", 
		"MUL", "DIV", "MOD", "ASSIGN", "WS", "COMMENT", "LINE_COMMENT"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'fn'", "'if'", "'else'", "'while'", "'return'", null, null, null, 
		"'('", "')'", "'{'", "'}'", "'['", "']'", "':'", "';'", "','", "'.'", 
		"'->'", "'>'", "'<'", "'!'", "'=='", "'<='", "'>='", "'!='", "'&&'", "'||'", 
		"'+'", "'-'", "'*'", "'/'", "'%'", "'='"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, "Identifier", "IntLiteral", "BoolLiteral", 
		"LPAREN", "RPAREN", "LBRACE", "RBRACE", "LBRACK", "RBRACK", "COLON", "SEMICOLON", 
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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\'\u00e9\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\3\2\3\2\3\2\3"+
		"\3\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6"+
		"\3\6\3\6\3\6\3\7\3\7\3\7\7\7o\n\7\f\7\16\7r\13\7\3\b\3\b\3\b\7\bw\n\b"+
		"\f\b\16\bz\13\b\5\b|\n\b\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\5\t\u0087"+
		"\n\t\3\n\3\n\3\13\3\13\3\f\3\f\3\r\3\r\3\16\3\16\3\17\3\17\3\20\3\20\3"+
		"\21\3\21\3\22\3\22\3\23\3\23\3\24\3\24\3\25\3\25\3\26\3\26\3\27\3\27\3"+
		"\27\3\30\3\30\3\31\3\31\3\32\3\32\3\33\3\33\3\33\3\34\3\34\3\34\3\35\3"+
		"\35\3\35\3\36\3\36\3\36\3\37\3\37\3\37\3 \3 \3 \3!\3!\3\"\3\"\3#\3#\3"+
		"$\3$\3%\3%\3&\3&\3\'\6\'\u00cb\n\'\r\'\16\'\u00cc\3\'\3\'\3(\3(\3(\3("+
		"\7(\u00d5\n(\f(\16(\u00d8\13(\3(\3(\3(\3(\3(\3)\3)\3)\3)\7)\u00e3\n)\f"+
		")\16)\u00e6\13)\3)\3)\3\u00d6\2*\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23"+
		"\2\25\2\27\2\31\13\33\f\35\r\37\16!\17#\20%\21\'\22)\23+\24-\25/\26\61"+
		"\27\63\30\65\31\67\329\33;\34=\35?\36A\37C E!G\"I#K$M%O&Q\'\3\2\7\5\2"+
		"C\\aac|\3\2\62;\3\2\63;\5\2\13\f\17\17\"\"\4\2\f\f\17\17\u00ed\2\3\3\2"+
		"\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17"+
		"\3\2\2\2\2\21\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2"+
		"\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2"+
		"\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3"+
		"\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2\2"+
		"\2\2E\3\2\2\2\2G\3\2\2\2\2I\3\2\2\2\2K\3\2\2\2\2M\3\2\2\2\2O\3\2\2\2\2"+
		"Q\3\2\2\2\3S\3\2\2\2\5V\3\2\2\2\7Y\3\2\2\2\t^\3\2\2\2\13d\3\2\2\2\rk\3"+
		"\2\2\2\17{\3\2\2\2\21\u0086\3\2\2\2\23\u0088\3\2\2\2\25\u008a\3\2\2\2"+
		"\27\u008c\3\2\2\2\31\u008e\3\2\2\2\33\u0090\3\2\2\2\35\u0092\3\2\2\2\37"+
		"\u0094\3\2\2\2!\u0096\3\2\2\2#\u0098\3\2\2\2%\u009a\3\2\2\2\'\u009c\3"+
		"\2\2\2)\u009e\3\2\2\2+\u00a0\3\2\2\2-\u00a2\3\2\2\2/\u00a5\3\2\2\2\61"+
		"\u00a7\3\2\2\2\63\u00a9\3\2\2\2\65\u00ab\3\2\2\2\67\u00ae\3\2\2\29\u00b1"+
		"\3\2\2\2;\u00b4\3\2\2\2=\u00b7\3\2\2\2?\u00ba\3\2\2\2A\u00bd\3\2\2\2C"+
		"\u00bf\3\2\2\2E\u00c1\3\2\2\2G\u00c3\3\2\2\2I\u00c5\3\2\2\2K\u00c7\3\2"+
		"\2\2M\u00ca\3\2\2\2O\u00d0\3\2\2\2Q\u00de\3\2\2\2ST\7h\2\2TU\7p\2\2U\4"+
		"\3\2\2\2VW\7k\2\2WX\7h\2\2X\6\3\2\2\2YZ\7g\2\2Z[\7n\2\2[\\\7u\2\2\\]\7"+
		"g\2\2]\b\3\2\2\2^_\7y\2\2_`\7j\2\2`a\7k\2\2ab\7n\2\2bc\7g\2\2c\n\3\2\2"+
		"\2de\7t\2\2ef\7g\2\2fg\7v\2\2gh\7w\2\2hi\7t\2\2ij\7p\2\2j\f\3\2\2\2kp"+
		"\5\23\n\2lo\5\23\n\2mo\5\25\13\2nl\3\2\2\2nm\3\2\2\2or\3\2\2\2pn\3\2\2"+
		"\2pq\3\2\2\2q\16\3\2\2\2rp\3\2\2\2s|\7\62\2\2tx\5\27\f\2uw\5\25\13\2v"+
		"u\3\2\2\2wz\3\2\2\2xv\3\2\2\2xy\3\2\2\2y|\3\2\2\2zx\3\2\2\2{s\3\2\2\2"+
		"{t\3\2\2\2|\20\3\2\2\2}~\7v\2\2~\177\7t\2\2\177\u0080\7w\2\2\u0080\u0087"+
		"\7g\2\2\u0081\u0082\7h\2\2\u0082\u0083\7c\2\2\u0083\u0084\7n\2\2\u0084"+
		"\u0085\7u\2\2\u0085\u0087\7g\2\2\u0086}\3\2\2\2\u0086\u0081\3\2\2\2\u0087"+
		"\22\3\2\2\2\u0088\u0089\t\2\2\2\u0089\24\3\2\2\2\u008a\u008b\t\3\2\2\u008b"+
		"\26\3\2\2\2\u008c\u008d\t\4\2\2\u008d\30\3\2\2\2\u008e\u008f\7*\2\2\u008f"+
		"\32\3\2\2\2\u0090\u0091\7+\2\2\u0091\34\3\2\2\2\u0092\u0093\7}\2\2\u0093"+
		"\36\3\2\2\2\u0094\u0095\7\177\2\2\u0095 \3\2\2\2\u0096\u0097\7]\2\2\u0097"+
		"\"\3\2\2\2\u0098\u0099\7_\2\2\u0099$\3\2\2\2\u009a\u009b\7<\2\2\u009b"+
		"&\3\2\2\2\u009c\u009d\7=\2\2\u009d(\3\2\2\2\u009e\u009f\7.\2\2\u009f*"+
		"\3\2\2\2\u00a0\u00a1\7\60\2\2\u00a1,\3\2\2\2\u00a2\u00a3\7/\2\2\u00a3"+
		"\u00a4\7@\2\2\u00a4.\3\2\2\2\u00a5\u00a6\7@\2\2\u00a6\60\3\2\2\2\u00a7"+
		"\u00a8\7>\2\2\u00a8\62\3\2\2\2\u00a9\u00aa\7#\2\2\u00aa\64\3\2\2\2\u00ab"+
		"\u00ac\7?\2\2\u00ac\u00ad\7?\2\2\u00ad\66\3\2\2\2\u00ae\u00af\7>\2\2\u00af"+
		"\u00b0\7?\2\2\u00b08\3\2\2\2\u00b1\u00b2\7@\2\2\u00b2\u00b3\7?\2\2\u00b3"+
		":\3\2\2\2\u00b4\u00b5\7#\2\2\u00b5\u00b6\7?\2\2\u00b6<\3\2\2\2\u00b7\u00b8"+
		"\7(\2\2\u00b8\u00b9\7(\2\2\u00b9>\3\2\2\2\u00ba\u00bb\7~\2\2\u00bb\u00bc"+
		"\7~\2\2\u00bc@\3\2\2\2\u00bd\u00be\7-\2\2\u00beB\3\2\2\2\u00bf\u00c0\7"+
		"/\2\2\u00c0D\3\2\2\2\u00c1\u00c2\7,\2\2\u00c2F\3\2\2\2\u00c3\u00c4\7\61"+
		"\2\2\u00c4H\3\2\2\2\u00c5\u00c6\7\'\2\2\u00c6J\3\2\2\2\u00c7\u00c8\7?"+
		"\2\2\u00c8L\3\2\2\2\u00c9\u00cb\t\5\2\2\u00ca\u00c9\3\2\2\2\u00cb\u00cc"+
		"\3\2\2\2\u00cc\u00ca\3\2\2\2\u00cc\u00cd\3\2\2\2\u00cd\u00ce\3\2\2\2\u00ce"+
		"\u00cf\b\'\2\2\u00cfN\3\2\2\2\u00d0\u00d1\7\61\2\2\u00d1\u00d2\7,\2\2"+
		"\u00d2\u00d6\3\2\2\2\u00d3\u00d5\13\2\2\2\u00d4\u00d3\3\2\2\2\u00d5\u00d8"+
		"\3\2\2\2\u00d6\u00d7\3\2\2\2\u00d6\u00d4\3\2\2\2\u00d7\u00d9\3\2\2\2\u00d8"+
		"\u00d6\3\2\2\2\u00d9\u00da\7,\2\2\u00da\u00db\7\61\2\2\u00db\u00dc\3\2"+
		"\2\2\u00dc\u00dd\b(\2\2\u00ddP\3\2\2\2\u00de\u00df\7\61\2\2\u00df\u00e0"+
		"\7\61\2\2\u00e0\u00e4\3\2\2\2\u00e1\u00e3\n\6\2\2\u00e2\u00e1\3\2\2\2"+
		"\u00e3\u00e6\3\2\2\2\u00e4\u00e2\3\2\2\2\u00e4\u00e5\3\2\2\2\u00e5\u00e7"+
		"\3\2\2\2\u00e6\u00e4\3\2\2\2\u00e7\u00e8\b)\2\2\u00e8R\3\2\2\2\13\2np"+
		"x{\u0086\u00cc\u00d6\u00e4\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}