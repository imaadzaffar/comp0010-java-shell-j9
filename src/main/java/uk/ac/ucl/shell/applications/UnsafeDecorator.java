package uk.ac.ucl.shell.applications;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class UnsafeDecorator implements Application {
    private Application baseApplcation;

    public UnsafeDecorator(Application baseApplication) {
        this.baseApplcation = baseApplication;
    }

    @Override
    public void exec(List<String> args, InputStream input, OutputStream output) throws IOException {
        try {
            this.baseApplcation.exec(args, input, output);
        } catch(IOException ex) {
            //add excetion to output
        }
    }
}
