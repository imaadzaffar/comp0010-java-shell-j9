package uk.ac.ucl.shell.applications;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.regex.Pattern;
import java.util.Scanner;

import uk.ac.ucl.shell.Shell;

public class Grep implements Application {
    @Override
    public void exec(List<String> args, InputStream input, OutputStreamWriter output) throws IOException {
        if (args.size() < 1) {
            throw new RuntimeException("grep: invalid number of arguments");
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
                        throw new RuntimeException("grep: cannot open " + file.getPath());
                    }
                } else {
                    throw new RuntimeException("grep: file does not exist " + file.getPath());
                }
            }
        }
    }
}
