package uk.ac.ucl.shell.applications;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.lang.NumberFormatException;
import java.util.NoSuchElementException;

import uk.ac.ucl.shell.Shell;

import static uk.ac.ucl.shell.Shell.writer;

public class Tail implements Application {
    @Override
    public void exec(List<String> args, InputStream input, OutputStream output) throws IOException {

        if ((args.size() > 3) || ((args.size() == 2 || args.size() == 3) && !args.get(0).equals("-n")) || (args.size() == 1 && args.get(0).equals("-n"))) {
            throw new RuntimeException("tail: wrong arguments");
        }

        int tailLines = 10;
        String tailArg = "";
        
        if (args.size() == 2 || args.size() == 3) {
            try {
                tailLines = Integer.parseInt(args.get(1));
            } catch (NumberFormatException e) {
                throw new RuntimeException("tail: wrong arguments");
            }
        }

        if (args.size() == 3) {
            tailArg = args.get(2);
        } else if (args.size() == 1) {
            tailArg = args.get(0);
        }

        ArrayList<String> lines = new ArrayList<>();
        Charset encoding = StandardCharsets.UTF_8;
        if (!tailArg.isEmpty()) {
            File tailFile = new File(Shell.getCurrentDirectory() + File.separator + tailArg);
            if (tailFile.exists()) {
                Path filePath = Paths.get(Shell.getCurrentDirectory() + File.separator + tailArg);
                try (BufferedReader reader = Files.newBufferedReader(filePath, encoding)) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        lines.add(line);
                    }
                } catch (IOException e) {
                    throw new RuntimeException("tail: cannot open " + tailArg);
                } 
            } else {
                throw new RuntimeException("tail: " + tailArg + " does not exist");
            }
        } else {
            // take input from stdin
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in, encoding));
            try {
                String line;
                while ((line = reader.readLine()) != null) {
                    lines.add(line);
                }
            } catch (NoSuchElementException e) {
                reader.close();
            }
        }

        int index = 0;
        if (tailLines > lines.size()) {
            index = 0;
        } else {
            index = lines.size() - tailLines;
        }
        for (int i = index; i < lines.size(); i++) {
            writer.write(lines.get(i) + System.getProperty("line.separator"));
            writer.flush();
        }
    }
}
