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
            currDir = new File(Shell.getCurrentDirectory());
        } else if (args.size() == 1) {
            currDir = new File(args.get(0));
        } else {
            throw new RuntimeException("ls: too many arguments");
        }
        try {
            File[] listOfFiles = currDir.listFiles();
            boolean atLeastOnePrinted = false;
            if (listOfFiles != null) {
                for (File file : listOfFiles) {
                    if (!file.getName().startsWith(".")) {
                        output.write(file.getName());
                        output.write("\t");
                        output.flush();
                        atLeastOnePrinted = true;
                    }
                }
            }
            if (atLeastOnePrinted) {
                output.write(System.getProperty("line.separator"));
                output.flush();
            }
        } catch (NullPointerException e) {
            throw new RuntimeException("ls: no such directory");
        }
    }
}

