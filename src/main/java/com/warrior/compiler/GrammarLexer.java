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
		T__0=1, T__1=2, T__2=3, Identifier=4, IntLiteral=5, BoolLiteral=6, LPAREN=7, 
		RPAREN=8, LBRACE=9, RBRACE=10, LBRACK=11, RBRACK=12, COLON=13, SEMICOLON=14, 
		COMMA=15, DOT=16, ARROW=17, GT=18, LT=19, BANG=20, EQUAL=21, LE=22, GE=23, 
		NOTEQUAL=24, AND=25, OR=26, ADD=27, SUB=28, MUL=29, DIV=30, MOD=31, ASSIGN=32, 
		WS=33, COMMENT=34, LINE_COMMENT=35;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "T__1", "T__2", "Identifier", "IntLiteral", "BoolLiteral", "Letter", 
		"Digit", "NonZeroDigit", "LPAREN", "RPAREN", "LBRACE", "RBRACE", "LBRACK", 
		"RBRACK", "COLON", "SEMICOLON", "COMMA", "DOT", "ARROW", "GT", "LT", "BANG", 
		"EQUAL", "LE", "GE", "NOTEQUAL", "AND", "OR", "ADD", "SUB", "MUL", "DIV", 
		"MOD", "ASSIGN", "WS", "COMMENT", "LINE_COMMENT"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'fn'", "'if'", "'else'", null, null, null, "'('", "')'", "'{'", 
		"'}'", "'['", "']'", "':'", "';'", "','", "'.'", "'->'", "'>'", "'<'", 
		"'!'", "'=='", "'<='", "'>='", "'!='", "'&&'", "'||'", "'+'", "'-'", "'*'", 
		"'/'", "'%'", "'='"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, "Identifier", "IntLiteral", "BoolLiteral", "LPAREN", 
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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2%\u00d8\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\3\2\3\2\3\2\3\3\3\3\3\3\3"+
		"\4\3\4\3\4\3\4\3\4\3\5\3\5\3\5\7\5^\n\5\f\5\16\5a\13\5\3\6\3\6\3\6\7\6"+
		"f\n\6\f\6\16\6i\13\6\5\6k\n\6\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\5\7"+
		"v\n\7\3\b\3\b\3\t\3\t\3\n\3\n\3\13\3\13\3\f\3\f\3\r\3\r\3\16\3\16\3\17"+
		"\3\17\3\20\3\20\3\21\3\21\3\22\3\22\3\23\3\23\3\24\3\24\3\25\3\25\3\25"+
		"\3\26\3\26\3\27\3\27\3\30\3\30\3\31\3\31\3\31\3\32\3\32\3\32\3\33\3\33"+
		"\3\33\3\34\3\34\3\34\3\35\3\35\3\35\3\36\3\36\3\36\3\37\3\37\3 \3 \3!"+
		"\3!\3\"\3\"\3#\3#\3$\3$\3%\6%\u00ba\n%\r%\16%\u00bb\3%\3%\3&\3&\3&\3&"+
		"\7&\u00c4\n&\f&\16&\u00c7\13&\3&\3&\3&\3&\3&\3\'\3\'\3\'\3\'\7\'\u00d2"+
		"\n\'\f\'\16\'\u00d5\13\'\3\'\3\'\3\u00c5\2(\3\3\5\4\7\5\t\6\13\7\r\b\17"+
		"\2\21\2\23\2\25\t\27\n\31\13\33\f\35\r\37\16!\17#\20%\21\'\22)\23+\24"+
		"-\25/\26\61\27\63\30\65\31\67\329\33;\34=\35?\36A\37C E!G\"I#K$M%\3\2"+
		"\7\5\2C\\aac|\3\2\62;\3\2\63;\5\2\13\f\17\17\"\"\4\2\f\f\17\17\u00dc\2"+
		"\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2"+
		"\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2"+
		"\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2"+
		"+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2"+
		"\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2"+
		"C\3\2\2\2\2E\3\2\2\2\2G\3\2\2\2\2I\3\2\2\2\2K\3\2\2\2\2M\3\2\2\2\3O\3"+
		"\2\2\2\5R\3\2\2\2\7U\3\2\2\2\tZ\3\2\2\2\13j\3\2\2\2\ru\3\2\2\2\17w\3\2"+
		"\2\2\21y\3\2\2\2\23{\3\2\2\2\25}\3\2\2\2\27\177\3\2\2\2\31\u0081\3\2\2"+
		"\2\33\u0083\3\2\2\2\35\u0085\3\2\2\2\37\u0087\3\2\2\2!\u0089\3\2\2\2#"+
		"\u008b\3\2\2\2%\u008d\3\2\2\2\'\u008f\3\2\2\2)\u0091\3\2\2\2+\u0094\3"+
		"\2\2\2-\u0096\3\2\2\2/\u0098\3\2\2\2\61\u009a\3\2\2\2\63\u009d\3\2\2\2"+
		"\65\u00a0\3\2\2\2\67\u00a3\3\2\2\29\u00a6\3\2\2\2;\u00a9\3\2\2\2=\u00ac"+
		"\3\2\2\2?\u00ae\3\2\2\2A\u00b0\3\2\2\2C\u00b2\3\2\2\2E\u00b4\3\2\2\2G"+
		"\u00b6\3\2\2\2I\u00b9\3\2\2\2K\u00bf\3\2\2\2M\u00cd\3\2\2\2OP\7h\2\2P"+
		"Q\7p\2\2Q\4\3\2\2\2RS\7k\2\2ST\7h\2\2T\6\3\2\2\2UV\7g\2\2VW\7n\2\2WX\7"+
		"u\2\2XY\7g\2\2Y\b\3\2\2\2Z_\5\17\b\2[^\5\17\b\2\\^\5\21\t\2][\3\2\2\2"+
		"]\\\3\2\2\2^a\3\2\2\2_]\3\2\2\2_`\3\2\2\2`\n\3\2\2\2a_\3\2\2\2bk\7\62"+
		"\2\2cg\5\23\n\2df\5\21\t\2ed\3\2\2\2fi\3\2\2\2ge\3\2\2\2gh\3\2\2\2hk\3"+
		"\2\2\2ig\3\2\2\2jb\3\2\2\2jc\3\2\2\2k\f\3\2\2\2lm\7v\2\2mn\7t\2\2no\7"+
		"w\2\2ov\7g\2\2pq\7h\2\2qr\7c\2\2rs\7n\2\2st\7u\2\2tv\7g\2\2ul\3\2\2\2"+
		"up\3\2\2\2v\16\3\2\2\2wx\t\2\2\2x\20\3\2\2\2yz\t\3\2\2z\22\3\2\2\2{|\t"+
		"\4\2\2|\24\3\2\2\2}~\7*\2\2~\26\3\2\2\2\177\u0080\7+\2\2\u0080\30\3\2"+
		"\2\2\u0081\u0082\7}\2\2\u0082\32\3\2\2\2\u0083\u0084\7\177\2\2\u0084\34"+
		"\3\2\2\2\u0085\u0086\7]\2\2\u0086\36\3\2\2\2\u0087\u0088\7_\2\2\u0088"+
		" \3\2\2\2\u0089\u008a\7<\2\2\u008a\"\3\2\2\2\u008b\u008c\7=\2\2\u008c"+
		"$\3\2\2\2\u008d\u008e\7.\2\2\u008e&\3\2\2\2\u008f\u0090\7\60\2\2\u0090"+
		"(\3\2\2\2\u0091\u0092\7/\2\2\u0092\u0093\7@\2\2\u0093*\3\2\2\2\u0094\u0095"+
		"\7@\2\2\u0095,\3\2\2\2\u0096\u0097\7>\2\2\u0097.\3\2\2\2\u0098\u0099\7"+
		"#\2\2\u0099\60\3\2\2\2\u009a\u009b\7?\2\2\u009b\u009c\7?\2\2\u009c\62"+
		"\3\2\2\2\u009d\u009e\7>\2\2\u009e\u009f\7?\2\2\u009f\64\3\2\2\2\u00a0"+
		"\u00a1\7@\2\2\u00a1\u00a2\7?\2\2\u00a2\66\3\2\2\2\u00a3\u00a4\7#\2\2\u00a4"+
		"\u00a5\7?\2\2\u00a58\3\2\2\2\u00a6\u00a7\7(\2\2\u00a7\u00a8\7(\2\2\u00a8"+
		":\3\2\2\2\u00a9\u00aa\7~\2\2\u00aa\u00ab\7~\2\2\u00ab<\3\2\2\2\u00ac\u00ad"+
		"\7-\2\2\u00ad>\3\2\2\2\u00ae\u00af\7/\2\2\u00af@\3\2\2\2\u00b0\u00b1\7"+
		",\2\2\u00b1B\3\2\2\2\u00b2\u00b3\7\61\2\2\u00b3D\3\2\2\2\u00b4\u00b5\7"+
		"\'\2\2\u00b5F\3\2\2\2\u00b6\u00b7\7?\2\2\u00b7H\3\2\2\2\u00b8\u00ba\t"+
		"\5\2\2\u00b9\u00b8\3\2\2\2\u00ba\u00bb\3\2\2\2\u00bb\u00b9\3\2\2\2\u00bb"+
		"\u00bc\3\2\2\2\u00bc\u00bd\3\2\2\2\u00bd\u00be\b%\2\2\u00beJ\3\2\2\2\u00bf"+
		"\u00c0\7\61\2\2\u00c0\u00c1\7,\2\2\u00c1\u00c5\3\2\2\2\u00c2\u00c4\13"+
		"\2\2\2\u00c3\u00c2\3\2\2\2\u00c4\u00c7\3\2\2\2\u00c5\u00c6\3\2\2\2\u00c5"+
		"\u00c3\3\2\2\2\u00c6\u00c8\3\2\2\2\u00c7\u00c5\3\2\2\2\u00c8\u00c9\7,"+
		"\2\2\u00c9\u00ca\7\61\2\2\u00ca\u00cb\3\2\2\2\u00cb\u00cc\b&\2\2\u00cc"+
		"L\3\2\2\2\u00cd\u00ce\7\61\2\2\u00ce\u00cf\7\61\2\2\u00cf\u00d3\3\2\2"+
		"\2\u00d0\u00d2\n\6\2\2\u00d1\u00d0\3\2\2\2\u00d2\u00d5\3\2\2\2\u00d3\u00d1"+
		"\3\2\2\2\u00d3\u00d4\3\2\2\2\u00d4\u00d6\3\2\2\2\u00d5\u00d3\3\2\2\2\u00d6"+
		"\u00d7\b\'\2\2\u00d7N\3\2\2\2\13\2]_gju\u00bb\u00c5\u00d3\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}