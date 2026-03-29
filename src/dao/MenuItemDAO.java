package dao;

import db.DbConnection;
import model.constants.ItemStatus;
import model.constants.ItemType;
import model.entity.MenuItem;
import utils.Color;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MenuItemDAO {
    private final Connection conn;

    public MenuItemDAO(Connection conn) {
        this.conn = conn;
    }

    public boolean insert(MenuItem item) {
        String sql = "INSERT INTO menu_items (name, price, stock, type, status) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, item.getName());
            ps.setDouble(2, item.getPrice());

            if (item.getStock() == null) {
                ps.setNull(3, Types.INTEGER);
            } else {
                ps.setInt(3, item.getStock());
            }

            ps.setString(4, item.getType().name());
            ps.setString(5, item.getStatus().name());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<MenuItem> findAll() {
        List<MenuItem> list = new ArrayList<>();
        String sql = "SELECT * FROM menu_items";

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapResultSet(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public MenuItem findById(int id) {
        String sql = "SELECT * FROM menu_items WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapResultSet(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<MenuItem> findAvailable() {
        List<MenuItem> list = new ArrayList<>();
        String sql = "SELECT * FROM menu_items WHERE status = 'AVAILABLE'";

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapResultSet(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<MenuItem> findByType(ItemType type) {
        List<MenuItem> list = new ArrayList<>();
        String sql = "SELECT * FROM menu_items WHERE type = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, type.name());

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapResultSet(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean update(MenuItem item) {
        String sql = "UPDATE menu_items SET name=?, price=?, stock=?, type=?, status=? WHERE id=?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, item.getName());
            ps.setDouble(2, item.getPrice());

            if (item.getStock() == null) {
                ps.setNull(3, Types.INTEGER);
            } else {
                ps.setInt(3, item.getStock());
            }

            ps.setString(4, item.getType().name());
            ps.setString(5, item.getStatus().name());
            ps.setInt(6, item.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM menu_items WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateStatus(int id, ItemStatus status) {
        String sql = "UPDATE menu_items SET status = ? WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status.name());
            ps.setInt(2, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateStock(int id, Integer stock) {
        String sql = "UPDATE menu_items SET stock = ? WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            if (stock == null) {
                ps.setNull(1, Types.INTEGER);
            } else {
                ps.setInt(1, stock);
            }

            ps.setInt(2, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private MenuItem mapResultSet(ResultSet rs) throws SQLException {
        Integer stock = rs.getObject("stock") == null ? null : rs.getInt("stock");

        return new MenuItem(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getDouble("price"),
                stock,
                ItemType.valueOf(rs.getString("type")),
                ItemStatus.valueOf(rs.getString("status"))
        );
    }
}
