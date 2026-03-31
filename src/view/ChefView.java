package view;

import exception.AppException;
import model.entity.OrderDetail;
import service.OrderDetailService;
import validation.InputValidator;

import java.util.List;
import java.util.Scanner;

public class ChefView {
    private final Scanner scanner;
    private final OrderDetailService  orderDetailService;

    public ChefView(Scanner scanner, OrderDetailService orderDetailService) {
        this.scanner = scanner;
        this.orderDetailService = orderDetailService;
    }

    public void start() {
        while (true) {
            System.out.println("\n===== CHEF MENU =====");
            System.out.println("1. Xem món đang chờ (PENDING)");
            System.out.println("2. Cập nhật trạng thái món");
            System.out.println("0. Thoát");

            int choice = InputValidator.inputInt(scanner, "Chọn: ");

            switch (choice) {
                case 1:
                    viewPending();
                    break;
                case 2:
                    updateStatus();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Sai lựa chọn");
            }
        }
    }

    private void viewPending() {
        List<OrderDetail> list = orderDetailService.getPendingItems();

        if (list.isEmpty()) {
            System.out.println("Không có món nào đang chờ");
            return;
        }

        System.out.println("\n===== DANH SÁCH PENDING =====");
        for (OrderDetail od : list) {
            System.out.printf("ID: %d | Order: %d | Item: %d | Qty: %d | Status: %s\n",
                    od.getId(),
                    od.getOrderId(),
                    od.getItemId(),
                    od.getQuantity(),
                    od.getStatus());
        }
    }

    private void updateStatus() {
        try {
            int id = InputValidator.inputInt(scanner, "Nhập ID món: ");

            orderDetailService.updateStatus(id);
            System.out.println("Cập nhật thành công!");
        } catch (AppException e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
    }


}
