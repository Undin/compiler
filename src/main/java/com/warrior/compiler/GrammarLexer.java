// Generated from /Users/warrior/Programming/compiler/src/main/java/com/warrior/compiler/Grammar.g4 by ANTLR 4.5.1
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
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, IntLiteral=8, 
		BoolLiteral=9, Identifier=10, LPAREN=11, RPAREN=12, LBRACE=13, RBRACE=14, 
		LBRACK=15, RBRACK=16, COLON=17, SEMICOLON=18, COMMA=19, DOT=20, ARROW=21, 
		GT=22, LT=23, BANG=24, EQUAL=25, LE=26, GE=27, NOTEQUAL=28, AND=29, OR=30, 
		ADD=31, SUB=32, MUL=33, DIV=34, MOD=35, ASSIGN=36, WS=37, COMMENT=38, 
		LINE_COMMENT=39;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "IntLiteral", 
		"BoolLiteral", "Identifier", "Letter", "Digit", "NonZeroDigit", "LPAREN", 
		"RPAREN", "LBRACE", "RBRACE", "LBRACK", "RBRACK", "COLON", "SEMICOLON", 
		"COMMA", "DOT", "ARROW", "GT", "LT", "BANG", "EQUAL", "LE", "GE", "NOTEQUAL", 
		"AND", "OR", "ADD", "SUB", "MUL", "DIV", "MOD", "ASSIGN", "WS", "COMMENT", 
		"LINE_COMMENT"
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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2)\u00fb\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\3"+
		"\2\3\2\3\2\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\5\3\6"+
		"\3\6\3\6\3\6\3\6\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3"+
		"\b\3\b\3\b\3\t\3\t\3\t\7\t\u0081\n\t\f\t\16\t\u0084\13\t\5\t\u0086\n\t"+
		"\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\5\n\u0091\n\n\3\13\3\13\3\13\7\13"+
		"\u0096\n\13\f\13\16\13\u0099\13\13\3\f\3\f\3\r\3\r\3\16\3\16\3\17\3\17"+
		"\3\20\3\20\3\21\3\21\3\22\3\22\3\23\3\23\3\24\3\24\3\25\3\25\3\26\3\26"+
		"\3\27\3\27\3\30\3\30\3\31\3\31\3\31\3\32\3\32\3\33\3\33\3\34\3\34\3\35"+
		"\3\35\3\35\3\36\3\36\3\36\3\37\3\37\3\37\3 \3 \3 \3!\3!\3!\3\"\3\"\3\""+
		"\3#\3#\3$\3$\3%\3%\3&\3&\3\'\3\'\3(\3(\3)\6)\u00dd\n)\r)\16)\u00de\3)"+
		"\3)\3*\3*\3*\3*\7*\u00e7\n*\f*\16*\u00ea\13*\3*\3*\3*\3*\3*\3+\3+\3+\3"+
		"+\7+\u00f5\n+\f+\16+\u00f8\13+\3+\3+\3\u00e8\2,\3\3\5\4\7\5\t\6\13\7\r"+
		"\b\17\t\21\n\23\13\25\f\27\2\31\2\33\2\35\r\37\16!\17#\20%\21\'\22)\23"+
		"+\24-\25/\26\61\27\63\30\65\31\67\329\33;\34=\35?\36A\37C E!G\"I#K$M%"+
		"O&Q\'S(U)\3\2\7\5\2C\\aac|\3\2\62;\3\2\63;\5\2\13\f\17\17\"\"\4\2\f\f"+
		"\17\17\u00ff\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2"+
		"\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2"+
		"\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2"+
		"\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2"+
		"\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2\2"+
		"\2A\3\2\2\2\2C\3\2\2\2\2E\3\2\2\2\2G\3\2\2\2\2I\3\2\2\2\2K\3\2\2\2\2M"+
		"\3\2\2\2\2O\3\2\2\2\2Q\3\2\2\2\2S\3\2\2\2\2U\3\2\2\2\3W\3\2\2\2\5Z\3\2"+
		"\2\2\7]\3\2\2\2\tb\3\2\2\2\13h\3\2\2\2\ro\3\2\2\2\17u\3\2\2\2\21\u0085"+
		"\3\2\2\2\23\u0090\3\2\2\2\25\u0092\3\2\2\2\27\u009a\3\2\2\2\31\u009c\3"+
		"\2\2\2\33\u009e\3\2\2\2\35\u00a0\3\2\2\2\37\u00a2\3\2\2\2!\u00a4\3\2\2"+
		"\2#\u00a6\3\2\2\2%\u00a8\3\2\2\2\'\u00aa\3\2\2\2)\u00ac\3\2\2\2+\u00ae"+
		"\3\2\2\2-\u00b0\3\2\2\2/\u00b2\3\2\2\2\61\u00b4\3\2\2\2\63\u00b7\3\2\2"+
		"\2\65\u00b9\3\2\2\2\67\u00bb\3\2\2\29\u00bd\3\2\2\2;\u00c0\3\2\2\2=\u00c3"+
		"\3\2\2\2?\u00c6\3\2\2\2A\u00c9\3\2\2\2C\u00cc\3\2\2\2E\u00cf\3\2\2\2G"+
		"\u00d1\3\2\2\2I\u00d3\3\2\2\2K\u00d5\3\2\2\2M\u00d7\3\2\2\2O\u00d9\3\2"+
		"\2\2Q\u00dc\3\2\2\2S\u00e2\3\2\2\2U\u00f0\3\2\2\2WX\7h\2\2XY\7p\2\2Y\4"+
		"\3\2\2\2Z[\7k\2\2[\\\7h\2\2\\\6\3\2\2\2]^\7g\2\2^_\7n\2\2_`\7u\2\2`a\7"+
		"g\2\2a\b\3\2\2\2bc\7y\2\2cd\7j\2\2de\7k\2\2ef\7n\2\2fg\7g\2\2g\n\3\2\2"+
		"\2hi\7t\2\2ij\7g\2\2jk\7v\2\2kl\7w\2\2lm\7t\2\2mn\7p\2\2n\f\3\2\2\2op"+
		"\7r\2\2pq\7t\2\2qr\7k\2\2rs\7p\2\2st\7v\2\2t\16\3\2\2\2uv\7r\2\2vw\7t"+
		"\2\2wx\7k\2\2xy\7p\2\2yz\7v\2\2z{\7n\2\2{|\7p\2\2|\20\3\2\2\2}\u0086\7"+
		"\62\2\2~\u0082\5\33\16\2\177\u0081\5\31\r\2\u0080\177\3\2\2\2\u0081\u0084"+
		"\3\2\2\2\u0082\u0080\3\2\2\2\u0082\u0083\3\2\2\2\u0083\u0086\3\2\2\2\u0084"+
		"\u0082\3\2\2\2\u0085}\3\2\2\2\u0085~\3\2\2\2\u0086\22\3\2\2\2\u0087\u0088"+
		"\7v\2\2\u0088\u0089\7t\2\2\u0089\u008a\7w\2\2\u008a\u0091\7g\2\2\u008b"+
		"\u008c\7h\2\2\u008c\u008d\7c\2\2\u008d\u008e\7n\2\2\u008e\u008f\7u\2\2"+
		"\u008f\u0091\7g\2\2\u0090\u0087\3\2\2\2\u0090\u008b\3\2\2\2\u0091\24\3"+
		"\2\2\2\u0092\u0097\5\27\f\2\u0093\u0096\5\27\f\2\u0094\u0096\5\31\r\2"+
		"\u0095\u0093\3\2\2\2\u0095\u0094\3\2\2\2\u0096\u0099\3\2\2\2\u0097\u0095"+
		"\3\2\2\2\u0097\u0098\3\2\2\2\u0098\26\3\2\2\2\u0099\u0097\3\2\2\2\u009a"+
		"\u009b\t\2\2\2\u009b\30\3\2\2\2\u009c\u009d\t\3\2\2\u009d\32\3\2\2\2\u009e"+
		"\u009f\t\4\2\2\u009f\34\3\2\2\2\u00a0\u00a1\7*\2\2\u00a1\36\3\2\2\2\u00a2"+
		"\u00a3\7+\2\2\u00a3 \3\2\2\2\u00a4\u00a5\7}\2\2\u00a5\"\3\2\2\2\u00a6"+
		"\u00a7\7\177\2\2\u00a7$\3\2\2\2\u00a8\u00a9\7]\2\2\u00a9&\3\2\2\2\u00aa"+
		"\u00ab\7_\2\2\u00ab(\3\2\2\2\u00ac\u00ad\7<\2\2\u00ad*\3\2\2\2\u00ae\u00af"+
		"\7=\2\2\u00af,\3\2\2\2\u00b0\u00b1\7.\2\2\u00b1.\3\2\2\2\u00b2\u00b3\7"+
		"\60\2\2\u00b3\60\3\2\2\2\u00b4\u00b5\7/\2\2\u00b5\u00b6\7@\2\2\u00b6\62"+
		"\3\2\2\2\u00b7\u00b8\7@\2\2\u00b8\64\3\2\2\2\u00b9\u00ba\7>\2\2\u00ba"+
		"\66\3\2\2\2\u00bb\u00bc\7#\2\2\u00bc8\3\2\2\2\u00bd\u00be\7?\2\2\u00be"+
		"\u00bf\7?\2\2\u00bf:\3\2\2\2\u00c0\u00c1\7>\2\2\u00c1\u00c2\7?\2\2\u00c2"+
		"<\3\2\2\2\u00c3\u00c4\7@\2\2\u00c4\u00c5\7?\2\2\u00c5>\3\2\2\2\u00c6\u00c7"+
		"\7#\2\2\u00c7\u00c8\7?\2\2\u00c8@\3\2\2\2\u00c9\u00ca\7(\2\2\u00ca\u00cb"+
		"\7(\2\2\u00cbB\3\2\2\2\u00cc\u00cd\7~\2\2\u00cd\u00ce\7~\2\2\u00ceD\3"+
		"\2\2\2\u00cf\u00d0\7-\2\2\u00d0F\3\2\2\2\u00d1\u00d2\7/\2\2\u00d2H\3\2"+
		"\2\2\u00d3\u00d4\7,\2\2\u00d4J\3\2\2\2\u00d5\u00d6\7\61\2\2\u00d6L\3\2"+
		"\2\2\u00d7\u00d8\7\'\2\2\u00d8N\3\2\2\2\u00d9\u00da\7?\2\2\u00daP\3\2"+
		"\2\2\u00db\u00dd\t\5\2\2\u00dc\u00db\3\2\2\2\u00dd\u00de\3\2\2\2\u00de"+
		"\u00dc\3\2\2\2\u00de\u00df\3\2\2\2\u00df\u00e0\3\2\2\2\u00e0\u00e1\b)"+
		"\2\2\u00e1R\3\2\2\2\u00e2\u00e3\7\61\2\2\u00e3\u00e4\7,\2\2\u00e4\u00e8"+
		"\3\2\2\2\u00e5\u00e7\13\2\2\2\u00e6\u00e5\3\2\2\2\u00e7\u00ea\3\2\2\2"+
		"\u00e8\u00e9\3\2\2\2\u00e8\u00e6\3\2\2\2\u00e9\u00eb\3\2\2\2\u00ea\u00e8"+
		"\3\2\2\2\u00eb\u00ec\7,\2\2\u00ec\u00ed\7\61\2\2\u00ed\u00ee\3\2\2\2\u00ee"+
		"\u00ef\b*\2\2\u00efT\3\2\2\2\u00f0\u00f1\7\61\2\2\u00f1\u00f2\7\61\2\2"+
		"\u00f2\u00f6\3\2\2\2\u00f3\u00f5\n\6\2\2\u00f4\u00f3\3\2\2\2\u00f5\u00f8"+
		"\3\2\2\2\u00f6\u00f4\3\2\2\2\u00f6\u00f7\3\2\2\2\u00f7\u00f9\3\2\2\2\u00f8"+
		"\u00f6\3\2\2\2\u00f9\u00fa\b+\2\2\u00faV\3\2\2\2\13\2\u0082\u0085\u0090"+
		"\u0095\u0097\u00de\u00e8\u00f6\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}