package uk.ac.ucl.shell.commands;

import java.io.ByteArrayOutputStream;

import uk.ac.ucl.shell.ShellVisitor;


/**
 * This is an interface for all commands to implement and provides an entry point for these to be executed
 */
public interface Command<TContext> {
    ByteArrayOutputStream execute(TContext context, ShellVisitor visitor);
}
