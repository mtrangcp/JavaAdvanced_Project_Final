package view;

import service.MenuItemService;
import service.TableService;
import view.manager.MenuItemManagementView;
import view.manager.TableManagementView;

import java.util.Scanner;

public class ManagerView {
    private final Scanner scanner;
    private final TableService tableService;
    private final MenuItemService menuItemService;

    public ManagerView(Scanner scanner,
                       TableService tableService,
                       MenuItemService menuItemService) {
        this.scanner = scanner;
        this.tableService = tableService;
        this.menuItemService = menuItemService;
    }

    public void start() {
        while (true) {
            System.out.println("\n===== MANAGER MENU =====");
            System.out.println("1. Quản lý bàn");
            System.out.println("2. Quản lý menu");
            System.out.println("3. Duyệt Order");
            System.out.println("4. Quản lý User(Ban/UnBan)");

            System.out.println("0. Đăng xuất");

            System.out.print("Chọn: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    new TableManagementView(scanner, tableService).start();
                    break;
                case "2":
                    new MenuItemManagementView(scanner, menuItemService).start();
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
