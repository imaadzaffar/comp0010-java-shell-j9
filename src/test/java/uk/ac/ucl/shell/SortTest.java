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

public class SortTest {
    public SortTest() {}

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
        Shell.eval("sort test1.txt", out);
        HashSet<String> expecteds = new HashSet<>(Arrays.asList("one", "three", "two"));
        Scanner scn = new Scanner(in);
        scn.useDelimiter("\n");
        for (int i = 0 ; i < expecteds.size(); i++) {
            assertTrue(expecteds.contains(scn.nextLine()));
        }
        // return to original filepath
        Shell.eval("cd " + originalDir, out);
    }

    @Test
    public void testReverseNormal() throws IOException {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);
        // save original directory
        String originalDir = Shell.getCurrentDirectory().toString();
        // navigate to test files
        Shell.eval("cd test_files", out);
        // execute actual test command
        Shell.eval("sort -r test1.txt", out);
        HashSet<String> expecteds = new HashSet<>(Arrays.asList("two", "three", "one"));
        Scanner scn = new Scanner(in);
        scn.useDelimiter("\n");
        for (int i = 0 ; i < expecteds.size(); i++) {
            assertTrue(expecteds.contains(scn.nextLine()));
        }
        // return to original filepath
        Shell.eval("cd " + originalDir, out);
    }

    @Test
    public void testInvalidArguments() throws IOException {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);
        try {
            Shell.eval("sort a b c", out);
        } catch(RuntimeException e) {
            assertEquals(e.toString(), "java.lang.RuntimeException: sort: invalid arguments");
        }
    }

    @Test
    public void testFileDoesntExist() throws IOException {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);
        try {
            Shell.eval("sort -r b", out);
        } catch (RuntimeException e) {
            assertEquals(e.toString(), "java.lang.RuntimeException: sort: file does not exist");
        }
    }

    @Test
    public void testFileDoesntOpen() throws IOException {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);
        try {
            Shell.eval("sort src", out);
        } catch (RuntimeException e) {
            assertEquals(e.toString(), "java.lang.RuntimeException: sort: cannot open " + Shell.getCurrentDirectory().toString() + "/src");
        }
    }
}
