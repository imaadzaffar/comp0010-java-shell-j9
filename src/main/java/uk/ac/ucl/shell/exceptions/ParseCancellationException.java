package uk.ac.ucl.shell.exceptions;

public class ParseCancellationException extends RuntimeException{
    public ParseCancellationException(int line, int charPositionInLine, String msg) {super("parser: line: " + line + ":" + charPositionInLine + " " + msg);}
}
