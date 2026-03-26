package model.entity;

//CREATE TABLE order_details (
//    id INT PRIMARY KEY AUTO_INCREMENT,
//    order_id INT,
//    item_id INT,
//    quantity INT NOT NULL,
//    status ENUM('PENDING', 'COOKING', 'READY', 'SERVED') DEFAULT 'PENDING',
//    FOREIGN KEY (order_id) REFERENCES orders(id),
//    FOREIGN KEY (item_id) REFERENCES menu_items(id)
//);

import model.constants.ItemStatus;

public class OrderDetails {
    private int id;
    private int orderId;
    private int itemId;
    private int quantity;
    private ItemStatus status;

    public OrderDetails() {
    }

    public OrderDetails(int id, int orderId, int itemId, int quantity, ItemStatus status) {
        this.id = id;
        this.orderId = orderId;
        this.itemId = itemId;
        this.quantity = quantity;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        this.quantity = quantity;
    }

    public ItemStatus getStatus() {
        return status;
    }

    public void setStatus(ItemStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return String.format("OrderDetail [ID: %d | ItemID: %d | Qty: %d | Status: %s]",
                id, itemId, quantity, status);
    }

}
