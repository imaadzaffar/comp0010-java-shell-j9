package uk.ac.ucl.shell.commands;

import uk.ac.ucl.shell.Shell;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class Globbing extends SimpleFileVisitor<Path> {
    List<String> matches;
    PathMatcher fileMatcher;
    PathMatcher dirMatcher;

    public ArrayList<String> glob(ArrayList<String> args, boolean substituted) throws IOException {
        ArrayList<String> globbedArgs = new ArrayList<>();

        for(String arg : args) {
            // The spec doesn't specify how we should handle globbing for applications that support wildcards already (ie: Find)
            // To prevent issues we disable globbing if the value is linked to a flag       
            if(!arg.contains("*") || globbedArgs.size() > 0 && globbedArgs.get(globbedArgs.size() - 1).startsWith("-")) {
                // Don't remove single quotes if substitution has already been performed as this would break substituting with file contents containing single quotes
                if (arg.contains("'") && !arg.startsWith("\"") && !substituted) {
                    globbedArgs.add(arg.replace("'", ""));
                } else {
                    globbedArgs.add(arg.replace("\"", ""));
                }
                continue;
            }

            matches = new ArrayList<>();

            fileMatcher = FileSystems.getDefault().getPathMatcher("glob:" + arg);
            dirMatcher = arg.contains("/") ? FileSystems.getDefault().getPathMatcher("glob:" + arg.substring(0, arg.lastIndexOf("/"))) : null;

            Files.walkFileTree(Shell.getCurrentDirectory(), this);

            globbedArgs.addAll(matches);
        }

        return globbedArgs;
    }

    @Override
    public FileVisitResult preVisitDirectory(Path directory, BasicFileAttributes attr) {
        Path name = Shell.getCurrentDirectory().relativize(directory);

        // Add all directories matching the globbing pattern
        if (fileMatcher.matches(name)) {
            matches.add(name.toString().replace("\\", "/"));
        }

        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attr) {
        Path name = Shell.getCurrentDirectory().relativize(file);
        Path directory = Shell.getCurrentDirectory().relativize(file).getParent();

        // Add all files matching the globbing pattern and if part of a sub-directory, must also match the the directory pattern
        // ie: "dir1/*" should only match files in dir1's directory and not any sub-directories
        if (fileMatcher.matches(name) && (name.getNameCount() == 1 || dirMatcher != null && dirMatcher.matches(directory))) {
            matches.add(name.toString().replace("\\", "/"));
        }

        return FileVisitResult.CONTINUE;
    }
}
