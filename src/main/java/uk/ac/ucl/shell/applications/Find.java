package uk.ac.ucl.shell.applications;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import uk.ac.ucl.shell.Shell;

public class Find implements Application {
    private File startDir;
    private String outputStart;
    private String filenamePattern;

    @Override
    public void exec(List<String> args, InputStream input, OutputStream output) throws IOException {
        if (args.isEmpty()) {
            throw new RuntimeException("find: missing argument");
        } else if (args.size() == 2) { 
            if (args.get(0).equals("-name")) {
                startDir = new File(Shell.getCurrentDirectory());
                filenamePattern = args.get(1);
                outputStart = ".";
            } else {
                throw new RuntimeException("find: missing -name flag");
            }
        } else if (args.size() == 3) {
            if (args.get(1).equals("-name")) {
                startDir = new File(Shell.getCurrentDirectory(), args.get(0));
                filenamePattern = args.get(2);
                outputStart = args.get(0);
            } else {
                throw new RuntimeException("find: missing -name flag");
            }
        } else if (args.size() >= 1) {
            throw new RuntimeException("find: incorrect number of arguments");
        }
        
        if (!startDir.exists() || !startDir.isDirectory()) {
            throw new RuntimeException("find: " + startDir.getName() + " is not an existing directory");
        }
        StringBuilder res = new StringBuilder();
        findFile(startDir, filenamePattern, res);
        // System.out.println(res.toString());
        Shell.writer.flush();
        // Shell.setCurrentDirectory(startDir.getCanonicalPath());
    }

    private void findFile(File file, String pattern, StringBuilder res) {
        if (file.isFile() &&
            (pattern.contains("*") && file.getName().contains(pattern.replace("*", "")) ||
            file.getName().equals(pattern))
            ) {
            String path = outputStart + "/" + new File(startDir.getAbsolutePath()).toURI().relativize(new File(file.getAbsolutePath()).toURI()).getPath();
            // res.append(path);
            // res.append("\n");
            try {
                Shell.writer.write(path);
                Shell.writer.write(System.getProperty("line.separator"));
                // Shell.writer.flush();
            } catch (IOException e) {
                throw new RuntimeException("find: error writing");
            }
        } else if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                findFile(f, pattern, res);
            }
        }
    }
    
}
