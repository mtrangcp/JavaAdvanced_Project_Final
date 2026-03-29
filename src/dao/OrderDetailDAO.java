package dao;

import model.constants.OrderDetailStatus;
import model.entity.OrderDetail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailDAO {
    private final Connection conn;

    public OrderDetailDAO(Connection conn) {
        this.conn = conn;
    }

    public boolean insert(OrderDetail od) {
        String sql = "INSERT INTO order_details (order_id, item_id, quantity, status) VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, od.getOrderId());
            ps.setInt(2, od.getItemId());
            ps.setInt(3, od.getQuantity());
            ps.setString(4, od.getStatus().name());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<OrderDetail> findByOrder(int orderId) {
        List<OrderDetail> list = new ArrayList<>();
        String sql = "SELECT * FROM order_details WHERE order_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderId);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapResultSet(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public OrderDetail findById(int id) {
        String sql = "SELECT * FROM order_details WHERE id = ?";

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

    public boolean updateQuantity(int id, int quantity) {
        String sql = "UPDATE order_details SET quantity = ? WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, quantity);
            ps.setInt(2, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateStatus(int id, OrderDetailStatus status) {
        String sql = "UPDATE order_details SET status = ? WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status.name());
            ps.setInt(2, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // hủy món
    public boolean delete(int id) {
        String sql = "DELETE FROM order_details WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private OrderDetail mapResultSet(ResultSet rs) throws SQLException {
        return new OrderDetail(
                rs.getInt("id"),
                rs.getInt("order_id"),
                rs.getInt("item_id"),
                rs.getInt("quantity"),
                OrderDetailStatus.valueOf(rs.getString("status"))
        );
    }



}
