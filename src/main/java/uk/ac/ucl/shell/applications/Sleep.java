package uk.ac.ucl.shell.applications;

import uk.ac.ucl.shell.exceptions.InvalidArgumentsException;
import uk.ac.ucl.shell.exceptions.MissingArgumentsException;
import uk.ac.ucl.shell.exceptions.TooManyArgumentsException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.List;

public class Sleep implements Application {
    @Override
    public void exec(List<String> args, InputStream input, OutputStreamWriter output) throws IOException {
        if (args.isEmpty()) {
            throw new MissingArgumentsException("sleep");
        } else if (args.size() > 1) {
            throw new TooManyArgumentsException("sleep");
        }

        try {
            int seconds = Integer.parseInt(args.get(0));
            Thread.sleep(seconds * 1000L);
            
            output.write(System.getProperty("line.separator"));
            output.flush();
        } catch (IllegalArgumentException e) {
            throw new InvalidArgumentsException("sleep");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
