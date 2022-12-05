package uk.ac.ucl.shell.exceptions;

public class NotExistingDirectoryException extends RuntimeException {
    public NotExistingDirectoryException(String app, String filePath) {
        super(app + ": " + filePath + " is not an existing directory");
    }
}
