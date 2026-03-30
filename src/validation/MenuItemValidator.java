package validation;

import exception.AppException;
import model.constants.ItemStatus;
import model.constants.ItemType;

public class MenuItemValidator {
    public static void validateCreate(String name, double price, Integer stock, ItemType type) {

        if (name == null || name.trim().isEmpty()) {
            throw new AppException("Tên món không được rỗng");
        }

        if (name.length() > 100) {
            throw new AppException("Tên món tối đa 100 ký tự");
        }

        if (price <= 0) {
            throw new AppException("Giá phải > 0");
        }

        if (type == null) {
            throw new AppException("Loại món không hợp lệ");
        }

        if (type == ItemType.DRINK) {
            if (stock == null || stock < 0) {
                throw new AppException("Đồ uống phải có stock >= 0");
            }
        }
    }

    public static void validateUpdate(String name, double price, Integer stock, ItemType type, ItemStatus status) {
        validateCreate(name, price, stock, type);

        if (status == null) {
            throw new AppException("Status không hợp lệ");
        }
    }

    public static void validateStock(Integer stock) {
        if (stock != null && stock < 0) {
            throw new AppException("Stock phải >= 0");
        }
    }


}
