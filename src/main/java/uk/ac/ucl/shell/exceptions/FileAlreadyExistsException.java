package uk.ac.ucl.shell.exceptions;

public class FileAlreadyExistsException extends RuntimeException{
    public FileAlreadyExistsException(String app, String filePath) {super(app + ": directory already exists: " + filePath);}
}
