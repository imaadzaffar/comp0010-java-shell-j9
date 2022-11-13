package uk.ac.ucl.shell.applications;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.NoSuchElementException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import uk.ac.ucl.shell.Shell;

import static uk.ac.ucl.shell.Shell.writer;

// creates a new file called "newfilename" that stores the sorted version of the file

public class Sort implements Application {
    @Override
    public void exec(List<String> args, InputStream input, OutputStream output) throws IOException {
        if (args.size() >= 3) { 
            throw new RuntimeException("sort: wrong arguments");
        }
        if (args.size() == 2 && !args.get(0).equals("-r")) {
            throw new RuntimeException("sort: wrong argument " + args.get(0));
        }

        boolean inReverseOrder = (args.size() == 2 || (args.size() == 1 && args.get(0).equals("-r"))) ? true : false;
        String sortArg = "";

        if (!inReverseOrder && args.size() == 1) {
            sortArg = args.get(0);
        } else if (args.size() == 2) {
            sortArg = args.get(1);
        }

        ArrayList<String> lines = new ArrayList<>();
        Charset encoding = StandardCharsets.UTF_8;
        if ((args.size() == 1 && !inReverseOrder) || (args.size() == 2)) {
            File sortFile = new File(Shell.getCurrentDirectory() + File.separator + sortArg);
            if (sortFile.exists()) {
                Path filePath = Paths.get(Shell.getCurrentDirectory() + File.separator + sortArg);
                try (BufferedReader reader = Files.newBufferedReader(filePath, encoding)) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        lines.add(line);
                    }
                }
            } else {
                throw new RuntimeException("sort: " + sortArg + " does not exist");
            }
        } else {
            // take input from stdin
            
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in, encoding))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    lines.add(line);
                }
            } 
        }   

        if (!lines.isEmpty()) {
            if (!inReverseOrder) {
                Collections.sort(lines);
            } else {
                Collections.sort(lines, Collections.reverseOrder());
            }
        } 

        for (String line : lines) {
            writer.write(line + System.getProperty("line.separator"));
            writer.flush();
        }
    }
}
