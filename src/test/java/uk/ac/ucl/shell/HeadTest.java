package uk.ac.ucl.shell;

import org.junit.Test;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class HeadTest {
    public HeadTest() {}

    @Test
    public void testNormal() throws IOException {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);
        // save original directory
        String originalDir = Shell.getCurrentDirectory().toString();
        // navigate to test files
        Shell.eval("cd test_files", out);
        // execute actual test command
        Shell.eval("head -n 2 test1.txt", out);
        HashSet<String> expecteds = new HashSet<>(Arrays.asList("one", "two"));
        Scanner scn = new Scanner(in);
        scn.useDelimiter("\n");
        for (int i = 0 ; i < expecteds.size(); i++) {
            assertTrue(expecteds.contains(scn.nextLine()));
        }
        // return to original filepath
        Shell.eval("cd " + originalDir, out);
    }

    @Test
    public void testThreeArguments() throws IOException {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);
        try {
            Shell.eval("head a b c", out);
        } catch (RuntimeException e) {
            assertEquals(e.toString(), "java.lang.RuntimeException: head: invalid arguments");
        }
    }

    @Test
    public void testWrongType() throws IOException {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);
        try {
            Shell.eval("head -n a", out);
        } catch (RuntimeException e) {
            assertEquals(e.toString(), "java.lang.RuntimeException: head: invalid arguments");
        }
    }

    @Test
    public void testInvalidFile() throws IOException {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);
        try {
            Shell.eval("head src", out);
        } catch (RuntimeException e) {
            assertEquals(e.toString(), "java.lang.RuntimeException: head: cannot open " + Shell.getCurrentDirectory().toString() + "/src");
        }
    }

    @Test
    public void testFileDoesntExist() throws IOException {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);
        try {
            Shell.eval("head abc", out);
        } catch (RuntimeException e) {
            assertEquals(e.toString(), "java.lang.RuntimeException: head: file does not exist");
        }
    }
}
