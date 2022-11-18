package uk.ac.ucl.shell.commands;

public interface Command {
    void eval(String input, String output);
}
