package uk.ac.ucl.shell.applications;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.List;

public interface Application {
    void exec(List<String> args, InputStream input, OutputStreamWriter output) throws IOException;
}
