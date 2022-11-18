package uk.ac.ucl.shell.applications;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.ArrayList;
import java.util.NoSuchElementException;

import uk.ac.ucl.shell.Shell;

public class Head implements Application {
    @Override
    public void exec(List<String> args, InputStream input, OutputStreamWriter output) throws IOException {
        if ((args.size() > 3) || ((args.size() == 2 || args.size() == 3) && !args.get(0).equals("-n")) || (args.size() == 1 && args.get(0).equals("-n"))) {
            throw new RuntimeException("head: wrong arguments");
        }

        int headLines = 10;
        String headArg = "";
        
        if (args.size() == 2 || args.size() == 3) {
            try {
                headLines = Integer.parseInt(args.get(1));
            } catch (NumberFormatException e) {
                throw new RuntimeException("head: wrong arguments");
            }
        }

        if (args.size() == 3) {
            headArg = args.get(2);
        } else if (args.size() == 1) {
            headArg = args.get(0);
        }

        ArrayList<String> lines = new ArrayList<>();
        Charset encoding = StandardCharsets.UTF_8;
        if (!headArg.isEmpty()) {
            File tailFile = new File(Shell.getCurrentDirectory() + File.separator + headArg);
            if (tailFile.exists()) {
                Path filePath = Paths.get(Shell.getCurrentDirectory() + File.separator + headArg);
                try (BufferedReader reader = Files.newBufferedReader(filePath, encoding)) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        lines.add(line);
                    }
                } catch (IOException e) {
                    throw new RuntimeException("head: cannot open " + headArg);
                } 
            } else {
                throw new RuntimeException("head: " + headArg + " does not exist");
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

        if (lines.size() <= headLines) {
            for (String line : lines) {
                output.write(line + System.getProperty("line.separator"));
                output.flush();
            }
        } else {
            for (int i = 0; i < headLines; i++) {
                output.write(lines.get(i) + System.getProperty("line.separator"));
                output.flush();
            }
        }
    }
}
