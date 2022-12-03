package uk.ac.ucl.shell;

import java.io.*;
import uk.ac.ucl.shell.ShellGrammarParser.*;
import uk.ac.ucl.shell.commands.*;

public class ShellVisitor extends ShellGrammarBaseVisitor<ByteArrayOutputStream>  {
    private InputStream pipedInput;

    public InputStream getPipedInput() {
        return pipedInput;
    }

    public void setPipedInput(InputStream pipedInput) {
        this.pipedInput = pipedInput;
    }

    @Override
    public ByteArrayOutputStream visitSequence(SequenceContext ctx) {
        return new Sequence().execute(ctx, this);
    }

    @Override
    public ByteArrayOutputStream visitPipe(PipeContext ctx) {
        return new Pipe().execute(ctx, this);
    }

    @Override
    public ByteArrayOutputStream visitArgument(ArgumentContext ctx) {
        return new Argument().execute(ctx, this);
    }

    @Override
    public ByteArrayOutputStream visitCall(CallContext ctx) {
        if (ctx.getChildCount() == 1) {
            return super.visitCall(ctx);
        }

        return new Call().execute(ctx, this);
    }
}
