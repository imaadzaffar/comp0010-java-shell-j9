package uk.ac.ucl.shell.applications;

import uk.ac.ucl.shell.Shell;
import uk.ac.ucl.shell.exceptions.MissingArgumentsException;
import uk.ac.ucl.shell.exceptions.TooManyArgumentsException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Rm implements Application {
    @Override
    public void exec(List<String> args, InputStream input, OutputStreamWriter output) throws IOException {
        if (args.isEmpty()) {
            throw new MissingArgumentsException("rm");
        } else if (args.size() > 1) {
            throw new TooManyArgumentsException("rm");
        }

        String fileName = args.get(0);
        Path filePath = Shell.getCurrentDirectory().resolve(fileName);
        Files.delete(filePath);
    }
}
