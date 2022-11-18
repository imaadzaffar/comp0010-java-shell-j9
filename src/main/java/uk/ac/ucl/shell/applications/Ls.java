package uk.ac.ucl.shell.applications;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import uk.ac.ucl.shell.Shell;

import static uk.ac.ucl.shell.Shell.writer;

public class Ls implements Application {
    @Override
    public void exec(List<String> args, InputStream input, OutputStream output) throws IOException {
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
                        writer.write(file.getName());
                        writer.write("\t");
                        writer.flush();
                        atLeastOnePrinted = true;
                    }
                }
            }
            if (atLeastOnePrinted) {
                writer.write(System.getProperty("line.separator"));
                writer.flush();
            }
        } catch (NullPointerException e) {
            throw new RuntimeException("ls: no such directory");
        }
    }
}

