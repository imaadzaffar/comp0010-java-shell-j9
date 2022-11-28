package uk.ac.ucl.shell;

import org.junit.Test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

public class LsTest {
    public LsTest() {}

    @Test
    public void testNoArgs() throws IOException {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);
        Shell.eval("ls", out);
        HashSet<String> expecteds = new HashSet<>(Arrays.asList("tools", "system_test", "Dockerfile", "target", "apps.svg", "pom.xml", "README.md", "sh", "test_files", "action.yml", "src"));
        Scanner scn = new Scanner(in);
        scn.useDelimiter("\t");
        for (int i = 0; i < expecteds.size(); i++) {
            assertTrue(expecteds.contains(scn.next()));
        }
        scn.close();
    }

    @Test
    public void testWithStartingPath() throws IOException {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);
        Shell.eval("ls test_files", out);
        HashSet<String> expecteds = new HashSet<>(Arrays.asList("test1.txt", "test2.txt", "test3.txt", "test4.txt"));
        Scanner scn = new Scanner(in);
        scn.useDelimiter("\t");
        for (int i = 0; i < expecteds.size(); i++) {
            assertTrue(expecteds.contains(scn.next()));
        }
        scn.close();
    }

    @Test(expected = RuntimeException.class)
    public void testWithNonExistentStartingPath() throws IOException {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);
        Shell.eval("ls hello", out);
    }

    @Test(expected = RuntimeException.class)
    public void testTooManyArguments() throws IOException {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);
        Shell.eval("ls one two", out);
    }
}
