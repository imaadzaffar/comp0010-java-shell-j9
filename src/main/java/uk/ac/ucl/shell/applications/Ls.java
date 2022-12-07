package uk.ac.ucl.shell.applications;

import java.io.*;
import java.util.List;
import java.util.Objects;

import uk.ac.ucl.shell.Shell;
import uk.ac.ucl.shell.exceptions.NotExistingDirectoryException;
import uk.ac.ucl.shell.exceptions.TooManyArgumentsException;

public class Ls implements Application {
    @Override
    public void exec(List<String> args, InputStream input, OutputStreamWriter output) throws IOException {
        File currDir;
        boolean showHidden = false;

        if (args.isEmpty()) {
            currDir = Shell.getCurrentDirectory().toFile();
        } else if (args.size() == 1) {
            currDir = Shell.getCurrentDirectory().resolve(args.get(0)).toFile();
        } else if (args.size() == 2 && Objects.equals(args.get(0), "-a")) {
            showHidden = true;
            currDir = Shell.getCurrentDirectory().resolve(args.get(1)).toFile();
        } else {
            throw new TooManyArgumentsException("ls");
        }

        if(!currDir.exists()) {
            throw new NotExistingDirectoryException("ls", currDir.getPath());
        }

        File[] listOfFiles = currDir.listFiles();

        for (File file : listOfFiles) {
            if (file.getName().startsWith(".") && !showHidden) continue;
            output.write(file.getName());
            output.write("\t");
            output.flush();
        }

        output.write(System.getProperty("line.separator"));
        output.flush();
    }
}

