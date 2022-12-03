package uk.ac.ucl.shell.applications;

import java.io.*;
import java.util.List;

import uk.ac.ucl.shell.Shell;
import uk.ac.ucl.shell.exceptions.MissingArgumentsException;
import uk.ac.ucl.shell.exceptions.NotExistingDirectoryException;
import uk.ac.ucl.shell.exceptions.TooManyArgumentsException;

public class Cd implements Application {
    @Override
    public void exec(List<String> args, InputStream input, OutputStreamWriter output) throws IOException {
        if (args.isEmpty()) {
            throw new MissingArgumentsException("cd");
        } else if (args.size() > 1) {
            throw new TooManyArgumentsException("cd");
        }
        String dirString = args.get(0);
        File dir = Shell.getCurrentDirectory().resolve(dirString).toFile();
        if (!dir.exists() || !dir.isDirectory()) {
            throw new NotExistingDirectoryException("cd", dirString);
        }
        Shell.setCurrentDirectory(dir.getCanonicalPath());
    }
}
