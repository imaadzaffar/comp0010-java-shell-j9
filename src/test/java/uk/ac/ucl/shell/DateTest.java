package uk.ac.ucl.shell;

import static org.junit.Assert.*;

import org.junit.Test;
import uk.ac.ucl.shell.applications.Date;
import uk.ac.ucl.shell.exceptions.TooManyArgumentsException;

import java.io.*;
import java.util.ArrayList;

public class DateTest {
    Date date;
    InputStream in;
    ByteArrayOutputStream stream;
    OutputStreamWriter output;

    public DateTest() {
        date = new Date();
        in = new PipedInputStream();
        stream = new ByteArrayOutputStream();
        output = new OutputStreamWriter(stream);
    }

    @Test
    public void testNormal() throws IOException {
        ArrayList<String> args = new ArrayList<>();

        date.exec(args, in, output);

        String expected = new java.util.Date().toString();
        String appOutput = stream.toString().trim();

        assertEquals(expected, appOutput);
    }

    @Test(expected = TooManyArgumentsException.class)
    public void testTooManyArguments() throws IOException {
        ArrayList<String> args = new ArrayList<>();
        args.add("One");

        date.exec(args, in, output);
    }
}
