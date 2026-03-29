package dao;

import model.entity.Review;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReviewDAO {
    private final Connection conn;

    public ReviewDAO(Connection conn) {
        this.conn = conn;
    }

    // ====== CREATE ======
    public boolean insert(Review review) {
        String sql = "INSERT INTO reviews (user_id, item_id, rating, comment) VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, review.getUserId());
            ps.setInt(2, review.getItemId());
            ps.setInt(3, review.getRating());
            ps.setString(4, review.getComment());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // ====== FIND BY ITEM ======
    public List<Review> findByItem(int itemId) {
        List<Review> list = new ArrayList<>();
        String sql = "SELECT * FROM reviews WHERE item_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, itemId);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapResultSet(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // ====== FIND BY USER ======
    public List<Review> findByUser(int userId) {
        List<Review> list = new ArrayList<>();
        String sql = "SELECT * FROM reviews WHERE user_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapResultSet(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // ====== READ ALL ======
    public List<Review> findAll() {
        List<Review> list = new ArrayList<>();
        String sql = "SELECT * FROM reviews";

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

    // ====== DELETE ======
    public boolean delete(int id) {
        String sql = "DELETE FROM reviews WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Review mapResultSet(ResultSet rs) throws SQLException {
        return new Review(
                rs.getInt("id"),
                rs.getInt("user_id"),
                rs.getInt("item_id"),
                rs.getInt("rating"),
                rs.getString("comment"),
                rs.getTimestamp("created_at")
        );
    }

}
