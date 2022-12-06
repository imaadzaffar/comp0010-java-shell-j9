package uk.ac.ucl.shell;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import uk.ac.ucl.shell.applications.Ls;
import uk.ac.ucl.shell.exceptions.NotExistingDirectoryException;
import uk.ac.ucl.shell.exceptions.TooManyArgumentsException;

import static org.junit.Assert.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
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
    Path testFile3;

    public LsTest() {
        ls = new Ls();
        in = new PipedInputStream();
        stream = new ByteArrayOutputStream();
        output = new OutputStreamWriter(stream);
    }

    @Before
    public void createTestFiles() throws IOException {
        String dirName = "testDir";
        String dirEmptyName = "testDirEmpty";

        testDirEmpty = Shell.getCurrentDirectory().resolve(dirEmptyName);
        testDirLs = Shell.getCurrentDirectory().resolve(dirName);
        testFile1 = Shell.getCurrentDirectory().resolve(dirName).resolve("test1.txt");
        testFile2 = Shell.getCurrentDirectory().resolve(dirName).resolve("test2.txt");
        testFile3 =  Shell.getCurrentDirectory().resolve(dirName).resolve(".test.txt");

        Files.createDirectories(testDirLs);
        Files.createDirectories(testDirEmpty);
        Files.createFile(testFile1);
        Files.createFile(testFile2);
        Files.createFile(testFile3);
    }

    @Test
    public void testNoArgs() throws IOException {
        String originalDir = Shell.getCurrentDirectory().toString();
        Shell.setCurrentDirectory(testDirLs.toString());

        ArrayList<String> args = new ArrayList<>();

        ls.exec(args, in, output);

        String[] expected = {testFile1.getFileName().toString(), testFile2.getFileName().toString()};
        String [] appOutput = stream.toString().trim().split("\t");

        Arrays.sort(expected);
        Arrays.sort(appOutput);

        Shell.setCurrentDirectory(originalDir);
        assertArrayEquals(expected, appOutput);
    }

    @Test
    public void testStartingPath() throws IOException {
        ArrayList<String> args = new ArrayList<>();
        args.add(testDirLs.toString());

        ls.exec(args, in, output);

        String[] expected = {testFile1.getFileName().toString(), testFile2.getFileName().toString()};
        String[] appOutput = stream.toString().trim().split("\t");
        Arrays.sort(expected);
        Arrays.sort(appOutput);

        assertArrayEquals(expected, appOutput);
    }

    @Test(expected = NotExistingDirectoryException.class)
    public void testNotExistingStartingPath() throws IOException {
        ArrayList<String> args = new ArrayList<>();
        args.add("ThisDirectoryDoesNotExist");

        ls.exec(args, in, output);
    }

    @Test(expected = TooManyArgumentsException.class)
    public void testTooManyArguments() throws IOException {
        ArrayList<String> args = new ArrayList<>();
        args.add("One");
        args.add("Two");

        ls.exec(args, in, output);
    }

    @After
    public void deleteTestFiles() throws IOException {
        Files.delete(testFile1);
        Files.delete(testFile2);
        Files.delete(testFile3);
        Files.delete(testDirEmpty);
        Files.delete(testDirLs);
    }
}
