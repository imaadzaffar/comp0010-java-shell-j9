package uk.ac.ucl.shell;

import org.junit.Test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

public class FindTest {
    public FindTest() {}

    @Test(expected = RuntimeException.class)
    public void testNoArgs() throws IOException {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);
        Shell.eval("find", out);
    }

    @Test(expected = RuntimeException.class)
    public void test1ArgumentNoNameFlag() throws IOException {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);
        Shell.eval("find pattern", out);
    }

    @Test(expected = RuntimeException.class)
    public void test2ArgumentsNoNameFlag() throws IOException {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);
        Shell.eval("find path pattern", out);
    }

    @Test(expected = RuntimeException.class)
    public void testTooManyArguments() throws IOException {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);
        Shell.eval("find one two three four", out);
    }

    @Test(expected = RuntimeException.class)
    public void testNonExistentDirectory() throws IOException {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);
        Shell.eval("find hello -name sh", out);
    }

    @Test
    public void testPattern() throws IOException {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);
        Shell.eval("find -name sh", out);
        Scanner scn = new Scanner(in);
        assertEquals("./sh", scn.nextLine());
    }

    @Test
    public void testPatternGlobbing() throws IOException {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);
        Shell.eval("find -name *.xml", out);
        try (Scanner scn = new Scanner(in)) {
            assertEquals("./pom.xml", scn.nextLine());
        }
    }

    @Test
    public void testPatternWithStartingPath() throws IOException {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);
        Shell.eval("find test_files -name test1.txt", out);
        Scanner scn = new Scanner(in);
        assertEquals("test_files/test1.txt", scn.nextLine());
        scn.close();
    }

    @Test
    public void testPatternWithStartingPathGlobbing() throws IOException {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);
        Shell.eval("find test_files -name *.txt", out);
        HashSet<String> expecteds = new HashSet<>(Arrays.asList("test_files/test1.txt", "test_files/test2.txt", "test_files/test3.txt", "test_files/test4.txt"));
        Scanner scn = new Scanner(in);
        for (int i = 0; i < expecteds.size(); i++) {
            assertTrue(expecteds.contains(scn.nextLine()));
        }
        scn.close();
    }
}
