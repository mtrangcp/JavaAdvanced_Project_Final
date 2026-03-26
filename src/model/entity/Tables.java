package model.entity;
//CREATE TABLE tables (
//    id INT PRIMARY KEY AUTO_INCREMENT,
//    table_name VARCHAR(10) UNIQUE NOT NULL,
//    capacity INT NOT NULL,
//    status ENUM('AVAILABLE', 'OCCUPIED') DEFAULT 'AVAILABLE'
//);

import model.constants.TableStatus;

public class Tables {
    private int id;
    private  String tableName;
    private  String capacity;
    private TableStatus status;

    public Tables() {
    }

    public Tables(int id, String tableName, String capacity, TableStatus status) {
        this.id = id;
        this.tableName = tableName;
        this.capacity = capacity;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public TableStatus getStatus() {
        return status;
    }

    public void setStatus(TableStatus status) {
        this.status = status;
    }
}
