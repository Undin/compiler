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
		T__0=1, T__1=2, T__2=3, T__3=4, Identifier=5, IntLiteral=6, BoolLiteral=7, 
		LPAREN=8, RPAREN=9, LBRACE=10, RBRACE=11, LBRACK=12, RBRACK=13, COLON=14, 
		SEMICOLON=15, COMMA=16, DOT=17, ARROW=18, GT=19, LT=20, BANG=21, EQUAL=22, 
		LE=23, GE=24, NOTEQUAL=25, AND=26, OR=27, ADD=28, SUB=29, MUL=30, DIV=31, 
		MOD=32, ASSIGN=33, WS=34, COMMENT=35, LINE_COMMENT=36;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "T__1", "T__2", "T__3", "Identifier", "IntLiteral", "BoolLiteral", 
		"Letter", "Digit", "NonZeroDigit", "LPAREN", "RPAREN", "LBRACE", "RBRACE", 
		"LBRACK", "RBRACK", "COLON", "SEMICOLON", "COMMA", "DOT", "ARROW", "GT", 
		"LT", "BANG", "EQUAL", "LE", "GE", "NOTEQUAL", "AND", "OR", "ADD", "SUB", 
		"MUL", "DIV", "MOD", "ASSIGN", "WS", "COMMENT", "LINE_COMMENT"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'fn'", "'if'", "'else'", "'while'", null, null, null, "'('", "')'", 
		"'{'", "'}'", "'['", "']'", "':'", "';'", "','", "'.'", "'->'", "'>'", 
		"'<'", "'!'", "'=='", "'<='", "'>='", "'!='", "'&&'", "'||'", "'+'", "'-'", 
		"'*'", "'/'", "'%'", "'='"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, "Identifier", "IntLiteral", "BoolLiteral", 
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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2&\u00e0\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\3\2\3\2\3\2\3\3\3\3"+
		"\3\3\3\4\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\6\7\6f\n\6"+
		"\f\6\16\6i\13\6\3\7\3\7\3\7\7\7n\n\7\f\7\16\7q\13\7\5\7s\n\7\3\b\3\b\3"+
		"\b\3\b\3\b\3\b\3\b\3\b\3\b\5\b~\n\b\3\t\3\t\3\n\3\n\3\13\3\13\3\f\3\f"+
		"\3\r\3\r\3\16\3\16\3\17\3\17\3\20\3\20\3\21\3\21\3\22\3\22\3\23\3\23\3"+
		"\24\3\24\3\25\3\25\3\26\3\26\3\26\3\27\3\27\3\30\3\30\3\31\3\31\3\32\3"+
		"\32\3\32\3\33\3\33\3\33\3\34\3\34\3\34\3\35\3\35\3\35\3\36\3\36\3\36\3"+
		"\37\3\37\3\37\3 \3 \3!\3!\3\"\3\"\3#\3#\3$\3$\3%\3%\3&\6&\u00c2\n&\r&"+
		"\16&\u00c3\3&\3&\3\'\3\'\3\'\3\'\7\'\u00cc\n\'\f\'\16\'\u00cf\13\'\3\'"+
		"\3\'\3\'\3\'\3\'\3(\3(\3(\3(\7(\u00da\n(\f(\16(\u00dd\13(\3(\3(\3\u00cd"+
		"\2)\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\2\23\2\25\2\27\n\31\13\33\f\35\r"+
		"\37\16!\17#\20%\21\'\22)\23+\24-\25/\26\61\27\63\30\65\31\67\329\33;\34"+
		"=\35?\36A\37C E!G\"I#K$M%O&\3\2\7\5\2C\\aac|\3\2\62;\3\2\63;\5\2\13\f"+
		"\17\17\"\"\4\2\f\f\17\17\u00e4\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t"+
		"\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2"+
		"\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2"+
		"\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2"+
		"\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3"+
		"\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2\2\2\2E\3\2\2\2\2G\3\2\2\2\2I\3\2\2"+
		"\2\2K\3\2\2\2\2M\3\2\2\2\2O\3\2\2\2\3Q\3\2\2\2\5T\3\2\2\2\7W\3\2\2\2\t"+
		"\\\3\2\2\2\13b\3\2\2\2\rr\3\2\2\2\17}\3\2\2\2\21\177\3\2\2\2\23\u0081"+
		"\3\2\2\2\25\u0083\3\2\2\2\27\u0085\3\2\2\2\31\u0087\3\2\2\2\33\u0089\3"+
		"\2\2\2\35\u008b\3\2\2\2\37\u008d\3\2\2\2!\u008f\3\2\2\2#\u0091\3\2\2\2"+
		"%\u0093\3\2\2\2\'\u0095\3\2\2\2)\u0097\3\2\2\2+\u0099\3\2\2\2-\u009c\3"+
		"\2\2\2/\u009e\3\2\2\2\61\u00a0\3\2\2\2\63\u00a2\3\2\2\2\65\u00a5\3\2\2"+
		"\2\67\u00a8\3\2\2\29\u00ab\3\2\2\2;\u00ae\3\2\2\2=\u00b1\3\2\2\2?\u00b4"+
		"\3\2\2\2A\u00b6\3\2\2\2C\u00b8\3\2\2\2E\u00ba\3\2\2\2G\u00bc\3\2\2\2I"+
		"\u00be\3\2\2\2K\u00c1\3\2\2\2M\u00c7\3\2\2\2O\u00d5\3\2\2\2QR\7h\2\2R"+
		"S\7p\2\2S\4\3\2\2\2TU\7k\2\2UV\7h\2\2V\6\3\2\2\2WX\7g\2\2XY\7n\2\2YZ\7"+
		"u\2\2Z[\7g\2\2[\b\3\2\2\2\\]\7y\2\2]^\7j\2\2^_\7k\2\2_`\7n\2\2`a\7g\2"+
		"\2a\n\3\2\2\2bg\5\21\t\2cf\5\21\t\2df\5\23\n\2ec\3\2\2\2ed\3\2\2\2fi\3"+
		"\2\2\2ge\3\2\2\2gh\3\2\2\2h\f\3\2\2\2ig\3\2\2\2js\7\62\2\2ko\5\25\13\2"+
		"ln\5\23\n\2ml\3\2\2\2nq\3\2\2\2om\3\2\2\2op\3\2\2\2ps\3\2\2\2qo\3\2\2"+
		"\2rj\3\2\2\2rk\3\2\2\2s\16\3\2\2\2tu\7v\2\2uv\7t\2\2vw\7w\2\2w~\7g\2\2"+
		"xy\7h\2\2yz\7c\2\2z{\7n\2\2{|\7u\2\2|~\7g\2\2}t\3\2\2\2}x\3\2\2\2~\20"+
		"\3\2\2\2\177\u0080\t\2\2\2\u0080\22\3\2\2\2\u0081\u0082\t\3\2\2\u0082"+
		"\24\3\2\2\2\u0083\u0084\t\4\2\2\u0084\26\3\2\2\2\u0085\u0086\7*\2\2\u0086"+
		"\30\3\2\2\2\u0087\u0088\7+\2\2\u0088\32\3\2\2\2\u0089\u008a\7}\2\2\u008a"+
		"\34\3\2\2\2\u008b\u008c\7\177\2\2\u008c\36\3\2\2\2\u008d\u008e\7]\2\2"+
		"\u008e \3\2\2\2\u008f\u0090\7_\2\2\u0090\"\3\2\2\2\u0091\u0092\7<\2\2"+
		"\u0092$\3\2\2\2\u0093\u0094\7=\2\2\u0094&\3\2\2\2\u0095\u0096\7.\2\2\u0096"+
		"(\3\2\2\2\u0097\u0098\7\60\2\2\u0098*\3\2\2\2\u0099\u009a\7/\2\2\u009a"+
		"\u009b\7@\2\2\u009b,\3\2\2\2\u009c\u009d\7@\2\2\u009d.\3\2\2\2\u009e\u009f"+
		"\7>\2\2\u009f\60\3\2\2\2\u00a0\u00a1\7#\2\2\u00a1\62\3\2\2\2\u00a2\u00a3"+
		"\7?\2\2\u00a3\u00a4\7?\2\2\u00a4\64\3\2\2\2\u00a5\u00a6\7>\2\2\u00a6\u00a7"+
		"\7?\2\2\u00a7\66\3\2\2\2\u00a8\u00a9\7@\2\2\u00a9\u00aa\7?\2\2\u00aa8"+
		"\3\2\2\2\u00ab\u00ac\7#\2\2\u00ac\u00ad\7?\2\2\u00ad:\3\2\2\2\u00ae\u00af"+
		"\7(\2\2\u00af\u00b0\7(\2\2\u00b0<\3\2\2\2\u00b1\u00b2\7~\2\2\u00b2\u00b3"+
		"\7~\2\2\u00b3>\3\2\2\2\u00b4\u00b5\7-\2\2\u00b5@\3\2\2\2\u00b6\u00b7\7"+
		"/\2\2\u00b7B\3\2\2\2\u00b8\u00b9\7,\2\2\u00b9D\3\2\2\2\u00ba\u00bb\7\61"+
		"\2\2\u00bbF\3\2\2\2\u00bc\u00bd\7\'\2\2\u00bdH\3\2\2\2\u00be\u00bf\7?"+
		"\2\2\u00bfJ\3\2\2\2\u00c0\u00c2\t\5\2\2\u00c1\u00c0\3\2\2\2\u00c2\u00c3"+
		"\3\2\2\2\u00c3\u00c1\3\2\2\2\u00c3\u00c4\3\2\2\2\u00c4\u00c5\3\2\2\2\u00c5"+
		"\u00c6\b&\2\2\u00c6L\3\2\2\2\u00c7\u00c8\7\61\2\2\u00c8\u00c9\7,\2\2\u00c9"+
		"\u00cd\3\2\2\2\u00ca\u00cc\13\2\2\2\u00cb\u00ca\3\2\2\2\u00cc\u00cf\3"+
		"\2\2\2\u00cd\u00ce\3\2\2\2\u00cd\u00cb\3\2\2\2\u00ce\u00d0\3\2\2\2\u00cf"+
		"\u00cd\3\2\2\2\u00d0\u00d1\7,\2\2\u00d1\u00d2\7\61\2\2\u00d2\u00d3\3\2"+
		"\2\2\u00d3\u00d4\b\'\2\2\u00d4N\3\2\2\2\u00d5\u00d6\7\61\2\2\u00d6\u00d7"+
		"\7\61\2\2\u00d7\u00db\3\2\2\2\u00d8\u00da\n\6\2\2\u00d9\u00d8\3\2\2\2"+
		"\u00da\u00dd\3\2\2\2\u00db\u00d9\3\2\2\2\u00db\u00dc\3\2\2\2\u00dc\u00de"+
		"\3\2\2\2\u00dd\u00db\3\2\2\2\u00de\u00df\b(\2\2\u00dfP\3\2\2\2\13\2eg"+
		"or}\u00c3\u00cd\u00db\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}