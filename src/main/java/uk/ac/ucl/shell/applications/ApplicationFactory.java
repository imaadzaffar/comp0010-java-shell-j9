package uk.ac.ucl.shell.applications;

public class ApplicationFactory {
    public Application getApp(String appName) {
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
            default -> throw new RuntimeException(appName + ": unknown application");
        };

        return unsafe ? new UnsafeDecorator(application) : application;
    }
}
