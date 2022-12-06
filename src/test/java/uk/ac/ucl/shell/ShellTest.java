package uk.ac.ucl.shell;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import uk.ac.ucl.shell.exceptions.FileNotFoundException;
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

    Path testDir;
    Path testFile1;
    Path testFile2;

    public ShellTest() throws IOException {
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

        Scanner scn = new Scanner(in);
        scn.useDelimiter(System.getProperty("line.separator"));
        for (String s : expected) {
            assertEquals(s, scn.nextLine());
        }
    }

    @Test
    public void testTwoSequencedCommands() throws IOException {
        String cmdline = "echo a;echo b";

        Shell.eval(cmdline, out);

        String [] expected = {"a", "b"};

        Scanner scn = new Scanner(in);
        scn.useDelimiter(System.getProperty("line.separator"));
        for (String s : expected) {
            assertEquals(s, scn.nextLine());
        }
    }

    @Test
    public void testTwoPipedCommands() throws IOException {
        String cmdline = "echo | echo a";

        Shell.eval(cmdline, out);

        String [] expected = {"a"};

        Scanner scn = new Scanner(in);
        scn.useDelimiter(System.getProperty("line.separator"));
        for (String s : expected) {
            assertEquals(s, scn.nextLine());
        }
    }

    @Test
    public void testInputRedirection() throws IOException {
        Shell.setCurrentDirectory(testDir.toString());
        String cmdline = "cat < test1.txt";

        Shell.eval(cmdline, out);

        String [] expected = {"Hello"};
        Scanner scn = new Scanner(in);
        scn.useDelimiter(System.getProperty("line.separator"));
        Shell.setCurrentDirectory(originalDir);

        for (String s : expected) {
            assertEquals(s, scn.nextLine());
        }
    }

    @Test
    public void testOutputRedirection() throws IOException {
        Shell.setCurrentDirectory(testDir.toString());
        String cmdline = "echo a > test2.txt";

        Shell.eval(cmdline, out);

        String [] expected = {"a"};
        BufferedReader reader = new BufferedReader(new FileReader(testFile2.toString()));
        Shell.setCurrentDirectory(originalDir);

        for (String s : expected) {
            assertEquals(s, reader.readLine());
        }
    }

    @Test(expected = TooManyArgumentsException.class)
    public void testTooManyArguments() throws IOException {
        String cmdline = "echo a > One > Two > Three > Four";

        Shell.eval(cmdline, out);
    }

    @Test(expected = FileNotFoundException.class)
    public void testFileNotFound() throws IOException {
        String cmdline = "cat < NoSuchFile";

        Shell.eval(cmdline, out);
    }

    @After
    public void deleteTestFiles() throws IOException {
        Files.delete(testFile1);
        Files.delete(testFile2);
        Files.delete(testDir);
    }
}
