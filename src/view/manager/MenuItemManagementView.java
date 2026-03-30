package view.manager;

import exception.AppException;
import model.constants.ItemStatus;
import model.constants.ItemType;
import model.entity.MenuItem;
import service.MenuItemService;

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

            System.out.print("Chọn: ");
            String choice = scanner.nextLine();

            try {
                switch (choice) {
                    case "1":
                        create();
                        break;
                    case "2":
                        show(menuItemService.getAll());
                        break;
                    case "3":
                        findByType();
                        break;
                    case "4":
                        update();
                        break;
                    case "5":
                        delete();
                        break;
                    case "0":
                        return;
                    default:
                        System.out.println("Sai lựa chọn");
                }
            } catch (AppException e) {
                System.out.println("Lỗi: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Lỗi hệ thống!");
            }
        }
    }

    private void create() {
        System.out.print("Tên: ");
        String name = scanner.nextLine();

        System.out.print("Giá: ");
        double price = Double.parseDouble(scanner.nextLine());

        System.out.print("Loại (FOOD/DRINK): ");
        ItemType type = ItemType.valueOf(scanner.nextLine().toUpperCase());

        Integer stock = null;
        if (type == ItemType.DRINK) {
            System.out.print("Stock: ");
            stock = Integer.parseInt(scanner.nextLine());
        }

        menuItemService.create(name, price, stock, type);
        System.out.println("Thêm thành công!");
    }

    private void show(List<MenuItem> list) {
        if (list.isEmpty()) {
            System.out.println("Danh sách trống");
            return;
        }
        list.forEach(System.out::println);
    }

    private void findByType() {
        System.out.print("Loại: ");
        ItemType type = ItemType.valueOf(scanner.nextLine().toUpperCase());

        show(menuItemService.getByType(type));
    }

    private void update() {
        System.out.print("ID: ");
        int id = Integer.parseInt(scanner.nextLine());

        System.out.print("Tên: ");
        String name = scanner.nextLine();

        System.out.print("Giá: ");
        double price = Double.parseDouble(scanner.nextLine());

        System.out.print("Loại: ");
        ItemType type = ItemType.valueOf(scanner.nextLine().toUpperCase());

        Integer stock = null;
        if (type == ItemType.DRINK) {
            System.out.print("Stock: ");
            stock = Integer.parseInt(scanner.nextLine());
        }

        System.out.print("Status: ");
        ItemStatus status = ItemStatus.valueOf(scanner.nextLine().toUpperCase());

        menuItemService.update(id, name, price, stock, type, status);
        System.out.println("Cập nhật thành công!");
    }

    private void delete() {
        System.out.print("ID: ");
        int id = Integer.parseInt(scanner.nextLine());

        menuItemService.delete(id);
        System.out.println("Xóa thành công!");
    }

}
