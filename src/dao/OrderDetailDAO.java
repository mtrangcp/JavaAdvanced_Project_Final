package dao;

import model.constants.OrderDetailStatus;
import model.entity.OrderDetail;

import java.sql.*;
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

    public boolean addItem(int orderId, int itemId, int quantity) {
        OrderDetail od = new OrderDetail();
        od.setOrderId(orderId);
        od.setItemId(itemId);
        od.setQuantity(quantity);
        od.setStatus(OrderDetailStatus.PENDING);
        return insert(od);
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

    // ================= CHEF VIEW (QUAN TRỌNG) =================
    public List<OrderDetail> findPendingApprovedItems() {
        List<OrderDetail> list = new ArrayList<>();

        String sql = """
                SELECT  od.id, t.table_name, m.name AS item_name, od.quantity, od.status
                FROM order_details od
                JOIN orders o ON od.order_id = o.id
                JOIN tables t ON o.table_id = t.id
                JOIN menu_items m ON od.item_id = m.id
                WHERE od.status = 'PENDING'
                AND o.status = 'APPROVED'
                ORDER BY o.created_at
                """;

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

    public boolean checkAllServed(int orderId) {
        String sql = """
                SELECT COUNT(*) 
                FROM order_details 
                WHERE order_id = ? 
                AND status != 'SERVED'
                """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderId);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) == 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // ================= CHECKOUT =================
    public double calculateTotal(int orderId) {
        String sql = """
                SELECT SUM(m.price * od.quantity) AS total
                FROM order_details od
                JOIN menu_items m ON od.item_id = m.id
                WHERE od.order_id = ?
                """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderId);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getDouble("total");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // ================= MAP =================
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