package uk.ac.ucl.shell.applications;

import java.io.*;
import java.util.List;

import uk.ac.ucl.shell.Shell;
import uk.ac.ucl.shell.exceptions.FileNotFoundException;
import uk.ac.ucl.shell.exceptions.MissingArgumentsException;
import uk.ac.ucl.shell.exceptions.TooManyArgumentsException;

public class Ls implements Application {
    @Override
    public void exec(List<String> args, InputStream input, OutputStreamWriter output) throws IOException {
        File currDir;
        if (args.isEmpty()) {
            currDir = Shell.getCurrentDirectory().toFile();
        } else if (args.size() == 1) {
            currDir = Shell.getCurrentDirectory().resolve(args.get(0)).toFile();
        } else {
            throw new TooManyArgumentsException("ls");
        }
        
        if(!currDir.exists()) {
            throw new FileNotFoundException("ls", currDir.getPath());
        }

        File[] listOfFiles = currDir.listFiles();

        for (File file : listOfFiles) {
            if (!file.getName().startsWith(".")) {
                output.write(file.getName());
                output.write("\t");
                output.flush();
            }
        }

        output.write(System.getProperty("line.separator"));
        output.flush();
    }
}

