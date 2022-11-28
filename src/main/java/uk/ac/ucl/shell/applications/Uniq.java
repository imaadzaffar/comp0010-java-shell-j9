package uk.ac.ucl.shell.applications;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import uk.ac.ucl.shell.Shell;
import uk.ac.ucl.shell.exceptions.CannotOpenFileException;
import uk.ac.ucl.shell.exceptions.FileNotFoundException;
import uk.ac.ucl.shell.exceptions.TooManyArgumentsException;

public class Uniq implements Application {

    @Override
    public void exec(List<String> args, InputStream input, OutputStreamWriter output) throws IOException {
        if (args.size() > 2) { 
            throw new TooManyArgumentsException("uniq");
        }

        boolean invariant = !args.isEmpty() && args.get(0).equals("-i");
        
        List<String> lines = new ArrayList<>();
        
        if (args.size() == (invariant ? 1 : 0)) {
            try (Scanner reader = new Scanner(input)) {
                while (reader.hasNextLine()) {
                    lines.add(reader.nextLine());
                }
            }
        } else {
            File file = Shell.getCurrentDirectory().resolve(args.get(args.size() - 1)).toFile();
            if (file.exists()) {
                try (Scanner reader = new Scanner(file)) {
                    while (reader.hasNextLine()) {
                        lines.add(reader.nextLine());
                    }
                } catch (IOException e) {
                    throw new CannotOpenFileException("uniq", file.getPath());
                }
            } else {
                throw new FileNotFoundException("uniq", file.getPath());
            }
        }

        for (int i = 0; i < lines.size(); i++) {
            if(i != 0 && (invariant ? lines.get(i).equalsIgnoreCase(lines.get(i - 1)) : lines.get(i).equals(lines.get(i - 1)))) {
                continue;
            }

            output.write(lines.get(i));
            output.write(System.getProperty("line.separator"));
            output.flush();
        }
    }
}
