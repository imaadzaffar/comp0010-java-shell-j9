package uk.ac.ucl.shell;

import org.junit.Test;
import uk.ac.ucl.shell.applications.Pwd;
import uk.ac.ucl.shell.exceptions.TooManyArgumentsException;

import java.io.*;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class PwdTest {
    Pwd pwd;
    InputStream in;
    ByteArrayOutputStream stream;
    OutputStreamWriter output;

    public PwdTest() {
        pwd = new Pwd();
        in = new PipedInputStream();
        stream = new ByteArrayOutputStream();
        output = new OutputStreamWriter(stream);
    }

    @Test
    public void testNormal() throws IOException {
        ArrayList<String> args = new ArrayList<>();
        pwd.exec(args, in, output);
        String appOutput = stream.toString().trim();
        assertEquals(Shell.getCurrentDirectory().toString(), appOutput);
    }

    @Test(expected = TooManyArgumentsException.class)
    public void testTooManyArguments() throws IOException {
        ArrayList<String> args = new ArrayList<>();
        args.add("foo");
        pwd.exec(args, in, output);
    }
}
