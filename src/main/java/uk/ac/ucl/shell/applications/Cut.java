package uk.ac.ucl.shell.applications;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.ByteArrayOutputStream;
import java.util.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.lang.String;
import uk.ac.ucl.shell.Shell;
import uk.ac.ucl.shell.exceptions.CannotOpenFileException;
import uk.ac.ucl.shell.exceptions.FileNotFoundException;
import uk.ac.ucl.shell.exceptions.InvalidArgumentsException;
import uk.ac.ucl.shell.exceptions.MissingArgumentsException;

public class Cut implements Application {
    @Override
    public void exec(List<String> args, InputStream input, OutputStreamWriter output) throws IOException {
        if ((args.size() <= 1) || !args.get(0).equals("-b")) {
            throw new MissingArgumentsException("cut");
        }

        String[] intervals = args.get(1).split(",");

        for (String interval : intervals) {
            String[] values = interval.split("-");
            for (String value : values) {
                try {
                    if (!value.equals("") && Integer.parseInt(value) == 0) {
                        throw new RuntimeException("cut: wrong argument");
                    }
                } catch (NumberFormatException e) {
                    throw new RuntimeException("cut: wrong argument");
                }
            }
        }
        
        if(args.size() == 2) {
            try (Scanner reader = new Scanner(input)) {
                while (reader.hasNextLine()) {
                    String cutLine = cutLine(reader.nextLine(), intervals);

                    output.write(cutLine);
                    output.write(System.getProperty("line.separator"));
                    output.flush();
                }
            }
        } else {
            File file = Shell.getCurrentDirectory().resolve(args.get(args.size() - 1)).toFile();
            if (file.exists()) {
                try (Scanner reader = new Scanner(file)) {
                    while (reader.hasNextLine()) {
                        String cutLine = cutLine(reader.nextLine(), intervals);

                        output.write(cutLine);
                        output.write(System.getProperty("line.separator"));
                        output.flush();
                    }
                } catch (IOException e) {
                    throw new CannotOpenFileException("cut", file.getPath());
                }
            } else {
                throw new FileNotFoundException("cut", file.getPath());
            }
        }
    }

    private String cutLine(String line, String[] intervals) throws IOException {
        try(ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            byte[] bytes = line.getBytes();

            HashSet<Integer> toCopy = new HashSet<>();
    
            for(String interval : intervals) {
                var parts = interval.split("-", -1);
    
                if(parts.length == 1) {
                    toCopy.add(Integer.parseInt(parts[0]) - 1);
                } else if(parts[0].equals("")) {
                    toCopy.addAll(IntStream.range(0, Integer.parseInt(parts[1])).boxed().collect(Collectors.toList()));
                } else if(parts[1].equals("")) {
                    toCopy.addAll(IntStream.range(Integer.parseInt(parts[0]) - 1, bytes.length).boxed().collect(Collectors.toList()));
                } else {
                    toCopy.addAll(IntStream.range(Integer.parseInt(parts[0]) - 1, Integer.parseInt(parts[1])).boxed().collect(Collectors.toList()));
                }
            }

            List<Integer> sorted = new ArrayList<>(toCopy);
            Collections.sort(sorted);

            for(int index : sorted) {
                if(index < bytes.length)
                    output.write(bytes[index]);
            }
    
            return output.toString();
        }
    }
}
