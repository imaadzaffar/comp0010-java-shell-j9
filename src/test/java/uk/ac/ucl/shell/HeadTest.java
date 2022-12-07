package uk.ac.ucl.shell;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import uk.ac.ucl.shell.applications.Head;
import uk.ac.ucl.shell.exceptions.*;
import uk.ac.ucl.shell.exceptions.MissingArgumentsException;
import uk.ac.ucl.shell.exceptions.FileNotFoundException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class HeadTest {
    Head head;
    InputStream in;
    ByteArrayOutputStream stream;
    OutputStreamWriter output;

    Path testDirHead;
    Path testFile1;

    public HeadTest() {
        head = new Head();
        in = new PipedInputStream();
        stream = new ByteArrayOutputStream();
        output = new OutputStreamWriter(stream);
    }

    @Before
    public void createTestFiles() throws IOException {
        String dirName = "testDir";

        testDirHead = Shell.getCurrentDirectory().resolve(dirName);
        testFile1 = Shell.getCurrentDirectory().resolve(dirName).resolve("test1.txt");

        List<String> lines = List.of("a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z");

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
        in = new ByteArrayInputStream("a\nb\nc\nd\ne\nf\ng\nh\ni\nj\nk".getBytes(StandardCharsets.UTF_8));

        head.exec(args, in, output);

        String[] expected = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j"};
        String[] appOutput = stream.toString().split(System.getProperty("line.separator"));
        
        assertArrayEquals(expected, appOutput);
    }

    @Test
    public void testStandardInputCustomN() throws IOException {
        List<String> args = new ArrayList<>();
        args.add("-n");
        args.add("5");
        in = new ByteArrayInputStream("a\nb\nc\nd\ne\nf\ng\nh\ni\nj\nk".getBytes(StandardCharsets.UTF_8));

        head.exec(args, in, output);

        String[] expected = {"a", "b", "c", "d", "e"};
        String[] appOutput = stream.toString().split(System.getProperty("line.separator"));
        
        assertArrayEquals(expected, appOutput);
    }

    @Test
    public void testNormal() throws IOException {
        List<String> args = new ArrayList<>();
        args.add(testFile1.toString());

        head.exec(args, in, output);

        String[] expected = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j"};
        String[] appOutput = stream.toString().split(System.getProperty("line.separator"));
        
        assertArrayEquals(expected, appOutput);
    }

    @Test
    public void testNormalZeroN() throws IOException {
        List<String> args = new ArrayList<>();
        args.add("-n");
        args.add("0");
        args.add(testFile1.toString());

        head.exec(args, in, output);

        String expected = "";
        String appOutput = stream.toString().trim();

        assertEquals(expected, appOutput);
    }

    @Test
    public void testCustomSmallN() throws IOException {
        List<String> args = new ArrayList<>();
        args.add("-n");
        args.add("3");
        args.add(testFile1.toString());

        head.exec(args, in, output);

        String[] expected = {"a", "b", "c"};
        String[] appOutput = stream.toString().split(System.getProperty("line.separator"));
        
        assertArrayEquals(expected, appOutput);
    }

    @Test
    public void testCustomLargeN() throws IOException {
        List<String> args = new ArrayList<>();
        args.add("-n");
        args.add("100");
        args.add(testFile1.toString());

        head.exec(args, in, output);

        String[] expected = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
        String[] appOutput = stream.toString().split(System.getProperty("line.separator"));
        
        assertArrayEquals(expected, appOutput);
    }

    @Test(expected = MissingArgumentsException.class)
    public void testNoArgs() throws IOException {
        List<String> args = new ArrayList<>();

        head.exec(args, null, output);
    }

    @Test(expected = TooManyArgumentsException.class)
    public void testTooManyArgs() throws IOException {
        List<String> args = new ArrayList<>();
        args.add("We");
        args.add("The");
        args.add("Best");
        args.add("Music");

        head.exec(args, in, output);
    }

    @Test(expected = InvalidArgumentsException.class)
    public void testWrongFirstArgument() throws IOException {
        List<String> args = new ArrayList<>();
        args.add("Another");
        args.add("One");

        head.exec(args, in, output);
    }

    @Test(expected = InvalidArgumentsException.class)
    public void testNonIntegerSecondArgument() throws IOException {
        List<String> args = new ArrayList<>();
        args.add("-n");
        args.add("DJ Khaled");

        head.exec(args, in, output);
    }

    @Test(expected = CannotOpenFileException.class)
    public void testCannotOpenFile() throws IOException {
        List<String> args = new ArrayList<>();
        args.add(testDirHead.toString());

        head.exec(args, in, output);
    }

    @Test(expected = FileNotFoundException.class)
    public void testFileNotFound() throws IOException {
        List<String> args = new ArrayList<>();
        args.add("FileThatDoesNotExist");

        head.exec(args, in, output);
    }

    @Test(expected = MissingArgumentsException.class)
    public void testNWithoutNum() throws IOException {
        List<String> args = new ArrayList<>();
        args.add("-n");

        head.exec(args, in, output);
    }

    @After
    public void deleteTestFiles() throws IOException {
        Files.delete(testFile1);
        Files.delete(testDirHead);
    }
}
