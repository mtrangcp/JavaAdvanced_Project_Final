package utils;

public class Color {

    public static final String RESET = "\u001B[0m";

    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";

    public static final String BOLD = "\u001B[1m";

    public static void printError(String message) {
        System.out.println(BOLD + RED + message + RESET);
    }

    public static void printSuccess(String message) {
        System.out.println(BOLD + GREEN + message + RESET);
    }

    public static void printWarning(String message) {
        System.out.println(BOLD + YELLOW + message + RESET);
    }
}
