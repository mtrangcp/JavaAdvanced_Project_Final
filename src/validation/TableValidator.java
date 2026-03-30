package validation;

import exception.AppException;

public class TableValidator {
    public static void validateCreate(String tableName, int capacity) {

        if (tableName == null || tableName.trim().isEmpty()) {
            throw new AppException("Tên bàn không được để trống");
        }

        if (tableName.length() > 10) {
            throw new AppException("Tên bàn tối đa 10 ký tự");
        }

        if (!tableName.matches("^[A-Za-z0-9]+$")) {
            throw new AppException("Tên bàn không được chứa ký tự đặc biệt");
        }

        if (capacity <= 0) {
            throw new AppException("Sức chứa phải > 0");
        }
    }
}
