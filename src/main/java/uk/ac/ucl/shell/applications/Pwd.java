package uk.ac.ucl.shell.applications;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.List;

import uk.ac.ucl.shell.Shell;
import uk.ac.ucl.shell.exceptions.TooManyArgumentsException;

public class Pwd implements Application {
    @Override
    public void exec(List<String> args, InputStream input, OutputStreamWriter output) throws IOException {
        if (args.size() > 0) {
            throw new TooManyArgumentsException("pwd");
        }
        output.write(Shell.getCurrentDirectory().toString());
        output.write(System.getProperty("line.separator"));
        output.flush();
    }
}
