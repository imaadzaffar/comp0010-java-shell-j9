package uk.ac.ucl.shell.exceptions;

public class FileNotFoundException extends RuntimeException {
    public FileNotFoundException (String app, String filePath) {
        super(app + ": file not found: " + filePath);
    }
}
