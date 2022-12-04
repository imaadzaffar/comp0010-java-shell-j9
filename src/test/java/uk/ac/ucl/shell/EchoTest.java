package uk.ac.ucl.shell;

import org.junit.Test;
import uk.ac.ucl.shell.applications.Echo;

import static org.junit.Assert.*;

import java.io.*;
import java.util.ArrayList;

public class EchoTest {
    Echo echo;
    InputStream in;
    ByteArrayOutputStream stream;
    OutputStreamWriter output;

    public EchoTest() {
        echo = new Echo();
        in = new PipedInputStream();
        stream = new ByteArrayOutputStream();
        output = new OutputStreamWriter(stream);
    }

    @Test
    public void testEmpty() throws IOException {
        ArrayList<String> args = new ArrayList<>();

        echo.exec(args, in, output);

        String expected = "";
        String appOutput = stream.toString().trim();

        assertEquals(expected, appOutput);
    }

    @Test
    public void testOneArg() throws IOException {
        ArrayList<String> args = new ArrayList<>();
        args.add("foo");

        echo.exec(args, in, output);

        String expected = "foo";
        String appOutput = stream.toString().trim();

        assertEquals(expected, appOutput);
    }

    @Test
    public void testMultipleArgs() throws IOException {
        ArrayList<String> args = new ArrayList<>();
        args.add("Hello");
        args.add("World");

        echo.exec(args, in, output);

        String expected = "Hello World";
        String appOutput = stream.toString().trim();

        assertEquals(expected, appOutput);
    }
}
