package uk.ac.ucl.shell.applications;

import uk.ac.ucl.shell.Shell;
import uk.ac.ucl.shell.exceptions.MissingArgumentsException;
import uk.ac.ucl.shell.exceptions.TooManyArgumentsException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Mkdir implements Application {
    @Override
    public void exec(List<String> args, InputStream input, OutputStreamWriter output) throws IOException {
        if (args.isEmpty()) {
            throw new MissingArgumentsException("mkdir");
        } else if (args.size() > 1) {
            throw new TooManyArgumentsException("mkdir");
        }

        String dirName = args.get(0);
        Path dirPath = Shell.getCurrentDirectory().resolve(dirName);
        Files.createDirectories(dirPath);
    }
}
