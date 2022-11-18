package uk.ac.ucl.shell.applications;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.List;

import uk.ac.ucl.shell.Shell;

public class Pwd implements Application {
    @Override
    public void exec(List<String> args, InputStream input, OutputStreamWriter output) throws IOException {
        output.write(Shell.getCurrentDirectory().toString());
        output.write(System.getProperty("line.separator"));
        output.flush();    
    }
}
