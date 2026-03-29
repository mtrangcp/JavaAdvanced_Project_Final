package dao;

import db.DbConnection;
import model.constants.ItemType;
import model.entity.MenuItem;
import utils.Color;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MenuItemDAO {

    public boolean addMenuItem(MenuItem menuItem) {
        if (menuItem.getPrice() <= 0) {
            System.out.println(Color.RED + "Giá tiền không hợp lệ! add DAO" + Color.RESET);
            return false;
        }

        String sql = "INSERT INTO menu_items (name, price, stock, type) VALUES (?, ?, ?, ?)";

        try (
                Connection conn = DbConnection.openConnection();
                PreparedStatement pstmt = Objects.requireNonNull(conn).prepareStatement(sql);
        ) {
            pstmt.setString(1, menuItem.getName());
            pstmt.setDouble(2, menuItem.getPrice());
            pstmt.setInt(3, menuItem.getStock());
            pstmt.setString(4, String.valueOf(menuItem.getType()));

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateMenuItem(MenuItem menuItem) {
        if (menuItem.getPrice() <= 0) {
            System.out.println(Color.RED + "Giá tiền không hợp lệ! update DAO" + Color.RESET);
            return false;
        }

        String sql = "UPDATE menu_items SET name = ?, price = ?, stock = ?, type = ? WHERE id = ?";
        try (Connection conn = DbConnection.openConnection();
             PreparedStatement pstmt = Objects.requireNonNull(conn).prepareStatement(sql)) {

            pstmt.setString(1, menuItem.getName());
            pstmt.setDouble(2, menuItem.getPrice());
            pstmt.setInt(3, menuItem.getStock());
            pstmt.setString(4, menuItem.getType().name());
            pstmt.setInt(5, menuItem.getId());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteMenuItem(int id) {
        String sql = "DELETE FROM menu_items WHERE id = ?";

        try (Connection conn = DbConnection.openConnection();
             PreparedStatement pstmt = Objects.requireNonNull(conn).prepareStatement(sql)) {

            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<MenuItem> getAll() {
        List<MenuItem> list = new ArrayList<>();
        String sql = "SELECT * FROM menu_items";

        try (Connection conn = DbConnection.openConnection();
             PreparedStatement pstmt = Objects.requireNonNull(conn).prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                list.add(mapResultSetToEntity(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<MenuItem> findByName(String name) {
        List<MenuItem> list = new ArrayList<>();
        String sql = "SELECT * FROM menu_items WHERE name LIKE ?";

        try (Connection conn = DbConnection.openConnection();
             PreparedStatement pstmt = Objects.requireNonNull(conn).prepareStatement(sql)) {

            pstmt.setString(1, "%" + name + "%");
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToEntity(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }


    private MenuItem mapResultSetToEntity(ResultSet rs) throws SQLException {
        return new MenuItem(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getDouble("price"),
                rs.getInt("stock"),
                ItemType.valueOf(rs.getString("type"))
        );
    }

}
