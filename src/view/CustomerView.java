package view;

import java.util.Scanner;

public class CustomerView {
    private final Scanner scanner;

    public CustomerView(Scanner scanner) {
        this.scanner = scanner;
    }

    public void start() {

        while (true) {
            System.out.println("\n===== CUSTOMER MENU =====");
            System.out.println("1. Xem menu");
            System.out.println("2. Gọi món");
            System.out.println("3. Xem order của tôi");
            System.out.println("0. Đăng xuất");

            System.out.print("Chọn: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.println("TODO: View Menu");
                    break;
                case "2":
                    System.out.println("TODO: Order Food");
                    break;
                case "3":
                    System.out.println("TODO: My Orders");
                    break;
                case "0":
                    System.out.println("Thoát menu custormer");
                    return;
                default:
                    System.out.println("Sai lựa chọn");
            }
        }
    }

}
