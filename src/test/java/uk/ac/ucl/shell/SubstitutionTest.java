package uk.ac.ucl.shell;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import uk.ac.ucl.shell.commands.Substitution;

import java.util.ArrayList;

public class SubstitutionTest {
    Substitution s;
    public SubstitutionTest() {
        // Instantiated for the sake of coverage
        s = new Substitution();
    }

    @Test
    public void testEmbeddedInDoubleQuotes() {
        ArrayList<String> appOutput;
        ArrayList<String> args = new ArrayList<>();
        args.add("echo");
        args.add("\"`echo b`\"");

        appOutput = Substitution.sub(args);

        ArrayList<String> expected = new ArrayList<>();
        expected.add("echo");
        expected.add("\"b\"");

        assertEquals(expected, appOutput);
    }

    @Test
    public void testBackquotes() {
        ArrayList<String> appOutput;
        ArrayList<String> args = new ArrayList<>();
        args.add("echo");
        args.add("`echo abc`");

        appOutput = Substitution.sub(args);

        ArrayList<String> expected = new ArrayList<>();
        expected.add("echo");
        expected.add("abc");

        assertEquals(expected, appOutput);
    }
}
