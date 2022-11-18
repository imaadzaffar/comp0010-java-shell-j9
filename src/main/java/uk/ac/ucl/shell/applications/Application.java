package uk.ac.ucl.shell.applications;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public interface Application {
    void exec(List<String> args, InputStream input, OutputStream output) throws IOException;
}
