package uk.ac.ucl.shell;

import java.io.IOException;
import java.util.List;

import static uk.ac.ucl.shell.Shell.writer;

public class Echo implements Application {
    @Override
    public void exec(List<String> args, String input, String output) throws IOException {
        boolean atLeastOnePrinted = false;
        for (String arg : args) {
            writer.write(arg);
            writer.write(" ");
            writer.flush();
            atLeastOnePrinted = true;
        }
        if (atLeastOnePrinted) {
            writer.write(System.getProperty("line.separator"));
            writer.flush();
        }
    }
}
