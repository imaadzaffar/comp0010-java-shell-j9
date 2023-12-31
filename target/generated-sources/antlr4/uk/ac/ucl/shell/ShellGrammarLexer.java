// Generated from uk\ac\u005Cucl\shell\ShellGrammar.g4 by ANTLR 4.7
package uk.ac.ucl.shell;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class ShellGrammarLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.7", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, DOUBLEQUOTED=5, SINGLEQUOTED=6, BACKQUOTED=7, 
		UNQUOTED=8, WS=9;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "T__1", "T__2", "T__3", "DOUBLEQUOTED", "SINGLEQUOTED", "BACKQUOTED", 
		"UNQUOTED", "WS"
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


	public ShellGrammarLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "ShellGrammar.g4"; }

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

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\13k\b\1\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\3\2\3\2"+
		"\3\3\3\3\3\4\3\4\3\5\3\5\3\6\7\6\37\n\6\f\6\16\6\"\13\6\3\6\3\6\7\6&\n"+
		"\6\f\6\16\6)\13\6\3\6\5\6,\n\6\3\6\7\6/\n\6\f\6\16\6\62\13\6\3\6\3\6\7"+
		"\6\66\n\6\f\6\16\69\13\6\3\7\7\7<\n\7\f\7\16\7?\13\7\3\7\3\7\7\7C\n\7"+
		"\f\7\16\7F\13\7\3\7\3\7\7\7J\n\7\f\7\16\7M\13\7\3\b\7\bP\n\b\f\b\16\b"+
		"S\13\b\3\b\3\b\7\bW\n\b\f\b\16\bZ\13\b\3\b\3\b\7\b^\n\b\f\b\16\ba\13\b"+
		"\3\t\6\td\n\t\r\t\16\te\3\n\3\n\3\n\3\n\2\2\13\3\3\5\4\7\5\t\6\13\7\r"+
		"\b\17\t\21\n\23\13\3\2\7\5\2\f\f$$bb\4\2\f\f))\4\2\f\fbb\n\2\13\f\"\""+
		"$$))=>@@bb~~\4\2\13\13\"\"\2v\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t"+
		"\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2"+
		"\2\3\25\3\2\2\2\5\27\3\2\2\2\7\31\3\2\2\2\t\33\3\2\2\2\13 \3\2\2\2\r="+
		"\3\2\2\2\17Q\3\2\2\2\21c\3\2\2\2\23g\3\2\2\2\25\26\7=\2\2\26\4\3\2\2\2"+
		"\27\30\7~\2\2\30\6\3\2\2\2\31\32\7>\2\2\32\b\3\2\2\2\33\34\7@\2\2\34\n"+
		"\3\2\2\2\35\37\5\21\t\2\36\35\3\2\2\2\37\"\3\2\2\2 \36\3\2\2\2 !\3\2\2"+
		"\2!#\3\2\2\2\" \3\2\2\2#\'\7$\2\2$&\n\2\2\2%$\3\2\2\2&)\3\2\2\2\'%\3\2"+
		"\2\2\'(\3\2\2\2(+\3\2\2\2)\'\3\2\2\2*,\5\17\b\2+*\3\2\2\2+,\3\2\2\2,\60"+
		"\3\2\2\2-/\n\2\2\2.-\3\2\2\2/\62\3\2\2\2\60.\3\2\2\2\60\61\3\2\2\2\61"+
		"\63\3\2\2\2\62\60\3\2\2\2\63\67\7$\2\2\64\66\5\21\t\2\65\64\3\2\2\2\66"+
		"9\3\2\2\2\67\65\3\2\2\2\678\3\2\2\28\f\3\2\2\29\67\3\2\2\2:<\5\21\t\2"+
		";:\3\2\2\2<?\3\2\2\2=;\3\2\2\2=>\3\2\2\2>@\3\2\2\2?=\3\2\2\2@D\7)\2\2"+
		"AC\n\3\2\2BA\3\2\2\2CF\3\2\2\2DB\3\2\2\2DE\3\2\2\2EG\3\2\2\2FD\3\2\2\2"+
		"GK\7)\2\2HJ\5\21\t\2IH\3\2\2\2JM\3\2\2\2KI\3\2\2\2KL\3\2\2\2L\16\3\2\2"+
		"\2MK\3\2\2\2NP\5\21\t\2ON\3\2\2\2PS\3\2\2\2QO\3\2\2\2QR\3\2\2\2RT\3\2"+
		"\2\2SQ\3\2\2\2TX\7b\2\2UW\n\4\2\2VU\3\2\2\2WZ\3\2\2\2XV\3\2\2\2XY\3\2"+
		"\2\2Y[\3\2\2\2ZX\3\2\2\2[_\7b\2\2\\^\5\21\t\2]\\\3\2\2\2^a\3\2\2\2_]\3"+
		"\2\2\2_`\3\2\2\2`\20\3\2\2\2a_\3\2\2\2bd\n\5\2\2cb\3\2\2\2de\3\2\2\2e"+
		"c\3\2\2\2ef\3\2\2\2f\22\3\2\2\2gh\t\6\2\2hi\3\2\2\2ij\b\n\2\2j\24\3\2"+
		"\2\2\17\2 \'+\60\67=DKQX_e\3\2\3\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}