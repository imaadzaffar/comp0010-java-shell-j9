package uk.ac.ucl.shell.applications;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.List;

 /**
 * UnsafeDecorator wraps a passed <b>Application</b> and silences its exceptions to the output.
 */
public class UnsafeDecorator implements Application {
    private Application baseApplication;

    public UnsafeDecorator(Application baseApplication) {
        this.baseApplication = baseApplication;
    }

    @Override
    public void exec(List<String> args, InputStream input, OutputStreamWriter output) throws IOException {
        try {
            this.baseApplication.exec(args, input, output);
        } catch(Exception ex) {
            output.write(ex.getMessage());
            output.write(System.getProperty("line.separator"));
            output.flush();
        }
    }
}
