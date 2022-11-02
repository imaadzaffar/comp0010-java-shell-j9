package uk.ac.ucl.shell;

import java.io.IOException;
import java.util.List;

import static uk.ac.ucl.shell.Shell.writer;

public class Pwd implements Application {
    @Override
    public void exec(List<String> args, String input, String output) throws IOException {
        writer.write(Shell.getCurrentDirectory());
        writer.write(System.getProperty("line.separator"));
        writer.flush();
    }
}
