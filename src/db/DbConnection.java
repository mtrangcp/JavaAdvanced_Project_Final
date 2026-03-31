package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/restaurant_management";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "123456";

    public static Connection openConnection() {

        try {
            Class.forName(DRIVER);
            System.out.println("Kết nối db thành công!");
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.err.println("Chưa cài đặt MySql Driver");
        } catch (SQLException e) {
            System.err.println("Lỗi SQL : Kết nối DB thất bại");
            e.printStackTrace();
        }
        return null;
    }

}
