package uk.ac.ucl.shell.applications;

import uk.ac.ucl.shell.exceptions.UnknownApplicationException;

public class ApplicationFactory {
    /**
     * Returns the corresponding <b>Application</b> from the passed <b>appName</b>
     * @param appName The requested application name
     * @return The corresponding application
     * @throws UnknownApplicationException If the passed application name is invalid
     */
    public Application getApp(String appName) throws UnknownApplicationException {
        boolean unsafe = false;

        if(appName.startsWith("_")) {
            unsafe = true;
            appName = appName.substring(1);
        }

        Application application = switch (appName) {
            case "cd" -> new Cd();
            case "pwd" -> new Pwd();
            case "ls" -> new Ls();
            case "cat" -> new Cat();
            case "echo" -> new Echo();
            case "head" -> new Head();
            case "tail" -> new Tail();
            case "grep" -> new Grep();
            case "find" -> new Find();
            case "sort" -> new Sort();
            case "uniq" -> new Uniq();
            case "cut" -> new Cut();
            case "rm" -> new Rm();
            case "mkdir" -> new Mkdir();
            case "touch" -> new Touch();
            case "sleep" -> new Sleep();
            case "date" -> new Date();
            case "wc" -> new Wc();
            default -> throw new UnknownApplicationException(appName);
        };

        return unsafe ? new UnsafeDecorator(application) : application;
    }
}
