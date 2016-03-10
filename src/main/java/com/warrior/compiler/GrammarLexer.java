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
		T__0=1, Identifier=2, IntLiteral=3, BoolLiteral=4, LPAREN=5, RPAREN=6, 
		LBRACE=7, RBRACE=8, LBRACK=9, RBRACK=10, COLON=11, SEMICOLON=12, COMMA=13, 
		DOT=14, GT=15, LT=16, BANG=17, EQUAL=18, LE=19, GE=20, NOTEQUAL=21, AND=22, 
		OR=23, ADD=24, SUB=25, MUL=26, DIV=27, MOD=28, WS=29, COMMENT=30, LINE_COMMENT=31;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "Identifier", "IntLiteral", "BoolLiteral", "Letter", "Digit", 
		"NonZeroDigit", "LPAREN", "RPAREN", "LBRACE", "RBRACE", "LBRACK", "RBRACK", 
		"COLON", "SEMICOLON", "COMMA", "DOT", "GT", "LT", "BANG", "EQUAL", "LE", 
		"GE", "NOTEQUAL", "AND", "OR", "ADD", "SUB", "MUL", "DIV", "MOD", "WS", 
		"COMMENT", "LINE_COMMENT"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'fn'", null, null, null, "'('", "')'", "'{'", "'}'", "'['", "']'", 
		"':'", "';'", "','", "'.'", "'>'", "'<'", "'!'", "'=='", "'<='", "'>='", 
		"'!='", "'&&'", "'||'", "'+'", "'-'", "'*'", "'/'", "'%'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, "Identifier", "IntLiteral", "BoolLiteral", "LPAREN", "RPAREN", 
		"LBRACE", "RBRACE", "LBRACK", "RBRACK", "COLON", "SEMICOLON", "COMMA", 
		"DOT", "GT", "LT", "BANG", "EQUAL", "LE", "GE", "NOTEQUAL", "AND", "OR", 
		"ADD", "SUB", "MUL", "DIV", "MOD", "WS", "COMMENT", "LINE_COMMENT"
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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2!\u00c3\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\3\2\3\2\3\2\3\3\3\3\3\3\7\3N\n\3\f\3\16\3Q\13\3\3\4"+
		"\3\4\3\4\7\4V\n\4\f\4\16\4Y\13\4\5\4[\n\4\3\5\3\5\3\5\3\5\3\5\3\5\3\5"+
		"\3\5\3\5\5\5f\n\5\3\6\3\6\3\7\3\7\3\b\3\b\3\t\3\t\3\n\3\n\3\13\3\13\3"+
		"\f\3\f\3\r\3\r\3\16\3\16\3\17\3\17\3\20\3\20\3\21\3\21\3\22\3\22\3\23"+
		"\3\23\3\24\3\24\3\25\3\25\3\26\3\26\3\26\3\27\3\27\3\27\3\30\3\30\3\30"+
		"\3\31\3\31\3\31\3\32\3\32\3\32\3\33\3\33\3\33\3\34\3\34\3\35\3\35\3\36"+
		"\3\36\3\37\3\37\3 \3 \3!\6!\u00a5\n!\r!\16!\u00a6\3!\3!\3\"\3\"\3\"\3"+
		"\"\7\"\u00af\n\"\f\"\16\"\u00b2\13\"\3\"\3\"\3\"\3\"\3\"\3#\3#\3#\3#\7"+
		"#\u00bd\n#\f#\16#\u00c0\13#\3#\3#\3\u00b0\2$\3\3\5\4\7\5\t\6\13\2\r\2"+
		"\17\2\21\7\23\b\25\t\27\n\31\13\33\f\35\r\37\16!\17#\20%\21\'\22)\23+"+
		"\24-\25/\26\61\27\63\30\65\31\67\329\33;\34=\35?\36A\37C E!\3\2\7\5\2"+
		"C\\aac|\3\2\62;\3\2\63;\5\2\13\f\17\17\"\"\4\2\f\f\17\17\u00c7\2\3\3\2"+
		"\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25"+
		"\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2"+
		"\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2"+
		"\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3"+
		"\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2\2"+
		"\2\2E\3\2\2\2\3G\3\2\2\2\5J\3\2\2\2\7Z\3\2\2\2\te\3\2\2\2\13g\3\2\2\2"+
		"\ri\3\2\2\2\17k\3\2\2\2\21m\3\2\2\2\23o\3\2\2\2\25q\3\2\2\2\27s\3\2\2"+
		"\2\31u\3\2\2\2\33w\3\2\2\2\35y\3\2\2\2\37{\3\2\2\2!}\3\2\2\2#\177\3\2"+
		"\2\2%\u0081\3\2\2\2\'\u0083\3\2\2\2)\u0085\3\2\2\2+\u0087\3\2\2\2-\u008a"+
		"\3\2\2\2/\u008d\3\2\2\2\61\u0090\3\2\2\2\63\u0093\3\2\2\2\65\u0096\3\2"+
		"\2\2\67\u0099\3\2\2\29\u009b\3\2\2\2;\u009d\3\2\2\2=\u009f\3\2\2\2?\u00a1"+
		"\3\2\2\2A\u00a4\3\2\2\2C\u00aa\3\2\2\2E\u00b8\3\2\2\2GH\7h\2\2HI\7p\2"+
		"\2I\4\3\2\2\2JO\5\13\6\2KN\5\13\6\2LN\5\r\7\2MK\3\2\2\2ML\3\2\2\2NQ\3"+
		"\2\2\2OM\3\2\2\2OP\3\2\2\2P\6\3\2\2\2QO\3\2\2\2R[\7\62\2\2SW\5\17\b\2"+
		"TV\5\r\7\2UT\3\2\2\2VY\3\2\2\2WU\3\2\2\2WX\3\2\2\2X[\3\2\2\2YW\3\2\2\2"+
		"ZR\3\2\2\2ZS\3\2\2\2[\b\3\2\2\2\\]\7v\2\2]^\7t\2\2^_\7w\2\2_f\7g\2\2`"+
		"a\7h\2\2ab\7c\2\2bc\7n\2\2cd\7u\2\2df\7g\2\2e\\\3\2\2\2e`\3\2\2\2f\n\3"+
		"\2\2\2gh\t\2\2\2h\f\3\2\2\2ij\t\3\2\2j\16\3\2\2\2kl\t\4\2\2l\20\3\2\2"+
		"\2mn\7*\2\2n\22\3\2\2\2op\7+\2\2p\24\3\2\2\2qr\7}\2\2r\26\3\2\2\2st\7"+
		"\177\2\2t\30\3\2\2\2uv\7]\2\2v\32\3\2\2\2wx\7_\2\2x\34\3\2\2\2yz\7<\2"+
		"\2z\36\3\2\2\2{|\7=\2\2| \3\2\2\2}~\7.\2\2~\"\3\2\2\2\177\u0080\7\60\2"+
		"\2\u0080$\3\2\2\2\u0081\u0082\7@\2\2\u0082&\3\2\2\2\u0083\u0084\7>\2\2"+
		"\u0084(\3\2\2\2\u0085\u0086\7#\2\2\u0086*\3\2\2\2\u0087\u0088\7?\2\2\u0088"+
		"\u0089\7?\2\2\u0089,\3\2\2\2\u008a\u008b\7>\2\2\u008b\u008c\7?\2\2\u008c"+
		".\3\2\2\2\u008d\u008e\7@\2\2\u008e\u008f\7?\2\2\u008f\60\3\2\2\2\u0090"+
		"\u0091\7#\2\2\u0091\u0092\7?\2\2\u0092\62\3\2\2\2\u0093\u0094\7(\2\2\u0094"+
		"\u0095\7(\2\2\u0095\64\3\2\2\2\u0096\u0097\7~\2\2\u0097\u0098\7~\2\2\u0098"+
		"\66\3\2\2\2\u0099\u009a\7-\2\2\u009a8\3\2\2\2\u009b\u009c\7/\2\2\u009c"+
		":\3\2\2\2\u009d\u009e\7,\2\2\u009e<\3\2\2\2\u009f\u00a0\7\61\2\2\u00a0"+
		">\3\2\2\2\u00a1\u00a2\7\'\2\2\u00a2@\3\2\2\2\u00a3\u00a5\t\5\2\2\u00a4"+
		"\u00a3\3\2\2\2\u00a5\u00a6\3\2\2\2\u00a6\u00a4\3\2\2\2\u00a6\u00a7\3\2"+
		"\2\2\u00a7\u00a8\3\2\2\2\u00a8\u00a9\b!\2\2\u00a9B\3\2\2\2\u00aa\u00ab"+
		"\7\61\2\2\u00ab\u00ac\7,\2\2\u00ac\u00b0\3\2\2\2\u00ad\u00af\13\2\2\2"+
		"\u00ae\u00ad\3\2\2\2\u00af\u00b2\3\2\2\2\u00b0\u00b1\3\2\2\2\u00b0\u00ae"+
		"\3\2\2\2\u00b1\u00b3\3\2\2\2\u00b2\u00b0\3\2\2\2\u00b3\u00b4\7,\2\2\u00b4"+
		"\u00b5\7\61\2\2\u00b5\u00b6\3\2\2\2\u00b6\u00b7\b\"\2\2\u00b7D\3\2\2\2"+
		"\u00b8\u00b9\7\61\2\2\u00b9\u00ba\7\61\2\2\u00ba\u00be\3\2\2\2\u00bb\u00bd"+
		"\n\6\2\2\u00bc\u00bb\3\2\2\2\u00bd\u00c0\3\2\2\2\u00be\u00bc\3\2\2\2\u00be"+
		"\u00bf\3\2\2\2\u00bf\u00c1\3\2\2\2\u00c0\u00be\3\2\2\2\u00c1\u00c2\b#"+
		"\2\2\u00c2F\3\2\2\2\13\2MOWZe\u00a6\u00b0\u00be\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}