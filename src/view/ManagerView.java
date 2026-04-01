package view;

import exception.AppException;
import model.constants.Role;
import model.entity.Order;
import model.entity.User;
import service.MenuItemService;
import service.OrderService;
import service.TableService;
import service.UserService;
import utils.Color;
import utils.TablePrinter;
import validation.InputValidator;
import validation.UserValidator;
import view.manager.MenuItemManagementView;
import view.manager.TableManagementView;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ManagerView {
    private final Scanner scanner;
    private final TableService tableService;
    private final MenuItemService menuItemService;
    private final OrderService orderService;
    private final UserService userService;

    public ManagerView(Scanner scanner,
                       TableService tableService,
                       MenuItemService menuItemService,
                       OrderService orderService, UserService userService) {
        this.scanner = scanner;
        this.tableService = tableService;
        this.menuItemService = menuItemService;
        this.orderService = orderService;
        this.userService = userService;
    }

    public void start() {
        while (true) {
            printMenu();
            int choice = InputValidator.inputInt(scanner, "Chọn: ");

            switch (choice) {
                case 1:
                    manageTables();
                    break;
                case 2:
                    manageMenu();
                    break;
                case 3:
                    manageUsers();
                    break;
                case 4:
                    approveOrders();
                    break;
                case 5:
                    statistics();
                    break;
                case 0:
                    System.out.println("Đăng xuất Manager...");
                    return;
                default:
                    Color.printWarning("Lựa chọn không hợp lệ");
            }
        }
    }

    private void printMenu() {
        System.out.println("""
                \n===== MANAGER MENU =====
                1. Quản lý bàn
                2. Quản lý menu
                3. Quản lý người dùng
                4. Duyệt order
                5. Thống kê
                0. Đăng xuất
                """);
    }

    private void manageTables() {
        new TableManagementView(scanner, tableService).start();
    }

    private void manageMenu() {
        new MenuItemManagementView(scanner, menuItemService).start();
    }

    private void manageUsers() {
        while (true){
            System.out.println("""
                
                ===== USER MANAGEMENT =====
                1. Xem danh sách user
                2. Tạo tài khoản Chef
                3. Ban/Unban user
                0. Quay lại
                """);
            int choice = InputValidator.inputInt(scanner, "Chọn: ");

            switch (choice) {
                case 1:
                    viewUsers();
                    break;
                case 2:
                    createChef();
                    break;
                case 3:
                    toggleUser();
                    break;
                case 0:
                    return;
                default:
                    Color.printWarning("Lựa chọn không hợp lệ");
            }
        }
    }

    private void viewUsers() {
        List<User> list = userService.getCustomerChef();

        if (list.isEmpty()) {
            System.out.println("Không có user");
            return;
        }

        TablePrinter.printTable(
                User.getHeader(),
                list.stream().map(User::toRow).collect(Collectors.toList())
        );
    }

    private void createChef() {
        try {
            String username = InputValidator.inputString(scanner, "Username: ");
            String password = InputValidator.inputString(scanner, "Password: ");
            String fullName = InputValidator.inputString(scanner, "Full name: ");

            User user = new User(username,password, fullName, Role.CHEF );
            userService.insertChef(user);
            Color.printSuccess("Tạo tài khoản Chef thành công!");

        } catch (AppException e) {
            Color.printError("Lỗi: " + e.getMessage());
        }
    }

    private void toggleUser() {
        try {
            viewUsers();
            int id = InputValidator.inputInt(scanner, "Nhập ID user: ");

            userService.toggleUserStatus(id);
            Color.printSuccess("Cập nhật trạng thái thành công!");

        } catch (AppException e) {
            Color.printError("Lỗi: " + e.getMessage());
        }
    }

    private void approveOrders() {
        try {
            System.out.println("\n===== ORDER CHỜ DUYỆT =====");

            var list = orderService.getPendingOrders();
            if (list.isEmpty()) {
                System.out.println("Không có order nào cần duyệt");
                return;
            }
            TablePrinter.printTable(
                    Order.getHeaders(),
                    list.stream().map(Order::toRow).toList()
            );

            int orderId = InputValidator.inputInt(scanner, "Nhập OrderID để duyệt (0 để thoát): ");
            if (orderId == 0) return;

            orderService.approveOrder(orderId);

            Color.printSuccess("Duyệt order thành công!");

        } catch (Exception e) {
            Color.printError("Lỗi: " + e.getMessage());
        }
    }

    private void statistics() {
        System.out.println("""
                \n===== THỐNG KÊ =====
                1. Doanh thu
                2. Món bán chạy
                0. Quay lại
                """);
        int choice = InputValidator.inputInt(scanner, "Chọn: ");
        switch (choice) {
            case 1:
                System.out.println("Chưa làm chức năng này");
                break;
            case 2:
                System.out.println("Chưa làm chức năng này");
                break;
            case 0:
                return;
            default:
                Color.printWarning("Lựa chọn không hợp lệ");
        }
    }
}