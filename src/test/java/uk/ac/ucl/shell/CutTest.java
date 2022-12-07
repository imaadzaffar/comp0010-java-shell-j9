package uk.ac.ucl.shell;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import uk.ac.ucl.shell.applications.Cut;
import uk.ac.ucl.shell.exceptions.CannotOpenFileException;
import uk.ac.ucl.shell.exceptions.FileNotFoundException;
import uk.ac.ucl.shell.exceptions.InvalidArgumentsException;
import uk.ac.ucl.shell.exceptions.MissingArgumentsException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class CutTest {
    Cut cut;
    InputStream in;
    ByteArrayOutputStream stream;
    OutputStreamWriter output;

    Path testDirCut;
    Path testFile1;

    public CutTest() {
        cut = new Cut();
        in = new PipedInputStream();
        stream = new ByteArrayOutputStream();
        output = new OutputStreamWriter(stream);
    }

    @Before
    public void createTestFiles() throws IOException {
        String dirName = "testDir";

        testDirCut = Shell.getCurrentDirectory().resolve(dirName);
        testFile1 = testDirCut.resolve("test1.txt");

        List<String> lines = List.of("abcde", "fghij", "klmno");

        Files.createDirectories(testDirCut);
        Files.createFile(testFile1);

        try {
            Files.write(testFile1, lines, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testStandardInputSingleByte() throws IOException {
        List<String> args = new ArrayList<>();
        args.add("-b");
        args.add("1");
        in = new ByteArrayInputStream("abcde\nfghij\nklmno".getBytes(StandardCharsets.UTF_8));

        cut.exec(args, in, output);

        String[] expected = {"a", "f", "k"};
        String[] appOutput = stream.toString().split(System.getProperty("line.separator"));

        assertArrayEquals(expected, appOutput);
    }

    @Test
    public void testStandardInputRangeOfBytes() throws IOException {
        List<String> args = new ArrayList<>();
        args.add("-b");
        args.add("2-3");
        in = new ByteArrayInputStream("abcde\nfghij\nklmno".getBytes(StandardCharsets.UTF_8));

        cut.exec(args, in, output);

        String[] expected = {"bc", "gh", "lm"};
        String[] appOutput = stream.toString().split(System.getProperty("line.separator"));

        assertArrayEquals(expected, appOutput);
    }

    @Test
    public void testStandardInputLeftToRight() throws IOException {
        List<String> args = new ArrayList<>();
        args.add("-b");
        args.add("3-");
        in = new ByteArrayInputStream("abcde\nfghij\nklmno".getBytes(StandardCharsets.UTF_8));

        cut.exec(args, in, output);

        String[] expected = {"cde", "hij", "mno"};
        String[] appOutput = stream.toString().split(System.getProperty("line.separator"));

        assertArrayEquals(expected, appOutput);
    }

    @Test
    public void testStandardInputRightToLeft() throws IOException {
        List<String> args = new ArrayList<>();
        args.add("-b");
        args.add("-3");
        in = new ByteArrayInputStream("abcde\nfghij\nklmno".getBytes(StandardCharsets.UTF_8));

        cut.exec(args, in, output);

        String[] expected = {"abc", "fgh", "klm"};
        String[] appOutput = stream.toString().split(System.getProperty("line.separator"));

        assertArrayEquals(expected, appOutput);
    }

    @Test(expected = MissingArgumentsException.class)
    public void testStandardInputEmpty() throws IOException {
        List<String> args = new ArrayList<>();
        args.add("-b");
        args.add("1");

        cut.exec(args, null, output);
    }

    @Test(expected = InvalidArgumentsException.class)
    public void testSingleByteIndexZero() throws IOException {
        List<String> args = new ArrayList<>();
        args.add("-b");
        args.add("0");
        args.add(testFile1.toString());

        cut.exec(args, in, output);
    }

    @Test(expected = InvalidArgumentsException.class)
    public void testRangeStartingFromZero() throws IOException {
        List<String> args = new ArrayList<>();
        args.add("-b");
        args.add("0-10");
        args.add(testFile1.toString());

        cut.exec(args, in, output);
    }

    @Test(expected = InvalidArgumentsException.class)
    public void testLeftToRightFromZero() throws IOException {
        List<String> args = new ArrayList<>();
        args.add("-b");
        args.add("0-");
        args.add(testFile1.toString());

        cut.exec(args, in, output);
    }

    @Test
    public void testSingleByte() throws IOException {
        List<String> args = new ArrayList<>();
        args.add("-b");
        args.add("1");
        args.add(testFile1.toString());

        cut.exec(args, in, output);

        String[] expected = {"a", "f", "k"};
        String[] appOutput = stream.toString().split(System.getProperty("line.separator"));

        assertArrayEquals(expected, appOutput);
    }

    @Test
    public void testOutOfRangeOfByte() throws IOException {
        List<String> args = new ArrayList<>();
        args.add("-b");
        args.add("6");
        args.add(testFile1.toString());

        cut.exec(args, in, output);

        String expected = "";
        String appOutput = stream.toString().trim();
        assertEquals(expected, appOutput);
    }

    @Test
    public void testInRangeBytes() throws IOException {
        List<String> args = new ArrayList<>();
        args.add("-b");
        args.add("1-3");
        args.add(testFile1.toString());

        cut.exec(args, in, output);

        String[] expected = {"abc", "fgh", "klm"};
        String[] appOutput = stream.toString().split(System.getProperty("line.separator"));

        assertArrayEquals(expected, appOutput);
    }

    @Test
    public void testRangeUpperBoundOutOfRange() throws IOException {
        List<String> args = new ArrayList<>();
        args.add("-b");
        args.add("5-10");
        args.add(testFile1.toString());

        cut.exec(args, in, output);

        String[] expected = {"e", "j", "o"};
        String[] appOutput = stream.toString().split(System.getProperty("line.separator"));

        assertArrayEquals(expected, appOutput);
    }

    @Test
    public void testUnity() throws IOException {
        List<String> args = new ArrayList<>();
        args.add("-b");
        args.add("-2,2-");
        args.add(testFile1.toString());

        cut.exec(args, in, output);

        String[] expected = {"abcde", "fghij", "klmno"};
        String[] appOutput = stream.toString().split(System.getProperty("line.separator"));

        assertArrayEquals(expected, appOutput);
    }

    @Test
    public void testLeftToRight() throws IOException {
        List<String> args = new ArrayList<>();
        args.add("-b");
        args.add("3-");
        args.add(testFile1.toString());

        cut.exec(args, in, output);

        String[] expected = {"cde", "hij", "mno"};
        String[] appOutput = stream.toString().split(System.getProperty("line.separator"));

        assertArrayEquals(expected, appOutput);
    }

    @Test
    public void testLeftToRightFromStart() throws IOException {
        List<String> args = new ArrayList<>();
        args.add("-b");
        args.add("1-");
        args.add(testFile1.toString());

        cut.exec(args, in, output);

        String[] expected = {"abcde", "fghij", "klmno"};
        String[] appOutput = stream.toString().split(System.getProperty("line.separator"));

        assertArrayEquals(expected, appOutput);
    }

    @Test
    public void testLeftToRightFromOutOfRange() throws IOException {
        List<String> args = new ArrayList<>();
        args.add("-b");
        args.add("10-");
        args.add(testFile1.toString());

        cut.exec(args, in, output);

        String expected = "";
        String appOutput = stream.toString().trim();

        assertEquals(expected, appOutput);
    }

    @Test
    public void testRightToLeft() throws IOException {
        List<String> args = new ArrayList<>();
        args.add("-b");
        args.add("-3");
        args.add(testFile1.toString());

        cut.exec(args, in, output);

        String[] expected = {"abc", "fgh", "klm"};
        String[] appOutput = stream.toString().split(System.getProperty("line.separator"));

        assertArrayEquals(expected, appOutput);
    }

    @Test
    public void testRightToLeftFromStart() throws IOException {
        List<String> args = new ArrayList<>();
        args.add("-b");
        args.add("-1");
        args.add(testFile1.toString());

        cut.exec(args, in, output);

        String[] expected = {"a", "f", "k"};
        String[] appOutput = stream.toString().split(System.getProperty("line.separator"));

        assertArrayEquals(expected, appOutput);
    }

    @Test
    public void testRightToLeftFromEnd() throws IOException {
        List<String> args = new ArrayList<>();
        args.add("-b");
        args.add("-5");
        args.add(testFile1.toString());

        cut.exec(args, in, output);

        String[] expected = {"abcde", "fghij", "klmno"};
        String[] appOutput = stream.toString().split(System.getProperty("line.separator"));

        assertArrayEquals(expected, appOutput);
    }

    @Test
    public void testRightToLeftFromOutOfRange() throws IOException {
        List<String> args = new ArrayList<>();
        args.add("-b");
        args.add("-10");
        args.add(testFile1.toString());

        cut.exec(args, in, output);

        String[] expected = {"abcde", "fghij", "klmno"};
        String[] appOutput = stream.toString().split(System.getProperty("line.separator"));

        assertArrayEquals(expected, appOutput);
    }

    @Test(expected = MissingArgumentsException.class)
    public void testNoArgs() throws IOException {
        List<String> args = new ArrayList<>();

        cut.exec(args, in, output);
    }

    @Test(expected = MissingArgumentsException.class)
    public void testNoByteFlag() throws IOException {
        List<String> args = new ArrayList<>();
        args.add("1");
        args.add(testFile1.toString());

        cut.exec(args, in, output);
    }

    @Test(expected = CannotOpenFileException.class)
    public void testCannotOpenFile() throws IOException {
        List<String> args = new ArrayList<>();
        args.add("-b");
        args.add("1");
        args.add(testDirCut.toString());

        cut.exec(args, in, output);
    }

    @Test(expected = FileNotFoundException.class)
    public void testFileDoesNotExist() throws IOException {
        List<String> args = new ArrayList<>();
        args.add("-b");
        args.add("1");
        args.add("This file does not exist");

        cut.exec(args, in, output);
    }

    @After
    public void deleteTestFiles() throws IOException {
        Files.delete(testFile1);
        Files.delete(testDirCut);
    }
}
