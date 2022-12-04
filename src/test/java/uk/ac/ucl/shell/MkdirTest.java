package uk.ac.ucl.shell;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Test;
import uk.ac.ucl.shell.applications.Mkdir;
import uk.ac.ucl.shell.exceptions.FileAlreadyExistsException;
import uk.ac.ucl.shell.exceptions.MissingArgumentsException;
import uk.ac.ucl.shell.exceptions.TooManyArgumentsException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class MkdirTest {
    Mkdir mkdir;
    InputStream in;
    ByteArrayOutputStream stream;
    OutputStreamWriter output;
    ArrayList<Path> pathsToRemove;

    public MkdirTest() {
        mkdir = new Mkdir();
        in = new PipedInputStream();
        stream = new ByteArrayOutputStream();
        output = new OutputStreamWriter(stream);
        pathsToRemove = new ArrayList<>();
    }

    @Test
    public void testNormal() throws IOException {
        ArrayList<String> args = new ArrayList<>();
        args.add("testDirOne");

        mkdir.exec(args, in, output);

        Path newDirPath = Shell.getCurrentDirectory().resolve("testDirOne");
        pathsToRemove.add(newDirPath);

        assertTrue(Files.exists(newDirPath));
    }

    @Test(expected = TooManyArgumentsException.class)
    public void testTooManyArguments() throws IOException {
        ArrayList<String> args = new ArrayList<>();
        args.add("One");
        args.add("Two");

        mkdir.exec(args, in, output);
    }

    @Test(expected = MissingArgumentsException.class)
    public void testNoArgs() throws IOException {
        ArrayList<String> args = new ArrayList<>();

        mkdir.exec(args, in, output);
    }

    @Test(expected = FileAlreadyExistsException.class)
    public void testFileAlreadyExists() throws IOException {
        ArrayList<String> args = new ArrayList<>();
        args.add("testDirTwo");

        mkdir.exec(args, in, output);

        Path newDirPath = Shell.getCurrentDirectory().resolve("testDirTwo");
        pathsToRemove.add(newDirPath);

        mkdir.exec(args, in, output);
    }

    @After
    public void removeTestDirectories() throws IOException {
        for (Path path : pathsToRemove) {
            Files.delete(path);
        }
    }
}
