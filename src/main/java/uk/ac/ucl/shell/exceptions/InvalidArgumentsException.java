package uk.ac.ucl.shell.exceptions;

public class InvalidArgumentsException extends RuntimeException {
    public InvalidArgumentsException(String app) {
        super(app + " - invalid arguments");
    }
}
