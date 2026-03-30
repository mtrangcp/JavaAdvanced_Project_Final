import dao.*;
import db.DbConnection;
import service.MenuItemService;
import service.OrderService;
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
        OrderDAO orderDAO = new OrderDAO(conn);
        OrderDetailDAO  orderDetailDAO = new OrderDetailDAO(conn);

        UserService userService = new UserService(userDAO);
        TableService tableService = new TableService(tableDAO);
        MenuItemService menuItemService = new MenuItemService(menuItemDAO);
        OrderService orderService = new OrderService(conn, orderDAO, orderDetailDAO, menuItemDAO);

        AuthView authView = new AuthView(userService,tableService, menuItemService, orderService, scanner);

        authView.start();
    }
}