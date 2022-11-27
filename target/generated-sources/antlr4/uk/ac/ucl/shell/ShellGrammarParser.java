// Generated from uk\ac\u005Cucl\shell\ShellGrammar.g4 by ANTLR 4.7
package uk.ac.ucl.shell;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class ShellGrammarParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.7", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, DOUBLEQUOTED=5, SINGLEQUOTED=6, BACKQUOTED=7, 
		UNQUOTED=8, WS=9;
	public static final int
		RULE_shell = 0, RULE_sequence = 1, RULE_command = 2, RULE_pipe = 3, RULE_call = 4, 
		RULE_atom = 5, RULE_argument = 6, RULE_redirection = 7, RULE_quoted = 8;
	public static final String[] ruleNames = {
		"shell", "sequence", "command", "pipe", "call", "atom", "argument", "redirection", 
		"quoted"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "';'", "'|'", "'<'", "'>'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, "DOUBLEQUOTED", "SINGLEQUOTED", "BACKQUOTED", 
		"UNQUOTED", "WS"
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

	@Override
	public String getGrammarFileName() { return "ShellGrammar.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public ShellGrammarParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class ShellContext extends ShellRuleContext {
		public SequenceContext sequence() {
			return getRuleContext(SequenceContext.class,0);
		}
		public ShellContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_shell; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShellGrammarVisitor ) return ((ShellGrammarVisitor<? extends T>)visitor).visitShell(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ShellContext shell() throws RecognitionException {
		ShellContext _localctx = new ShellContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_shell);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(19);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__2) | (1L << T__3) | (1L << DOUBLEQUOTED) | (1L << SINGLEQUOTED) | (1L << BACKQUOTED) | (1L << UNQUOTED) | (1L << WS))) != 0)) {
				{
				setState(18);
				sequence();
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

	public static class SequenceContext extends ShellRuleContext {
		public List<CommandContext> command() {
			return getRuleContexts(CommandContext.class);
		}
		public CommandContext command(int i) {
			return getRuleContext(CommandContext.class,i);
		}
		public SequenceContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_sequence; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShellGrammarVisitor ) return ((ShellGrammarVisitor<? extends T>)visitor).visitSequence(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SequenceContext sequence() throws RecognitionException {
		SequenceContext _localctx = new SequenceContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_sequence);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(21);
			command();
			setState(26);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__0) {
				{
				{
				setState(22);
				match(T__0);
				setState(23);
				command();
				}
				}
				setState(28);
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

	public static class CommandContext extends ShellRuleContext {
		public PipeContext pipe() {
			return getRuleContext(PipeContext.class,0);
		}
		public CallContext call() {
			return getRuleContext(CallContext.class,0);
		}
		public CommandContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_command; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShellGrammarVisitor ) return ((ShellGrammarVisitor<? extends T>)visitor).visitCommand(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CommandContext command() throws RecognitionException {
		CommandContext _localctx = new CommandContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_command);
		try {
			setState(31);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(29);
				pipe();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(30);
				call();
				}
				break;
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

	public static class PipeContext extends ShellRuleContext {
		public List<CallContext> call() {
			return getRuleContexts(CallContext.class);
		}
		public CallContext call(int i) {
			return getRuleContext(CallContext.class,i);
		}
		public PipeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pipe; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShellGrammarVisitor ) return ((ShellGrammarVisitor<? extends T>)visitor).visitPipe(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PipeContext pipe() throws RecognitionException {
		PipeContext _localctx = new PipeContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_pipe);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(33);
			call();
			setState(36); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(34);
				match(T__1);
				setState(35);
				call();
				}
				}
				setState(38); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==T__1 );
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

	public static class CallContext extends ShellRuleContext {
		public ArgumentContext argument() {
			return getRuleContext(ArgumentContext.class,0);
		}
		public List<TerminalNode> WS() { return getTokens(ShellGrammarParser.WS); }
		public TerminalNode WS(int i) {
			return getToken(ShellGrammarParser.WS, i);
		}
		public List<RedirectionContext> redirection() {
			return getRuleContexts(RedirectionContext.class);
		}
		public RedirectionContext redirection(int i) {
			return getRuleContext(RedirectionContext.class,i);
		}
		public List<AtomContext> atom() {
			return getRuleContexts(AtomContext.class);
		}
		public AtomContext atom(int i) {
			return getRuleContext(AtomContext.class,i);
		}
		public CallContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_call; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShellGrammarVisitor ) return ((ShellGrammarVisitor<? extends T>)visitor).visitCall(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CallContext call() throws RecognitionException {
		CallContext _localctx = new CallContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_call);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(43);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==WS) {
				{
				{
				setState(40);
				match(WS);
				}
				}
				setState(45);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(55);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__2 || _la==T__3) {
				{
				{
				setState(46);
				redirection();
				setState(50);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==WS) {
					{
					{
					setState(47);
					match(WS);
					}
					}
					setState(52);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				}
				setState(57);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(58);
			argument();
			setState(68);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,8,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(62);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==WS) {
						{
						{
						setState(59);
						match(WS);
						}
						}
						setState(64);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					setState(65);
					atom();
					}
					} 
				}
				setState(70);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,8,_ctx);
			}
			setState(74);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==WS) {
				{
				{
				setState(71);
				match(WS);
				}
				}
				setState(76);
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

	public static class AtomContext extends ShellRuleContext {
		public RedirectionContext redirection() {
			return getRuleContext(RedirectionContext.class,0);
		}
		public ArgumentContext argument() {
			return getRuleContext(ArgumentContext.class,0);
		}
		public AtomContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_atom; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShellGrammarVisitor ) return ((ShellGrammarVisitor<? extends T>)visitor).visitAtom(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AtomContext atom() throws RecognitionException {
		AtomContext _localctx = new AtomContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_atom);
		try {
			setState(79);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__2:
			case T__3:
				enterOuterAlt(_localctx, 1);
				{
				setState(77);
				redirection();
				}
				break;
			case DOUBLEQUOTED:
			case SINGLEQUOTED:
			case BACKQUOTED:
			case UNQUOTED:
				enterOuterAlt(_localctx, 2);
				{
				setState(78);
				argument();
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

	public static class ArgumentContext extends ShellRuleContext {
		public List<QuotedContext> quoted() {
			return getRuleContexts(QuotedContext.class);
		}
		public QuotedContext quoted(int i) {
			return getRuleContext(QuotedContext.class,i);
		}
		public List<TerminalNode> UNQUOTED() { return getTokens(ShellGrammarParser.UNQUOTED); }
		public TerminalNode UNQUOTED(int i) {
			return getToken(ShellGrammarParser.UNQUOTED, i);
		}
		public ArgumentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_argument; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShellGrammarVisitor ) return ((ShellGrammarVisitor<? extends T>)visitor).visitArgument(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArgumentContext argument() throws RecognitionException {
		ArgumentContext _localctx = new ArgumentContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_argument);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(83); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					setState(83);
					_errHandler.sync(this);
					switch (_input.LA(1)) {
					case DOUBLEQUOTED:
					case SINGLEQUOTED:
					case BACKQUOTED:
						{
						setState(81);
						quoted();
						}
						break;
					case UNQUOTED:
						{
						setState(82);
						match(UNQUOTED);
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(85); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
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

	public static class RedirectionContext extends ShellRuleContext {
		public ArgumentContext argument() {
			return getRuleContext(ArgumentContext.class,0);
		}
		public List<TerminalNode> WS() { return getTokens(ShellGrammarParser.WS); }
		public TerminalNode WS(int i) {
			return getToken(ShellGrammarParser.WS, i);
		}
		public RedirectionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_redirection; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShellGrammarVisitor ) return ((ShellGrammarVisitor<? extends T>)visitor).visitRedirection(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RedirectionContext redirection() throws RecognitionException {
		RedirectionContext _localctx = new RedirectionContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_redirection);
		int _la;
		try {
			setState(103);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__2:
				enterOuterAlt(_localctx, 1);
				{
				setState(87);
				match(T__2);
				setState(91);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==WS) {
					{
					{
					setState(88);
					match(WS);
					}
					}
					setState(93);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(94);
				argument();
				}
				break;
			case T__3:
				enterOuterAlt(_localctx, 2);
				{
				setState(95);
				match(T__3);
				setState(99);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==WS) {
					{
					{
					setState(96);
					match(WS);
					}
					}
					setState(101);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(102);
				argument();
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

	public static class QuotedContext extends ShellRuleContext {
		public TerminalNode SINGLEQUOTED() { return getToken(ShellGrammarParser.SINGLEQUOTED, 0); }
		public TerminalNode DOUBLEQUOTED() { return getToken(ShellGrammarParser.DOUBLEQUOTED, 0); }
		public TerminalNode BACKQUOTED() { return getToken(ShellGrammarParser.BACKQUOTED, 0); }
		public QuotedContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_quoted; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof ShellGrammarVisitor ) return ((ShellGrammarVisitor<? extends T>)visitor).visitQuoted(this);
			else return visitor.visitChildren(this);
		}
	}

	public final QuotedContext quoted() throws RecognitionException {
		QuotedContext _localctx = new QuotedContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_quoted);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(105);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << DOUBLEQUOTED) | (1L << SINGLEQUOTED) | (1L << BACKQUOTED))) != 0)) ) {
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\13n\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\3\2\5\2\26"+
		"\n\2\3\3\3\3\3\3\7\3\33\n\3\f\3\16\3\36\13\3\3\4\3\4\5\4\"\n\4\3\5\3\5"+
		"\3\5\6\5\'\n\5\r\5\16\5(\3\6\7\6,\n\6\f\6\16\6/\13\6\3\6\3\6\7\6\63\n"+
		"\6\f\6\16\6\66\13\6\7\68\n\6\f\6\16\6;\13\6\3\6\3\6\7\6?\n\6\f\6\16\6"+
		"B\13\6\3\6\7\6E\n\6\f\6\16\6H\13\6\3\6\7\6K\n\6\f\6\16\6N\13\6\3\7\3\7"+
		"\5\7R\n\7\3\b\3\b\6\bV\n\b\r\b\16\bW\3\t\3\t\7\t\\\n\t\f\t\16\t_\13\t"+
		"\3\t\3\t\3\t\7\td\n\t\f\t\16\tg\13\t\3\t\5\tj\n\t\3\n\3\n\3\n\2\2\13\2"+
		"\4\6\b\n\f\16\20\22\2\3\3\2\7\t\2t\2\25\3\2\2\2\4\27\3\2\2\2\6!\3\2\2"+
		"\2\b#\3\2\2\2\n-\3\2\2\2\fQ\3\2\2\2\16U\3\2\2\2\20i\3\2\2\2\22k\3\2\2"+
		"\2\24\26\5\4\3\2\25\24\3\2\2\2\25\26\3\2\2\2\26\3\3\2\2\2\27\34\5\6\4"+
		"\2\30\31\7\3\2\2\31\33\5\6\4\2\32\30\3\2\2\2\33\36\3\2\2\2\34\32\3\2\2"+
		"\2\34\35\3\2\2\2\35\5\3\2\2\2\36\34\3\2\2\2\37\"\5\b\5\2 \"\5\n\6\2!\37"+
		"\3\2\2\2! \3\2\2\2\"\7\3\2\2\2#&\5\n\6\2$%\7\4\2\2%\'\5\n\6\2&$\3\2\2"+
		"\2\'(\3\2\2\2(&\3\2\2\2()\3\2\2\2)\t\3\2\2\2*,\7\13\2\2+*\3\2\2\2,/\3"+
		"\2\2\2-+\3\2\2\2-.\3\2\2\2.9\3\2\2\2/-\3\2\2\2\60\64\5\20\t\2\61\63\7"+
		"\13\2\2\62\61\3\2\2\2\63\66\3\2\2\2\64\62\3\2\2\2\64\65\3\2\2\2\658\3"+
		"\2\2\2\66\64\3\2\2\2\67\60\3\2\2\28;\3\2\2\29\67\3\2\2\29:\3\2\2\2:<\3"+
		"\2\2\2;9\3\2\2\2<F\5\16\b\2=?\7\13\2\2>=\3\2\2\2?B\3\2\2\2@>\3\2\2\2@"+
		"A\3\2\2\2AC\3\2\2\2B@\3\2\2\2CE\5\f\7\2D@\3\2\2\2EH\3\2\2\2FD\3\2\2\2"+
		"FG\3\2\2\2GL\3\2\2\2HF\3\2\2\2IK\7\13\2\2JI\3\2\2\2KN\3\2\2\2LJ\3\2\2"+
		"\2LM\3\2\2\2M\13\3\2\2\2NL\3\2\2\2OR\5\20\t\2PR\5\16\b\2QO\3\2\2\2QP\3"+
		"\2\2\2R\r\3\2\2\2SV\5\22\n\2TV\7\n\2\2US\3\2\2\2UT\3\2\2\2VW\3\2\2\2W"+
		"U\3\2\2\2WX\3\2\2\2X\17\3\2\2\2Y]\7\5\2\2Z\\\7\13\2\2[Z\3\2\2\2\\_\3\2"+
		"\2\2][\3\2\2\2]^\3\2\2\2^`\3\2\2\2_]\3\2\2\2`j\5\16\b\2ae\7\6\2\2bd\7"+
		"\13\2\2cb\3\2\2\2dg\3\2\2\2ec\3\2\2\2ef\3\2\2\2fh\3\2\2\2ge\3\2\2\2hj"+
		"\5\16\b\2iY\3\2\2\2ia\3\2\2\2j\21\3\2\2\2kl\t\2\2\2l\23\3\2\2\2\22\25"+
		"\34!(-\649@FLQUW]ei";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}