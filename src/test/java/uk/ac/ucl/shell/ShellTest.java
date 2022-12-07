package uk.ac.ucl.shell;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import uk.ac.ucl.shell.exceptions.CannotOpenFileException;
import uk.ac.ucl.shell.exceptions.FileNotFoundException;
import uk.ac.ucl.shell.exceptions.InvalidArgumentsException;
import uk.ac.ucl.shell.exceptions.ParseCancellationException;
import uk.ac.ucl.shell.exceptions.TooManyArgumentsException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

import static org.junit.Assert.*;

public class ShellTest {
    PipedInputStream in;
    PipedOutputStream out;
    String originalDir;
    Shell shell;

    Path testDir;
    Path testFile1;
    Path testFile2;

    public ShellTest() throws IOException {
        // Shell is a utility class which is instantiated for the sake of coverage
        shell = new Shell();
        in = new PipedInputStream();
        out = new PipedOutputStream(in);
        originalDir = Shell.getCurrentDirectory().toString();
    }

    @Before
    public void createTestFiles() throws IOException {
        String dirName = "testDir";

        testDir = Shell.getCurrentDirectory().resolve(dirName);
        testFile1 = Shell.getCurrentDirectory().resolve(dirName).resolve("test1.txt");
        testFile2 = Shell.getCurrentDirectory().resolve(dirName).resolve("test2.txt");

        List<String> lines = List.of("Hello");

        Files.createDirectories(testDir);
        Files.createFile(testFile1);
        Files.createFile(testFile2);

        try {
            Files.write(testFile1, lines, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testOneCommand() throws IOException {
        String cmdline = "echo a";

        Shell.eval(cmdline, out);

        String [] expected = {"a"};

        try(Scanner scanner = new Scanner(in)) {
            for (String s : expected) {
                assertEquals(s, scanner.nextLine());
            }
        }
    }

    @Test
    public void testTwoSequencedCommands() throws IOException {
        String cmdline = "echo a;echo b";

        Shell.eval(cmdline, out);

        String [] expected = {"a", "b"};

        try(Scanner scanner = new Scanner(in)) {
            for (String s : expected) {
                assertEquals(s, scanner.nextLine());
            }
        }
    }

    @Test
    public void testTwoPipedCommands() throws IOException {
        String cmdline = "echo | echo a";

        Shell.eval(cmdline, out);

        String [] expected = {"a"};

        try(Scanner scanner = new Scanner(in)) {
            for (String s : expected) {
                assertEquals(s, scanner.nextLine());
            }
        }
    }

    @Test
    public void testInputRedirection() throws IOException {
        Shell.setCurrentDirectory(testDir.toString());
        String cmdline = "cat < test1.txt";

        Shell.eval(cmdline, out);

        String[] expected = {"Hello"};

        Shell.setCurrentDirectory(originalDir);

        try(Scanner scanner = new Scanner(in)) {
            for (String s : expected) {
                assertEquals(s, scanner.nextLine());
            }
        }
    }

    @Test
    public void testOutputRedirection() throws IOException {
        Shell.setCurrentDirectory(testDir.toString());
        String cmdline = "echo a > test2.txt";

        Shell.eval(cmdline, out);

        String [] expected = {"a"};

        Shell.setCurrentDirectory(originalDir);

        try(Scanner scanner = new Scanner(testFile2)) {
            for (String s : expected) {
                assertEquals(s, scanner.nextLine());
            }
        }
    }

    @Test(expected = TooManyArgumentsException.class)
    public void testTooManyArguments() throws IOException {
        String cmdline = "echo a > One > Two > Three > Four";

        Shell.eval(cmdline, out);
    }

    @Test(expected = InvalidArgumentsException.class)
    public void testInvalidArguments() throws IOException {
        String cmdline = "echo a > One > Two";

        Shell.eval(cmdline, out);
    }

    /*
    @Test(expected = CannotOpenFileException.class)
    public void testLockedFile() throws IOException {
        String cmdline = "echo a > /test3.txt";

        Shell.eval(cmdline, out);
    }*/

    @Test(expected = FileNotFoundException.class)
    public void testFileNotFound() throws IOException {
        String cmdline = "cat < NoSuchFile";

        Shell.eval(cmdline, out);
    }

    @Test(expected = ParseCancellationException.class)
    public void testParserError() throws IOException {
        String cmdline = "echo a; ";

        Shell.eval(cmdline, out);
    }

    @Test(expected = RuntimeException.class)
    public void testErrorDuringSequencing() throws IOException {
        String cmdline = "echo a; cut";

        Shell.eval(cmdline, out);
    }

    @Test(expected = RuntimeException.class)
    public void testErrorDuringSubstitution() throws IOException {
        String cmdline = "echo `test`";

        Shell.eval(cmdline, out);
    }

    @After
    public void deleteTestFiles() throws IOException {
        Files.delete(testFile1);
        Files.delete(testFile2);
        Files.delete(testDir);
    }
}
