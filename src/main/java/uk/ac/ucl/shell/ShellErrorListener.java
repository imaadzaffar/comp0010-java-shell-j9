package uk.ac.ucl.shell;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.ParseCancellationException;

public class ShellErrorListener extends BaseErrorListener {

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) throws ParseCancellationException {
        throw new ParseCancellationException("parser: line: " + line + ":" + charPositionInLine + " " + msg);
    }
}
