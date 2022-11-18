package uk.ac.ucl.shell.applications;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.List;

public class UnsafeDecorator implements Application {
    private Application baseApplcation;

    public UnsafeDecorator(Application baseApplication) {
        this.baseApplcation = baseApplication;
    }

    @Override
    public void exec(List<String> args, InputStream input, OutputStreamWriter output) throws IOException {
        try {
            this.baseApplcation.exec(args, input, output);
        } catch(Exception ex) {
            output.write(ex.getMessage());
            output.write(System.getProperty("line.separator"));
            output.flush();
        }
    }
}
