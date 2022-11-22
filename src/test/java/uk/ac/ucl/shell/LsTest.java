package uk.ac.ucl.shell;

import org.junit.Test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class LsTest {
    public LsTest() {
    }

    @Test
    public void test() throws IOException {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);
        Shell.eval("ls", out);
        ArrayList<String> expecteds = new ArrayList<>(Arrays.asList("tools", "system_test", "Dockerfile", "target", "apps.svg", "pom.xml", "README.md", "sh", "test_files", "action.yml", "src"));
        // ArrayList<String> actual = new ArrayList<>();
        Scanner scn = new Scanner(in);
        scn.useDelimiter("\t");

        for (String expected : expecteds) {
            assertEquals(expected, scn.next());
        }

        scn.close();
    }

    @Test
    public void testWithStartingPath() throws IOException {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);
        Shell.eval("ls test_files", out);
        ArrayList<String> expecteds = new ArrayList<>(Arrays.asList("test1.txt", "test2.txt", "test3.txt"));
        Scanner scn = new Scanner(in);
        scn.useDelimiter("\t");

        for (String expected : expecteds) {
            assertEquals(expected, scn.next());
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
}
