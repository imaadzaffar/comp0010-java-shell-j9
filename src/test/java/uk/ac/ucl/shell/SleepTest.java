package uk.ac.ucl.shell;

import static org.junit.Assert.*;

import org.junit.Test;
import uk.ac.ucl.shell.applications.Sleep;
import uk.ac.ucl.shell.exceptions.InvalidArgumentsException;
import uk.ac.ucl.shell.exceptions.MissingArgumentsException;
import uk.ac.ucl.shell.exceptions.TooManyArgumentsException;

import java.io.*;
import java.util.ArrayList;

public class SleepTest {
    Sleep sleep;
    InputStream in;
    ByteArrayOutputStream stream;
    OutputStreamWriter output;

    public SleepTest() {
        sleep = new Sleep();
        in = new PipedInputStream();
        stream = new ByteArrayOutputStream();
        output = new OutputStreamWriter(stream);
    }

    @Test
    public void testNormal() throws IOException {
        ArrayList<String> args = new ArrayList<>();
        args.add("1");

        sleep.exec(args, in, output);

        String expected = System.getProperty("line.separator");
        String appOutput = stream.toString();

        assertEquals(expected, appOutput);
    }

    @Test(expected = MissingArgumentsException.class)
    public void testNoArgs() throws IOException {
        ArrayList<String> args = new ArrayList<>();

        sleep.exec(args, in, output);
    }

    @Test(expected = TooManyArgumentsException.class)
    public void testTooManyArguments() throws IOException {
        ArrayList<String> args = new ArrayList<>();
        args.add("1");
        args.add("2");

        sleep.exec(args, in, output);
    }

    @Test(expected = InvalidArgumentsException.class)
    public void testNegativeInteger() throws IOException {
        ArrayList<String> args = new ArrayList<>();
        args.add("-1");

        sleep.exec(args, in, output);
    }

    @Test(expected = InvalidArgumentsException.class)
    public void testNonInteger() throws IOException {
        ArrayList<String> args = new ArrayList<>();
        args.add("a");

        sleep.exec(args, in, output);
    }
}
