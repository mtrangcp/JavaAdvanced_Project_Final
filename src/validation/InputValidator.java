package validation;

import java.util.Scanner;

public class InputValidator {
    public static int inputInt(Scanner sc, String message) {
        while (true) {
            try {
                System.out.print(message);
                int value = Integer.parseInt(sc.nextLine());

                if (value < 0) {
                    System.out.println("Phải >= 0");
                    continue;
                }

                return value;

            } catch (NumberFormatException e) {
                System.out.println("Phải nhập số!");
            }
        }
    }


    public static String inputString(Scanner scanner, String s) {


    }
}
