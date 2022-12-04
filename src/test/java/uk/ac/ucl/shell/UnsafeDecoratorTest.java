package uk.ac.ucl.shell;

import static org.junit.Assert.assertEquals;

import java.io.*;
import java.util.ArrayList;

import org.junit.Test;
import uk.ac.ucl.shell.applications.Cd;
import uk.ac.ucl.shell.applications.Echo;
import uk.ac.ucl.shell.applications.UnsafeDecorator;

public class UnsafeDecoratorTest {
    UnsafeDecorator unsafeDecorator;
    InputStream in;
    ByteArrayOutputStream stream;
    OutputStreamWriter output;

    public UnsafeDecoratorTest() {
        in = new PipedInputStream();
        stream = new ByteArrayOutputStream();
        output = new OutputStreamWriter(stream);
    }

    @Test
    public void testNoErrorsThrown() throws IOException {
        ArrayList<String> args = new ArrayList<>();
        unsafeDecorator = new UnsafeDecorator(new Echo());
        args.add("hello");

        unsafeDecorator.exec(args, in, output);

        String expected = "hello";
        String appOutput = stream.toString().trim();

        assertEquals(expected, appOutput);
    }

    @Test
    public void testErrorsThrown() throws IOException {
        ArrayList<String> args = new ArrayList<>();
        unsafeDecorator = new UnsafeDecorator(new Cd());

        unsafeDecorator.exec(args, in, output);

        String expected = "cd: missing arguments";
        String appOutput = stream.toString().trim();

        assertEquals(expected, appOutput);
    }
}
