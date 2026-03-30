package service;

import dao.MenuItemDAO;
import dao.OrderDAO;
import dao.OrderDetailDAO;
import exception.AppException;
import model.constants.OrderStatus;
import model.entity.MenuItem;
import model.entity.Order;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class OrderService {
    private final Connection conn;
    private final OrderDAO orderDAO;
    private final OrderDetailDAO orderDetailDAO;
    private final MenuItemDAO menuItemDAO;

    public OrderService(Connection conn,
                        OrderDAO orderDAO,
                        OrderDetailDAO orderDetailDAO,
                        MenuItemDAO menuItemDAO) {
        this.conn = conn;
        this.orderDAO = orderDAO;
        this.orderDetailDAO = orderDetailDAO;
        this.menuItemDAO = menuItemDAO;
    }

    public int createOrder(int userId, int tableId) {
        if (userId <= 0 || tableId <= 0) {
            throw new AppException("Dữ liệu không hợp lệ");
        }

        Order order = new Order();
        order.setUserId(userId);
        order.setTableId(tableId);

        return orderDAO.insert(order);
    }

    //
    public void addItem(int orderId, int itemId, int quantity) {
        if (quantity <= 0) {
            throw new AppException("Số lượng phải > 0");
        }

        try {
            conn.setAutoCommit(false);

            MenuItem item = menuItemDAO.findById(itemId);
            if (item == null || !item.getStatus().equals("AVAILABLE")) {
                throw new AppException("Món không tồn tại hoặc không còn bán");
            }

            if (item.getStock() != null && item.getStock() < quantity) {
                throw new AppException("Không đủ món");
            }

            orderDetailDAO.addItem(orderId, itemId, quantity);

            if (item.getStock() != null) {
                item.setStock(item.getStock() - quantity);
                menuItemDAO.update(item);
            }
            conn.commit();

        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException ignored) {
            }
            throw new AppException(e.getMessage());
        }
    }

    public List<Order> getOrdersByUser(int userId) {
        return orderDAO.findByUser(userId);
    }

    public void checkout(int orderId) {
        try {
            conn.setAutoCommit(false);

            boolean ok = orderDAO.updateStatus(orderId, OrderStatus.DONE);
            if (!ok) {
                throw new AppException("Thanh toán thất bại");
            }
            conn.commit();

        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException ignored) {
            }
            throw new AppException(e.getMessage());
        }
    }

}
