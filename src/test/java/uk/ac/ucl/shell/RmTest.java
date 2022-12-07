package uk.ac.ucl.shell;

import static org.junit.Assert.*;

import org.junit.Test;
import uk.ac.ucl.shell.applications.Rm;
import uk.ac.ucl.shell.exceptions.FileNotFoundException;
import uk.ac.ucl.shell.exceptions.MissingArgumentsException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class RmTest {
    Rm rm;
    InputStream in;
    ByteArrayOutputStream stream;
    OutputStreamWriter output;

    public RmTest() {
        rm = new Rm();
        in = new PipedInputStream();
        stream = new ByteArrayOutputStream();
        output = new OutputStreamWriter(stream);
    }

    @Test
    public void testOneDirectory() throws IOException {
        String dirName = "testDir";
        Path testDir = Shell.getCurrentDirectory().resolve(dirName);
        Files.createDirectories(testDir);

        ArrayList<String> args = new ArrayList<>();
        args.add("testDir");

        rm.exec(args, in, output);

        assertFalse(Files.exists(testDir));
    }

    @Test
    public void testMultipleDirectories() throws IOException {
        String dirName = "testDir";
        Path testDir = Shell.getCurrentDirectory().resolve(dirName);
        Files.createDirectories(testDir);
        String dirName2 = "testDir2";
        Path testDir2 = Shell.getCurrentDirectory().resolve(dirName2);
        Files.createDirectories(testDir2);

        ArrayList<String> args = new ArrayList<>();
        args.add("testDir");
        args.add("testDir2");

        rm.exec(args, in, output);

        assertFalse(Files.exists(testDir) && Files.exists(testDir2));
    }

    @Test
    public void testStandardInput() throws IOException {
        String dirName = "testDir";
        Path testDir = Shell.getCurrentDirectory().resolve(dirName);
        Files.createDirectories(testDir);

        ArrayList<String> args = new ArrayList<>();
        in = new ByteArrayInputStream("testDir".getBytes(StandardCharsets.UTF_8));

        rm.exec(args, in, output);

        assertFalse(Files.exists(testDir));
    }

    @Test(expected = FileNotFoundException.class)
    public void testFileDoesNotExist() throws IOException {
        ArrayList<String> args = new ArrayList<>();
        args.add("TestFile");

        rm.exec(args, in, output);
    }

    @Test(expected = MissingArgumentsException.class)
    public void testNoArgs() throws IOException {
        ArrayList<String> args = new ArrayList<>();

        rm.exec(args, null, output);
    }
}
