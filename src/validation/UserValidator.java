package validation;

import exception.AppException;

public class UserValidator {
    public static void validateRegister(String username, String password, String fullName) {

        if (username == null || username.trim().isEmpty()) {
            throw new AppException("Username không được để trống");
        }

        if (!username.matches("^[a-zA-Z0-9_]{6,20}$")) {
            throw new AppException("Username phải 6-20 ký tự, không chứa ký tự đặc biệt");
        }

        if (password == null || password.trim().isEmpty()) {
            throw new AppException("Password không được để trống");
        }

        if (password.length() < 6) {
            throw new AppException("Password phải >= 6 ký tự");
        }

        if (fullName == null || fullName.trim().isEmpty()) {
            throw new AppException("Họ tên không được để trống");
        }

        if (fullName.length() > 100) {
            throw new AppException("Họ tên tối đa 100 ký tự");
        }
    }

    public static void validateLogin(String username, String password) {

        if (username == null || username.trim().isEmpty()) {
            throw new AppException("Username không được để trống");
        }

        if (password == null || password.trim().isEmpty()) {
            throw new AppException("Password không được để trống");
        }
    }

}
