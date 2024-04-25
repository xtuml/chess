// Generated from src/main/antlr4/PGN.g4 by ANTLR 4.13.1
package lib.pgn.parser;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class PGNParser extends Parser {
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
	public static final int
		RULE_parse = 0, RULE_pgn_database = 1, RULE_pgn_game = 2, RULE_tag_section = 3, 
		RULE_tag_pair = 4, RULE_tag_name = 5, RULE_tag_value = 6, RULE_movetext_section = 7, 
		RULE_element_sequence = 8, RULE_element = 9, RULE_move_number_indication = 10, 
		RULE_san_move = 11, RULE_recursive_variation = 12, RULE_game_termination = 13;
	private static String[] makeRuleNames() {
		return new String[] {
			"parse", "pgn_database", "pgn_game", "tag_section", "tag_pair", "tag_name", 
			"tag_value", "movetext_section", "element_sequence", "element", "move_number_indication", 
			"san_move", "recursive_variation", "game_termination"
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

	@Override
	public String getGrammarFileName() { return "PGN.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public PGNParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ParseContext extends ParserRuleContext {
		public Pgn_databaseContext pgn_database() {
			return getRuleContext(Pgn_databaseContext.class,0);
		}
		public TerminalNode EOF() { return getToken(PGNParser.EOF, 0); }
		public ParseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parse; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PGNVisitor ) return ((PGNVisitor<? extends T>)visitor).visitParse(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParseContext parse() throws RecognitionException {
		ParseContext _localctx = new ParseContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_parse);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(28);
			pgn_database();
			setState(29);
			match(EOF);
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

	@SuppressWarnings("CheckReturnValue")
	public static class Pgn_databaseContext extends ParserRuleContext {
		public List<Pgn_gameContext> pgn_game() {
			return getRuleContexts(Pgn_gameContext.class);
		}
		public Pgn_gameContext pgn_game(int i) {
			return getRuleContext(Pgn_gameContext.class,i);
		}
		public Pgn_databaseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pgn_database; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PGNVisitor ) return ((PGNVisitor<? extends T>)visitor).visitPgn_database(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Pgn_databaseContext pgn_database() throws RecognitionException {
		Pgn_databaseContext _localctx = new Pgn_databaseContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_pgn_database);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(34);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 807424L) != 0)) {
				{
				{
				setState(31);
				pgn_game();
				}
				}
				setState(36);
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

	@SuppressWarnings("CheckReturnValue")
	public static class Pgn_gameContext extends ParserRuleContext {
		public Tag_sectionContext tag_section() {
			return getRuleContext(Tag_sectionContext.class,0);
		}
		public Movetext_sectionContext movetext_section() {
			return getRuleContext(Movetext_sectionContext.class,0);
		}
		public Pgn_gameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pgn_game; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PGNVisitor ) return ((PGNVisitor<? extends T>)visitor).visitPgn_game(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Pgn_gameContext pgn_game() throws RecognitionException {
		Pgn_gameContext _localctx = new Pgn_gameContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_pgn_game);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(37);
			tag_section();
			setState(38);
			movetext_section();
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

	@SuppressWarnings("CheckReturnValue")
	public static class Tag_sectionContext extends ParserRuleContext {
		public List<Tag_pairContext> tag_pair() {
			return getRuleContexts(Tag_pairContext.class);
		}
		public Tag_pairContext tag_pair(int i) {
			return getRuleContext(Tag_pairContext.class,i);
		}
		public Tag_sectionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tag_section; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PGNVisitor ) return ((PGNVisitor<? extends T>)visitor).visitTag_section(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Tag_sectionContext tag_section() throws RecognitionException {
		Tag_sectionContext _localctx = new Tag_sectionContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_tag_section);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(43);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==LEFT_BRACKET) {
				{
				{
				setState(40);
				tag_pair();
				}
				}
				setState(45);
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

	@SuppressWarnings("CheckReturnValue")
	public static class Tag_pairContext extends ParserRuleContext {
		public TerminalNode LEFT_BRACKET() { return getToken(PGNParser.LEFT_BRACKET, 0); }
		public Tag_nameContext tag_name() {
			return getRuleContext(Tag_nameContext.class,0);
		}
		public Tag_valueContext tag_value() {
			return getRuleContext(Tag_valueContext.class,0);
		}
		public TerminalNode RIGHT_BRACKET() { return getToken(PGNParser.RIGHT_BRACKET, 0); }
		public Tag_pairContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tag_pair; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PGNVisitor ) return ((PGNVisitor<? extends T>)visitor).visitTag_pair(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Tag_pairContext tag_pair() throws RecognitionException {
		Tag_pairContext _localctx = new Tag_pairContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_tag_pair);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(46);
			match(LEFT_BRACKET);
			setState(47);
			tag_name();
			setState(48);
			tag_value();
			setState(49);
			match(RIGHT_BRACKET);
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

	@SuppressWarnings("CheckReturnValue")
	public static class Tag_nameContext extends ParserRuleContext {
		public TerminalNode SYMBOL() { return getToken(PGNParser.SYMBOL, 0); }
		public Tag_nameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tag_name; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PGNVisitor ) return ((PGNVisitor<? extends T>)visitor).visitTag_name(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Tag_nameContext tag_name() throws RecognitionException {
		Tag_nameContext _localctx = new Tag_nameContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_tag_name);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(51);
			match(SYMBOL);
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

	@SuppressWarnings("CheckReturnValue")
	public static class Tag_valueContext extends ParserRuleContext {
		public TerminalNode STRING() { return getToken(PGNParser.STRING, 0); }
		public Tag_valueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tag_value; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PGNVisitor ) return ((PGNVisitor<? extends T>)visitor).visitTag_value(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Tag_valueContext tag_value() throws RecognitionException {
		Tag_valueContext _localctx = new Tag_valueContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_tag_value);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(53);
			match(STRING);
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

	@SuppressWarnings("CheckReturnValue")
	public static class Movetext_sectionContext extends ParserRuleContext {
		public Element_sequenceContext element_sequence() {
			return getRuleContext(Element_sequenceContext.class,0);
		}
		public Game_terminationContext game_termination() {
			return getRuleContext(Game_terminationContext.class,0);
		}
		public Movetext_sectionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_movetext_section; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PGNVisitor ) return ((PGNVisitor<? extends T>)visitor).visitMovetext_section(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Movetext_sectionContext movetext_section() throws RecognitionException {
		Movetext_sectionContext _localctx = new Movetext_sectionContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_movetext_section);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(55);
			element_sequence();
			setState(57);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 2062L) != 0)) {
				{
				setState(56);
				game_termination();
				}
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

	@SuppressWarnings("CheckReturnValue")
	public static class Element_sequenceContext extends ParserRuleContext {
		public List<ElementContext> element() {
			return getRuleContexts(ElementContext.class);
		}
		public ElementContext element(int i) {
			return getRuleContext(ElementContext.class,i);
		}
		public Element_sequenceContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_element_sequence; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PGNVisitor ) return ((PGNVisitor<? extends T>)visitor).visitElement_sequence(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Element_sequenceContext element_sequence() throws RecognitionException {
		Element_sequenceContext _localctx = new Element_sequenceContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_element_sequence);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(60); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(59);
					element();
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(62); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,3,_ctx);
			} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
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

	@SuppressWarnings("CheckReturnValue")
	public static class ElementContext extends ParserRuleContext {
		public Move_number_indicationContext move_number_indication() {
			return getRuleContext(Move_number_indicationContext.class,0);
		}
		public San_moveContext san_move() {
			return getRuleContext(San_moveContext.class,0);
		}
		public TerminalNode NUMERIC_ANNOTATION_GLYPH() { return getToken(PGNParser.NUMERIC_ANNOTATION_GLYPH, 0); }
		public Recursive_variationContext recursive_variation() {
			return getRuleContext(Recursive_variationContext.class,0);
		}
		public ElementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_element; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PGNVisitor ) return ((PGNVisitor<? extends T>)visitor).visitElement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ElementContext element() throws RecognitionException {
		ElementContext _localctx = new ElementContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_element);
		try {
			setState(68);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case INTEGER:
				enterOuterAlt(_localctx, 1);
				{
				setState(64);
				move_number_indication();
				}
				break;
			case SYMBOL:
				enterOuterAlt(_localctx, 2);
				{
				setState(65);
				san_move();
				}
				break;
			case NUMERIC_ANNOTATION_GLYPH:
				enterOuterAlt(_localctx, 3);
				{
				setState(66);
				match(NUMERIC_ANNOTATION_GLYPH);
				}
				break;
			case LEFT_PARENTHESIS:
				enterOuterAlt(_localctx, 4);
				{
				setState(67);
				recursive_variation();
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

	@SuppressWarnings("CheckReturnValue")
	public static class Move_number_indicationContext extends ParserRuleContext {
		public TerminalNode INTEGER() { return getToken(PGNParser.INTEGER, 0); }
		public List<TerminalNode> PERIOD() { return getTokens(PGNParser.PERIOD); }
		public TerminalNode PERIOD(int i) {
			return getToken(PGNParser.PERIOD, i);
		}
		public Move_number_indicationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_move_number_indication; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PGNVisitor ) return ((PGNVisitor<? extends T>)visitor).visitMove_number_indication(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Move_number_indicationContext move_number_indication() throws RecognitionException {
		Move_number_indicationContext _localctx = new Move_number_indicationContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_move_number_indication);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(70);
			match(INTEGER);
			setState(71);
			match(PERIOD);
			setState(74);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==PERIOD) {
				{
				setState(72);
				match(PERIOD);
				setState(73);
				match(PERIOD);
				}
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

	@SuppressWarnings("CheckReturnValue")
	public static class San_moveContext extends ParserRuleContext {
		public TerminalNode SYMBOL() { return getToken(PGNParser.SYMBOL, 0); }
		public TerminalNode SUFFIX_ANNOTATION() { return getToken(PGNParser.SUFFIX_ANNOTATION, 0); }
		public San_moveContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_san_move; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PGNVisitor ) return ((PGNVisitor<? extends T>)visitor).visitSan_move(this);
			else return visitor.visitChildren(this);
		}
	}

	public final San_moveContext san_move() throws RecognitionException {
		San_moveContext _localctx = new San_moveContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_san_move);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(76);
			match(SYMBOL);
			setState(78);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==SUFFIX_ANNOTATION) {
				{
				setState(77);
				match(SUFFIX_ANNOTATION);
				}
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

	@SuppressWarnings("CheckReturnValue")
	public static class Recursive_variationContext extends ParserRuleContext {
		public TerminalNode LEFT_PARENTHESIS() { return getToken(PGNParser.LEFT_PARENTHESIS, 0); }
		public Element_sequenceContext element_sequence() {
			return getRuleContext(Element_sequenceContext.class,0);
		}
		public TerminalNode RIGHT_PARENTHESIS() { return getToken(PGNParser.RIGHT_PARENTHESIS, 0); }
		public Recursive_variationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_recursive_variation; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PGNVisitor ) return ((PGNVisitor<? extends T>)visitor).visitRecursive_variation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Recursive_variationContext recursive_variation() throws RecognitionException {
		Recursive_variationContext _localctx = new Recursive_variationContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_recursive_variation);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(80);
			match(LEFT_PARENTHESIS);
			setState(81);
			element_sequence();
			setState(82);
			match(RIGHT_PARENTHESIS);
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

	@SuppressWarnings("CheckReturnValue")
	public static class Game_terminationContext extends ParserRuleContext {
		public TerminalNode WHITE_WINS() { return getToken(PGNParser.WHITE_WINS, 0); }
		public TerminalNode BLACK_WINS() { return getToken(PGNParser.BLACK_WINS, 0); }
		public TerminalNode DRAWN_GAME() { return getToken(PGNParser.DRAWN_GAME, 0); }
		public TerminalNode ASTERISK() { return getToken(PGNParser.ASTERISK, 0); }
		public Game_terminationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_game_termination; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof PGNVisitor ) return ((PGNVisitor<? extends T>)visitor).visitGame_termination(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Game_terminationContext game_termination() throws RecognitionException {
		Game_terminationContext _localctx = new Game_terminationContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_game_termination);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(84);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 2062L) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
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

	public static final String _serializedATN =
		"\u0004\u0001\u0015W\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002"+
		"\f\u0007\f\u0002\r\u0007\r\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0001"+
		"\u0005\u0001!\b\u0001\n\u0001\f\u0001$\t\u0001\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0003\u0005\u0003*\b\u0003\n\u0003\f\u0003-\t\u0003"+
		"\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0005"+
		"\u0001\u0005\u0001\u0006\u0001\u0006\u0001\u0007\u0001\u0007\u0003\u0007"+
		":\b\u0007\u0001\b\u0004\b=\b\b\u000b\b\f\b>\u0001\t\u0001\t\u0001\t\u0001"+
		"\t\u0003\tE\b\t\u0001\n\u0001\n\u0001\n\u0001\n\u0003\nK\b\n\u0001\u000b"+
		"\u0001\u000b\u0003\u000bO\b\u000b\u0001\f\u0001\f\u0001\f\u0001\f\u0001"+
		"\r\u0001\r\u0001\r\u0000\u0000\u000e\u0000\u0002\u0004\u0006\b\n\f\u000e"+
		"\u0010\u0012\u0014\u0016\u0018\u001a\u0000\u0001\u0002\u0000\u0001\u0003"+
		"\u000b\u000bQ\u0000\u001c\u0001\u0000\u0000\u0000\u0002\"\u0001\u0000"+
		"\u0000\u0000\u0004%\u0001\u0000\u0000\u0000\u0006+\u0001\u0000\u0000\u0000"+
		"\b.\u0001\u0000\u0000\u0000\n3\u0001\u0000\u0000\u0000\f5\u0001\u0000"+
		"\u0000\u0000\u000e7\u0001\u0000\u0000\u0000\u0010<\u0001\u0000\u0000\u0000"+
		"\u0012D\u0001\u0000\u0000\u0000\u0014F\u0001\u0000\u0000\u0000\u0016L"+
		"\u0001\u0000\u0000\u0000\u0018P\u0001\u0000\u0000\u0000\u001aT\u0001\u0000"+
		"\u0000\u0000\u001c\u001d\u0003\u0002\u0001\u0000\u001d\u001e\u0005\u0000"+
		"\u0000\u0001\u001e\u0001\u0001\u0000\u0000\u0000\u001f!\u0003\u0004\u0002"+
		"\u0000 \u001f\u0001\u0000\u0000\u0000!$\u0001\u0000\u0000\u0000\" \u0001"+
		"\u0000\u0000\u0000\"#\u0001\u0000\u0000\u0000#\u0003\u0001\u0000\u0000"+
		"\u0000$\"\u0001\u0000\u0000\u0000%&\u0003\u0006\u0003\u0000&\'\u0003\u000e"+
		"\u0007\u0000\'\u0005\u0001\u0000\u0000\u0000(*\u0003\b\u0004\u0000)(\u0001"+
		"\u0000\u0000\u0000*-\u0001\u0000\u0000\u0000+)\u0001\u0000\u0000\u0000"+
		"+,\u0001\u0000\u0000\u0000,\u0007\u0001\u0000\u0000\u0000-+\u0001\u0000"+
		"\u0000\u0000./\u0005\f\u0000\u0000/0\u0003\n\u0005\u000001\u0003\f\u0006"+
		"\u000012\u0005\r\u0000\u00002\t\u0001\u0000\u0000\u000034\u0005\u0013"+
		"\u0000\u00004\u000b\u0001\u0000\u0000\u000056\u0005\b\u0000\u00006\r\u0001"+
		"\u0000\u0000\u000079\u0003\u0010\b\u00008:\u0003\u001a\r\u000098\u0001"+
		"\u0000\u0000\u00009:\u0001\u0000\u0000\u0000:\u000f\u0001\u0000\u0000"+
		"\u0000;=\u0003\u0012\t\u0000<;\u0001\u0000\u0000\u0000=>\u0001\u0000\u0000"+
		"\u0000><\u0001\u0000\u0000\u0000>?\u0001\u0000\u0000\u0000?\u0011\u0001"+
		"\u0000\u0000\u0000@E\u0003\u0014\n\u0000AE\u0003\u0016\u000b\u0000BE\u0005"+
		"\u0012\u0000\u0000CE\u0003\u0018\f\u0000D@\u0001\u0000\u0000\u0000DA\u0001"+
		"\u0000\u0000\u0000DB\u0001\u0000\u0000\u0000DC\u0001\u0000\u0000\u0000"+
		"E\u0013\u0001\u0000\u0000\u0000FG\u0005\t\u0000\u0000GJ\u0005\n\u0000"+
		"\u0000HI\u0005\n\u0000\u0000IK\u0005\n\u0000\u0000JH\u0001\u0000\u0000"+
		"\u0000JK\u0001\u0000\u0000\u0000K\u0015\u0001\u0000\u0000\u0000LN\u0005"+
		"\u0013\u0000\u0000MO\u0005\u0014\u0000\u0000NM\u0001\u0000\u0000\u0000"+
		"NO\u0001\u0000\u0000\u0000O\u0017\u0001\u0000\u0000\u0000PQ\u0005\u000e"+
		"\u0000\u0000QR\u0003\u0010\b\u0000RS\u0005\u000f\u0000\u0000S\u0019\u0001"+
		"\u0000\u0000\u0000TU\u0007\u0000\u0000\u0000U\u001b\u0001\u0000\u0000"+
		"\u0000\u0007\"+9>DJN";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}