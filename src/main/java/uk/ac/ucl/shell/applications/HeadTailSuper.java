package uk.ac.ucl.shell.applications;

import uk.ac.ucl.shell.Shell;
import uk.ac.ucl.shell.exceptions.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Scanner;

public abstract class HeadTailSuper implements Application {
    private final String appName;

    public HeadTailSuper(String appName) {
        this.appName = appName;
    }

    @Override
    public void exec(List<String> args, InputStream input, OutputStreamWriter output) throws IOException {
        if (args.size() == 1 && args.get(0).equals("-n")) {
            throw new MissingArgumentsException(appName);
        } else if (args.size() > 3) {
            throw new TooManyArgumentsException(appName);
        } else if ((args.size() > 1 && !args.get(0).equals("-n"))) {
            throw new InvalidArgumentsException(appName);
        }

        int lines = 10;

        if (args.size() > 1) {
            try {
                lines = Integer.parseInt(args.get(1));
            } catch (NumberFormatException e) {
                throw new InvalidArgumentsException(appName);
            }
        }

        List<String> results;

        if (args.size() == 0 || args.size() == 2) {
            try (Scanner reader = new Scanner(input)) {
                results = getLines(reader, lines);
            } catch (NullPointerException e) {
                throw new MissingArgumentsException(appName);
            }
        } else {
            File file = Shell.getCurrentDirectory().resolve(args.get(args.size() - 1)).toFile();

            if (file.exists()) {
                try (Scanner reader = new Scanner(file)) {
                    results = getLines(reader, lines);
                } catch (IOException e) {
                    throw new CannotOpenFileException(appName, file.getPath());
                }
            } else {
                throw new FileNotFoundException(appName, file.getPath());
            }
        }

        for (String line : results) {
            output.write(line);
            output.write(System.getProperty("line.separator"));
            output.flush();
        }
    }

    abstract List<String> getLines(Scanner reader, int count);
}
