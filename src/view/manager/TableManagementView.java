package view.manager;

import exception.AppException;
import model.constants.TableStatus;
import model.entity.Table;
import service.TableService;
import utils.Color;
import utils.TablePrinter;
import validation.InputValidator;

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
            int choice = InputValidator.inputInt(scanner, "Chọn: ");

            try {
                switch (choice) {
                    case 1:
                        createTable();
                        break;
                    case 2:
                        showTables(tableService.getAll());
                        break;
                    case 3:
                        findByName();
                        break;
                    case 4:
                        findByStatus();
                        break;
                    case 5:
                        updateTable();
                        break;
                    case 6:
                        deleteTable();
                        break;
                    case 0:
                        return;
                    default:
                        Color.printWarning("Lựa chọn không hợp lệ");
                }
            } catch (AppException e) {
                Color.printError("Lỗi: " + e.getMessage());
            } catch (Exception e) {
                Color.printSuccess("Lỗi hệ thống, vui lòng thử lại!");
            }
        }
    }

    private void createTable() {
        String name = InputValidator.inputString(scanner, "Tên bàn: ");
        int capacity = InputValidator.inputInt(scanner, "Sức chứa: ");

        tableService.createTable(name, capacity);
        Color.printSuccess("Thêm bàn thành công!");
    }

    private void showTables(List<Table> list) {
        if (list.isEmpty()) {
            System.out.println("Danh sách trống");
            return;
        }
        TablePrinter.printTable(
                Table.getHeaders(),
                list.stream().map(Table::toRow).toList()
        );
    }

    private void findByName() {
        String name = InputValidator.inputString(scanner, "Nhập tên: ");
        try {
            Table table = tableService.findByName(name);
            TablePrinter.printTable(
                    Table.getHeaders(),
                    List.of(table).stream().map(Table::toRow).toList()
            );

        } catch (AppException e) {
            Color.printWarning("Không tìm thấy");
            Color.printError(e.getMessage());
        }
    }

    private void findByStatus() {
        TableStatus status = InputValidator.inputEnum(
                scanner,
                "Nhập trạng thái (AVAILABLE/OCCUPIED/INACTIVE): ",
                TableStatus.class
        );

        List<Table> list = tableService.findByStatus(status);
        showTables(list);
    }

    private void updateTable() {
        int id = InputValidator.inputInt(scanner, "ID: ");
        String name = InputValidator.inputString(scanner, "Tên mới: ");
        int capacity = InputValidator.inputInt(scanner, "Sức chứa: ");
        TableStatus status = InputValidator.inputEnum(
                scanner,
                "Trạng thái (AVAILABLE/OCCUPIED/INACTIVE): ",
                TableStatus.class
        );

        tableService.update(id, name, capacity, status);
        Color.printSuccess("Cập nhật thành công!");
    }

    private void deleteTable() {
        int id = InputValidator.inputInt(scanner, "ID cần xóa: ");

        tableService.updateStatus(id, TableStatus.INACTIVE );
        Color.printSuccess("Xóa thành công!");
    }

}
