package model.entity;
//CREATE TABLE tables (
//    id INT PRIMARY KEY AUTO_INCREMENT,
//    table_name VARCHAR(10) UNIQUE NOT NULL,
//    capacity INT NOT NULL check(capacity > 0),
//    status ENUM('AVAILABLE', 'OCCUPIED') DEFAULT 'AVAILABLE'
//);

import model.constants.TableStatus;

public class Table {
    private int id;
    private  String tableName;
    private  int capacity;
    private TableStatus status;

    public Table() {
    }
    public Table(String tableName, int capacity, TableStatus status) {
        this.tableName = tableName;
        this.capacity = capacity;
        this.status = status;
    }


    public Table(int id, String tableName, int capacity, TableStatus status) {
        this.id = id;
        this.tableName = tableName;
        this.capacity = capacity;
        this.status = status;
    }
    public Table(String tableName, int capacity) {
        this.tableName = tableName;
        this.capacity = capacity;
        this.status = TableStatus.AVAILABLE;
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

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity phải > 0");
        }
        this.capacity = capacity;
    }

    public TableStatus getStatus() {
        return status;
    }

    public void setStatus(TableStatus status) {
        this.status = status;
    }

    public String[] toRow() {
        return new String[]{
                String.valueOf(id),
                tableName,
                String.valueOf(capacity),
                status.name()
        };
    }

    public static String[] getHeaders() {
        return new String[]{
                "ID", "TABLE NAME", "CAPACITY", "STATUS"
        };
    }
}
