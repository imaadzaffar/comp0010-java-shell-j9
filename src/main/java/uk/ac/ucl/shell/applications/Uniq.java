package uk.ac.ucl.shell.applications;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.NoSuchElementException;

import uk.ac.ucl.shell.Shell;

public class Uniq implements Application {

    @Override
    public void exec(List<String> args, InputStream input, OutputStreamWriter output) throws IOException {
        // For uniq to work, the input list must be sorted since it removes adjacent duplicate elements

        if (args.size() > 2 || (args.size() == 2 && !args.get(0).equals("-i"))) {
            throw new RuntimeException("uniq: wrong arguments");
        }

        boolean ignoreCase = (args.size() == 2 || (args.size() == 1 && args.get(0).equals("-i"))) ? true : false;;
        String uniqArg = "";

        if (!ignoreCase && args.size() == 1) {
            uniqArg = args.get(0);
        } else if (args.size() == 2) {
            uniqArg = args.get(1);
        }

        Charset encoding = StandardCharsets.UTF_8;

        if (!uniqArg.isEmpty()) {
            File sortFile = new File(Shell.getCurrentDirectory() + File.separator + uniqArg);
            if (sortFile.exists()) {
                Path filePath = Paths.get(Shell.getCurrentDirectory() + File.separator + uniqArg);
                try (BufferedReader reader = Files.newBufferedReader(filePath, encoding)) {
                    String line = reader.readLine();
                    String prev = String.valueOf(line);
                    if (line != null) {
                        output.write(line + System.getProperty("line.separator"));
                        while ((line = reader.readLine()) != null) {
                            if (!ignoreCase) {
                                if (!prev.equals(line)) {
                                    output.write(line + System.getProperty("line.separator"));
                                    prev = line;
                                }
                            } else {
                                if (!prev.equalsIgnoreCase(line)) {
                                    output.write(line + System.getProperty("line.separator"));
                                    prev = line;
                                }
                            }
                        }
                    }
                } catch (IOException e) {
                    throw new RuntimeException("uniq: cannot open " + uniqArg);
                } 
            } else {
                throw new RuntimeException("uniq: " + uniqArg + " does not exist");
            }
        } else {
            // take input from stdin
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in, encoding));
            try {
                String line = reader.readLine();
                String prev = String.valueOf(line);
                if (line != null) {
                    output.write(line + System.getProperty("line.separator"));
                    while ((line = reader.readLine()) != null) {
                        if (!ignoreCase) {
                            if (!prev.equals(line)) {
                                output.write(line + System.getProperty("line.separator"));
                                prev = line;
                            }
                        } else {
                            if (!prev.equalsIgnoreCase(line)) {
                                output.write(line + System.getProperty("line.separator"));
                                prev = line;
                            }
                        }
                    }
                }
            } catch (NoSuchElementException e) {
                reader.close();
            }
        }
        output.flush();
    }
}
