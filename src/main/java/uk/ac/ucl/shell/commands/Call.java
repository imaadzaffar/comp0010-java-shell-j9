package uk.ac.ucl.shell.commands;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.antlr.v4.runtime.tree.ParseTree;

import uk.ac.ucl.shell.Shell;
import uk.ac.ucl.shell.ShellVisitor;
import uk.ac.ucl.shell.ShellGrammarParser.CallContext;
import uk.ac.ucl.shell.exceptions.*;

public class Call implements Command<CallContext> {

    @Override
    public ByteArrayOutputStream execute(CallContext context, ShellVisitor visitor) {
        if (context.getChildCount() > 3) {
            throw new TooManyArgumentsException("call");
        }

        File inputFile = null;
        File outputFile = null;
        ParseTree command = null;

        // Find the call command parts as these might not be ordered
        for (ParseTree child : context.children) {
            String text = child.getText();

            if (text.startsWith("<")) {
                inputFile = Shell.getCurrentDirectory().resolve(text.substring(1, text.length()))
                        .toFile();
            } else if (text.startsWith(">")) {
                outputFile = Shell.getCurrentDirectory().resolve(text.substring(1, text.length()))
                        .toFile();
            } else {
                command = child;
            }
        }

        // If we expected both and input and an output but only received one (ex: echo a > a > a)
        if ((inputFile == null || outputFile == null) && context.getChildCount() == 3) {
            throw new InvalidArgumentsException("call");
        }

        if (inputFile != null) {
            try {
                visitor.setPipedInput(new FileInputStream(inputFile));
            } catch (Exception ex) {
                throw new FileNotFoundException("call", inputFile.getPath());
            }
        }

        ByteArrayOutputStream output = command.accept(visitor);

        if (outputFile != null) {
            try (FileOutputStream outputStream = new FileOutputStream(outputFile)) {
                outputStream.write(output.toByteArray());
            } catch (Exception ex) {
                throw new CannotOpenFileException("call", outputFile.getPath());
            }

            return null;
        }

        return output;
    }
}
