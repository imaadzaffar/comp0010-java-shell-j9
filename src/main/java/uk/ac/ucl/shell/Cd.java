package uk.ac.ucl.shell;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Cd implements Application {
    @Override
    public void exec(List<String> args, String input, String output) throws IOException {
        if (args.isEmpty()) {
            throw new RuntimeException("cd: missing argument");
        } else if (args.size() > 1) {
            throw new RuntimeException("cd: too many arguments");
        }
        String dirString = args.get(0);
        File dir = new File(Shell.getCurrentDirectory(), dirString);
        if (!dir.exists() || !dir.isDirectory()) {
            throw new RuntimeException("cd: " + dirString + " is not an existing directory");
        }
        Shell.setCurrentDirectory(dir.getCanonicalPath());
    }
}
