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
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		IntLiteral=10, BoolLiteral=11, Identifier=12, LPAREN=13, RPAREN=14, LBRACE=15, 
		RBRACE=16, LBRACK=17, RBRACK=18, COLON=19, SEMICOLON=20, COMMA=21, DOT=22, 
		ARROW=23, GT=24, LT=25, BANG=26, EQUAL=27, LE=28, GE=29, NOTEQUAL=30, 
		AND=31, OR=32, ADD=33, SUB=34, MUL=35, DIV=36, MOD=37, ASSIGN=38, WS=39, 
		COMMENT=40, LINE_COMMENT=41;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8", 
		"IntLiteral", "BoolLiteral", "Identifier", "Letter", "Digit", "NonZeroDigit", 
		"LPAREN", "RPAREN", "LBRACE", "RBRACE", "LBRACK", "RBRACK", "COLON", "SEMICOLON", 
		"COMMA", "DOT", "ARROW", "GT", "LT", "BANG", "EQUAL", "LE", "GE", "NOTEQUAL", 
		"AND", "OR", "ADD", "SUB", "MUL", "DIV", "MOD", "ASSIGN", "WS", "COMMENT", 
		"LINE_COMMENT"
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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2+\u0108\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\3\2\3\2\3\2\3\2\3\3\3\3\3\3\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5"+
		"\3\6\3\6\3\6\3\6\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3"+
		"\b\3\b\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3\13\3\13\3"+
		"\13\7\13\u008e\n\13\f\13\16\13\u0091\13\13\5\13\u0093\n\13\3\f\3\f\3\f"+
		"\3\f\3\f\3\f\3\f\3\f\3\f\5\f\u009e\n\f\3\r\3\r\3\r\7\r\u00a3\n\r\f\r\16"+
		"\r\u00a6\13\r\3\16\3\16\3\17\3\17\3\20\3\20\3\21\3\21\3\22\3\22\3\23\3"+
		"\23\3\24\3\24\3\25\3\25\3\26\3\26\3\27\3\27\3\30\3\30\3\31\3\31\3\32\3"+
		"\32\3\33\3\33\3\33\3\34\3\34\3\35\3\35\3\36\3\36\3\37\3\37\3\37\3 \3 "+
		"\3 \3!\3!\3!\3\"\3\"\3\"\3#\3#\3#\3$\3$\3$\3%\3%\3&\3&\3\'\3\'\3(\3(\3"+
		")\3)\3*\3*\3+\6+\u00ea\n+\r+\16+\u00eb\3+\3+\3,\3,\3,\3,\7,\u00f4\n,\f"+
		",\16,\u00f7\13,\3,\3,\3,\3,\3,\3-\3-\3-\3-\7-\u0102\n-\f-\16-\u0105\13"+
		"-\3-\3-\3\u00f5\2.\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r"+
		"\31\16\33\2\35\2\37\2!\17#\20%\21\'\22)\23+\24-\25/\26\61\27\63\30\65"+
		"\31\67\329\33;\34=\35?\36A\37C E!G\"I#K$M%O&Q\'S(U)W*Y+\3\2\7\5\2C\\a"+
		"ac|\3\2\62;\3\2\63;\5\2\13\f\17\17\"\"\4\2\f\f\17\17\u010c\2\3\3\2\2\2"+
		"\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2"+
		"\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2"+
		"\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2"+
		"-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2"+
		"\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2\2\2\2"+
		"E\3\2\2\2\2G\3\2\2\2\2I\3\2\2\2\2K\3\2\2\2\2M\3\2\2\2\2O\3\2\2\2\2Q\3"+
		"\2\2\2\2S\3\2\2\2\2U\3\2\2\2\2W\3\2\2\2\2Y\3\2\2\2\3[\3\2\2\2\5_\3\2\2"+
		"\2\7b\3\2\2\2\te\3\2\2\2\13j\3\2\2\2\rp\3\2\2\2\17w\3\2\2\2\21}\3\2\2"+
		"\2\23\u0085\3\2\2\2\25\u0092\3\2\2\2\27\u009d\3\2\2\2\31\u009f\3\2\2\2"+
		"\33\u00a7\3\2\2\2\35\u00a9\3\2\2\2\37\u00ab\3\2\2\2!\u00ad\3\2\2\2#\u00af"+
		"\3\2\2\2%\u00b1\3\2\2\2\'\u00b3\3\2\2\2)\u00b5\3\2\2\2+\u00b7\3\2\2\2"+
		"-\u00b9\3\2\2\2/\u00bb\3\2\2\2\61\u00bd\3\2\2\2\63\u00bf\3\2\2\2\65\u00c1"+
		"\3\2\2\2\67\u00c4\3\2\2\29\u00c6\3\2\2\2;\u00c8\3\2\2\2=\u00ca\3\2\2\2"+
		"?\u00cd\3\2\2\2A\u00d0\3\2\2\2C\u00d3\3\2\2\2E\u00d6\3\2\2\2G\u00d9\3"+
		"\2\2\2I\u00dc\3\2\2\2K\u00de\3\2\2\2M\u00e0\3\2\2\2O\u00e2\3\2\2\2Q\u00e4"+
		"\3\2\2\2S\u00e6\3\2\2\2U\u00e9\3\2\2\2W\u00ef\3\2\2\2Y\u00fd\3\2\2\2["+
		"\\\7n\2\2\\]\7g\2\2]^\7v\2\2^\4\3\2\2\2_`\7h\2\2`a\7p\2\2a\6\3\2\2\2b"+
		"c\7k\2\2cd\7h\2\2d\b\3\2\2\2ef\7g\2\2fg\7n\2\2gh\7u\2\2hi\7g\2\2i\n\3"+
		"\2\2\2jk\7y\2\2kl\7j\2\2lm\7k\2\2mn\7n\2\2no\7g\2\2o\f\3\2\2\2pq\7t\2"+
		"\2qr\7g\2\2rs\7v\2\2st\7w\2\2tu\7t\2\2uv\7p\2\2v\16\3\2\2\2wx\7r\2\2x"+
		"y\7t\2\2yz\7k\2\2z{\7p\2\2{|\7v\2\2|\20\3\2\2\2}~\7r\2\2~\177\7t\2\2\177"+
		"\u0080\7k\2\2\u0080\u0081\7p\2\2\u0081\u0082\7v\2\2\u0082\u0083\7n\2\2"+
		"\u0083\u0084\7p\2\2\u0084\22\3\2\2\2\u0085\u0086\7t\2\2\u0086\u0087\7"+
		"g\2\2\u0087\u0088\7c\2\2\u0088\u0089\7f\2\2\u0089\24\3\2\2\2\u008a\u0093"+
		"\7\62\2\2\u008b\u008f\5\37\20\2\u008c\u008e\5\35\17\2\u008d\u008c\3\2"+
		"\2\2\u008e\u0091\3\2\2\2\u008f\u008d\3\2\2\2\u008f\u0090\3\2\2\2\u0090"+
		"\u0093\3\2\2\2\u0091\u008f\3\2\2\2\u0092\u008a\3\2\2\2\u0092\u008b\3\2"+
		"\2\2\u0093\26\3\2\2\2\u0094\u0095\7v\2\2\u0095\u0096\7t\2\2\u0096\u0097"+
		"\7w\2\2\u0097\u009e\7g\2\2\u0098\u0099\7h\2\2\u0099\u009a\7c\2\2\u009a"+
		"\u009b\7n\2\2\u009b\u009c\7u\2\2\u009c\u009e\7g\2\2\u009d\u0094\3\2\2"+
		"\2\u009d\u0098\3\2\2\2\u009e\30\3\2\2\2\u009f\u00a4\5\33\16\2\u00a0\u00a3"+
		"\5\33\16\2\u00a1\u00a3\5\35\17\2\u00a2\u00a0\3\2\2\2\u00a2\u00a1\3\2\2"+
		"\2\u00a3\u00a6\3\2\2\2\u00a4\u00a2\3\2\2\2\u00a4\u00a5\3\2\2\2\u00a5\32"+
		"\3\2\2\2\u00a6\u00a4\3\2\2\2\u00a7\u00a8\t\2\2\2\u00a8\34\3\2\2\2\u00a9"+
		"\u00aa\t\3\2\2\u00aa\36\3\2\2\2\u00ab\u00ac\t\4\2\2\u00ac \3\2\2\2\u00ad"+
		"\u00ae\7*\2\2\u00ae\"\3\2\2\2\u00af\u00b0\7+\2\2\u00b0$\3\2\2\2\u00b1"+
		"\u00b2\7}\2\2\u00b2&\3\2\2\2\u00b3\u00b4\7\177\2\2\u00b4(\3\2\2\2\u00b5"+
		"\u00b6\7]\2\2\u00b6*\3\2\2\2\u00b7\u00b8\7_\2\2\u00b8,\3\2\2\2\u00b9\u00ba"+
		"\7<\2\2\u00ba.\3\2\2\2\u00bb\u00bc\7=\2\2\u00bc\60\3\2\2\2\u00bd\u00be"+
		"\7.\2\2\u00be\62\3\2\2\2\u00bf\u00c0\7\60\2\2\u00c0\64\3\2\2\2\u00c1\u00c2"+
		"\7/\2\2\u00c2\u00c3\7@\2\2\u00c3\66\3\2\2\2\u00c4\u00c5\7@\2\2\u00c58"+
		"\3\2\2\2\u00c6\u00c7\7>\2\2\u00c7:\3\2\2\2\u00c8\u00c9\7#\2\2\u00c9<\3"+
		"\2\2\2\u00ca\u00cb\7?\2\2\u00cb\u00cc\7?\2\2\u00cc>\3\2\2\2\u00cd\u00ce"+
		"\7>\2\2\u00ce\u00cf\7?\2\2\u00cf@\3\2\2\2\u00d0\u00d1\7@\2\2\u00d1\u00d2"+
		"\7?\2\2\u00d2B\3\2\2\2\u00d3\u00d4\7#\2\2\u00d4\u00d5\7?\2\2\u00d5D\3"+
		"\2\2\2\u00d6\u00d7\7(\2\2\u00d7\u00d8\7(\2\2\u00d8F\3\2\2\2\u00d9\u00da"+
		"\7~\2\2\u00da\u00db\7~\2\2\u00dbH\3\2\2\2\u00dc\u00dd\7-\2\2\u00ddJ\3"+
		"\2\2\2\u00de\u00df\7/\2\2\u00dfL\3\2\2\2\u00e0\u00e1\7,\2\2\u00e1N\3\2"+
		"\2\2\u00e2\u00e3\7\61\2\2\u00e3P\3\2\2\2\u00e4\u00e5\7\'\2\2\u00e5R\3"+
		"\2\2\2\u00e6\u00e7\7?\2\2\u00e7T\3\2\2\2\u00e8\u00ea\t\5\2\2\u00e9\u00e8"+
		"\3\2\2\2\u00ea\u00eb\3\2\2\2\u00eb\u00e9\3\2\2\2\u00eb\u00ec\3\2\2\2\u00ec"+
		"\u00ed\3\2\2\2\u00ed\u00ee\b+\2\2\u00eeV\3\2\2\2\u00ef\u00f0\7\61\2\2"+
		"\u00f0\u00f1\7,\2\2\u00f1\u00f5\3\2\2\2\u00f2\u00f4\13\2\2\2\u00f3\u00f2"+
		"\3\2\2\2\u00f4\u00f7\3\2\2\2\u00f5\u00f6\3\2\2\2\u00f5\u00f3\3\2\2\2\u00f6"+
		"\u00f8\3\2\2\2\u00f7\u00f5\3\2\2\2\u00f8\u00f9\7,\2\2\u00f9\u00fa\7\61"+
		"\2\2\u00fa\u00fb\3\2\2\2\u00fb\u00fc\b,\2\2\u00fcX\3\2\2\2\u00fd\u00fe"+
		"\7\61\2\2\u00fe\u00ff\7\61\2\2\u00ff\u0103\3\2\2\2\u0100\u0102\n\6\2\2"+
		"\u0101\u0100\3\2\2\2\u0102\u0105\3\2\2\2\u0103\u0101\3\2\2\2\u0103\u0104"+
		"\3\2\2\2\u0104\u0106\3\2\2\2\u0105\u0103\3\2\2\2\u0106\u0107\b-\2\2\u0107"+
		"Z\3\2\2\2\13\2\u008f\u0092\u009d\u00a2\u00a4\u00eb\u00f5\u0103\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}