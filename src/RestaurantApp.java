import dao.MenuItemDAO;
import dao.TableDAO;
import dao.UserDAO;
import db.DbConnection;
import service.MenuItemService;
import service.TableService;
import service.UserService;
import view.AuthView;

import java.sql.Connection;
import java.util.Scanner;

public class RestaurantApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Connection conn = DbConnection.openConnection();

        UserDAO userDAO = new UserDAO(conn);
        TableDAO tableDAO = new TableDAO(conn);
        MenuItemDAO menuItemDAO = new MenuItemDAO(conn);

        UserService userService = new UserService(userDAO);
        TableService tableService = new TableService(tableDAO);
        MenuItemService menuItemService = new MenuItemService(menuItemDAO);

        AuthView authView = new AuthView(userService,tableService, menuItemService, scanner);

        authView.start();
    }
}