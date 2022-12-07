package uk.ac.ucl.shell;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import uk.ac.ucl.shell.applications.Grep;
import uk.ac.ucl.shell.exceptions.CannotOpenFileException;
import uk.ac.ucl.shell.exceptions.FileNotFoundException;
import uk.ac.ucl.shell.exceptions.MissingArgumentsException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import static org.junit.Assert.*;

public class GrepTest {
    Grep grep;
    InputStream in;
    ByteArrayOutputStream stream;
    OutputStreamWriter output;
    String originalDir;

    Path testDir;
    Path testFile1;
    Path testFile2;

    public GrepTest() {
        grep = new Grep();
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

        List<String> lines = List.of("one", "two", "three");
        List<String> lines2 = List.of("four", "five", "six");

        Files.createDirectories(testDir);
        Files.createFile(testFile1);
        Files.createFile(testFile2);

        try {
            Files.write(testFile1, lines, StandardCharsets.UTF_8);
            Files.write(testFile2, lines2, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test(expected = MissingArgumentsException.class)
    public void testNoPattern() throws IOException {
        ArrayList<String> args = new ArrayList<>();

        grep.exec(args, in, output);
    }

    @Test(expected = FileNotFoundException.class)
    public void testInvalidFile() throws IOException {
        ArrayList<String> args = new ArrayList<>();
        args.add("a");
        args.add(testFile1.getFileName().toString());

        grep.exec(args, in, output);
    }

    @Test(expected = CannotOpenFileException.class)
    public void testCannotOpenFile() throws IOException {
        ArrayList<String> args = new ArrayList<>();
        args.add("a");
        args.add(testDir.getFileName().toString());

        grep.exec(args, in, output);
    }

    @Test(expected = MissingArgumentsException.class)
    public void testStandardInputEmpty() throws IOException {
        ArrayList<String> args = new ArrayList<>();
        args.add("a");

        grep.exec(args, null, output);
    }

    @Test
    public void testStandardInput() throws IOException {
        List<String> args = new ArrayList<>();
        args.add("e");
        InputStream in = new ByteArrayInputStream("one\ntwo\nthree".getBytes(StandardCharsets.UTF_8));

        grep.exec(args, in, output);

        String[] expected = {"one", "three"};
        String[] appOutput = stream.toString().split(System.getProperty("line.separator"));

        assertArrayEquals(expected, appOutput);
    }

    @Test
    public void testMultipleFiles() throws IOException {
        Shell.setCurrentDirectory(testDir.toString());

        List<String> args = new ArrayList<>();
        args.add("e");
        args.add(testFile1.getFileName().toString());
        args.add(testFile2.getFileName().toString());

        grep.exec(args, in, output);

        String[] expected = {
                testFile1.getFileName() + ":one",
                testFile1.getFileName() + ":three",
                testFile2.getFileName() + ":five"
        };
        String[] appOutput = stream.toString().split(System.getProperty("line.separator"));

        Arrays.sort(expected);
        Arrays.sort(appOutput);

        Shell.setCurrentDirectory(originalDir);
        assertArrayEquals(expected, appOutput);
    }

    @Test
    public void testNormal() throws IOException {
        Shell.setCurrentDirectory(testDir.toString());

        ArrayList<String> args = new ArrayList<>();
        args.add("e");
        args.add(testFile1.getFileName().toString());

        grep.exec(args, in, output);

        String[] expected = {
                "one",
                "three"
        };
        String[] appOutput = stream.toString().split(System.getProperty("line.separator"));
        Arrays.sort(expected);
        Arrays.sort(appOutput);

        Shell.setCurrentDirectory(originalDir);
        assertArrayEquals(expected, appOutput);
    }

    @After
    public void deleteTestFiles() throws IOException {
        Files.delete(testFile1);
        Files.delete(testFile2);
        Files.delete(testDir);
    }
}
