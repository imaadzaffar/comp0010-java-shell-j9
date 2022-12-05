package uk.ac.ucl.shell;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import uk.ac.ucl.shell.applications.Rm;
import uk.ac.ucl.shell.exceptions.FileNotFoundException;
import uk.ac.ucl.shell.exceptions.MissingArgumentsException;
import uk.ac.ucl.shell.exceptions.TooManyArgumentsException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class RmTest {
    Rm rm;
    InputStream in;
    ByteArrayOutputStream stream;
    OutputStreamWriter output;

    Path testDir;

    public RmTest() {
        rm = new Rm();
        in = new PipedInputStream();
        stream = new ByteArrayOutputStream();
        output = new OutputStreamWriter(stream);
    }

    @Before
    public void createTestFiles() throws IOException {
        String dirName = "testDir";

        testDir = Shell.getCurrentDirectory().resolve(dirName);

        Files.createDirectories(testDir);
    }

    @Test
    public void testRmDirectory() throws IOException {
        ArrayList<String> args = new ArrayList<>();
        args.add("testDir");

        rm.exec(args, in, output);

        assertFalse(Files.exists(testDir));
    }

    @Test(expected = FileNotFoundException.class)
    public void testFileDoesNotExist() throws IOException {
        ArrayList<String> args = new ArrayList<>();
        args.add("TestFile");

        rm.exec(args, in, output);
    }

    @Test(expected = MissingArgumentsException.class)
    public void testNoArgs() throws IOException {
        ArrayList<String> args = new ArrayList<>();

        rm.exec(args, in, output);
    }

    @Test(expected = TooManyArgumentsException.class)
    public void testTooManyArguments() throws IOException {
        ArrayList<String> args = new ArrayList<>();
        args.add("One");
        args.add("Two");

        rm.exec(args, in, output);
    }
}
