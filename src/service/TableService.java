package service;

import dao.TableDAO;
import exception.AppException;
import model.constants.TableStatus;
import model.entity.Table;
import validation.TableValidator;

import java.util.List;

public class TableService {
    private final TableDAO tableDAO;

    public TableService(TableDAO tableDAO) {
        this.tableDAO = tableDAO;
    }

    public boolean createTable(String tableName, int capacity) {

        TableValidator.validateCreate(tableName, capacity);

        if (tableDAO.findByName(tableName) != null) {
            throw new AppException("Tên bàn đã tồn tại");
        }

        Table table = new Table(tableName, capacity, TableStatus.AVAILABLE);

        boolean success = tableDAO.insert(table);

        if (!success) {
            throw new AppException("Tạo bàn thất bại");
        }

        return true;
    }

    public List<Table> getAll() {
        return tableDAO.findAll();
    }

    public Table findById(int id) {
        Table table = tableDAO.findById(id);

        if (table == null) {
            throw new AppException("Không tìm thấy bàn");
        }

        return table;
    }

    public boolean updateStatus(int tableId, TableStatus status) {

        Table table = findById(tableId);

        if (table.getStatus() == status) {
            throw new AppException("Trạng thái bàn đã là " + status);
        }

        boolean success = tableDAO.updateStatus(tableId, status);

        if (!success) {
            throw new AppException("Cập nhật trạng thái thất bại");
        }

        return true;
    }

    public boolean delete(int tableId) {

        Table table = findById(tableId);

        if (table.getStatus() == TableStatus.OCCUPIED) {
            throw new AppException("Không thể xóa bàn đang có khách");
        }

        boolean success = tableDAO.delete(tableId);

        if (!success) {
            throw new AppException("Xóa bàn thất bại");
        }

        return true;
    }

    public List<Table> getAvailableTables() {
        return tableDAO.findByStatus(TableStatus.AVAILABLE);
    }
}
