package service;

import dao.MenuItemDAO;
import exception.AppException;
import model.constants.ItemStatus;
import model.constants.ItemType;
import model.entity.MenuItem;
import validation.MenuItemValidator;

import java.util.List;

public class MenuItemService {
    private final MenuItemDAO menuItemDAO;

    public MenuItemService(MenuItemDAO menuItemDAO) {
        this.menuItemDAO = menuItemDAO;
    }

    public boolean create(String name, double price, Integer stock, ItemType type) {
        MenuItemValidator.validateCreate(name, price, stock, type);
        MenuItem item = new MenuItem(name, price, stock, type, ItemStatus.AVAILABLE);

        boolean success = menuItemDAO.insert(item);
        if (!success) {
            throw new AppException("Thêm món thất bại");
        }
        return true;
    }

    public List<MenuItem> getAll() {
        return menuItemDAO.findAll();
    }

    public MenuItem findById(int id) {
        MenuItem item = menuItemDAO.findById(id);

        if (item == null) {
            throw new AppException("Không tìm thấy món");
        }

        return item;
    }

    public List<MenuItem> getAvailable() {
        return menuItemDAO.findAvailable();
    }

    public List<MenuItem> getByType(ItemType type) {
        return menuItemDAO.findByType(type);
    }

    public boolean update(int id, String name, double price, Integer stock, ItemType type, ItemStatus status) {

        MenuItemValidator.validateUpdate(name, price, stock, type, status);

        MenuItem existing = findById(id);

        existing.setName(name);
        existing.setPrice(price);
        existing.setStock(stock);
        existing.setType(type);
        existing.setStatus(status);

        boolean success = menuItemDAO.update(existing);

        if (!success) {
            throw new AppException("Cập nhật thất bại");
        }

        return true;
    }

    public boolean delete(int id) {

        findById(id);

        boolean success = menuItemDAO.delete(id);

        if (!success) {
            throw new AppException("Xóa thất bại");
        }

        return true;
    }

    public boolean updateStatus(int id, ItemStatus status) {
        MenuItem item = findById(id);

        if (item.getStatus() == status) {
            throw new AppException("Trạng thái đã là " + status);
        }

        boolean success = menuItemDAO.updateStatus(id, status);

        if (!success) {
            throw new AppException("Cập nhật trạng thái thất bại");
        }

        return true;
    }

    public boolean updateStock(int id, Integer stock) {
        MenuItemValidator.validateStock(stock);

        findById(id);

        boolean success = menuItemDAO.updateStock(id, stock);

        if (!success) {
            throw new AppException("Cập nhật stock thất bại");
        }

        return true;
    }

}
