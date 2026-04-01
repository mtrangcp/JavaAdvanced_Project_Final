package view.manager;

import exception.AppException;
import model.constants.ItemStatus;
import model.constants.ItemType;
import model.entity.MenuItem;
import service.MenuItemService;
import utils.Color;
import utils.TablePrinter;
import validation.InputValidator;

import java.util.List;
import java.util.Scanner;

public class MenuItemManagementView {
    private final Scanner scanner;
    private final MenuItemService menuItemService;
    public MenuItemManagementView(Scanner scanner, MenuItemService menuItemService) {
        this.scanner = scanner;
        this.menuItemService = menuItemService;
    }

    public void start() {

        while (true) {
            System.out.println("\n===== MENU ITEM MANAGEMENT =====");
            System.out.println("1. Thêm món");
            System.out.println("2. Xem tất cả");
            System.out.println("3. Tìm theo loại");
            System.out.println("4. Cập nhật");
            System.out.println("5. Xóa");
            System.out.println("0. Quay lại");
            int choice = InputValidator.inputInt(scanner, "Chọn: ");

            try {
                switch (choice) {
                    case 1:
                        create();
                        break;
                    case 2:
                        show(menuItemService.getAll());
                        break;
                    case 3:
                        findByType();
                        break;
                    case 4:
                        update();
                        break;
                    case 5:
                        delete();
                        break;
                    case 0:
                        return;
                    default:
                        Color.printWarning("Lựa chọn không hợp lệ");
                }
            } catch (AppException e) {
                Color.printError("Lỗi: " + e.getMessage());
            } catch (Exception e) {
                Color.printError("Lỗi hệ thống!");
            }
        }
    }

    private void create() {
        String name = InputValidator.inputString(scanner, "Tên: ");
        double price = InputValidator.inputDouble(scanner, "Giá: ");

        ItemType type = null;
        while (type == null) {
            try {
                String input = InputValidator.inputString(scanner, "Loại (FOOD/DRINK): ");
                type = ItemType.valueOf(input.toUpperCase());
            } catch (IllegalArgumentException e) {
                Color.printError("Loại không hợp lệ! Chỉ nhập FOOD hoặc DRINK");
            }
        }

        Integer stock = null;
        if (type == ItemType.DRINK) {
            stock = InputValidator.inputInt(scanner, "Stock: ");
        }

        menuItemService.create(name, price, stock, type);
        Color.printSuccess("Thêm thành công!");
    }

    private void show(List<MenuItem> list) {
        if (list.isEmpty()) {
            System.out.println("Danh sách trống");
            return;
        }
        TablePrinter.printTable(
                MenuItem.getHeaders(),
                list.stream().map(MenuItem::toRow).toList()
        );
    }

    private void findByType() {
        ItemType type = InputValidator.inputEnum(scanner, "Loại (FOOD/DRINK): ", ItemType.class);
        show(menuItemService.getByType(type));
    }

    private void update() {
        int id = InputValidator.inputInt(scanner, "ID: ");
        String name = InputValidator.inputString(scanner, "Tên: ");
        double price = InputValidator.inputDouble(scanner, "Giá: ");
        ItemType type = InputValidator.inputEnum(scanner, "Loại (FOOD/DRINK): ", ItemType.class);

        Integer stock = null;
        if (type == ItemType.DRINK) {
            System.out.print("Stock: ");
            stock = Integer.parseInt(scanner.nextLine());
        }
        ItemStatus status = InputValidator.inputEnum(scanner, "Status: ", ItemStatus.class);

        menuItemService.update(id, name, price, stock, type, status);
        Color.printSuccess("Cập nhật thành công!");
    }

    private void delete() {
        int id = InputValidator.inputInt(scanner, "ID: ");

        menuItemService.delete(id);
        Color.printSuccess("Xóa thành công!");
    }

}
