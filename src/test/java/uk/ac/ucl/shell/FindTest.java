package uk.ac.ucl.shell;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import uk.ac.ucl.shell.applications.Find;
import uk.ac.ucl.shell.exceptions.InvalidArgumentsException;
import uk.ac.ucl.shell.exceptions.MissingArgumentsException;
import uk.ac.ucl.shell.exceptions.NotExistingDirectoryException;
import uk.ac.ucl.shell.exceptions.TooManyArgumentsException;

import static org.junit.Assert.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;

public class FindTest {
    Find find;
    InputStream in;
    ByteArrayOutputStream stream;
    OutputStreamWriter output;
    String originalDir;

    Path testDir;
    Path testFile1;
    Path testFile2;

    public FindTest() {
        find = new Find();
        in = new PipedInputStream();
        stream = new ByteArrayOutputStream();
        output = new OutputStreamWriter(stream);
        originalDir = Shell.getCurrentDirectory().toString();
    }

    @Before
    public void createTestFiles() throws IOException {
        String dirName = "testDir";

        testDir = Shell.getCurrentDirectory().resolve(dirName);
        testFile1 = Shell.getCurrentDirectory().resolve(dirName).resolve("test1.txt");
        testFile2 = Shell.getCurrentDirectory().resolve(dirName).resolve("test2.txt");

        Files.createDirectories(testDir);
        Files.createFile(testFile1);
        Files.createFile(testFile2);
    }


    @Test
    public void testPattern() throws IOException {
        Shell.setCurrentDirectory(testDir.toString());
        ArrayList<String> args = new ArrayList<>();
        args.add("-name");
        args.add("test1.txt");

        find.exec(args, in, output);

        String expected = "./test1.txt";
        String appOutput = stream.toString().trim();

        Shell.setCurrentDirectory(originalDir);
        assertEquals(expected, appOutput);
    }

    @Test
    public void testPatternGlobbing() throws IOException {
        Shell.setCurrentDirectory(testDir.toString());
        ArrayList<String> args = new ArrayList<>();
        args.add("-name");
        args.add("*.txt");

        find.exec(args, in, output);

        String[] expected = {
                "./" + testFile1.getFileName().toString(),
                "./" + testFile2.getFileName().toString()
        };
        String[] appOutput = stream.toString().split("[\n\t]");
        Arrays.sort(expected);
        Arrays.sort(appOutput);

        Shell.setCurrentDirectory(originalDir);
        assertArrayEquals(expected, appOutput);
    }

    @Test
    public void testPatternWithStartingPath() throws IOException {
        Shell.setCurrentDirectory(testDir.toString());
        ArrayList<String> args = new ArrayList<>();
        args.add("-name");
        args.add("*.txt");

        find.exec(args, in, output);

        String[] expected = {
                "./" + testFile1.getFileName().toString(),
                "./" + testFile2.getFileName().toString()
        };
        String[] appOutput = stream.toString().split("[\n\t]");
        Arrays.sort(expected);
        Arrays.sort(appOutput);

        Shell.setCurrentDirectory(originalDir);
        assertArrayEquals(expected, appOutput);
    }

    @Test
    public void testPatternWithStartingPathGlobbing() throws IOException {
        ArrayList<String> args = new ArrayList<>();
        args.add(testDir.getFileName().toString());
        args.add("-name");
        args.add("*.txt");

        find.exec(args, in, output);

        String[] expected = {
                "testDir/" + testFile1.getFileName().toString(),
                "testDir/" + testFile2.getFileName().toString()
        };
        String[] appOutput = stream.toString().split("[\n\t]");
        Arrays.sort(expected);
        Arrays.sort(appOutput);

        Shell.setCurrentDirectory(originalDir);
        assertArrayEquals(expected, appOutput);
    }

    @Test(expected = MissingArgumentsException.class)
    public void testNoArgs() throws IOException {
        ArrayList<String> args = new ArrayList<>();

        find.exec(args, in, output);
    }

    @Test(expected = MissingArgumentsException.class)
    public void test1ArgumentNoNameFlag() throws IOException {ArrayList<String> args = new ArrayList<>();
        args.add("pattern");

        find.exec(args, in, output);
    }

    @Test(expected = InvalidArgumentsException.class)
    public void test2ArgumentsNoNameFlag() throws IOException {ArrayList<String> args = new ArrayList<>();
        args.add("path");
        args.add("pattern");

        find.exec(args, in, output);
    }

    @Test(expected = TooManyArgumentsException.class)
    public void testTooManyArguments() throws IOException {
        ArrayList<String> args = new ArrayList<>();
        args.add("One");
        args.add("Two");
        args.add("Three");
        args.add("Four");

        find.exec(args, in, output);
    }

    @Test(expected = NotExistingDirectoryException.class)
    public void testDirectoryDoesNotExist() throws IOException {
        ArrayList<String> args = new ArrayList<>();
        args.add("foo");
        args.add("-name");
        args.add("file");

        find.exec(args, in, output);
    }

    @After
    public void deleteTestFiles() throws IOException {
        Files.delete(testFile1);
        Files.delete(testFile2);
        Files.delete(testDir);
    }
}
