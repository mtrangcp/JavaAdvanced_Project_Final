package model.entity;

//CREATE TABLE order_details (
//    id INT PRIMARY KEY AUTO_INCREMENT,
//    order_id INT NOT NULL,
//    item_id INT NOT NULL,
//    quantity INT NOT NULL check(quantity > 0),
//    status ENUM('PENDING', 'COOKING', 'READY', 'SERVED', 'CANCELLED') DEFAULT 'PENDING',
//
//    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
//    FOREIGN KEY (item_id) REFERENCES menu_items(id)
//);

import model.constants.OrderDetailStatus;

public class OrderDetail {
    private int id;
    private int orderId;
    private int itemId;
    private int quantity;
    private OrderDetailStatus status;

    public OrderDetail() {
    }

    public OrderDetail(int id, int orderId, int itemId, int quantity, OrderDetailStatus status) {
        this.id = id;
        this.orderId = orderId;
        this.itemId = itemId;
        this.quantity = quantity;
        this.status = status;
    }

    public OrderDetail(int orderId, int itemId, int quantity) {
        this.orderId = orderId;
        this.itemId = itemId;
        setQuantity(quantity);
        this.status = OrderDetailStatus.PENDING;
    }

    public int getId() {
        return id;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be > 0");
        }
        this.quantity = quantity;
    }

    public OrderDetailStatus getStatus() {
        return status;
    }

    public void setStatus(OrderDetailStatus status) {
        this.status = status;
    }

    public String[] toRow() {
        return new String[]{
                String.valueOf(id),
                String.valueOf(orderId),
                String.valueOf(itemId),
                String.valueOf(quantity),
                status.name()
        };
    }

    public static String[] getHeaders() {
        return new String[]{
                "ID", "ORDER ID", "ITEM ID", "QUANTITY", "STATUS"
        };
    }

}
