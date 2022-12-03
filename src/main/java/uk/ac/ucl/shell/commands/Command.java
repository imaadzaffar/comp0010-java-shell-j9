package uk.ac.ucl.shell.commands;

import java.io.ByteArrayOutputStream;

import uk.ac.ucl.shell.ShellVisitor;

public interface Command<TContext> {
    ByteArrayOutputStream execute(TContext context, ShellVisitor visitor);
}
