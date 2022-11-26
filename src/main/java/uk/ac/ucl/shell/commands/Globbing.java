package uk.ac.ucl.shell.commands;

import uk.ac.ucl.shell.Shell;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Globbing {
    public ArrayList<String> glob(ArrayList<String> rawArguments) throws IOException {
        String spaceRegex = "[^\\s\"']+|\"([^\"]*)\"|'([^']*)'";
        ArrayList<String> tokens = new ArrayList<>();
        Pattern regex = Pattern.compile(spaceRegex);
        for (String argument : rawArguments) {
            Matcher regexMatcher = regex.matcher(argument);
            String nonQuote;
            while (regexMatcher.find()) {
                if (regexMatcher.group(1) != null || regexMatcher.group(2) != null) {
                    String quoted = regexMatcher.group(0).trim();
                    tokens.add(quoted.substring(1, quoted.length() - 1));

                } else {
                    nonQuote = regexMatcher.group().trim();
                    ArrayList<String> globbingResult = new ArrayList<>();
                    Path dir = Paths.get(Shell.getCurrentDirectory().toString());
                    DirectoryStream<Path> stream = Files.newDirectoryStream(dir, nonQuote);

                    for (Path entry : stream) {
                        globbingResult.add(entry.getFileName().toString());
                    }

                    if (globbingResult.isEmpty()) {
                        globbingResult.add(nonQuote);
                    }
                    tokens.addAll(globbingResult);
                }
            }
        }
        return tokens;

    }
}
