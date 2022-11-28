package uk.ac.ucl.shell.applications;

import java.io.*;
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
import uk.ac.ucl.shell.exceptions.MissingArgumentsException;
import uk.ac.ucl.shell.exceptions.NotExistingDirectoryException;
import uk.ac.ucl.shell.exceptions.TooManyArgumentsException;

public class Find extends SimpleFileVisitor<Path> implements Application {
    List<Path> matches = new ArrayList<Path>();
    PathMatcher matcher;

    @Override
    public void exec(List<String> args, InputStream input, OutputStreamWriter output) throws IOException {
        if (args.isEmpty()) {
            throw new MissingArgumentsException("find");
        } else if (args.size() > 3) {
            throw new TooManyArgumentsException("find");
        } else if(!args.get(0).equals("-name") && !args.get(1).equals("-name")){
//            throw new MissingArgumentsException("find: missing -name flag");
            throw new MissingArgumentsException("find");
        }
        
        var startDir = args.size() == 2 ? Shell.getCurrentDirectory().toFile() : Shell.getCurrentDirectory().resolve(args.get(0)).toFile();
        var filenamePattern = args.get(args.size() - 1);

        if (!startDir.exists() || !startDir.isDirectory()) {
            throw new NotExistingDirectoryException("find", startDir.getName());
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
