package view;

import exception.AppException;
import model.entity.User;
import service.MenuItemService;
import service.OrderService;
import service.TableService;
import service.UserService;
import java.util.Scanner;

public class AuthView {
    private final UserService userService;
    private final Scanner scanner;
    private final TableService tableService;
    private final MenuItemService menuItemService;
    private final OrderService orderService;

    public AuthView(UserService userService,
                    TableService tableService,
                    MenuItemService menuItemService
                    , OrderService orderService, Scanner scanner) {
        this.userService = userService;
        this.tableService = tableService;
        this.menuItemService = menuItemService;
        this.scanner = scanner;
        this.orderService = orderService;
    }

    public void start() {

        while (true) {
            System.out.println("\n===== RESTAURANT SYSTEM =====");
            System.out.println("1. Đăng ký");
            System.out.println("2. Đăng nhập");
            System.out.println("0. Thoát");

            System.out.print("Chọn: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    register();
                    break;
                case "2":
                    login();
                    break;
                case "0":
                    System.out.println("Thoát...");
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ");
            }
        }
    }

    private void register() {
        try {
            System.out.print("Username: ");
            String username = scanner.nextLine();

            System.out.print("Password: ");
            String password = scanner.nextLine();

            System.out.print("Full name: ");
            String fullName = scanner.nextLine();

            userService.register(username, password, fullName);

            System.out.println("Đăng ký thành công!");

        } catch (AppException e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
    }

    private void login() {
        try {
            System.out.print("Username: ");
            String username = scanner.nextLine();

            System.out.print("Password: ");
            String password = scanner.nextLine();

            User user = userService.login(username, password);

            System.out.println("Đăng nhập thành công!");

            switch (user.getRole()) {
                case MANAGER:
                    new ManagerView(scanner, tableService, menuItemService).start();
                    break;
                case CHEF:
                    new ChefView(scanner).start();
                    break;
                case CUSTOMER:
                    new CustomerView(scanner,
                            tableService,
                            menuItemService,
                            orderService,
                            user.getId()).start();
                    break;
            }

        } catch (AppException e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
    }

}
