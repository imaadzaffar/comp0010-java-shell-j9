package uk.ac.ucl.shell.applications;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.List;

public class Echo implements Application {
    @Override
    public void exec(List<String> args, InputStream input, OutputStreamWriter output) throws IOException {
        boolean atLeastOnePrinted = false;
        for (String arg : args) {
            output.write(arg);
            output.write(" ");
            output.flush();
            atLeastOnePrinted = true;
        }
        if (atLeastOnePrinted) {
            output.write(System.getProperty("line.separator"));
            output.flush();
        }
    }
}
