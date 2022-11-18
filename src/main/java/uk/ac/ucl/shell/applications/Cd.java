package uk.ac.ucl.shell.applications;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.List;

import uk.ac.ucl.shell.Shell;

public class Cd implements Application {
    @Override
    public void exec(List<String> args, InputStream input, OutputStreamWriter output) throws IOException {
        if (args.isEmpty()) {
            throw new RuntimeException("cd: missing argument");
        } else if (args.size() > 1) {
            throw new RuntimeException("cd: too many arguments");
        }
        String dirString = args.get(0);
        File dir = Shell.getCurrentDirectory().resolve(dirString).toFile();
        if (!dir.exists() || !dir.isDirectory()) {
            throw new RuntimeException("cd: " + dirString + " is not an existing directory");
        }
        Shell.setCurrentDirectory(dir.getCanonicalPath());
    }
}
