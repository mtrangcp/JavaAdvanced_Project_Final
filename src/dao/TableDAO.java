package dao;

import model.constants.TableStatus;
import model.entity.Table;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TableDAO {
    private final Connection conn;

    public TableDAO(Connection conn) {
        this.conn = conn;
    }

    public boolean insert(Table table) {
        String sql = "INSERT INTO tables (table_name, capacity, status) VALUES (?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, table.getTableName());
            ps.setInt(2, table.getCapacity());
            ps.setString(3, table.getStatus().name());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Table> findAll() {
        List<Table> list = new ArrayList<>();
        String sql = "SELECT * FROM tables ORDER BY id";

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

    public Table findById(int id) {
        String sql = "SELECT * FROM tables WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapResultSet(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Table findByName(String tableName) {
        String sql = "SELECT * FROM tables WHERE table_name = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, tableName);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapResultSet(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Table> findByStatus(TableStatus status) {
        List<Table> list = new ArrayList<>();
        String sql = "SELECT * FROM tables WHERE status = ? ORDER BY id";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status.name());

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSet(rs));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Table> findAvailableTables() {
        return findByStatus(TableStatus.AVAILABLE);
    }

    public boolean update(Table table) {
        String sql = "UPDATE tables SET table_name=?, capacity=?, status=? WHERE id=?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, table.getTableName());
            ps.setInt(2, table.getCapacity());
            ps.setString(3, table.getStatus().name());
            ps.setInt(4, table.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateStatus(int id, TableStatus status) {
        String sql = "UPDATE tables SET status = ? WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status.name());
            ps.setInt(2, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Table mapResultSet(ResultSet rs) throws SQLException {
        return new Table(
                rs.getInt("id"),
                rs.getString("table_name"),
                rs.getInt("capacity"),
                TableStatus.valueOf(rs.getString("status"))
        );
    }
}