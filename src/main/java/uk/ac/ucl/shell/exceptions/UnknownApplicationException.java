package uk.ac.ucl.shell.exceptions;

public class UnknownApplicationException extends RuntimeException {
    public UnknownApplicationException(String app) {
        super(app + " - unknown application");
    }
}
