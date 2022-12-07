package uk.ac.ucl.shell;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import uk.ac.ucl.shell.commands.Globbing;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class GlobbingTest {
    Globbing globbing;
    InputStream in;
    ByteArrayOutputStream stream;
    OutputStreamWriter output;
    String originalDir;

    Path testDirGlobbing;
    Path testFile1;
    Path testFile2;


    public GlobbingTest() {
        globbing = new Globbing();
        in = new PipedInputStream();
        stream = new ByteArrayOutputStream();
        output = new OutputStreamWriter(stream);
        originalDir = Shell.getCurrentDirectory().toString();
    }

    @Before
    public void createTestFiles() throws IOException {
        String dirName = "testDir";

        testDirGlobbing = Shell.getCurrentDirectory().resolve(dirName);
        testFile1 = Shell.getCurrentDirectory().resolve(dirName).resolve("test1.txt");
        testFile2 = Shell.getCurrentDirectory().resolve(dirName).resolve("test2.txt");

        Files.createDirectories(testDirGlobbing);
        Files.createFile(testFile1);
        Files.createFile(testFile2);
    }

    @Test
    public void testFile() throws IOException {
        Shell.setCurrentDirectory(testDirGlobbing.toString());
        ArrayList<String> args = new ArrayList<>();
        ArrayList<String> globOutput;
        args.add("*.txt");

        globOutput = globbing.glob(args, false);

        String [] expected = {"test1.txt", "test2.txt"};
        Shell.setCurrentDirectory(originalDir);

        assertArrayEquals(expected, globOutput.toArray());
    }

    @Test
    public void testFileInOtherDirectory() throws IOException {
        ArrayList<String> args = new ArrayList<>();
        args.add(testDirGlobbing.getFileName() + "/*.txt");
        ArrayList<String> globOutput;

        globOutput = globbing.glob(args, false);

        String [] expected = {"testDir/test1.txt", "testDir/test2.txt"};

        assertArrayEquals(expected, globOutput.toArray());
    }

    @Test
    public void testDirectory() throws IOException {
        ArrayList<String> args = new ArrayList<>();
        args.add("test*");
        ArrayList<String> globOutput;

        globOutput = globbing.glob(args, false);

        String [] expected = {"testDir"};

        assertArrayEquals(expected, globOutput.toArray());
    }

    @Test
    public void testNoArgs() throws IOException {
        ArrayList<String> args = new ArrayList<>();
        ArrayList<String> globOutput;

        globOutput = globbing.glob(args, false);

        String [] expected = {};

        assertArrayEquals(expected, globOutput.toArray());
    }

    @Test
    public void testNoGlobbableArgs() throws IOException {
        ArrayList<String> args = new ArrayList<>();
        args.add("One");
        args.add("Two");
        ArrayList<String> globOutput;

        globOutput = globbing.glob(args, false);

        String [] expected = {"One", "Two"};

        assertArrayEquals(expected, globOutput.toArray());
    }

    @Test
    public void testFindGlobbing() throws IOException {
        ArrayList<String> args = new ArrayList<>();
        args.add("-name");
        args.add("*.txt");
        ArrayList<String> globOutput;

        globOutput = globbing.glob(args, false);

        String [] expected = {"-name", "*.txt"};

        assertArrayEquals(expected, globOutput.toArray());
    }

    @Test
    public void testSingleQuoteArguments() throws IOException {
        ArrayList<String> args = new ArrayList<>();
        args.add("'abc'");
        args.add("\"'abc'\"");
        ArrayList<String> globOutput;

        globOutput = globbing.glob(args, false);

        String [] expected = {"abc", "'abc'"};

        assertArrayEquals(expected, globOutput.toArray());
    }

    @After
    public void deleteTestFiles() throws IOException {
        Files.delete(testFile1);
        Files.delete(testFile2);
        Files.delete(testDirGlobbing);
    }
}
