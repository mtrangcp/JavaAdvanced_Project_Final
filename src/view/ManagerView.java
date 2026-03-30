package view;

import service.MenuItemService;
import service.TableService;
import java.util.Scanner;

public class ManagerView {
    private final Scanner scanner;
    public ManagerView(Scanner scanner) {
        this.scanner = scanner;
    }

    public void start() {
        while (true) {
            System.out.println("\n===== MANAGER MENU =====");
            System.out.println("1. Quản lý bàn");
            System.out.println("2. Quản lý menu");
            System.out.println("0. Đăng xuất");

            System.out.print("Chọn: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.println("TODO: Table Management");
                    break;
                case "2":
                    System.out.println("TODO: Menu Management");
                    break;
                case "0":
                    System.out.println("Thoát menu manager");
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ! ");
            }
        }
    }

}
