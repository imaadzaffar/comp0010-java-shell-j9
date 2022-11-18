package uk.ac.ucl.shell.applications;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.NoSuchElementException;

import uk.ac.ucl.shell.Shell;

public class Grep implements Application {
    @Override
    public void exec(List<String> args, InputStream input, OutputStreamWriter output) throws IOException {
        if (args.size() < 1) {
            throw new RuntimeException("grep: wrong number of arguments");
        }
        Pattern grepPattern = Pattern.compile(args.get(0));
        Charset encoding = StandardCharsets.UTF_8;

        if (args.size() != 1) {
            int numOfFiles = args.size() - 1;
            Path filePath;
            Path[] filePathArray = new Path[numOfFiles];
            Path currentDir = Paths.get(Shell.getCurrentDirectory());
            for (int i = 0; i < numOfFiles; i++) {
                filePath = currentDir.resolve(args.get(i + 1));
                if (Files.notExists(filePath) || Files.isDirectory(filePath) ||
                        !Files.exists(filePath) || !Files.isReadable(filePath)) {
                    throw new RuntimeException("grep: wrong file argument");
                }
                filePathArray[i] = filePath;
            }
            for (int j = 0; j < filePathArray.length; j++) {
                try (BufferedReader reader = Files.newBufferedReader(filePathArray[j], encoding)) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        Matcher matcher = grepPattern.matcher(line);
                        if (matcher.find()) {
                            if (numOfFiles > 1) {
                                output.write(args.get(j+1));
                                output.write(":");
                            }
                            output.write(line);
                            output.write(System.getProperty("line.separator"));
                            output.flush();
                        }
                    }
                } catch (IOException e) {
                    throw new RuntimeException("grep: cannot open " + args.get(j + 1));
                }
            }
        } else {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in, encoding));
            try {
                String line;
                while ((line = reader.readLine()) != null) {
                    Matcher matcher = grepPattern.matcher(line);
                    if (matcher.find()) {
                        output.write(line);
                        output.write(System.getProperty("line.separator"));
                        output.flush();
                    }
                }
            } catch (NoSuchElementException e) {
                reader.close();
            }
        }
    }
}
