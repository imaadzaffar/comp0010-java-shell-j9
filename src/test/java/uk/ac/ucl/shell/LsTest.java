package uk.ac.ucl.shell;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import uk.ac.ucl.shell.applications.Cd;
import uk.ac.ucl.shell.applications.Ls;
import uk.ac.ucl.shell.exceptions.FileNotFoundException;
import uk.ac.ucl.shell.exceptions.NotExistingDirectoryException;
import uk.ac.ucl.shell.exceptions.TooManyArgumentsException;

import static org.junit.Assert.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class LsTest {
    Ls ls;
    InputStream in;
    ByteArrayOutputStream stream;
    OutputStreamWriter output;

    Path testDirLs;
    Path testDirEmpty;
    Path testFile1;
    Path testFile2;

    public LsTest() {
        ls = new Ls();
        in = new PipedInputStream();
        stream = new ByteArrayOutputStream();
        output = new OutputStreamWriter(stream);
    }

    @Before
    public void createTestFiles() throws IOException {
        String dirName = "test_dir_ls";
        String dirEmptyName = "test_dir_empty";
        testDirEmpty = Paths.get(System.getProperty("user.dir"), dirEmptyName);
        testDirLs = Paths.get(System.getProperty("user.dir"), dirName);
        testFile1 = Paths.get(System.getProperty("user.dir"), dirName, "test1.txt");
        testFile2 = Paths.get(System.getProperty("user.dir"), dirName, "test2.txt");
        Files.createDirectories(testDirLs);
        Files.createDirectories(testDirEmpty);
        Files.createFile(testFile1);
        Files.createFile(testFile2);
    }

    @Test
    public void testNoArgs() throws IOException {
        Shell.setCurrentDirectory(testDirLs.toString());
        ArrayList<String> args = new ArrayList<>();

        ls.exec(args, in, output);
        String appOutput = stream.toString().trim();

        String[] expecteds = {testFile1.getFileName().toString(), testFile2.getFileName().toString()};
        String[] actuals = appOutput.split("[\n\t]");
        Arrays.sort(expecteds);
        Arrays.sort(actuals);

        assertArrayEquals(expecteds, actuals);
    }

    @Test
    public void testStartingPath() throws IOException {
        ArrayList<String> args = new ArrayList<>();
        args.add(testDirLs.toString());
        ls.exec(args, in, output);
        String appOutput = stream.toString().trim();
        String[] expecteds = {testFile1.getFileName().toString(), testFile2.getFileName().toString()};
        String[] actuals = appOutput.split("[\n\t]");
        Arrays.sort(expecteds);
        Arrays.sort(actuals);

        assertArrayEquals(expecteds, actuals);
    }

    @Test(expected = NotExistingDirectoryException.class)
    public void testNotExistingStartingPath() throws IOException {
        ArrayList<String> args = new ArrayList<>();
        args.add("foo");
        ls.exec(args, in, output);
    }

    @Test(expected = TooManyArgumentsException.class)
    public void testTooManyArguments() throws IOException {
        ArrayList<String> args = new ArrayList<>();
        args.add("one");
        args.add("two");
        ls.exec(args, in, output);
    }

    @After
    public void deleteTestFiles() throws IOException {
        Files.delete(testFile1);
        Files.delete(testFile2);
        Files.delete(testDirLs);
        Files.delete(testDirEmpty);
    }
}
