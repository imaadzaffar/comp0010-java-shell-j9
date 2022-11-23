package uk.ac.ucl.shell;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Scanner;

import org.junit.Test;

public class UnsafeDecoratorTest {
    public UnsafeDecoratorTest() {}

    @Test
    public void testNoErrorsThrown() throws IOException {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);
        Shell.eval("_echo hello", out);
        try (Scanner scn = new Scanner(in)) {
            assertEquals("hello", scn.nextLine());
        }
    }

    @Test
    public void testErrorsThrown() throws IOException {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);
        Shell.eval("_cd hello", out);
        try (Scanner scn = new Scanner(in)) {
            assertEquals("cd: hello is not an existing directory", scn.nextLine());
        }
    }
}
