package uk.ac.ucl.shell;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import uk.ac.ucl.shell.applications.Wc;
import uk.ac.ucl.shell.exceptions.FileNotFoundException;
import uk.ac.ucl.shell.exceptions.MissingArgumentsException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class WcTest {
    Wc wc;
    InputStream in;
    ByteArrayOutputStream stream;
    OutputStreamWriter output;

    Path testDirWc;
    Path testFile1;
    Path testFile2;
    Path testFile3;

    public WcTest() {
        wc = new Wc();
        in = new PipedInputStream();
        stream = new ByteArrayOutputStream();
        output = new OutputStreamWriter(stream);
    }

    @Before
    public void createTestFiles() throws IOException {
        String dirName = "testDir";

        testDirWc = Shell.getCurrentDirectory().resolve(dirName);
        testFile1 = Shell.getCurrentDirectory().resolve(dirName).resolve("test1.txt");
        testFile2 = Shell.getCurrentDirectory().resolve(dirName).resolve("test2.txt");
        testFile3 = Shell.getCurrentDirectory().resolve(dirName).resolve("test3.txt");

        List<String> lines = List.of("abcde", "e  f  g");
        List<String> lines2 = List.of("a     b", "c      d");

        Files.createDirectories(testDirWc);
        Files.createFile(testFile1);
        Files.createFile(testFile2);
        Files.createFile(testFile3);

        try {
            Files.write(testFile1, lines, StandardCharsets.UTF_8);
            Files.write(testFile2, lines2, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testOneFile() throws IOException {
        ArrayList<String> args = new ArrayList<>();
        args.add(testFile1.toString());

        wc.exec(args, in, output);

        String expected = "2 4 12 " + testFile1.getFileName();
        String appOutput = stream.toString().trim();

        assertEquals(expected, appOutput);
    }

    @Test
    public void testTwoFiles() throws IOException {
        ArrayList<String> args = new ArrayList<>();
        args.add(testFile1.toString());
        args.add(testFile2.toString());

        wc.exec(args, in, output);

        String expected = "2 4 12 " + testFile1.getFileName() + System.getProperty("line.separator") + "2 4 15 " + testFile2.getFileName();
        String appOutput = stream.toString().trim();

        assertEquals(expected, appOutput);
    }

    @Test
    public void testEmptyFile() throws IOException {
        ArrayList<String> args = new ArrayList<>();
        args.add(testFile3.toString());

        wc.exec(args, in, output);

        String expected = "0 0 0 " + testFile3.getFileName();
        String appOutput = stream.toString().trim();

        assertEquals(expected, appOutput);
    }

    @Test(expected = MissingArgumentsException.class)
    public void testNoArgs() throws IOException {
        ArrayList<String> args = new ArrayList<>();

        wc.exec(args, in, output);
    }

    @Test(expected = FileNotFoundException.class)
    public void testFileDoesNotExist() throws IOException {
        ArrayList<String> args = new ArrayList<>();
        args.add("ThisFileDoesNotExist");

        wc.exec(args, in, output);
    }

    @Test(expected = FileNotFoundException.class)
    public void testPassDirectoryAsFile() throws IOException {
        ArrayList<String> args = new ArrayList<>();
        args.add(testDirWc.getFileName().toString());

        wc.exec(args, in, output);
    }

    @After
    public void deleteTestFiles() throws IOException {
        Files.delete(testFile1);
        Files.delete(testFile2);
        Files.delete(testFile3);
        Files.delete(testDirWc);
    }
}
