package uk.ac.ucl.shell.exceptions;

public class TooManyArgumentsException extends RuntimeException {
    public TooManyArgumentsException(String app) {
        super(app + " - too many arguments");
    }
}
