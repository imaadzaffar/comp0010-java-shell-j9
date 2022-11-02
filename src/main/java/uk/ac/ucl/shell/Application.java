package uk.ac.ucl.shell;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

public interface Application {
    void exec(List<String> args, String input, String output) throws IOException;
}
