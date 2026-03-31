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

    public void occupyTable(int tableId) {
        Table table = findById(tableId);
        if (table.getStatus() != TableStatus.AVAILABLE) {
            throw new AppException("Bàn đã có người hoặc không khả dụng");
        }

        boolean success = tableDAO.updateStatus(tableId, TableStatus.OCCUPIED);
        if (!success) {
            throw new AppException("Không thể cập nhật trạng thái bàn");
        }
    }

//    public boolean delete(int tableId) {
//
//        Table table = findById(tableId);
//
//        if (table.getStatus() == TableStatus.OCCUPIED) {
//            throw new AppException("Không thể xóa bàn đang có khách");
//        }
//
//        boolean success = tableDAO.delete(tableId);
//
//        if (!success) {
//            throw new AppException("Xóa bàn thất bại");
//        }
//
//        return true;
//    }

    public List<Table> getAvailableTables() {
        return tableDAO.findAvailableTables();
    }

    public Table findByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new AppException("Tên bàn không được để trống");
        }

        Table table = tableDAO.findByName(name.trim());

        if (table == null) {
            throw new AppException("Không tìm thấy bàn");
        }
        return table;
    }

    public List<Table> findByStatus(TableStatus status) {
        if (status == null) {
            throw new AppException("Trạng thái không hợp lệ");
        }

        List<Table> list = tableDAO.findByStatus(status);

        if (list == null || list.isEmpty()) {
            throw new AppException("Không có bàn nào phù hợp");
        }

        return list;
    }

    public void update(int id, String name, int capacity, TableStatus status) {
        if (id <= 0) {
            throw new AppException("ID không hợp lệ");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new AppException("Tên bàn không được để trống");
        }
        if (capacity <= 0) {
            throw new AppException("Sức chứa phải > 0");
        }
        if (status == null) {
            throw new AppException("Trạng thái không hợp lệ");
        }
        Table existing = tableDAO.findById(id);
        if (existing == null) {
            throw new AppException("Bàn không tồn tại");
        }

        existing.setTableName(name.trim());
        existing.setCapacity(capacity);
        existing.setStatus(status);

        boolean success = tableDAO.update(existing);

        if (!success) {
            throw new AppException("Cập nhật thất bại");
        }
    }

}
