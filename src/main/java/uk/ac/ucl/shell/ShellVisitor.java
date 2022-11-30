package uk.ac.ucl.shell;

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
import uk.ac.ucl.shell.commands.Substitution;

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

            if(childOutput == null) continue;

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
        boolean substituted = false;

        ArrayList<String> args = new ArrayList<>();

        // omits the application from the list of arguments
        for (ParseTree child : ctx.children) {
            args.add(child.getText());

            substituted = substituted || child.getText().contains("`");
        }
        
        try {
            args = Substitution.sub(args);    
        } catch (Exception ex) {
            throw new RuntimeException("error during substitution: " + ex);
        }

        Application app = new ApplicationFactory().getApp(args.get(0));
        args.remove(0);

        var stream = new ByteArrayOutputStream();
        var output = new OutputStreamWriter(stream);

        try {
            args = new Globbing().glob(args, substituted);
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
        ParseTree command = null;

        for(ParseTree child : ctx.children) {
            String text = child.getText();

            if (text.startsWith("<")) {
                inputFile = Shell.getCurrentDirectory().resolve(text.substring(1, text.length()))
                    .toFile();
            } else if(text.startsWith(">")) {
                outputFile = Shell.getCurrentDirectory().resolve(text.substring(1, text.length()))
                    .toFile();
            } else {
                command = child;
            }
        }

        if(command == null || (inputFile == null && outputFile == null)) {
            throw new RuntimeException("call: wrong call arguments");
        }

        if (inputFile != null) {
            try {
                input = new FileInputStream(inputFile);
            } catch (Exception ex) {
                throw new RuntimeException("call: file does not exist " + inputFile.getPath());
            }
        }

        try {
            ByteArrayOutputStream output = command.accept(this);

            if (outputFile != null) {
                FileOutputStream outputStream = new FileOutputStream(outputFile);

                outputStream.write(output.toByteArray());
                outputStream.close();

                return null;
            }

            return output;
        } catch (Exception ex) {
            throw new RuntimeException("call: failed to write to outfile file " + outputFile.getPath());
        }
    }
}
