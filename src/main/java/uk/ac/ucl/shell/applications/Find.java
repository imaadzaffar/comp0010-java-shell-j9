package uk.ac.ucl.shell.applications;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

import uk.ac.ucl.shell.Shell;

public class Find extends SimpleFileVisitor<Path> implements Application {
    List<Path> matches = new ArrayList<Path>();
    PathMatcher matcher;

    @Override
    public void exec(List<String> args, InputStream input, OutputStreamWriter output) throws IOException {
        if (args.isEmpty()) {
            throw new RuntimeException("find: missing argument");
        } else if (args.size() > 3) {
            throw new RuntimeException("find: incorrect number of arguments");
        } else if(!args.get(0).equals("-name") && !args.get(1).equals("-name")){
            throw new RuntimeException("find: missing -name flag");
        }
        
        var startDir = args.size() == 2 ? Shell.getCurrentDirectory().toFile() : Shell.getCurrentDirectory().resolve(args.get(0)).toFile();
        var filenamePattern = args.get(args.size() - 1);

        if (!startDir.exists() || !startDir.isDirectory()) {
            throw new RuntimeException("find: " + startDir.getName() + " is not an existing directory");
        }

        matcher = FileSystems.getDefault().getPathMatcher("glob:" + filenamePattern);
        
        Files.walkFileTree(startDir.toPath(), this);

        for(Path path : matches) {
            output.write((args.size() == 2 ? "./" : "") + Shell.getCurrentDirectory().relativize(path).toString().replace("\\", "/"));
            output.write(System.getProperty("line.separator"));
        }

        output.flush();
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attributes) throws IOException {
        Path name = file.getFileName();
        if (matcher.matches(name)) {
            matches.add(file);
        }
        return FileVisitResult.CONTINUE;
    }
    
}
