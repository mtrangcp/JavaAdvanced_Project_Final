package dao;

import db.DbConnection;
import model.constants.TableStatus;
import model.entity.Table;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TableDAO {
    public boolean addTable(Table table) {
        String sql = "INSERT INTO tables (table_name, capacity, status) VALUES (?, ?, ?)";

        try (Connection conn = DbConnection.openConnection();
             PreparedStatement pstmt = Objects.requireNonNull(conn).prepareStatement(sql)) {
            pstmt.setString(1, table.getTableName());
            pstmt.setInt(2, table.getCapacity());
            pstmt.setString(3, TableStatus.AVAILABLE.name());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Table> getAllTables() {
        List<Table> list = new ArrayList<>();
        String sql = "SELECT * FROM tables";
        try (Connection conn = DbConnection.openConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Table(
                        rs.getInt("id"),
                        rs.getString("table_name"),
                        rs.getInt("capacity"),
                        TableStatus.valueOf(rs.getString("status"))
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }



}
