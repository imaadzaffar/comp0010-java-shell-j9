package uk.ac.ucl.shell.applications;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import uk.ac.ucl.shell.Shell;

import static uk.ac.ucl.shell.Shell.writer;

public class Pwd implements Application {
    @Override
    public void exec(List<String> args, InputStream input, OutputStream output) throws IOException {
        writer.write(Shell.getCurrentDirectory());
        writer.write(System.getProperty("line.separator"));
        writer.flush();

        var builder = new StringBuilder();

        builder.append("");
        
    }
}
