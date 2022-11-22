package uk.ac.ucl.shell;

import org.junit.Test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Scanner;

public class EchoTest {
    public EchoTest() {
    }

    @Test
    public void testEmpty() throws IOException {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);
        Shell.eval("echo", out);
        try (Scanner scn = new Scanner(in)) {
            assertEquals("", scn.nextLine());
        }
    }

    @Test
    public void testNormal() throws IOException {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);
        Shell.eval("echo foo", out);
        try (Scanner scn = new Scanner(in)) {
            assertEquals("foo", scn.nextLine());
        }
    }

    @Test
    public void testQuoted() throws IOException {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);
        Shell.eval("echo \"foo\"", out);
        try (Scanner scn = new Scanner(in)) {
            assertEquals("foo", scn.nextLine());
        }
    }

    @Test
    public void testMultipleArgs() throws IOException {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);
        Shell.eval("echo Hello World", out);
        try (Scanner scn = new Scanner(in)) {
            assertEquals("Hello World", scn.nextLine());
        }
    }
}
