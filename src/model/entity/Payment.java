package model.entity;
// CREATE TABLE payments (
//    id INT AUTO_INCREMENT PRIMARY KEY,
//    order_id INT NOT NULL unique,
//    total_amount DOUBLE NOT NULL,
//    payment_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
//
//    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE
//);

import java.sql.Timestamp;

public class Payment {
    private int id;
    private int orderId;
    private double totalAmount;
    private Timestamp paymentTime;

    public Payment() {
    }

    public Payment(int id, int orderId, double totalAmount, Timestamp paymentTime) {
        this.id = id;
        this.orderId = orderId;
        this.totalAmount = totalAmount;
        this.paymentTime = paymentTime;
    }

    public Payment(int orderId, double totalAmount) {
        this.orderId = orderId;
        this.totalAmount = totalAmount;
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

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        if (totalAmount <= 0) {
            throw new IllegalArgumentException("Total must be > 0");
        }
        this.totalAmount = totalAmount;
    }

    public Timestamp getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(Timestamp paymentTime) {
        this.paymentTime = paymentTime;
    }

    public String[] toRow() {
        return new String[]{
                String.valueOf(id),
                String.valueOf(orderId),
                String.valueOf(totalAmount),
                paymentTime == null ? "" : paymentTime.toString()
        };
    }

    public static String[] getHeaders() {
        return new String[]{
                "ID", "ORDER ID", "TOTAL", "PAYMENT TIME"
        };
    }

}
