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
import java.nio.file.Paths;
import java.util.ArrayList;

public class CdTest {
    Cd cd;
    InputStream in;
    ByteArrayOutputStream stream;
    OutputStreamWriter output;

    Path testDirCd;

    public CdTest() {
        cd = new Cd();
        in = new PipedInputStream();
        stream = new ByteArrayOutputStream();
        output = new OutputStreamWriter(stream);
    }

    @Before
    public void createTestFiles() throws IOException {
        String dirName = "test_dir_cd";
        testDirCd = Paths.get(System.getProperty("user.dir"), dirName);
        Files.createDirectories(testDirCd);
    }

    @Test(expected = MissingArgumentsException.class)
    public void testNoArgs() throws IOException {
        ArrayList<String> args = new ArrayList<>();
        cd.exec(args, in, output);
    }

    @Test
    public void testStartingPath() throws IOException {
        ArrayList<String> args = new ArrayList<>();
        args.add(testDirCd.getFileName().toString());
        cd.exec(args, in, output);
        String expected = System.getProperty("user.dir") + File.separator + testDirCd.getFileName().toString();
        String currentDirectory = Shell.getCurrentDirectory().toString();
        assertEquals(expected, currentDirectory);
        Shell.setCurrentDirectory(Paths.get(currentDirectory).getParent().toString());
    }

    @Test(expected = NotExistingDirectoryException.class)
    public void testNotExistingStartingPath() throws IOException {
        ArrayList<String> args = new ArrayList<>();
        args.add("foo");
        cd.exec(args, in, output);
    }

    @Test(expected = TooManyArgumentsException.class)
    public void testTooManyArguments() throws IOException {
        ArrayList<String> args = new ArrayList<>();
        args.add("one");
        args.add("two");
        cd.exec(args, in, output);
    }

    @After
    public void deleteTestFiles() throws IOException {
        Files.delete(testDirCd);
    }
}
