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
    public static String inputString(Scanner scanner, String message) {
        while (true) {
            System.out.print(message);
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("Không được để trống!");
            } else {
                return input;
            }
        }
    }
    public static double inputDouble(Scanner scanner, String message) {
        while (true) {
            System.out.print(message);
            try {
                double value = Double.parseDouble(scanner.nextLine().trim());

                if (value <= 0) {
                    System.out.println("Giá trị phải > 0!");
                    continue;
                }

                return value;

            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập số hợp lệ!");
            }
        }
    }

    public static <T extends Enum<T>> T inputEnum(Scanner scanner, String message, Class<T> enumClass) {
        while (true) {
            System.out.print(message);
            try {
                return Enum.valueOf(enumClass, scanner.nextLine().toUpperCase());
            } catch (Exception e) {
                System.out.println("Giá trị không hợp lệ. Nhập lại!");
            }
        }
    }
}
