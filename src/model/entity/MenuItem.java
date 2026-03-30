package model.entity;

//CREATE TABLE menu_items (
//    id INT PRIMARY KEY AUTO_INCREMENT,
//    name VARCHAR(100) NOT NULL,
//    price DOUBLE NOT NULL check(price > 0),
//    stock INT DEFAULT NULL check(stock >= 0),
//    type ENUM('FOOD', 'DRINK') NOT NULL,
//    status ENUM('AVAILABLE', 'UNAVAILABLE') DEFAULT 'AVAILABLE'
//);

import model.constants.ItemStatus;
import model.constants.ItemType;

public class MenuItem {
    private int id;
    private String name;
    private double price;
    private Integer stock;
    private ItemType type;
    private ItemStatus status;

    public MenuItem() {
    }

    public MenuItem(int id, String name, double price, Integer stock, ItemType type, ItemStatus status) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.type = type;
        this.status = status;
    }

    public MenuItem(String name, double price, Integer stock, ItemType type) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.type = type;
        this.status = ItemStatus.AVAILABLE;
    }

    public MenuItem(String name, double price, Integer stock, ItemType type, ItemStatus itemStatus) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.type = type;
        this.status = ItemStatus.AVAILABLE;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if (price <= 0) {
            throw new IllegalArgumentException("Price must be > 0");
        }
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        if (stock != null && stock < 0) {
            throw new IllegalArgumentException("Stock must >= 0");
        }
        this.stock = stock;
    }

    public ItemType getType() {
        return type;
    }

    public void setType(ItemType type) {
        this.type = type;
    }

    public ItemStatus getStatus() {
        return status;
    }

    public void setStatus(ItemStatus status) {
        this.status = status;
    }

    public String[] toRow() {
        return new String[]{
                String.valueOf(id),
                name,
                String.valueOf(price),
                stock == null ? "NULL" : String.valueOf(stock),
                type.name(),
                status.name()
        };
    }

    public static String[] getHeaders() {
        return new String[]{
                "ID", "NAME", "PRICE", "STOCK", "TYPE", "STATUS"
        };
    }
}
