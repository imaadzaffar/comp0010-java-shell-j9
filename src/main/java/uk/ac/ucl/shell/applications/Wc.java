package uk.ac.ucl.shell.applications;

import uk.ac.ucl.shell.Shell;
import uk.ac.ucl.shell.exceptions.FileNotFoundException;
import uk.ac.ucl.shell.exceptions.MissingArgumentsException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

public class Wc implements Application {
    @Override
    public void exec(List<String> args, InputStream input, OutputStreamWriter output) throws IOException {
        if (args.isEmpty()) {
            throw new MissingArgumentsException("wc");
        }

        for (String fileName : args) {
            int lineCount = 0;
            int wordCount = 0;
            int byteCount = 0;

            Path filePath = Shell.getCurrentDirectory().resolve(fileName);

            if (!Files.exists(filePath) || Files.isDirectory(filePath)) {
                throw new FileNotFoundException("wc", filePath.toString());
            }

            try(Scanner scanner = new Scanner(new FileReader(filePath.toString()))){
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();

                    lineCount++;
                    wordCount += line.split("\\s+").length;
                    byteCount += line.length();
                }
            }

            output.write(lineCount + " " + wordCount + " " + byteCount + " " + filePath.getFileName());
            output.write(System.getProperty("line.separator"));
            output.flush();
        }
    }
}
