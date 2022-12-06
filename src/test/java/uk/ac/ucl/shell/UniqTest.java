package uk.ac.ucl.shell;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import uk.ac.ucl.shell.applications.Uniq;
import uk.ac.ucl.shell.exceptions.CannotOpenFileException;
import uk.ac.ucl.shell.exceptions.FileNotFoundException;
import uk.ac.ucl.shell.exceptions.MissingArgumentsException;
import uk.ac.ucl.shell.exceptions.TooManyArgumentsException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class UniqTest {
    Uniq uniq;
    InputStream in;
    ByteArrayOutputStream stream;
    OutputStreamWriter output;

    Path testDirHead;
    Path testFile1;

    public UniqTest() {
        uniq = new Uniq();
        in = new PipedInputStream();
        stream = new ByteArrayOutputStream();
        output = new OutputStreamWriter(stream);
    }

    @Before
    public void createTestFiles() throws IOException {
        String dirName = "testDir";

        testDirHead = Shell.getCurrentDirectory().resolve(dirName);
        testFile1 = Shell.getCurrentDirectory().resolve(dirName).resolve("test1.txt");

        List<String> lines = List.of("a", "A", "a", "b", "b", "b", "C", "c", "D");

        Files.createDirectories(testDirHead);
        Files.createFile(testFile1);

        try {
            Files.write(testFile1, lines, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testStandardInput() throws IOException {
        List<String> args = new ArrayList<>();
        in = new ByteArrayInputStream("a\nA\na\na\nb\nb\nc\nd\nD".getBytes(StandardCharsets.UTF_8));

        uniq.exec(args, in, output);

        String[] expected = {"a", "A", "a", "b", "c", "d", "D"};
        String[] appOutput = stream.toString().split(System.getProperty("line.separator"));

        assertArrayEquals(expected, appOutput);
    }

    @Test
    public void testStandardInputCaseInsensitive() throws IOException {
        List<String> args = new ArrayList<>();
        args.add("-i");
        in = new ByteArrayInputStream("a\nA\na\na\nb\nb\nc\nd\nD".getBytes(StandardCharsets.UTF_8));

        uniq.exec(args, in, output);

        String[] expected = {"a", "b", "c", "d"};
        String[] appOutput = stream.toString().split(System.getProperty("line.separator"));

        assertArrayEquals(expected, appOutput);
    }

    @Test
    public void testNormal() throws IOException {
        List<String> args = new ArrayList<>();
        args.add(testFile1.toString());

        uniq.exec(args, in, output);

        String[] expected = {"a", "A", "a", "b", "C", "c", "D"};
        String[] appOutput = stream.toString().split(System.getProperty("line.separator"));

        assertArrayEquals(expected, appOutput);
    }

    @Test
    public void testNormalCaseInsensitive() throws IOException {
        List<String> args = new ArrayList<>();
        args.add("-i");
        args.add(testFile1.toString());

        uniq.exec(args, in, output);

        String[] expected = {"a", "b", "C", "D"};
        String[] appOutput = stream.toString().split(System.getProperty("line.separator"));

        assertArrayEquals(expected, appOutput);
    }

    @Test(expected = MissingArgumentsException.class)
    public void testNoArgs() throws IOException {
        List<String> args = new ArrayList<>();

        uniq.exec(args, null, output);
    }

    @Test(expected = MissingArgumentsException.class)
    public void testJustCaseInsensitiveFlag() throws IOException {
        List<String> args = new ArrayList<>();
        args.add("-i");

        uniq.exec(args, null, output);
    }

    @Test(expected = TooManyArgumentsException.class)
    public void testTooManyArgs() throws IOException {
        List<String> args = new ArrayList<>();
        args.add("One");
        args.add("Two");
        args.add("Three");

        uniq.exec(args, in, output);
    }

    @Test(expected = CannotOpenFileException.class)
    public void testCannotOpenFile() throws IOException {
        List<String> args = new ArrayList<>();
        args.add(testDirHead.toString());

        uniq.exec(args, in, output);
    }

    @Test(expected = FileNotFoundException.class)
    public void testFileDoesNotExist() throws IOException {
        List<String> args = new ArrayList<>();
        args.add("ThisFileDoesNotExist");

        uniq.exec(args, in, output);
    }

    @After
    public void deleteTestFiles() throws IOException {
        Files.delete(testFile1);
        Files.delete(testDirHead);
    }
}
