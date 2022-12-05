package uk.ac.ucl.shell;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import uk.ac.ucl.shell.applications.Sort;
import uk.ac.ucl.shell.exceptions.*;
import uk.ac.ucl.shell.exceptions.FileNotFoundException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import static org.junit.Assert.assertEquals;

public class SortTest {
    Sort sort;
    InputStream in;
    ByteArrayOutputStream stream;
    OutputStreamWriter output;

    Path testDirHead;
    Path testFile1;

    public SortTest() {
        sort = new Sort();
        in = new PipedInputStream();
        stream = new ByteArrayOutputStream();
        output = new OutputStreamWriter(stream);
    }

    @Before
    public void createTestFiles() throws IOException {
        String dirName = "testDir";

        testDirHead = Shell.getCurrentDirectory().resolve(dirName);
        testFile1 = Shell.getCurrentDirectory().resolve(dirName).resolve("test1.txt");

        List<String> lines = List.of("c", "d", "a", "b", "z", "e");

        Files.createDirectories(testDirHead);
        Files.createFile(testFile1);

        try {
            Files.write(testFile1, lines, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testNormal() throws IOException {
        List<String> args = new ArrayList<>();
        args.add(testFile1.toString());

        sort.exec(args, in, output);

        String expected = "a\nb\nc\nd\ne\nz";
        String appOutput = stream.toString().trim();

        assertEquals(expected, appOutput);
    }

    @Test
    public void testNormalReverse() throws IOException {
        List<String> args = new ArrayList<>();
        args.add("-r");
        args.add(testFile1.toString());

        sort.exec(args, in, output);

        String expected = "z\ne\nd\nc\nb\na";
        String appOutput = stream.toString().trim();

        assertEquals(expected, appOutput);
    }

    @Test
    public void testStandardInput() throws IOException {
        List<String> args = new ArrayList<>();
        in = new ByteArrayInputStream("z\ne\nd\nc\nb\na".getBytes(StandardCharsets.UTF_8));

        sort.exec(args, in, output);

        String appOutput = stream.toString().trim();
        String expected = "a\nb\nc\nd\ne\nz";

        assertEquals(expected, appOutput);
    }

    @Test
    public void testStandardInputReverse() throws IOException {
        List<String> args = new ArrayList<>();
        args.add("-r");
        in = new ByteArrayInputStream("z\ne\nd\nc\nb\na".getBytes(StandardCharsets.UTF_8));

        sort.exec(args, in, output);

        String expected = "z\ne\nd\nc\nb\na";
        String appOutput = stream.toString().trim();

        assertEquals(expected, appOutput);
    }

    @Test(expected =  MissingArgumentsException.class)
    public void testNoArgs() throws IOException {
        List<String> args = new ArrayList<>();

        sort.exec(args, null, output);
    }

    @Test(expected = MissingArgumentsException.class)
    public void testJustReverseFlag() throws IOException {
        List<String> args = new ArrayList<>();
        args.add("-r");

        sort.exec(args, null, output);
    }

    @Test(expected = TooManyArgumentsException.class)
    public void testTooManyArgs() throws IOException {
        List<String> args = new ArrayList<>();
        args.add("One");
        args.add("Two");
        args.add("Three");

        sort.exec(args, in, output);
    }

    @Test(expected = CannotOpenFileException.class)
    public void testCannotOpenFile() throws IOException {
        List<String> args = new ArrayList<>();
        args.add(testDirHead.toString());

        sort.exec(args, in, output);
    }

    @Test(expected = FileNotFoundException.class)
    public void testFileDoesNotExist() throws IOException {
        List<String> args = new ArrayList<>();
        args.add("ThisFileDoesNotExist");

        sort.exec(args, in, output);
    }

    @After
    public void deleteTestFiles() throws IOException {
        Files.delete(testFile1);
        Files.delete(testDirHead);
    }
}
