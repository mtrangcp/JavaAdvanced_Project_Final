package view;

import exception.AppException;
import model.entity.User;
import service.*;
import utils.Color;
import validation.InputValidator;

import java.util.Scanner;

public class AuthView {
    private final UserService userService;
    private final Scanner scanner;
    private final TableService tableService;
    private final MenuItemService menuItemService;
    private final OrderService orderService;
    private final OrderDetailService orderDetailService;

    public AuthView(UserService userService,
                    TableService tableService,
                    MenuItemService menuItemService,
                    OrderService orderService,
                    OrderDetailService orderDetailService,
                    Scanner scanner
                    ) {
        this.userService = userService;
        this.tableService = tableService;
        this.menuItemService = menuItemService;
        this.scanner = scanner;
        this.orderService = orderService;
        this.orderDetailService = orderDetailService;
    }

    public void start() {
        while (true) {
            System.out.println("\n===== RESTAURANT SYSTEM =====");
            System.out.println("1. Đăng ký");
            System.out.println("2. Đăng nhập");
            System.out.println("0. Thoát");
            int choice = InputValidator.inputInt(scanner, "Chọn: ");

            switch (choice) {
                case 1:
                    register();
                    break;
                case 2:
                    login();
                    break;
                case 0:
                    System.out.println("Thoát...");
                    return;
                default:
                    Color.printWarning("Lựa chọn không hợp lệ");
            }
        }
    }

    private void register() {
        try {
            String username = InputValidator.inputString(scanner, "Username: ");
            String password = InputValidator.inputString(scanner, "Password: ");
            String fullName = InputValidator.inputString(scanner, "Full name: ");

            userService.register(username, password, fullName);
            Color.printSuccess("Đăng ký thành công!");

        } catch (AppException e) {
            Color.printError(e.getMessage());
        }
    }

    private void login() {
        try {
            String username = InputValidator.inputString(scanner, "Username: ");
            String password = InputValidator.inputString(scanner, "Password: ");

            User user = userService.login(username, password);
            Color.printSuccess("Đăng nhập thành công!");

            switch (user.getRole()) {
                case MANAGER:
                    new ManagerView(scanner, tableService, menuItemService, orderService, userService).start();
                    break;
                case CHEF:
                    new ChefView(scanner, orderDetailService).start();
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
            Color.printError("Lỗi: " + e.getMessage());
        }
    }

}
