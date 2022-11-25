package uk.ac.ucl.shell.commands;

import java.io.InputStream;
import java.io.OutputStreamWriter;

public interface Command {
    void eval(InputStream input, OutputStreamWriter output);
}
