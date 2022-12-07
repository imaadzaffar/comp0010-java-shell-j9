package uk.ac.ucl.shell.applications;

import uk.ac.ucl.shell.Shell;
import uk.ac.ucl.shell.exceptions.MissingArgumentsException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Touch implements Application {
    @Override
    public void exec(List<String> args, InputStream input, OutputStreamWriter output) throws IOException {
        if (args.isEmpty()) {
            try (Scanner reader = new Scanner(input)) {
                while (reader.hasNextLine()) {
                    args.add(reader.nextLine());
                }
            } catch (NullPointerException e) {
                throw new MissingArgumentsException("touch");
            }
        }

        for (String fileName : args) {
            Path filePath = Shell.getCurrentDirectory().resolve(fileName);
            Files.createFile(filePath);
        }
    }
}
