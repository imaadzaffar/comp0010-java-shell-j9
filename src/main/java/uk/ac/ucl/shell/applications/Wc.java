package uk.ac.ucl.shell.applications;

import uk.ac.ucl.shell.Shell;
import uk.ac.ucl.shell.exceptions.FileNotFoundException;
import uk.ac.ucl.shell.exceptions.MissingArgumentsException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Wc implements Application {
    @Override
    public void exec(List<String> args, InputStream input, OutputStreamWriter output) throws IOException {
        if (args.isEmpty()) {
            throw new MissingArgumentsException("wc");
        }

        BufferedReader reader;
        for (String fileName : args) {
            int num_of_lines = 0;
            int num_of_words = 0;
            int num_of_bytes = 0;

            Path filePath = Shell.getCurrentDirectory().resolve(fileName);

            if (!Files.exists(filePath) || Files.isDirectory(filePath)) {
                throw new FileNotFoundException("wc", filePath.toString());
            }

            reader = new BufferedReader(new FileReader(filePath.toString()));
            String line;

            while ((line = reader.readLine()) != null) {
                num_of_lines++;
                num_of_words += line.split("\\s+").length;
                num_of_bytes += line.length();
            }

            output.write(num_of_lines + " " + num_of_words + " " + num_of_bytes + " " + fileName);
            output.write(System.getProperty("line.separator"));
            output.flush();
        }
    }
}
