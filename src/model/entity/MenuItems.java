package model.entity;

//CREATE TABLE menu_items (
//    id INT PRIMARY KEY AUTO_INCREMENT,
//    name VARCHAR(100) NOT NULL,
//    price DOUBLE NOT NULL,
//    stock INT DEFAULT -1,
//    type ENUM('FOOD', 'DRINK') NOT NULL
//);

import model.constants.ItemType;

public class MenuItems {
    private int id;
    private String name;
    private double price;
    private int stock;
    private ItemType type;

    public MenuItems() {
    }

    public MenuItems(int id, String name, double price, int stock, ItemType type) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public ItemType getType() {
        return type;
    }

    public void setType(ItemType type) {
        this.type = type;
    }
}
