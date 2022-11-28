package uk.ac.ucl.shell.applications;

import java.io.*;
import java.util.List;
import java.util.regex.Pattern;
import java.util.Scanner;

import uk.ac.ucl.shell.Shell;
import uk.ac.ucl.shell.exceptions.CannotOpenFileException;
import uk.ac.ucl.shell.exceptions.FileNotFoundException;
import uk.ac.ucl.shell.exceptions.MissingArgumentsException;

public class Grep implements Application {
    @Override
    public void exec(List<String> args, InputStream input, OutputStreamWriter output) throws IOException {
        if (args.size() < 1) {
            throw new MissingArgumentsException("grep");
        }

        Pattern grepPattern = Pattern.compile(args.get(0));
        
        if(args.size() == 1) {
            try (Scanner reader = new Scanner(input)) {
                while (reader.hasNextLine()) {
                    String line = reader.nextLine();

                    if(grepPattern.matcher(line).find()) {
                        output.write(line);
                        output.write(System.getProperty("line.separator"));
                        output.flush();
                    }
                }
            }
        } else {
            List<String> files = args.subList(1, args.size());

            for(String arg : files) {
                File file = Shell.getCurrentDirectory().resolve(arg).toFile();
                if (file.exists()) {
                    try (Scanner reader = new Scanner(file)) {
                        while (reader.hasNextLine()) {
                            String line = reader.nextLine();
        
                            if(grepPattern.matcher(line).find()) {
                                if(files.size() > 1)
                                    output.write(arg + ":");

                                output.write(line);
                                output.write(System.getProperty("line.separator"));
                                output.flush();
                            }
                        }
                    } catch (IOException e) {
                        throw new CannotOpenFileException("grep", file.getPath());
                    }
                } else {
                    throw new FileNotFoundException("grep", file.getPath());
                }
            }
        }
    }
}
