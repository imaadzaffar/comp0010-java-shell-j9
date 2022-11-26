package uk.ac;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.antlr.v4.runtime.tree.ParseTree;

import uk.ac.ucl.shell.Shell;
import uk.ac.ucl.shell.ShellGrammarBaseVisitor;
import uk.ac.ucl.shell.ShellGrammarParser.*;
import uk.ac.ucl.shell.applications.Application;
import uk.ac.ucl.shell.applications.ApplicationFactory;
import uk.ac.ucl.shell.commands.Globbing;

public class ShellVisitor extends ShellGrammarBaseVisitor<ByteArrayOutputStream>  {
    private InputStream input;

    @Override
    public ByteArrayOutputStream visitSequence(SequenceContext ctx) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        // MANY ERRORS STEM FROM THIS FOR LOOP
        for (ParseTree child : ctx.children) {
            if (child.getText().startsWith(";")) {
                continue;
            }
            // resets sequence input stream to null
            input = null;
            var childOutput = child.accept(this);

            try {
                output.write(childOutput.toByteArray());
                childOutput.close();
            } catch (IOException e) {
                // this exception must be properly caught or sent up the stack trace
            }
        }
        return output;
    }

    @Override
    public ByteArrayOutputStream visitPipe(PipeContext ctx) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        for (ParseTree child : ctx.children) {
            // skips over pipe characters
            if (child.getText().startsWith("|")) {
                continue;
            }

            output = child.accept(this);
            input = new ByteArrayInputStream(output.toByteArray());
        }

        return output;
    }

    @Override
    public ByteArrayOutputStream visitArgument(ArgumentContext ctx) {
        Application app = new ApplicationFactory().getApp(ctx.getChild(0).getText());
        ArrayList<String> args = new ArrayList<>();

        // omits the application from the list of arguments
        for (ParseTree child : ctx.children.subList(1, ctx.children.size())) {
            args.add(child.getText());
        }

        var stream = new ByteArrayOutputStream();
        var output = new OutputStreamWriter(stream);

        try {
            Globbing globbing = new Globbing();
            args = globbing.glob(args);
            app.exec(args, input, output);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        return stream;
    }

    @Override
    public ByteArrayOutputStream visitCall(CallContext ctx) {
        if (ctx.getChildCount() == 1) {
            return super.visitCall(ctx);
        } else if (ctx.getChildCount() > 3) {
            throw new RuntimeException("call: wrong call arguments");
        }

        File inputFile = null;
        File outputFile = null;

        if (ctx.getChildCount() == 3) {
            String inputFileName = ctx.getChild(1).getText();
            String outputFileName = ctx.getChild(2).getText();

            if (!inputFileName.startsWith("<"))
                throw new RuntimeException("idk");

            if (!outputFileName.startsWith("<"))
                throw new RuntimeException("idk");

            inputFile = Shell.getCurrentDirectory().resolve(inputFileName.substring(1, inputFileName.length()))
                    .toFile();
            outputFile = Shell.getCurrentDirectory().resolve(outputFileName.substring(1, outputFileName.length()))
                    .toFile();
        } else {
            String fileName = ctx.getChild(1).getText();

            if (fileName.startsWith("<")) {
                inputFile = Shell.getCurrentDirectory().resolve(fileName.substring(1, fileName.length())).toFile();
            } else {
                outputFile = Shell.getCurrentDirectory().resolve(fileName.substring(1, fileName.length())).toFile();
            }
        }

        try {
            if (inputFile != null) {
                if (inputFile.exists()) {
                    input = new FileInputStream(inputFile);
                } else {
                    throw new RuntimeException("call: file does not exist " + inputFile.getPath());
                }
            }

            ByteArrayOutputStream output = ctx.getChild(0).accept(this);

            if (outputFile != null) {
                FileOutputStream outputStream = new FileOutputStream(outputFile);

                outputStream.write(output.toByteArray());
                outputStream.close();

                return null;
            }

            return output;
        } catch (Exception ex) {
            throw new RuntimeException("idk");
        }
    }
}
