package view;

import exception.AppException;
import model.entity.MenuItem;
import model.entity.Order;
import model.entity.Table;
import service.MenuItemService;
import service.OrderService;
import service.TableService;
import validation.InputValidator;

import java.util.List;
import java.util.Scanner;

public class CustomerView {
    private final Scanner scanner;
    private final TableService tableService;
    private final MenuItemService menuItemService;
    private final OrderService orderService;
    private final int userId;

    private Integer currentTableId = null;
    private Integer currentOrderId = null;

    public CustomerView(Scanner scanner,
                        TableService tableService,
                        MenuItemService menuItemService,
                        OrderService orderService,
                        int userId) {
        this.scanner = scanner;
        this.tableService = tableService;
        this.menuItemService = menuItemService;
        this.orderService = orderService;
        this.userId = userId;
    }

    public void start() {
        while (true) {
            System.out.println("""
                    \n===== CUSTOMER MENU =====
                    1. Xem menu
                    2. Chọn bàn
                    3. Gọi món
                    4. Xem order của tôi
                    5. Hủy order
                    6. Thanh toán
                    0. Đăng xuất
                    """);

            int choice = InputValidator.inputInt(scanner, "Chọn: ");

            switch (choice) {
                case 1:
                    viewMenu();
                    break;
                case 2:
                    chooseTable();
                    break;
                case 3:
                    orderFood();
                    break;
                case 4:
                    viewMyOrders();
                    break;
                case 5:
                    cancelOrder();
                    break;
                case 6:
                    checkout();
                    break;
                case 0:
                    System.out.println("Đăng xuất Customer...");
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ");
            }
        }
    }

    private void viewMenu() {
        List<MenuItem> list = menuItemService.getAvailable();

        if (list.isEmpty()) {
            System.out.println("Menu trống");
            return;
        }

        System.out.println("\n===== MENU =====");
        System.out.printf("%-5s %-20s %-10s\n", "ID", "Tên", "Giá");

        for (MenuItem m : list) {
            System.out.printf("%-5d %-20s %-10.2f\n",
                    m.getId(),
                    m.getName(),
                    m.getPrice());
        }
    }

    private void chooseTable() {
        try {
            List<Table> tables = tableService.getAvailableTables();
            if (tables.isEmpty()) {
                System.out.println("Không có bàn trống");
                return;
            }

            System.out.println("\n===== BÀN TRỐNG =====");
            for (Table t : tables) {
                System.out.printf("%d | %s | capacity: %d\n",
                        t.getId(),
                        t.getTableName(),
                        t.getCapacity());
            }

            int tableId = InputValidator.inputInt(scanner, "Chọn bàn: ");
            int orderId = orderService.createOrder(userId, tableId);

            currentTableId = tableId;
            currentOrderId = orderId;

            System.out.println("Chọn bàn thành công! Order ID: " + orderId);

        } catch (AppException e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
    }

    private void orderFood() {
        if (currentOrderId == null) {
            System.out.println("Bạn chưa chọn bàn!");
            return;
        }

        try {
            while (true) {
                viewMenu();

                int itemId = InputValidator.inputInt(scanner, "Chọn món (0 để thoát): ");
                if (itemId == 0) break;

                int quantity = InputValidator.inputInt(scanner, "Số lượng: ");
                orderService.addItem(currentOrderId, itemId, quantity);

                System.out.println("Đã thêm món!");
            }

        } catch (AppException e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
    }

    private void viewMyOrders() {
        List<Order> list = orderService.getOrdersByUser(userId);

        if (list.isEmpty()) {
            System.out.println("Bạn chưa có order nào");
            return;
        }

        System.out.println("\n===== ORDER CỦA TÔI =====");
        for (Order o : list) {
            System.out.printf("OrderID: %d | Table: %d | Status: %s | Time: %s\n",
                    o.getId(),
                    o.getTableId(),
                    o.getStatus(),
                    o.getCreatedAt());
        }
    }

    private void cancelOrder() {
        try {
            int orderId = InputValidator.inputInt(scanner, "Nhập OrderID cần hủy: ");
            orderService.cancelOrder(orderId);

            if (currentOrderId != null && currentOrderId == orderId) {
                currentOrderId = null;
                currentTableId = null;
            }

            System.out.println("Hủy order thành công!");

        } catch (AppException e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
    }

    private void checkout() {
        try {
            int orderId = InputValidator.inputInt(scanner, "Nhập OrderID thanh toán: ");
            double total = orderService.checkout(orderId);

            System.out.println("Thanh toán thành công!");
            System.out.println("Tổng tiền: " + total);

            if (currentOrderId != null && currentOrderId == orderId) {
                currentOrderId = null;
                currentTableId = null;
            }

        } catch (AppException e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
    }
}