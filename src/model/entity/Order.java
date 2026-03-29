package model.entity;

// CREATE TABLE orders (
//    id INT PRIMARY KEY AUTO_INCREMENT,
//    user_id INT NOT NULL,
//    table_id INT NOT NULL,
//    status ENUM('PENDING', 'APPROVED', 'DONE', 'CANCELLED') DEFAULT 'PENDING',
//    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
//
//    FOREIGN KEY (user_id) REFERENCES users(id),
//    FOREIGN KEY (table_id) REFERENCES tables(id)
//);

import model.constants.OrderStatus;

import java.security.Timestamp;

public class Order {
    private int id;
    private int userId;
    private int tableId;
    private OrderStatus status;
    private Timestamp createdAt;

    public Order() {
    }

    public Order(int id, int userId, int tableId, OrderStatus status, Timestamp createdAt) {
        this.id = id;
        this.userId = userId;
        this.tableId = tableId;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Order(int userId, int tableId) {
        this.userId = userId;
        this.tableId = tableId;
        this.status = OrderStatus.PENDING;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public String[] toRow() {
        return new String[]{
                String.valueOf(id),
                String.valueOf(userId),
                String.valueOf(tableId),
                status.name(),
                createdAt == null ? "" : createdAt.toString()
        };
    }

    public static String[] getHeaders() {
        return new String[]{
                "ID", "USER ID", "TABLE ID", "STATUS", "CREATED AT"
        };
    }

}
