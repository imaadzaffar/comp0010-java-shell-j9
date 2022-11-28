package uk.ac.ucl.shell;

import org.junit.Test;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

import static org.junit.Assert.assertTrue;

public class TailTest {
    public TailTest() {}

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
        Shell.eval("tail -n 2 test1.txt", out);
        HashSet<String> expecteds = new HashSet<>(Arrays.asList("two", "three"));
        Scanner scn = new Scanner(in);
        scn.useDelimiter("\n");
        for (int i = 0 ; i < expecteds.size(); i++) {
            assertTrue(expecteds.contains(scn.nextLine()));
        }
        // return to original filepath
        Shell.eval("cd " + originalDir, out);
    }
}
