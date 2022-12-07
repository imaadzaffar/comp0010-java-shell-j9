package uk.ac.ucl.shell;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Test;
import uk.ac.ucl.shell.applications.Touch;
import uk.ac.ucl.shell.exceptions.MissingArgumentsException;

import java.io.*;
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
        args.add("test1.txt");
        Path filePath = Shell.getCurrentDirectory().resolve("test1.txt");
        filesToRemove.add(filePath);

        touch.exec(args, in, output);

        assertTrue(Files.exists(filePath));
    }

    @Test
    public void testCreateMultipleFiles() throws IOException {
        ArrayList<String> args = new ArrayList<>();
        args.add("test2.txt");
        args.add("test3.txt");

        Path filePath = Shell.getCurrentDirectory().resolve("test2.txt");
        Path filePath2 = Shell.getCurrentDirectory().resolve("test3.txt");
        filesToRemove.add(filePath);
        filesToRemove.add(filePath2);

        touch.exec(args, in, output);

        assertTrue(Files.exists(filePath) && Files.exists(filePath2));
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