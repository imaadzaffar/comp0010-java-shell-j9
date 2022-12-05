package uk.ac.ucl.shell.commands;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.antlr.v4.runtime.tree.ParseTree;

import uk.ac.ucl.shell.ShellVisitor;
import uk.ac.ucl.shell.ShellGrammarParser.PipeContext;

public class Pipe implements Command<PipeContext> {

    @Override
    public ByteArrayOutputStream execute(PipeContext context, ShellVisitor visitor) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        for (ParseTree child : context.children) {
            // skips over pipe characters
            if (child.getText().startsWith("|")) {
                continue;
            }

            output = child.accept(visitor);
            visitor.setPipedInput(new ByteArrayInputStream(output.toByteArray()));
        }

        return output;
    }
}
