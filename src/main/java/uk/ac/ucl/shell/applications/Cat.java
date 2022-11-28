package uk.ac.ucl.shell.applications;

import java.io.*;
import java.util.List;
import java.util.Scanner;

import uk.ac.ucl.shell.Shell;
import uk.ac.ucl.shell.exceptions.CannotOpenFileException;
import uk.ac.ucl.shell.exceptions.FileNotFoundException;

public class Cat implements Application {
    @Override
    public void exec(List<String> args, InputStream input, OutputStreamWriter output) throws IOException {
        if (args.isEmpty()) {
            try (Scanner reader = new Scanner(input)) {
                while (reader.hasNextLine()) {
                    output.write(reader.nextLine());
                    output.write(System.getProperty("line.separator"));
                    output.flush();
                }
            }
        } else {
            for (String arg : args) {
                File file = Shell.getCurrentDirectory().resolve(arg).toFile();
                if (file.exists()) {
                    try (Scanner reader = new Scanner(file)) {
                        while (reader.hasNextLine()) {
                            output.write(reader.nextLine());
                            output.write(System.getProperty("line.separator"));
                            output.flush();
                        }
                    } catch (IOException e) {
                        throw new CannotOpenFileException("cat", file.getPath());
                    }
                } else {
                    throw new FileNotFoundException("cat", file.getPath());
                }
            }
        }
    }
}

