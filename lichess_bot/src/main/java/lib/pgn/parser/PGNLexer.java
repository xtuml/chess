// Generated from src/main/antlr4/PGN.g4 by ANTLR 4.13.1
package lib.pgn.parser;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class PGNLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.13.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		WHITE_WINS=1, BLACK_WINS=2, DRAWN_GAME=3, REST_OF_LINE_COMMENT=4, BRACE_COMMENT=5, 
		ESCAPE=6, SPACES=7, STRING=8, INTEGER=9, PERIOD=10, ASTERISK=11, LEFT_BRACKET=12, 
		RIGHT_BRACKET=13, LEFT_PARENTHESIS=14, RIGHT_PARENTHESIS=15, LEFT_ANGLE_BRACKET=16, 
		RIGHT_ANGLE_BRACKET=17, NUMERIC_ANNOTATION_GLYPH=18, SYMBOL=19, SUFFIX_ANNOTATION=20, 
		UNEXPECTED_CHAR=21;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"WHITE_WINS", "BLACK_WINS", "DRAWN_GAME", "REST_OF_LINE_COMMENT", "BRACE_COMMENT", 
			"ESCAPE", "SPACES", "STRING", "INTEGER", "PERIOD", "ASTERISK", "LEFT_BRACKET", 
			"RIGHT_BRACKET", "LEFT_PARENTHESIS", "RIGHT_PARENTHESIS", "LEFT_ANGLE_BRACKET", 
			"RIGHT_ANGLE_BRACKET", "NUMERIC_ANNOTATION_GLYPH", "SYMBOL", "SUFFIX_ANNOTATION", 
			"UNEXPECTED_CHAR"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'1-0'", "'0-1'", "'1/2-1/2'", null, null, null, null, null, null, 
			"'.'", "'*'", "'['", "']'", "'('", "')'", "'<'", "'>'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "WHITE_WINS", "BLACK_WINS", "DRAWN_GAME", "REST_OF_LINE_COMMENT", 
			"BRACE_COMMENT", "ESCAPE", "SPACES", "STRING", "INTEGER", "PERIOD", "ASTERISK", 
			"LEFT_BRACKET", "RIGHT_BRACKET", "LEFT_PARENTHESIS", "RIGHT_PARENTHESIS", 
			"LEFT_ANGLE_BRACKET", "RIGHT_ANGLE_BRACKET", "NUMERIC_ANNOTATION_GLYPH", 
			"SYMBOL", "SUFFIX_ANNOTATION", "UNEXPECTED_CHAR"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
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


	public PGNLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "PGN.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	@Override
	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 5:
			return ESCAPE_sempred((RuleContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean ESCAPE_sempred(RuleContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return getCharPositionInLine() == 0;
		}
		return true;
	}

	public static final String _serializedATN =
		"\u0004\u0000\u0015\u0095\u0006\uffff\uffff\u0002\u0000\u0007\u0000\u0002"+
		"\u0001\u0007\u0001\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002"+
		"\u0004\u0007\u0004\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002"+
		"\u0007\u0007\u0007\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002"+
		"\u000b\u0007\u000b\u0002\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e"+
		"\u0002\u000f\u0007\u000f\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011"+
		"\u0002\u0012\u0007\u0012\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014"+
		"\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0003\u0001\u0003"+
		"\u0005\u0003>\b\u0003\n\u0003\f\u0003A\t\u0003\u0001\u0003\u0001\u0003"+
		"\u0001\u0004\u0001\u0004\u0005\u0004G\b\u0004\n\u0004\f\u0004J\t\u0004"+
		"\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0005\u0001\u0005"+
		"\u0001\u0005\u0005\u0005S\b\u0005\n\u0005\f\u0005V\t\u0005\u0001\u0005"+
		"\u0001\u0005\u0001\u0006\u0004\u0006[\b\u0006\u000b\u0006\f\u0006\\\u0001"+
		"\u0006\u0001\u0006\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001"+
		"\u0007\u0001\u0007\u0005\u0007g\b\u0007\n\u0007\f\u0007j\t\u0007\u0001"+
		"\u0007\u0001\u0007\u0001\b\u0004\bo\b\b\u000b\b\f\bp\u0001\t\u0001\t\u0001"+
		"\n\u0001\n\u0001\u000b\u0001\u000b\u0001\f\u0001\f\u0001\r\u0001\r\u0001"+
		"\u000e\u0001\u000e\u0001\u000f\u0001\u000f\u0001\u0010\u0001\u0010\u0001"+
		"\u0011\u0001\u0011\u0004\u0011\u0085\b\u0011\u000b\u0011\f\u0011\u0086"+
		"\u0001\u0012\u0001\u0012\u0005\u0012\u008b\b\u0012\n\u0012\f\u0012\u008e"+
		"\t\u0012\u0001\u0013\u0001\u0013\u0003\u0013\u0092\b\u0013\u0001\u0014"+
		"\u0001\u0014\u0000\u0000\u0015\u0001\u0001\u0003\u0002\u0005\u0003\u0007"+
		"\u0004\t\u0005\u000b\u0006\r\u0007\u000f\b\u0011\t\u0013\n\u0015\u000b"+
		"\u0017\f\u0019\r\u001b\u000e\u001d\u000f\u001f\u0010!\u0011#\u0012%\u0013"+
		"\'\u0014)\u0015\u0001\u0000\b\u0002\u0000\n\n\r\r\u0001\u0000}}\u0003"+
		"\u0000\t\n\r\r  \u0002\u0000\"\"\\\\\u0001\u000009\u0003\u000009AZaz\b"+
		"\u0000##++--0:==AZ__az\u0002\u0000!!??\u009f\u0000\u0001\u0001\u0000\u0000"+
		"\u0000\u0000\u0003\u0001\u0000\u0000\u0000\u0000\u0005\u0001\u0000\u0000"+
		"\u0000\u0000\u0007\u0001\u0000\u0000\u0000\u0000\t\u0001\u0000\u0000\u0000"+
		"\u0000\u000b\u0001\u0000\u0000\u0000\u0000\r\u0001\u0000\u0000\u0000\u0000"+
		"\u000f\u0001\u0000\u0000\u0000\u0000\u0011\u0001\u0000\u0000\u0000\u0000"+
		"\u0013\u0001\u0000\u0000\u0000\u0000\u0015\u0001\u0000\u0000\u0000\u0000"+
		"\u0017\u0001\u0000\u0000\u0000\u0000\u0019\u0001\u0000\u0000\u0000\u0000"+
		"\u001b\u0001\u0000\u0000\u0000\u0000\u001d\u0001\u0000\u0000\u0000\u0000"+
		"\u001f\u0001\u0000\u0000\u0000\u0000!\u0001\u0000\u0000\u0000\u0000#\u0001"+
		"\u0000\u0000\u0000\u0000%\u0001\u0000\u0000\u0000\u0000\'\u0001\u0000"+
		"\u0000\u0000\u0000)\u0001\u0000\u0000\u0000\u0001+\u0001\u0000\u0000\u0000"+
		"\u0003/\u0001\u0000\u0000\u0000\u00053\u0001\u0000\u0000\u0000\u0007;"+
		"\u0001\u0000\u0000\u0000\tD\u0001\u0000\u0000\u0000\u000bO\u0001\u0000"+
		"\u0000\u0000\rZ\u0001\u0000\u0000\u0000\u000f`\u0001\u0000\u0000\u0000"+
		"\u0011n\u0001\u0000\u0000\u0000\u0013r\u0001\u0000\u0000\u0000\u0015t"+
		"\u0001\u0000\u0000\u0000\u0017v\u0001\u0000\u0000\u0000\u0019x\u0001\u0000"+
		"\u0000\u0000\u001bz\u0001\u0000\u0000\u0000\u001d|\u0001\u0000\u0000\u0000"+
		"\u001f~\u0001\u0000\u0000\u0000!\u0080\u0001\u0000\u0000\u0000#\u0082"+
		"\u0001\u0000\u0000\u0000%\u0088\u0001\u0000\u0000\u0000\'\u008f\u0001"+
		"\u0000\u0000\u0000)\u0093\u0001\u0000\u0000\u0000+,\u00051\u0000\u0000"+
		",-\u0005-\u0000\u0000-.\u00050\u0000\u0000.\u0002\u0001\u0000\u0000\u0000"+
		"/0\u00050\u0000\u000001\u0005-\u0000\u000012\u00051\u0000\u00002\u0004"+
		"\u0001\u0000\u0000\u000034\u00051\u0000\u000045\u0005/\u0000\u000056\u0005"+
		"2\u0000\u000067\u0005-\u0000\u000078\u00051\u0000\u000089\u0005/\u0000"+
		"\u00009:\u00052\u0000\u0000:\u0006\u0001\u0000\u0000\u0000;?\u0005;\u0000"+
		"\u0000<>\b\u0000\u0000\u0000=<\u0001\u0000\u0000\u0000>A\u0001\u0000\u0000"+
		"\u0000?=\u0001\u0000\u0000\u0000?@\u0001\u0000\u0000\u0000@B\u0001\u0000"+
		"\u0000\u0000A?\u0001\u0000\u0000\u0000BC\u0006\u0003\u0000\u0000C\b\u0001"+
		"\u0000\u0000\u0000DH\u0005{\u0000\u0000EG\b\u0001\u0000\u0000FE\u0001"+
		"\u0000\u0000\u0000GJ\u0001\u0000\u0000\u0000HF\u0001\u0000\u0000\u0000"+
		"HI\u0001\u0000\u0000\u0000IK\u0001\u0000\u0000\u0000JH\u0001\u0000\u0000"+
		"\u0000KL\u0005}\u0000\u0000LM\u0001\u0000\u0000\u0000MN\u0006\u0004\u0000"+
		"\u0000N\n\u0001\u0000\u0000\u0000OP\u0004\u0005\u0000\u0000PT\u0005%\u0000"+
		"\u0000QS\b\u0000\u0000\u0000RQ\u0001\u0000\u0000\u0000SV\u0001\u0000\u0000"+
		"\u0000TR\u0001\u0000\u0000\u0000TU\u0001\u0000\u0000\u0000UW\u0001\u0000"+
		"\u0000\u0000VT\u0001\u0000\u0000\u0000WX\u0006\u0005\u0000\u0000X\f\u0001"+
		"\u0000\u0000\u0000Y[\u0007\u0002\u0000\u0000ZY\u0001\u0000\u0000\u0000"+
		"[\\\u0001\u0000\u0000\u0000\\Z\u0001\u0000\u0000\u0000\\]\u0001\u0000"+
		"\u0000\u0000]^\u0001\u0000\u0000\u0000^_\u0006\u0006\u0000\u0000_\u000e"+
		"\u0001\u0000\u0000\u0000`h\u0005\"\u0000\u0000ab\u0005\\\u0000\u0000b"+
		"g\u0005\\\u0000\u0000cd\u0005\\\u0000\u0000dg\u0005\"\u0000\u0000eg\b"+
		"\u0003\u0000\u0000fa\u0001\u0000\u0000\u0000fc\u0001\u0000\u0000\u0000"+
		"fe\u0001\u0000\u0000\u0000gj\u0001\u0000\u0000\u0000hf\u0001\u0000\u0000"+
		"\u0000hi\u0001\u0000\u0000\u0000ik\u0001\u0000\u0000\u0000jh\u0001\u0000"+
		"\u0000\u0000kl\u0005\"\u0000\u0000l\u0010\u0001\u0000\u0000\u0000mo\u0007"+
		"\u0004\u0000\u0000nm\u0001\u0000\u0000\u0000op\u0001\u0000\u0000\u0000"+
		"pn\u0001\u0000\u0000\u0000pq\u0001\u0000\u0000\u0000q\u0012\u0001\u0000"+
		"\u0000\u0000rs\u0005.\u0000\u0000s\u0014\u0001\u0000\u0000\u0000tu\u0005"+
		"*\u0000\u0000u\u0016\u0001\u0000\u0000\u0000vw\u0005[\u0000\u0000w\u0018"+
		"\u0001\u0000\u0000\u0000xy\u0005]\u0000\u0000y\u001a\u0001\u0000\u0000"+
		"\u0000z{\u0005(\u0000\u0000{\u001c\u0001\u0000\u0000\u0000|}\u0005)\u0000"+
		"\u0000}\u001e\u0001\u0000\u0000\u0000~\u007f\u0005<\u0000\u0000\u007f"+
		" \u0001\u0000\u0000\u0000\u0080\u0081\u0005>\u0000\u0000\u0081\"\u0001"+
		"\u0000\u0000\u0000\u0082\u0084\u0005$\u0000\u0000\u0083\u0085\u0007\u0004"+
		"\u0000\u0000\u0084\u0083\u0001\u0000\u0000\u0000\u0085\u0086\u0001\u0000"+
		"\u0000\u0000\u0086\u0084\u0001\u0000\u0000\u0000\u0086\u0087\u0001\u0000"+
		"\u0000\u0000\u0087$\u0001\u0000\u0000\u0000\u0088\u008c\u0007\u0005\u0000"+
		"\u0000\u0089\u008b\u0007\u0006\u0000\u0000\u008a\u0089\u0001\u0000\u0000"+
		"\u0000\u008b\u008e\u0001\u0000\u0000\u0000\u008c\u008a\u0001\u0000\u0000"+
		"\u0000\u008c\u008d\u0001\u0000\u0000\u0000\u008d&\u0001\u0000\u0000\u0000"+
		"\u008e\u008c\u0001\u0000\u0000\u0000\u008f\u0091\u0007\u0007\u0000\u0000"+
		"\u0090\u0092\u0007\u0007\u0000\u0000\u0091\u0090\u0001\u0000\u0000\u0000"+
		"\u0091\u0092\u0001\u0000\u0000\u0000\u0092(\u0001\u0000\u0000\u0000\u0093"+
		"\u0094\t\u0000\u0000\u0000\u0094*\u0001\u0000\u0000\u0000\u000b\u0000"+
		"?HT\\fhp\u0086\u008c\u0091\u0001\u0006\u0000\u0000";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}