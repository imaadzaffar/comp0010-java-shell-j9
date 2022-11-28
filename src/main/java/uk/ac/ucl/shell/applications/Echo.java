package uk.ac.ucl.shell.applications;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.List;

public class Echo implements Application {
    @Override
    public void exec(List<String> args, InputStream input, OutputStreamWriter output) throws IOException {
        System.out.println(args.get(0).contains("\n"));
        System.out.println(String.join(" ", args));
        output.write(String.join(" ", args));
        output.write(System.getProperty("line.separator"));
        output.flush();
    }
}
