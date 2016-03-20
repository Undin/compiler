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
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, IntLiteral=9, 
		BoolLiteral=10, Identifier=11, LPAREN=12, RPAREN=13, LBRACE=14, RBRACE=15, 
		LBRACK=16, RBRACK=17, COLON=18, SEMICOLON=19, COMMA=20, DOT=21, ARROW=22, 
		GT=23, LT=24, BANG=25, EQUAL=26, LE=27, GE=28, NOTEQUAL=29, AND=30, OR=31, 
		ADD=32, SUB=33, MUL=34, DIV=35, MOD=36, ASSIGN=37, WS=38, COMMENT=39, 
		LINE_COMMENT=40;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "IntLiteral", 
		"BoolLiteral", "Identifier", "Letter", "Digit", "NonZeroDigit", "LPAREN", 
		"RPAREN", "LBRACE", "RBRACE", "LBRACK", "RBRACK", "COLON", "SEMICOLON", 
		"COMMA", "DOT", "ARROW", "GT", "LT", "BANG", "EQUAL", "LE", "GE", "NOTEQUAL", 
		"AND", "OR", "ADD", "SUB", "MUL", "DIV", "MOD", "ASSIGN", "WS", "COMMENT", 
		"LINE_COMMENT"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'fn'", "'if'", "'else'", "'while'", "'return'", "'print'", "'println'", 
		"'read'", null, null, null, "'('", "')'", "'{'", "'}'", "'['", "']'", 
		"':'", "';'", "','", "'.'", "'->'", "'>'", "'<'", "'!'", "'=='", "'<='", 
		"'>='", "'!='", "'&&'", "'||'", "'+'", "'-'", "'*'", "'/'", "'%'", "'='"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, "IntLiteral", "BoolLiteral", 
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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2*\u0102\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\3\2\3\2\3\2\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3"+
		"\5\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3\b"+
		"\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\n\3\n\3\n\7\n\u0088\n\n\f\n\16"+
		"\n\u008b\13\n\5\n\u008d\n\n\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3"+
		"\13\5\13\u0098\n\13\3\f\3\f\3\f\7\f\u009d\n\f\f\f\16\f\u00a0\13\f\3\r"+
		"\3\r\3\16\3\16\3\17\3\17\3\20\3\20\3\21\3\21\3\22\3\22\3\23\3\23\3\24"+
		"\3\24\3\25\3\25\3\26\3\26\3\27\3\27\3\30\3\30\3\31\3\31\3\32\3\32\3\32"+
		"\3\33\3\33\3\34\3\34\3\35\3\35\3\36\3\36\3\36\3\37\3\37\3\37\3 \3 \3 "+
		"\3!\3!\3!\3\"\3\"\3\"\3#\3#\3#\3$\3$\3%\3%\3&\3&\3\'\3\'\3(\3(\3)\3)\3"+
		"*\6*\u00e4\n*\r*\16*\u00e5\3*\3*\3+\3+\3+\3+\7+\u00ee\n+\f+\16+\u00f1"+
		"\13+\3+\3+\3+\3+\3+\3,\3,\3,\3,\7,\u00fc\n,\f,\16,\u00ff\13,\3,\3,\3\u00ef"+
		"\2-\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\2\33\2\35\2"+
		"\37\16!\17#\20%\21\'\22)\23+\24-\25/\26\61\27\63\30\65\31\67\329\33;\34"+
		"=\35?\36A\37C E!G\"I#K$M%O&Q\'S(U)W*\3\2\7\5\2C\\aac|\3\2\62;\3\2\63;"+
		"\5\2\13\f\17\17\"\"\4\2\f\f\17\17\u0106\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3"+
		"\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2"+
		"\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2"+
		"\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2"+
		"\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3"+
		"\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2\2\2\2E\3\2\2\2\2G\3\2\2"+
		"\2\2I\3\2\2\2\2K\3\2\2\2\2M\3\2\2\2\2O\3\2\2\2\2Q\3\2\2\2\2S\3\2\2\2\2"+
		"U\3\2\2\2\2W\3\2\2\2\3Y\3\2\2\2\5\\\3\2\2\2\7_\3\2\2\2\td\3\2\2\2\13j"+
		"\3\2\2\2\rq\3\2\2\2\17w\3\2\2\2\21\177\3\2\2\2\23\u008c\3\2\2\2\25\u0097"+
		"\3\2\2\2\27\u0099\3\2\2\2\31\u00a1\3\2\2\2\33\u00a3\3\2\2\2\35\u00a5\3"+
		"\2\2\2\37\u00a7\3\2\2\2!\u00a9\3\2\2\2#\u00ab\3\2\2\2%\u00ad\3\2\2\2\'"+
		"\u00af\3\2\2\2)\u00b1\3\2\2\2+\u00b3\3\2\2\2-\u00b5\3\2\2\2/\u00b7\3\2"+
		"\2\2\61\u00b9\3\2\2\2\63\u00bb\3\2\2\2\65\u00be\3\2\2\2\67\u00c0\3\2\2"+
		"\29\u00c2\3\2\2\2;\u00c4\3\2\2\2=\u00c7\3\2\2\2?\u00ca\3\2\2\2A\u00cd"+
		"\3\2\2\2C\u00d0\3\2\2\2E\u00d3\3\2\2\2G\u00d6\3\2\2\2I\u00d8\3\2\2\2K"+
		"\u00da\3\2\2\2M\u00dc\3\2\2\2O\u00de\3\2\2\2Q\u00e0\3\2\2\2S\u00e3\3\2"+
		"\2\2U\u00e9\3\2\2\2W\u00f7\3\2\2\2YZ\7h\2\2Z[\7p\2\2[\4\3\2\2\2\\]\7k"+
		"\2\2]^\7h\2\2^\6\3\2\2\2_`\7g\2\2`a\7n\2\2ab\7u\2\2bc\7g\2\2c\b\3\2\2"+
		"\2de\7y\2\2ef\7j\2\2fg\7k\2\2gh\7n\2\2hi\7g\2\2i\n\3\2\2\2jk\7t\2\2kl"+
		"\7g\2\2lm\7v\2\2mn\7w\2\2no\7t\2\2op\7p\2\2p\f\3\2\2\2qr\7r\2\2rs\7t\2"+
		"\2st\7k\2\2tu\7p\2\2uv\7v\2\2v\16\3\2\2\2wx\7r\2\2xy\7t\2\2yz\7k\2\2z"+
		"{\7p\2\2{|\7v\2\2|}\7n\2\2}~\7p\2\2~\20\3\2\2\2\177\u0080\7t\2\2\u0080"+
		"\u0081\7g\2\2\u0081\u0082\7c\2\2\u0082\u0083\7f\2\2\u0083\22\3\2\2\2\u0084"+
		"\u008d\7\62\2\2\u0085\u0089\5\35\17\2\u0086\u0088\5\33\16\2\u0087\u0086"+
		"\3\2\2\2\u0088\u008b\3\2\2\2\u0089\u0087\3\2\2\2\u0089\u008a\3\2\2\2\u008a"+
		"\u008d\3\2\2\2\u008b\u0089\3\2\2\2\u008c\u0084\3\2\2\2\u008c\u0085\3\2"+
		"\2\2\u008d\24\3\2\2\2\u008e\u008f\7v\2\2\u008f\u0090\7t\2\2\u0090\u0091"+
		"\7w\2\2\u0091\u0098\7g\2\2\u0092\u0093\7h\2\2\u0093\u0094\7c\2\2\u0094"+
		"\u0095\7n\2\2\u0095\u0096\7u\2\2\u0096\u0098\7g\2\2\u0097\u008e\3\2\2"+
		"\2\u0097\u0092\3\2\2\2\u0098\26\3\2\2\2\u0099\u009e\5\31\r\2\u009a\u009d"+
		"\5\31\r\2\u009b\u009d\5\33\16\2\u009c\u009a\3\2\2\2\u009c\u009b\3\2\2"+
		"\2\u009d\u00a0\3\2\2\2\u009e\u009c\3\2\2\2\u009e\u009f\3\2\2\2\u009f\30"+
		"\3\2\2\2\u00a0\u009e\3\2\2\2\u00a1\u00a2\t\2\2\2\u00a2\32\3\2\2\2\u00a3"+
		"\u00a4\t\3\2\2\u00a4\34\3\2\2\2\u00a5\u00a6\t\4\2\2\u00a6\36\3\2\2\2\u00a7"+
		"\u00a8\7*\2\2\u00a8 \3\2\2\2\u00a9\u00aa\7+\2\2\u00aa\"\3\2\2\2\u00ab"+
		"\u00ac\7}\2\2\u00ac$\3\2\2\2\u00ad\u00ae\7\177\2\2\u00ae&\3\2\2\2\u00af"+
		"\u00b0\7]\2\2\u00b0(\3\2\2\2\u00b1\u00b2\7_\2\2\u00b2*\3\2\2\2\u00b3\u00b4"+
		"\7<\2\2\u00b4,\3\2\2\2\u00b5\u00b6\7=\2\2\u00b6.\3\2\2\2\u00b7\u00b8\7"+
		".\2\2\u00b8\60\3\2\2\2\u00b9\u00ba\7\60\2\2\u00ba\62\3\2\2\2\u00bb\u00bc"+
		"\7/\2\2\u00bc\u00bd\7@\2\2\u00bd\64\3\2\2\2\u00be\u00bf\7@\2\2\u00bf\66"+
		"\3\2\2\2\u00c0\u00c1\7>\2\2\u00c18\3\2\2\2\u00c2\u00c3\7#\2\2\u00c3:\3"+
		"\2\2\2\u00c4\u00c5\7?\2\2\u00c5\u00c6\7?\2\2\u00c6<\3\2\2\2\u00c7\u00c8"+
		"\7>\2\2\u00c8\u00c9\7?\2\2\u00c9>\3\2\2\2\u00ca\u00cb\7@\2\2\u00cb\u00cc"+
		"\7?\2\2\u00cc@\3\2\2\2\u00cd\u00ce\7#\2\2\u00ce\u00cf\7?\2\2\u00cfB\3"+
		"\2\2\2\u00d0\u00d1\7(\2\2\u00d1\u00d2\7(\2\2\u00d2D\3\2\2\2\u00d3\u00d4"+
		"\7~\2\2\u00d4\u00d5\7~\2\2\u00d5F\3\2\2\2\u00d6\u00d7\7-\2\2\u00d7H\3"+
		"\2\2\2\u00d8\u00d9\7/\2\2\u00d9J\3\2\2\2\u00da\u00db\7,\2\2\u00dbL\3\2"+
		"\2\2\u00dc\u00dd\7\61\2\2\u00ddN\3\2\2\2\u00de\u00df\7\'\2\2\u00dfP\3"+
		"\2\2\2\u00e0\u00e1\7?\2\2\u00e1R\3\2\2\2\u00e2\u00e4\t\5\2\2\u00e3\u00e2"+
		"\3\2\2\2\u00e4\u00e5\3\2\2\2\u00e5\u00e3\3\2\2\2\u00e5\u00e6\3\2\2\2\u00e6"+
		"\u00e7\3\2\2\2\u00e7\u00e8\b*\2\2\u00e8T\3\2\2\2\u00e9\u00ea\7\61\2\2"+
		"\u00ea\u00eb\7,\2\2\u00eb\u00ef\3\2\2\2\u00ec\u00ee\13\2\2\2\u00ed\u00ec"+
		"\3\2\2\2\u00ee\u00f1\3\2\2\2\u00ef\u00f0\3\2\2\2\u00ef\u00ed\3\2\2\2\u00f0"+
		"\u00f2\3\2\2\2\u00f1\u00ef\3\2\2\2\u00f2\u00f3\7,\2\2\u00f3\u00f4\7\61"+
		"\2\2\u00f4\u00f5\3\2\2\2\u00f5\u00f6\b+\2\2\u00f6V\3\2\2\2\u00f7\u00f8"+
		"\7\61\2\2\u00f8\u00f9\7\61\2\2\u00f9\u00fd\3\2\2\2\u00fa\u00fc\n\6\2\2"+
		"\u00fb\u00fa\3\2\2\2\u00fc\u00ff\3\2\2\2\u00fd\u00fb\3\2\2\2\u00fd\u00fe"+
		"\3\2\2\2\u00fe\u0100\3\2\2\2\u00ff\u00fd\3\2\2\2\u0100\u0101\b,\2\2\u0101"+
		"X\3\2\2\2\13\2\u0089\u008c\u0097\u009c\u009e\u00e5\u00ef\u00fd\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}