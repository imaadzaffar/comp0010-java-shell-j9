package uk.ac.ucl.shell.applications;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Tail extends HeadTailSuper {
    public Tail() {
        super("tail");
    }

    public List<String> getLines(Scanner reader, int count) {
        List<String> lines = new ArrayList<>();

        while (reader.hasNextLine()) {
            lines.add(reader.nextLine());

            if(lines.size() > count)
                lines.remove(0);
        }

        return lines;
    }
}
