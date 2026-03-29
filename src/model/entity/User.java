package model.entity;

//CREATE TABLE users (
//    id INT PRIMARY KEY AUTO_INCREMENT,
//    username VARCHAR(50) UNIQUE NOT NULL,
//    password VARCHAR(255) NOT NULL,
//    full_name VARCHAR(100) NOT NULL,
//    role ENUM('MANAGER', 'CHEF', 'CUSTOMER') NOT NULL,
//    is_active BOOLEAN DEFAULT TRUE,
//    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
//);

import model.constants.Role;
import utils.Color;

import java.sql.Timestamp;

public class User {

    private int id;
    private String username;
    private String password;
    private String fullName;
    private Role role;
    private boolean isActive;
    private Timestamp createdAt;

    public User() {
    }

    public User(int id, String username, String password, String fullName, Role role, boolean isActive, Timestamp createdAt) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.role = role;
        this.isActive = isActive;
        this.createdAt = createdAt;
    }

    public User(String username, String password, String fullName, Role role) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.role = role;
        this.isActive = true;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return String.format(
                "| %-3d | %-15s | %-20s | %-10s | %-8s | %-20s |",
                id,
                username,
                fullName,
                role,
                isActive ? "ACTIVE" : "INACTIVE",
                createdAt
        );
    }
    public static String[] getHeader() {
        return new String[]{
                "ID", "USERNAME", "FULL NAME", "ROLE", "STATUS", "CREATED AT"
        };
    }

    public static String getLine() {
        return "+-----+-----------------+----------------------+------------+----------+----------------------+";
    }

    public String[] toRow() {
        String statusStr = isActive
                ? Color.GREEN + "ACTIVE" + Color.RESET
                : Color.RED + "BANNED" + Color.RESET;

        return new String[]{
                String.valueOf(id),
                username,
                fullName,
                role.name(),
                isActive ? "ACTIVE" : "INACTIVE",
                createdAt == null ? "" : createdAt.toString()
        };
    }

}
