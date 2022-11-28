package uk.ac.ucl.shell;

import java.io.File;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.NoSuchElementException;
import java.util.Scanner;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import uk.ac.ShellVisitor;

public class Shell {
    private static Path currentDirectory = Paths.get(System.getProperty("user.dir"));

    public static Path getCurrentDirectory() {
        return currentDirectory;
    }

    public static void setCurrentDirectory(String cd) {
        currentDirectory = Paths.get(cd);
    }

    public static void eval(String cmdline, OutputStream output) throws IOException {
        CharStream parserInput = CharStreams.fromString(cmdline);
        ShellGrammarLexer lexer = new ShellGrammarLexer(parserInput);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);        
        ShellGrammarParser parser = new ShellGrammarParser(tokenStream);
        ParseTree tree = parser.shell();
        ShellVisitor visitor = new ShellVisitor();
        ByteArrayOutputStream stream = visitor.visit(tree);

        if (stream != null) {
            OutputStreamWriter writer = new OutputStreamWriter(output);
            writer.write(stream.toString());
            writer.flush();
            stream.close();
        }
    }

    public static void main(String[] args) {
        if (args.length > 0) {
            if (args.length != 2) {
                System.out.println("COMP0010 shell: invalid number of arguments");
                return;
            }
            if (!args[0].equals("-c")) {
                System.out.println("COMP0010 shell: " + args[0] + ": unexpected argument");
            }
            try {
                eval(args[1], System.out);
            } catch (Exception e) {
                System.err.println("COMP0010 shell: " + e.getMessage());
            }
        } else {
            try (Scanner input = new Scanner(System.in)) {
                while (true) {
                    String prompt = currentDirectory + "> ";
                    System.out.print(prompt);
                    try {
                        String cmdline = input.nextLine();
                        eval(cmdline, System.out);
                    } catch (NoSuchElementException e) {
                        input.close();
                        break;
                    } catch (Exception e) {
                        System.err.println("COMP0010 shell: " + e.getMessage());
                    }
                }
            }
        }
    }

}
