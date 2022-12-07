package uk.ac.ucl.shell;

import static org.junit.Assert.*;

import org.junit.Test;
import uk.ac.ucl.shell.applications.Date;
import uk.ac.ucl.shell.exceptions.TooManyArgumentsException;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class DateTest {
    Date date;
    InputStream in;
    ByteArrayOutputStream stream;
    OutputStreamWriter output;

    public DateTest() {
        date = new Date();
        in = new PipedInputStream();
        stream = new ByteArrayOutputStream();
        output = new OutputStreamWriter(stream);
    }

    @Test
    public void testNormal() throws IOException, ParseException {
        ArrayList<String> args = new ArrayList<>();

        long startTime = new java.util.Date().getTime();
        date.exec(args, in, output);
        String appOutput = stream.toString().trim();
        SimpleDateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
        long outputTime = formatter.parse(appOutput).getTime();
        assertTrue(startTime - outputTime <= 5000);
    }

    @Test(expected = TooManyArgumentsException.class)
    public void testTooManyArguments() throws IOException {
        ArrayList<String> args = new ArrayList<>();
        args.add("One");

        date.exec(args, in, output);
    }
}
