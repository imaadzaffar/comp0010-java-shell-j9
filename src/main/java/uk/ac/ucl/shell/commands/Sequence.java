package uk.ac.ucl.shell.commands;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.antlr.v4.runtime.tree.ParseTree;

import uk.ac.ucl.shell.ShellVisitor;
import uk.ac.ucl.shell.ShellGrammarParser.SequenceContext;

public class Sequence implements Command<SequenceContext> {

    @Override
    public ByteArrayOutputStream execute(SequenceContext context, ShellVisitor visitor) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        for (ParseTree child : context.children) {
            if (child.getText().startsWith(";")) {
                continue;
            }

            // Reset the piped input as sequenced commands are independent
            visitor.setPipedInput(null);

            ByteArrayOutputStream childOutput = child.accept(visitor);

            if(childOutput == null) continue;

            try(childOutput) {
                output.write(childOutput.toByteArray());
            } catch(IOException ex) {
                throw new RuntimeException(ex.getMessage());
            }
        }
        
        return output;
    }
}
