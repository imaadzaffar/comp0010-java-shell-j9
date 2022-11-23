package uk.ac.ucl.shell;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Scanner;

import org.junit.Test;

public class CatTest {
    public CatTest() {}

    // TODO: Standard input
    // @Test
    // public void testNoArgs() throws IOException {
    //     PipedInputStream in = new PipedInputStream();
    //     PipedOutputStream out;
    //     out = new PipedOutputStream(in);
    //     Shell.eval("cat", out);
    // }

    @Test(expected = RuntimeException.class)
    public void testNonExistentFile() throws IOException {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);
        Shell.eval("cat hello.txt", out);
    }

    // TODO: Cannot open file
    // @Test(expected = RuntimeException.class)
    // public void testFileError() throws IOException {
    //     PipedInputStream in = new PipedInputStream();
    //     PipedOutputStream out;
    //     out = new PipedOutputStream(in);
    //     Shell.eval("cat hello.txt", out);
    // }

    @Test
    public void testSingleArg() throws IOException {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);
        Shell.eval("cd test_files", out);
        Shell.eval("cat test1.txt", out);
        String[] expecteds = {"one", "two", "three"};
        try (Scanner scn = new Scanner(in)) {
            for (String expected : expecteds) {
                assertEquals(expected, scn.nextLine());
            }
        }
        Shell.eval("cd ..", out);
    }

    @Test
    public void testMultipleArg() throws IOException {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);
        Shell.eval("cd test_files", out);
        Shell.eval("cat test1.txt test2.txt test3.txt", out);
        String[] expecteds = {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};
        try (Scanner scn = new Scanner(in)) {
            for (String expected : expecteds) {
                assertEquals(expected, scn.nextLine());
            }
        }
        Shell.eval("cd ..", out);
    }
}
