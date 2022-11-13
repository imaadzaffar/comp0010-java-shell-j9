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
import java.util.List;
import java.util.NoSuchElementException;

import uk.ac.ucl.shell.Shell;

import static uk.ac.ucl.shell.Shell.writer;

public class Cat implements Application {
    @Override
    public void exec(List<String> args, InputStream input, OutputStream output) throws IOException {
        Charset encoding = StandardCharsets.UTF_8;
        if (args.isEmpty()) {
            // take input from stdin
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in, encoding));
            try {
                String line;
                while ((line = reader.readLine()) != null) {
                    writer.write(line);
                    writer.write(System.getProperty("line.separator"));
                    writer.flush();
                }
            } catch (IOException e) {
                // this exception will never be thrown
                throw new RuntimeException(e);
            } catch (NoSuchElementException e) {
                reader.close();
            }
        } else {
            for (String arg : args) {
                File currFile = new File(Shell.getCurrentDirectory() + File.separator + arg);
                if (currFile.exists()) {
                    Path filePath = Paths.get(Shell.getCurrentDirectory() + File.separator + arg);
                    try (BufferedReader reader = Files.newBufferedReader(filePath, encoding)) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            writer.write(line);
                            writer.write(System.getProperty("line.separator"));
                            writer.flush();
                        }
                    } catch (IOException e) {
                        throw new RuntimeException("cat: cannot open " + arg);
                    }
                } else {
                    throw new RuntimeException("cat: file does not exist");
                }
            }
        }
    }
}

