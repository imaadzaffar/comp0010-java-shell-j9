package uk.ac.ucl.shell.applications;

import java.io.OutputStreamWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import uk.ac.ucl.shell.Shell;

public class Sort implements Application {
    @Override
    public void exec(List<String> args, InputStream input, OutputStreamWriter output) throws IOException {
        if (args.size() > 2) { 
            throw new RuntimeException("sort: invalid arguments");
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
                    throw new RuntimeException("sort: cannot open " + file.getPath());
                }
            } else {
                throw new RuntimeException("sort: file does not exist");
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