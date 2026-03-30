import dao.UserDAO;
import db.DbConnection;
import service.UserService;
import view.AuthView;

import java.sql.Connection;
import java.util.Scanner;

public class RestaurantApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Connection conn = DbConnection.openConnection();

        UserDAO userDAO = new UserDAO(conn);
        UserService userService = new UserService(userDAO);

        AuthView authView = new AuthView(userService, scanner);

        authView.start();
    }
}