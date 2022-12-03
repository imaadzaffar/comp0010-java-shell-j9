package uk.ac.ucl.shell.commands;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

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

        if (command == null || (inputFile == null && outputFile == null)) {
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
            } catch (IOException eception) {
                throw new RuntimeException("call: failed to write to outfile file " + outputFile.getPath());
            }

            return null;
        }

        return output;
    }
}
