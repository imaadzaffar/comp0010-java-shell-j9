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

public class UniqTest {
    public UniqTest() {}

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
        Shell.eval("uniq test4.txt", out);
        HashSet<String> expecteds = new HashSet<>(Arrays.asList("A", "a", "b", "c", "d"));
        Scanner scn = new Scanner(in);
        scn.useDelimiter("\n");
        for (int i = 0 ; i < expecteds.size(); i++) {
            assertTrue(expecteds.contains(scn.nextLine()));
        }
        // return to original filepath
        Shell.eval("cd " + originalDir, out);
    }

    @Test
    public void testNormalInsensitive() throws IOException{
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);
        // save original directory
        String originalDir = Shell.getCurrentDirectory().toString();
        // navigate to test files
        Shell.eval("cd test_files", out);
        // execute actual test command
        Shell.eval("uniq -i test4.txt", out);
        HashSet<String> expecteds = new HashSet<>(Arrays.asList("a", "b", "c", "d"));
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
            Shell.eval("uniq a b c", out);
        } catch(RuntimeException e) {
            assertEquals(e.toString(), "java.lang.RuntimeException: uniq: invalid arguments");
        }
    }

    @Test
    public void testFileDoesntOpen() throws IOException {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);
        try {
            Shell.eval("uniq src", out);
        } catch (RuntimeException e) {
            assertEquals(e.toString(), "java.lang.RuntimeException: uniq: cannot open " + Shell.getCurrentDirectory().toString() + "/src");
        }
    }

    @Test
    public void testFileDoesntExist() throws IOException {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);
        try {
            Shell.eval("uniq abc", out);
        } catch (RuntimeException e) {
            assertEquals(e.toString(), "java.lang.RuntimeException: uniq: file does not exist");
        }
    }
}
