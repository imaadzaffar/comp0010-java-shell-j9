package uk.ac.ucl.shell;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import uk.ac.ucl.shell.applications.Cat;
import uk.ac.ucl.shell.exceptions.CannotOpenFileException;
import uk.ac.ucl.shell.exceptions.FileNotFoundException;
import uk.ac.ucl.shell.exceptions.MissingArgumentsException;

public class CatTest {
    Cat cat;
    InputStream in;
    ByteArrayOutputStream stream;
    OutputStreamWriter output;

    Path testDirCat;
    Path testFile1;
    Path testFile2;

    public CatTest() {
        cat = new Cat();
        in = new PipedInputStream();
        stream = new ByteArrayOutputStream();
        output = new OutputStreamWriter(stream);
    }

    @Before
    public void createTestFiles() throws IOException {
        String dirName = "testDir";

        testDirCat = Shell.getCurrentDirectory().resolve(dirName);
        testFile1 = testDirCat.resolve("test1.txt");
        testFile2 = testDirCat.resolve("test2.txt");

        List<String> lines = List.of("Saudi Arabia");
        List<String> lines2 = List.of("Argentina");

        Files.createDirectories(testDirCat);
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
    public void testNoArgs() throws IOException {
        List<String> args = new ArrayList<>();

        cat.exec(args, null, output);
    }

    @Test
    public void testSingleArg() throws IOException {
        List<String> args = new ArrayList<>();
        args.add(testFile1.toString());

        cat.exec(args, in, output);

        String expected = "Saudi Arabia";
        String appOutput = stream.toString().trim();

        assertEquals(expected, appOutput);
    }

    @Test
    public void testDoubleArg() throws IOException {
        List<String> args = new ArrayList<>();
        args.add(testFile1.toString());
        args.add(testFile2.toString());

        cat.exec(args, in, output);

        String[] expected = {"Saudi Arabia", "Argentina"};
        String[] appOutput = stream.toString().split(System.getProperty("line.separator"));

        assertArrayEquals(expected, appOutput);
    }

    @Test
    public void testStandardInput() throws IOException {
        List<String> args = new ArrayList<>();
        in = new ByteArrayInputStream("World Cup".getBytes(StandardCharsets.UTF_8));

        cat.exec(args, in, output);

        String expected = "World Cup";
        String appOutput = stream.toString().trim();

        assertEquals(expected, appOutput);
    }

    @Test(expected = CannotOpenFileException.class)
    public void testCannotOpenFile() throws IOException {
         List<String> args = new ArrayList<>();
         args.add(testDirCat.toString());

         cat.exec(args, in, output);
    }

    @Test(expected = FileNotFoundException.class)
    public void testFileNotFoundException() throws IOException {
        List<String> args = new ArrayList<>();
        args.add("test1.txt");

        cat.exec(args, in, output);
    }

    @After
    public void deleteTestFiles() throws IOException {
        Files.delete(testFile1);
        Files.delete(testFile2);
        Files.delete(testDirCat);
    }
}
