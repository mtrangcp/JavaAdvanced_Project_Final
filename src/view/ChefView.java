package view;

import exception.AppException;
import model.entity.OrderDetail;
import service.OrderDetailService;
import utils.Color;
import utils.TablePrinter;
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
                    Color.printWarning("Lựa chọn không hợp lệ");
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
        TablePrinter.printTable(
                OrderDetail.getHeaders(),
                list.stream().map(OrderDetail::toRow).toList()
        );
    }

    private void updateStatus() {
        try {
            viewItems();

            int id = InputValidator.inputInt(scanner, "Nhập ID món (0 để thoát): ");
            if (id == 0) return;

            orderDetailService.updateStatus(id);
            Color.printSuccess("Cập nhật trạng thái thành công!");

        } catch (AppException e) {
            Color.printError("Lỗi: " + e.getMessage());
        }
    }
}