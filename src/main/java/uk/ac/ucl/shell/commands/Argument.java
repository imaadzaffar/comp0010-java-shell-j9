package uk.ac.ucl.shell.commands;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import org.antlr.v4.runtime.tree.ParseTree;

import uk.ac.ucl.shell.ShellGrammarParser.ArgumentContext;
import uk.ac.ucl.shell.applications.Application;
import uk.ac.ucl.shell.applications.ApplicationFactory;
import uk.ac.ucl.shell.ShellVisitor;

public class Argument implements Command<ArgumentContext> {

    @Override
    public ByteArrayOutputStream execute(ArgumentContext context, ShellVisitor visitor) {
        boolean substituted = false;

        ArrayList<String> args = new ArrayList<>();

        for (ParseTree child : context.children) {
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
            app.exec(args, visitor.getPipedInput(), output);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        return stream;
    }
    
}
