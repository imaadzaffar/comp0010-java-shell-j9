package uk.ac.ucl.shell.applications;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.List;

import uk.ac.ucl.shell.Shell;

public class Ls implements Application {
    @Override
    public void exec(List<String> args, InputStream input, OutputStreamWriter output) throws IOException {
        File currDir;
        if (args.isEmpty()) {
            currDir = Shell.getCurrentDirectory().toFile();
        } else if (args.size() == 1) {
            currDir = Shell.getCurrentDirectory().resolve(args.get(0)).toFile();
        } else {
            throw new RuntimeException("ls: too many arguments");
        }
        
        if(!currDir.exists()) {
            throw new RuntimeException("ls: no such directory");
        }

        File[] listOfFiles = currDir.listFiles();

        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                if (!file.getName().startsWith(".")) {
                    output.write(file.getName());
                    output.write("\t");
                    output.flush();
                }
            }
        }

        output.write(System.getProperty("line.separator"));
        output.flush();
    }
}

