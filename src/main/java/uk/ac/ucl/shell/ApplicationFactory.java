package uk.ac.ucl.shell;

public class ApplicationFactory {
    private Application app;

    public ApplicationFactory() {}

    public Application getApp(String appName) {
        return switch (appName) {
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
    }
}
