package uk.ac.ucl.shell.applications;

import uk.ac.ucl.shell.Shell;
import uk.ac.ucl.shell.exceptions.FileAlreadyExistsException;
import uk.ac.ucl.shell.exceptions.MissingArgumentsException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

public class Mkdir implements Application {
    @Override
    public void exec(List<String> args, InputStream input, OutputStreamWriter output) throws IOException {
        if (args.isEmpty()) {
            try (Scanner reader = new Scanner(input)) {
                while (reader.hasNextLine()) {
                    args.add(reader.nextLine());
                }
            } catch (NullPointerException e) {
                throw new MissingArgumentsException("mkdir");
            }
        }

        for (String dirName : args) {
            Path dirPath = Shell.getCurrentDirectory().resolve(dirName);
            if (Files.exists(dirPath)) {
                throw new FileAlreadyExistsException("mkdir", dirPath.toString());
            } else {
                Files.createDirectories(dirPath);
            }
        }
    }
}
