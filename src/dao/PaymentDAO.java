package dao;

import model.entity.Payment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAO {
    private final Connection conn;

    public PaymentDAO(Connection conn) {
        this.conn = conn;
    }

    public boolean insert(Payment payment) {
        String sql = "INSERT INTO payments (order_id, total_amount) VALUES (?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, payment.getOrderId());
            ps.setDouble(2, payment.getTotalAmount());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Payment findByOrderId(int orderId) {
        String sql = "SELECT * FROM payments WHERE order_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderId);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapResultSet(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Payment> findAll() {
        List<Payment> list = new ArrayList<>();
        String sql = "SELECT * FROM payments";

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

    private Payment mapResultSet(ResultSet rs) throws SQLException {
        return new Payment(
                rs.getInt("id"),
                rs.getInt("order_id"),
                rs.getDouble("total_amount"),
                rs.getTimestamp("payment_time")
        );
    }


}
