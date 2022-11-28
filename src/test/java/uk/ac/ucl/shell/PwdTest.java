package uk.ac.ucl.shell;

import org.junit.Test;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;

public class PwdTest {
    public PwdTest() {}

    @Test
    public void testPwd() throws IOException {
        PipedInputStream in = new PipedInputStream();
        PipedOutputStream out;
        out = new PipedOutputStream(in);
        Shell.eval("pwd", out);
        try (Scanner scn = new Scanner(in)) {
            assertEquals(Shell.getCurrentDirectory().toString(), scn.nextLine());
        }

    }
}
