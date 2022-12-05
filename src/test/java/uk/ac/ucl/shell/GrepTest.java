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

public class GrepTest {
    public GrepTest() {}

    @Test
    public void testNoPattern() throws IOException {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);
        try {
            Shell.eval("grep", out);
        } catch(RuntimeException e) {
            assertEquals(e.toString(), "java.lang.RuntimeException: grep: invalid number of arguments");
        }

    }

    @Test
    public void testInvalidFile() throws IOException {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);
        try {
            Shell.eval("grep a test1.txt", out);
        } catch (RuntimeException e) {
            assertEquals(e.toString(), "java.lang.RuntimeException: grep: file does not exist " + Shell.getCurrentDirectory().toString() + "/test1.txt");
        }
    }

    @Test
    public void testCannotOpenFile() throws IOException {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);
        try (Scanner scn = new Scanner(in)) {
            Shell.eval("grep a test_files", out);
        } catch (RuntimeException e) {
            assertEquals(e.toString(), "java.lang.RuntimeException: grep: cannot open " + Shell.getCurrentDirectory().toString() + "/test_files");
        }
    }

    @Test
    public void testNormal() throws IOException {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);
        // save current directory path
        String originalDir = Shell.getCurrentDirectory().toString();
        // navigate to text files
        Shell.eval("cd test_files", out);
        // execute actual test command
        Shell.eval("grep e test1.txt", out);
        HashSet<String> expecteds = new HashSet<>(Arrays.asList("one", "three"));
        
        try(Scanner scn = new Scanner(in)){
            scn.useDelimiter("\n");
            for (int i = 0 ; i < expecteds.size(); i++) {
                assertTrue(expecteds.contains(scn.nextLine()));
            }
        }
        // return to original filepath
        Shell.eval("cd " + originalDir, out);

    }
}
