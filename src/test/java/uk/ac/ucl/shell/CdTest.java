package uk.ac.ucl.shell;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import uk.ac.ucl.shell.applications.Cd;
import uk.ac.ucl.shell.exceptions.MissingArgumentsException;
import uk.ac.ucl.shell.exceptions.NotExistingDirectoryException;
import uk.ac.ucl.shell.exceptions.TooManyArgumentsException;

import static org.junit.Assert.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CdTest {
    Cd cd;
    InputStream in;
    ByteArrayOutputStream stream;
    OutputStreamWriter output;
    String originalDir;

    Path testDirCd;
    Path testFile1;

    public CdTest() {
        cd = new Cd();
        in = new PipedInputStream();
        stream = new ByteArrayOutputStream();
        output = new OutputStreamWriter(stream);
        originalDir = Shell.getCurrentDirectory().toString();
    }

    @Before
    public void createTestFiles() throws IOException {
        String dirName = "testDir";

        testDirCd = Shell.getCurrentDirectory().resolve(dirName);
        testFile1 = Shell.getCurrentDirectory().resolve(dirName).resolve("test.txt");

        Files.createDirectories(testDirCd);
        Files.createFile(testFile1);
    }

    @Test
    public void testStartingPath() throws IOException {
        ArrayList<String> args = new ArrayList<>();
        args.add(testDirCd.getFileName().toString());
        String expected = Shell.getCurrentDirectory().resolve(testDirCd.getFileName().toString()).toString();

        cd.exec(args, in, output);

        String currentDirectory = Shell.getCurrentDirectory().toString();
        Shell.setCurrentDirectory(originalDir);
        assertEquals(expected, currentDirectory);
    }

    @Test(expected = MissingArgumentsException.class)
    public void testNoArgs() throws IOException {
        List<String> args = new ArrayList<>();

        cd.exec(args, in, output);
    }

    @Test(expected = NotExistingDirectoryException.class)
    public void testDirectoryDoesNotExist() throws IOException {
        List<String> args = new ArrayList<>();
        args.add(testFile1.toString());

        cd.exec(args, in, output);
    }

    @Test(expected = NotExistingDirectoryException.class)
    public void testNotExistingStartingPath() throws IOException {
        ArrayList<String> args = new ArrayList<>();
        args.add("ThisDirectoryDoesNotExist");

        cd.exec(args, in, output);
    }

    @Test(expected = TooManyArgumentsException.class)
    public void testTooManyArguments() throws IOException {
        ArrayList<String> args = new ArrayList<>();
        args.add("One");
        args.add("Two");

        cd.exec(args, in, output);
    }

    @After
    public void deleteTestFiles() throws IOException {
        Files.delete(testFile1);
        Files.delete(testDirCd);
    }
}
