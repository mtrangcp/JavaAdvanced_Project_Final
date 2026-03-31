package view;

import exception.AppException;
import model.entity.OrderDetail;
import service.OrderDetailService;
import validation.InputValidator;

import java.util.List;
import java.util.Scanner;

public class ChefView {
    private final Scanner scanner;
    private final OrderDetailService orderDetailService;

    public ChefView(Scanner scanner, OrderDetailService orderDetailService) {
        this.scanner = scanner;
        this.orderDetailService = orderDetailService;
    }

    public void start() {
        while (true) {
            System.out.println("""
                    \n===== CHEF MENU =====
                    1. Xem danh sách món
                    2. Cập nhật trạng thái món
                    0. Đăng xuất
                    """);

            int choice = InputValidator.inputInt(scanner, "Chọn: ");

            switch (choice) {
                case 1:
                    viewItems();
                    break;
                case 2:
                    updateStatus();
                    break;
                case 0:
                    System.out.println("Đăng xuất Chef...");
                    return;
                default:
                    System.out.println("Sai lựa chọn");
            }
        }
    }

    private void viewItems() {
        List<OrderDetail> list = orderDetailService.getActiveItems();

        if (list.isEmpty()) {
            System.out.println("Không có món nào");
            return;
        }

        System.out.println("\n===== DANH SÁCH MÓN =====");
        System.out.printf("%-5s %-8s %-8s %-8s %-12s\n",
                "ID", "Order", "Item", "Qty", "Status");

        for (OrderDetail od : list) {
            System.out.printf("%-5d %-8d %-8d %-8d %-12s\n",
                    od.getId(),
                    od.getOrderId(),
                    od.getItemId(),
                    od.getQuantity(),
                    od.getStatus());
        }
    }

    private void updateStatus() {
        try {
            viewItems();

            int id = InputValidator.inputInt(scanner, "Nhập ID món (0 để thoát): ");
            if (id == 0) return;

            orderDetailService.updateStatus(id);

            System.out.println("Cập nhật trạng thái thành công!");

        } catch (AppException e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
    }
}