package uk.ac.ucl.shell;

import org.junit.Test;

import java.io.*;
import java.util.Scanner;

import static org.junit.Assert.*;

public class ShellTest {
    PipedInputStream in;
    PipedOutputStream out;


    public ShellTest() throws IOException {
        in = new PipedInputStream();
        out = new PipedOutputStream(in);
    }

    @Test
    public void testOneCommand() throws IOException {
        String cmdline = "echo a";

        Shell.eval(cmdline, out);

        String [] expected = {"a"};

        Scanner scn = new Scanner(in);
        scn.useDelimiter(System.getProperty("line.separator"));
        for (String s : expected) {
            assertEquals(s, scn.nextLine());
        }
    }

    @Test
    public void testTwoSequencedCommands() throws IOException {
        String cmdline = "echo a;echo b";

        Shell.eval(cmdline, out);

        String [] expected = {"a", "b"};

        Scanner scn = new Scanner(in);
        scn.useDelimiter(System.getProperty("line.separator"));
        for (String s : expected) {
            assertEquals(s, scn.nextLine());
        }
    }

    @Test
    public void testTwoPipedCommands() throws IOException {
        String cmdline = "echo | echo a";

        Shell.eval(cmdline, out);

        String [] expected = {"a"};

        Scanner scn = new Scanner(in);
        scn.useDelimiter(System.getProperty("line.separator"));
        for (String s : expected) {
            assertEquals(s, scn.nextLine());
        }
    }
}
