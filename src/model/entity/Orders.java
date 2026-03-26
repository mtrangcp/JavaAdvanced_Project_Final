package model.entity;

// CREATE TABLE orders (
//    id INT PRIMARY KEY AUTO_INCREMENT,
//    user_id INT,
//    table_id INT,
//    total_price DOUBLE DEFAULT 0,
//    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
//    status ENUM('UNPAID', 'PAID') DEFAULT 'UNPAID',
//    FOREIGN KEY (user_id) REFERENCES users(id),
//    FOREIGN KEY (table_id) REFERENCES tables(id)
//);

import model.constants.OrderStatus;

import java.time.LocalDateTime;

public class Orders {
    private int id;
    private int userId;
    private int tableId;
    private double totalPrice;
    private LocalDateTime orderDate;
    private OrderStatus status;

    public Orders() {
    }

    public Orders(int id, int userId, int tableId, double totalPrice, LocalDateTime orderDate, OrderStatus status) {
        this.id = id;
        this.userId = userId;
        this.tableId = tableId;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return String.format("Order[ID: %d | Table ID: %d | Total: %.2f | Status: %s | Date: %s]",
                id, tableId, totalPrice, status, orderDate);
    }

}
