package uk.ac.ucl.shell.applications;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Head extends HeadTailSuper{
    public Head() {
        super("head");
    }

    public List<String> getLines(Scanner reader, int count) {
        List<String> lines = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            if (!reader.hasNextLine()) {
                break;
            }

            lines.add(reader.nextLine());
        }

        return lines;
    }
}
