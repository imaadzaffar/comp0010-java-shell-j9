package uk.ac.ucl.shell.exceptions;

public class CannotOpenFileException extends RuntimeException {
    public CannotOpenFileException(String app, String filePath) {
        super(app + " - cannot open " + filePath);
    }
}
