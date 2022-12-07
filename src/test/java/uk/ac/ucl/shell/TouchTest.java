package uk.ac.ucl.shell;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Test;
import uk.ac.ucl.shell.applications.Touch;
import uk.ac.ucl.shell.exceptions.MissingArgumentsException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class TouchTest {
    Touch touch;
    InputStream in;
    ByteArrayOutputStream stream;
    OutputStreamWriter output;
    ArrayList<Path> filesToRemove;

    public TouchTest() {
        touch = new Touch();
        in = new PipedInputStream();
        stream = new ByteArrayOutputStream();
        output = new OutputStreamWriter(stream);
        filesToRemove = new ArrayList<>();
    }

    @Test
    public void testCreateOneFile() throws IOException {
        ArrayList<String> args = new ArrayList<>();
        String fileName = "test1.txt";
        args.add(fileName);
        Path filePath = Shell.getCurrentDirectory().resolve(fileName);
        filesToRemove.add(filePath);

        touch.exec(args, in, output);

        assertTrue(Files.exists(filePath));
    }

    @Test
    public void testCreateMultipleFiles() throws IOException {
        ArrayList<String> args = new ArrayList<>();
        String fileName = "test2.txt";
        String fileName2 = "test3.txt";
        args.add(fileName);
        args.add(fileName2);

        Path filePath = Shell.getCurrentDirectory().resolve(fileName);
        Path filePath2 = Shell.getCurrentDirectory().resolve(fileName2);
        filesToRemove.add(filePath);
        filesToRemove.add(filePath2);

        touch.exec(args, in, output);

        assertTrue(Files.exists(filePath) && Files.exists(filePath2));
    }

    @Test
    public void testStandardInput() throws IOException {
        ArrayList<String> args = new ArrayList<>();
        String fileName = "test4.txt";
        in = new ByteArrayInputStream(fileName.getBytes(StandardCharsets.UTF_8));
        Path filePath = Shell.getCurrentDirectory().resolve(fileName);
        filesToRemove.add(filePath);

        touch.exec(args, in, output);

        assertTrue(Files.exists(filePath));

    }

    @Test(expected = MissingArgumentsException.class)
    public void testNoArgs() throws IOException {
        ArrayList<String> args = new ArrayList<>();

        touch.exec(args, null, output);
    }

    @After
    public void removeTestFiles() throws IOException {
        for (Path filePath : filesToRemove) {
            Files.delete(filePath);
        }
    }
}