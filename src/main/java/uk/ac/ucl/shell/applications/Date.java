package uk.ac.ucl.shell.applications;

import uk.ac.ucl.shell.exceptions.TooManyArgumentsException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.List;

public class Date implements Application {
    @Override
    public void exec(List<String> args, InputStream input, OutputStreamWriter output) throws IOException {
        if (args.size() > 0) {
            throw new TooManyArgumentsException("date");
        }

        String date = new java.util.Date().toString();
        
        output.write(date);
        output.write(System.getProperty("line.separator"));
        output.flush();
    }
}
