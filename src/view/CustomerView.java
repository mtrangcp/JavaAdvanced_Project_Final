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

    private final int userId; // truyền từ login

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
            System.out.println("\n===== CUSTOMER MENU =====");
            System.out.println("1. Xem menu");
            System.out.println("2. Gọi món");
            System.out.println("3. Xem order của tôi");
            System.out.println("0. Đăng xuất");

            int choice = InputValidator.inputInt(scanner, "Chọn: ");

            switch (choice) {
                case 1:
                    viewMenu();
                    break;
                case 2:
                    orderFood();
                    break;
                case 3:
                    viewMyOrders();
                    break;
                case 0:
                    System.out.println("Thoát menu customer");
                    return;
                default:
                    System.out.println("Sai lựa chọn");
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
        for (MenuItem m : list) {
            System.out.printf("%d | %s | %.2f\n",
                    m.getId(),
                    m.getName(),
                    m.getPrice());
        }
    }

    private void orderFood() {
        try {
            // chon ban
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

            // update trạng thái bàn
            tableService.occupyTable(tableId);

            // tạo order
            int orderId = orderService.createOrder(userId, tableId);

            // ===== 2. GỌI MÓN =====
            while (true) {
                viewMenu();

                int itemId = InputValidator.inputInt(scanner, "Chọn món (0 để thoát): ");

                if (itemId == 0) break;

                int quantity = InputValidator.inputInt(scanner, "Số lượng: ");

                orderService.addItem(orderId, itemId, quantity);

                System.out.println("Đã thêm món!");
            }

            System.out.println("Hoàn tất gọi món!");

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
}