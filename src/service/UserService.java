package service;

import dao.UserDAO;
import exception.AppException;
import model.constants.Role;
import model.entity.User;
import utils.PasswordHasher;
import validation.UserValidator;

import java.util.List;

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

    public boolean insertChef(User user) {
        if ( user == null){
            throw new AppException("Dữ liệu không hợp lệ");
        }
        UserValidator.validateRegister(user.getUsername(), user.getPassword(), user.getFullName());

        if (userDAO.findByUsername(user.getUsername()) != null) {
            throw new AppException("Username đã tồn tại");
        }

        String hashedPassword = PasswordHasher.hashPassword(user.getPassword());
        user.setPassword(hashedPassword);

        user.setRole(Role.CHEF);
        user.setActive(true);

        boolean success = userDAO.insert(user);
        if (!success) {
            throw new AppException("Tạo tài khoản Chef thất bại");
        }

        return true;
    }

    public List<User> getAllUsers() {
        return userDAO.findAll();
    }

    public void toggleUserStatus(int userId) {
        User user = userDAO.findById(userId);

        if (user == null) {
            throw new AppException("Không tìm thấy user");
        }

        user.setActive(!user.isActive());

        boolean success = userDAO.update(user);
        if (!success) {
            throw new AppException("Cập nhật trạng thái thất bại");
        }
    }

}
