package uk.ac.ucl.shell;

import org.junit.Test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

public class CdTest {
    public CdTest() {
    }

    @Test(expected = RuntimeException.class)
    public void testNoArgs() throws IOException {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);
        Shell.eval("cd", out);
        assertEquals("/", Shell.getCurrentDirectory());
    }

    public void testWithCurrentDirectory() throws IOException {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);
        Shell.eval("cd .", out);
        assertEquals(".", Shell.getCurrentDirectory());
    }

    @Test
    public void testWithStartingPath() throws IOException {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);
        Shell.eval("cd test_files", out);
        assertEquals("test_files", Shell.getCurrentDirectory().getFileName().toString());
        Shell.eval("cd ..", out);
    }

    @Test(expected = RuntimeException.class)
    public void testWithNonExistentStartingPath() throws IOException {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);
        Shell.eval("cd hello", out);
    }

    @Test(expected = RuntimeException.class)
    public void testTooManyArguments() throws IOException {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);
        Shell.eval("cd one two", out);
    }
}
