package service;

import dao.*;
import exception.AppException;
import model.constants.*;
import model.entity.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class OrderService {
    private final Connection conn;
    private final OrderDAO orderDAO;
    private final OrderDetailDAO orderDetailDAO;
    private final MenuItemDAO menuItemDAO;
    private final PaymentDAO paymentDAO;
    private final TableDAO tableDAO;

    public OrderService(Connection conn,
                        OrderDAO orderDAO,
                        OrderDetailDAO orderDetailDAO,
                        MenuItemDAO menuItemDAO,
                        PaymentDAO paymentDAO,
                        TableDAO tableDAO) {
        this.conn = conn;
        this.orderDAO = orderDAO;
        this.orderDetailDAO = orderDetailDAO;
        this.menuItemDAO = menuItemDAO;
        this.paymentDAO = paymentDAO;
        this.tableDAO = tableDAO;
    }

    public int createOrder(int userId, int tableId) {
        try {
            conn.setAutoCommit(false);
            if (userId <= 0 || tableId <= 0) {
                throw new AppException("Dữ liệu không hợp lệ");
            }

            Table table = tableDAO.findById(tableId);
            if (table == null) {
                throw new AppException("Bàn không tồn tại");
            }

            if (table.getStatus() != TableStatus.AVAILABLE) {
                throw new AppException("Bàn không khả dụng");
            }

            Order order = new Order();
            order.setUserId(userId);
            order.setTableId(tableId);
            order.setStatus(OrderStatus.PENDING);

            int orderId = orderDAO.insert(order);
            if (orderId <= 0) {
                throw new AppException("Tạo order thất bại");
            }

            tableDAO.updateStatus(tableId, TableStatus.OCCUPIED);

            conn.commit();
            return orderId;

        } catch (Exception e) {
            try { conn.rollback(); } catch (SQLException ignored) {}
            throw new AppException(e.getMessage());
        }
    }

    public void addItem(int orderId, int itemId, int quantity) {
        try {
            conn.setAutoCommit(false);
            if (quantity <= 0) {
                throw new AppException("Số lượng phải > 0");
            }

            Order order = orderDAO.findById(orderId);
            if (order == null) {
                throw new AppException("Order không tồn tại");
            }

            if (order.getStatus() != OrderStatus.PENDING) {
                throw new AppException("Chỉ thêm món khi order PENDING");
            }

            MenuItem item = menuItemDAO.findById(itemId);
            if (item == null || item.getStatus() != ItemStatus.AVAILABLE) {
                throw new AppException("Món không tồn tại hoặc không bán");
            }

            orderDetailDAO.addItem(orderId, itemId, quantity);
            conn.commit();
        } catch (Exception e) {
            try { conn.rollback(); } catch (SQLException ignored) {}
            throw new AppException(e.getMessage());
        }

    }

    public void approveOrder(int orderId) {
        try {
            conn.setAutoCommit(false);

            Order order = orderDAO.findById(orderId);
            if (order == null) {
                throw new AppException("Order không tồn tại");
            }

            if (order.getStatus() != OrderStatus.PENDING) {
                throw new AppException("Chỉ duyệt order PENDING");
            }

            List<OrderDetail> items = orderDetailDAO.findByOrder(orderId);
            if (items.isEmpty()) {
                throw new AppException("Order không có món");
            }

            // check stock
            for (OrderDetail od : items) {
                MenuItem item = menuItemDAO.findById(od.getItemId());

                if (item.getStock() != null && item.getStock() < od.getQuantity()) {
                    throw new AppException("Không đủ hàng: " + item.getName());
                }
            }

            // trừ stock
            for (OrderDetail od : items) {
                orderDetailDAO.updateStatus(od.getId(), OrderDetailStatus.APPROVED);
                MenuItem item = menuItemDAO.findById(od.getItemId());

                if (item.getStock() != null) {
                    item.setStock(item.getStock() - od.getQuantity());
                    menuItemDAO.update(item);
                }
            }

            orderDAO.updateStatus(orderId, OrderStatus.APPROVED);
            conn.commit();

        } catch (Exception e) {
            try { conn.rollback(); } catch (SQLException ignored) {}
            throw new AppException(e.getMessage());
        }
    }

    public void cancelOrder(int orderId) {
        Order order = orderDAO.findById(orderId);

        if (order == null) {
            throw new AppException("Order không tồn tại");
        }

        if (order.getStatus() != OrderStatus.PENDING) {
            throw new AppException("Chỉ hủy khi chưa duyệt");
        }

        orderDAO.updateStatus(orderId, OrderStatus.CANCELLED);
        tableDAO.updateStatus(order.getTableId(), TableStatus.AVAILABLE);
    }

    public void updateOrderIfDone(int orderId) {
        boolean allServed = orderDetailDAO.checkAllServed(orderId);

        if (allServed) {
            orderDAO.updateStatus(orderId, OrderStatus.DONE);
        }
    }

    public double checkout(int orderId) {
        try {
            conn.setAutoCommit(false);

            Order order = orderDAO.findById(orderId);
            if (order == null) {
                throw new AppException("Order không tồn tại");
            }

            if (order.getStatus() != OrderStatus.DONE) {
                throw new AppException("Order chưa hoàn tất");
            }

            if (paymentDAO.existsByOrderId(orderId)) {
                throw new AppException("Order đã thanh toán");
            }

            double total = orderDetailDAO.calculateTotal(orderId);

            Payment payment = new Payment();
            payment.setOrderId(orderId);
            payment.setTotalAmount(total);

            boolean check1 = paymentDAO.insert(payment);
            boolean check2 = tableDAO.updateStatus(order.getTableId(), TableStatus.AVAILABLE);

            if (!check1 || !check2) {
                throw new AppException("Checkout thất bại");
            }

            conn.commit();
            return total;

        } catch (Exception e) {
            try { conn.rollback(); } catch (SQLException ignored) {}
            throw new AppException(e.getMessage());
        }
    }

    public List<Order> getOrdersByUser(int userId) {
        return orderDAO.findByUser(userId);
    }

    public List<Order> getPendingOrders() {
        return orderDAO.findByStatus(OrderStatus.PENDING);
    }

    public List<Order> getApprovedOrders() {
        return orderDAO.findByStatus(OrderStatus.APPROVED);
    }
}