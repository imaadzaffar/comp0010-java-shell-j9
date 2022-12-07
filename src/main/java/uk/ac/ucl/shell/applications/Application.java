package uk.ac.ucl.shell.applications;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.List;

/**
 * This is an interface for all applications to implement and provides an entry point for these to be executed
 */
public interface Application {
    /**
     * Executes the specified application with arguments and writes to the output
     * @param args The application arguments
     * @param input The application input if any
     * @param output The application output stream to write to
     * @throws IOException If an error occured
     */
    void exec(List<String> args, InputStream input, OutputStreamWriter output) throws IOException;
}
