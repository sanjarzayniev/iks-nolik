package helpers;

public class Logger {
    private static void log(String type, String message) {
        System.out.println("[" + type.toUpperCase() + "] " + message);
    }

    public static void info(String message) {
        log("info", message);
    }

    public static void error(String message) {
        log("error", message);
    }

    public static void warning(String message) {
        log("warning", message);
    }

    public static void success(String message) {
        log("success", message);
    }
}
