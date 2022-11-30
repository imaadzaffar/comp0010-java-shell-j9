package uk.ac.ucl.shell.commands;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import uk.ac.ucl.shell.ShellVisitor;
import uk.ac.ucl.shell.ShellErrorListener;
import uk.ac.ucl.shell.ShellGrammarLexer;
import uk.ac.ucl.shell.ShellGrammarParser;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class Substitution {
    public static ArrayList<String> sub(ArrayList<String> rawArguments) {

        ArrayList<String> res = new ArrayList<>();
        StringBuilder tempArgument = new StringBuilder();

        for (String argument : rawArguments) {
            if (!argument.contains("`")) {
                res.add(argument);
                continue;
            }

            String evalString;
            int startIndex = argument.indexOf("`");
            int endIndex = argument.lastIndexOf("`");

            tempArgument.append(argument.substring(0, startIndex));

            // String to be evaluated is extracted from within double quotes
            evalString = argument.substring(startIndex + 1, endIndex);

            // Create parse tree
            CharStream parserInput = CharStreams.fromString(evalString);
            ShellGrammarLexer lexer = new ShellGrammarLexer(parserInput);

            // removes default ConsoleErrorListener
            lexer.removeErrorListeners();
            // adds a custom error listener
            lexer.addErrorListener(new ShellErrorListener());

            CommonTokenStream tokenStream = new CommonTokenStream(lexer);
            ShellGrammarParser parser = new ShellGrammarParser(tokenStream);

            // removes default ConsoleErrorListener
            parser.removeErrorListeners();
            // adds a custom error listener
            parser.addErrorListener(new ShellErrorListener());

            ParseTree tree = parser.shell();
            ShellVisitor visitor = new ShellVisitor();
            ByteArrayOutputStream stream = visitor.visit(tree);

            // Append result to arg
            tempArgument.append(stream.toString().replace(System.getProperty("line.separator"), " ").trim());

            tempArgument.append(argument.substring(endIndex + 1));

            res.add(tempArgument.toString());
            tempArgument.setLength(0);
        }

        return res;
    }
}
