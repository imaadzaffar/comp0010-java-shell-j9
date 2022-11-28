package uk.ac.ucl.shell.applications;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import uk.ac.ucl.shell.Shell;
import uk.ac.ucl.shell.exceptions.CannotOpenFileException;
import uk.ac.ucl.shell.exceptions.FileNotFoundException;
import uk.ac.ucl.shell.exceptions.InvalidArgumentsException;
import uk.ac.ucl.shell.exceptions.MissingArgumentsException;
import uk.ac.ucl.shell.exceptions.TooManyArgumentsException;

public class Head implements Application {
    @Override
    public void exec(List<String> args, InputStream input, OutputStreamWriter output) throws IOException {
        if (args.size() > 3) {
            throw new TooManyArgumentsException("head");
        } else if ((args.size() > 1 && !args.get(0).equals("-n"))) {
            throw new InvalidArgumentsException("head");
        }

        int lines = 10;
        
        if (args.size() > 1) {
            try {
                lines = Integer.parseInt(args.get(1));
            } catch (NumberFormatException e) {
                throw new InvalidArgumentsException("head");
            }
        }

        List<String> results;

        if(args.size() == 0 || args.size() == 2) {
            try (Scanner reader = new Scanner(input)) {
                results = getLines(reader, lines);
            }
        } else {
            File file = Shell.getCurrentDirectory().resolve(args.get(args.size() - 1)).toFile();

            if (file.exists()) {
                try (Scanner reader = new Scanner(file)) {
                    results = getLines(reader, lines);
                } catch (IOException e) {
                    throw new CannotOpenFileException("head", file.getPath());
                }
            } else {
                throw new FileNotFoundException("head", file.getPath());
            }
        }

        for(String line : results){
            output.write(line);
            output.write(System.getProperty("line.separator"));
            output.flush();
        }
    }

    public List<String> getLines(Scanner reader, int count) {
        List<String> lines = new ArrayList<String>();

        for(int i = 0; i < count; i++) {
            if(!reader.hasNextLine()) {
                break;
            }

            lines.add(reader.nextLine());
        }

        return lines;
    }
}
