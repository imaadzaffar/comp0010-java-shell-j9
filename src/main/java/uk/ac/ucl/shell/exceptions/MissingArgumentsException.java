package uk.ac.ucl.shell.exceptions;

public class MissingArgumentsException extends RuntimeException {
    public MissingArgumentsException(String app) {
        super(app + " - missing arguments");
    }
}
