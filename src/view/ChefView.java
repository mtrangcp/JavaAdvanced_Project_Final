package view;

import java.util.Scanner;

public class ChefView {
    private final Scanner scanner;

    public ChefView(Scanner scanner) {
        this.scanner = scanner;
    }

    public void start() {

        while (true) {
            System.out.println("\n===== CHEF MENU =====");
            System.out.println("1. Xem order");
            System.out.println("2. Cập nhật trạng thái món");
            System.out.println("0. Đăng xuất");

            System.out.print("Chọn: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.println("TODO: View Orders");
                    break;
                case "2":
                    System.out.println("TODO: Update Status");
                    break;
                case "0":
                    System.out.println("Thoát menu chef");
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ! ");
            }
        }
    }


}
