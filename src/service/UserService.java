package service;

import dao.UserDAO;
import exception.AppException;
import model.constants.Role;
import model.entity.User;
import utils.PasswordHasher;
import validation.UserValidator;

public class UserService {
    private final UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public boolean register(String username, String password, String fullName) {

        UserValidator.validateRegister(username, password, fullName);

        if (userDAO.findByUsername(username) != null) {
            throw new AppException("Username đã tồn tại");
        }

        String hashedPassword = PasswordHasher.hashPassword(password);

        User user = new User(username, hashedPassword, fullName, Role.CUSTOMER);

        boolean success = userDAO.insert(user);

        if (!success) {
            throw new AppException("Đăng ký thất bại");
        }

        return true;
    }

    public User login(String username, String password) {
        UserValidator.validateLogin(username, password);

        User user = userDAO.findByUsername(username);

        if (user == null) {
            throw new AppException("User không tồn tại");
        }

        if (!user.isActive()) {
            throw new AppException("Tài khoản đã bị khóa");
        }

        String hashedPassword = PasswordHasher.hashPassword(password);

        if (!user.getPassword().equals(hashedPassword)) {
            throw new AppException("Sai mật khẩu");
        }

        return user;
    }


}
