package uk.ac.ucl.shell.applications;

import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import uk.ac.ucl.shell.Shell;
import uk.ac.ucl.shell.exceptions.CannotOpenFileException;
import uk.ac.ucl.shell.exceptions.TooManyArgumentsException;

public class Sort implements Application {
    @Override
    public void exec(List<String> args, InputStream input, OutputStreamWriter output) throws IOException {
        if (args.size() > 2) { 
            throw new TooManyArgumentsException("sort");
        }

        boolean reverse = !args.isEmpty() && args.get(0).equals("-r");
        
        List<String> lines = new ArrayList<String>();
        
        if(args.size() == (reverse ? 1 : 0)) {
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
                    throw new CannotOpenFileException("sort", file.getPath());
                }
            } else {
                throw new FileNotFoundException("sort");
            }
        }

        if(reverse) {
            Collections.sort(lines, Collections.reverseOrder());
        } else {
            Collections.sort(lines);
        }

        for (String line : lines) {
            output.write(line);
            output.write(System.getProperty("line.separator"));
            output.flush();
        }
    }
}