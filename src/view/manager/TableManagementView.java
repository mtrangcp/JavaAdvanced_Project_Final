package view.manager;

import exception.AppException;
import model.constants.TableStatus;
import model.entity.Table;
import service.TableService;

import java.util.List;
import java.util.Scanner;


public class TableManagementView {
    private final Scanner scanner;
    private final TableService tableService;

    public TableManagementView(Scanner scanner, TableService tableService) {
        this.scanner = scanner;
        this.tableService = tableService;
    }

    public void start() {

        while (true) {
            System.out.println("\n===== TABLE MANAGEMENT =====");
            System.out.println("1. Thêm bàn");
            System.out.println("2. Xem danh sách");
            System.out.println("3. Tìm theo tên");
            System.out.println("4. Tìm theo trạng thái");
            System.out.println("5. Cập nhật");
            System.out.println("6. Xóa");
            System.out.println("0. Quay lại");

            System.out.print("Chọn: ");
            String choice = scanner.nextLine();

            try {
                switch (choice) {
                    case "1":
                        createTable();
                        break;
                    case "2":
                        showTables(tableService.getAll());
                        break;
                    case "3":
                        findByName();
                        break;
                    case "4":
                        findByStatus();
                        break;
                    case "5":
                        updateTable();
                        break;
                    case "6":
                        deleteTable();
                        break;
                    case "0":
                        return;
                    default:
                        System.out.println("Lựa chọn không hợp lệ");
                }
            } catch (AppException e) {
                System.out.println("Lỗi: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Lỗi hệ thống, vui lòng thử lại!");
            }
        }
    }

    //

    private void createTable() {
        System.out.print("Tên bàn: ");
        String name = scanner.nextLine();

        System.out.print("Sức chứa: ");
        int capacity = Integer.parseInt(scanner.nextLine());

        tableService.createTable(name, capacity);
        System.out.println("Thêm bàn thành công!");
    }

    private void showTables(List<Table> list) {
        if (list.isEmpty()) {
            System.out.println("Danh sách trống");
            return;
        }
        list.forEach(System.out::println);
    }

    private void findByName() {
        System.out.print("Nhập tên: ");
        String name = scanner.nextLine();

        Table table = tableService.findByName(name);

        if (table == null) {
            System.out.println("Không tìm thấy");
        } else {
            System.out.println(table);
        }
    }

    private void findByStatus() {
        System.out.print("Nhập trạng thái (AVAILABLE/OCCUPIED): ");
        TableStatus status = TableStatus.valueOf(scanner.nextLine().toUpperCase());

        List<Table> list = tableService.findByStatus(status);
        showTables(list);
    }

    private void updateTable() {
        System.out.print("ID: ");
        int id = Integer.parseInt(scanner.nextLine());

        System.out.print("Tên mới: ");
        String name = scanner.nextLine();

        System.out.print("Sức chứa: ");
        int capacity = Integer.parseInt(scanner.nextLine());

        System.out.print("Trạng thái: ");
        TableStatus status = TableStatus.valueOf(scanner.nextLine().toUpperCase());

        tableService.update(id, name, capacity, status);
        System.out.println("Cập nhật thành công!");
    }

    private void deleteTable() {
        System.out.print("ID cần xóa: ");
        int id = Integer.parseInt(scanner.nextLine());

        tableService.delete(id);
        System.out.println("Xóa thành công!");
    }


}
