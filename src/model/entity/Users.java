package model.entity;

//CREATE TABLE users (
//    id INT PRIMARY KEY AUTO_INCREMENT,
//    username VARCHAR(50) UNIQUE NOT NULL,
//    password VARCHAR(255) NOT NULL,
//    full_name VARCHAR(100),
//    role ENUM('MANAGER', 'CHEF', 'CUSTOMER') NOT NULL,
//    is_active BOOLEAN DEFAULT TRUE
//);

import model.constants.UserRole;

public class Users {

    private int id;
    private String username;
    private String password;
    private String full_name;
    private UserRole role;
    private boolean is_active;

    public Users() {
    }

    public Users(int id, String username, String password, String full_name, UserRole role, boolean is_active) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.full_name = full_name;
        this.role = role;
        this.is_active = is_active;
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

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public boolean isIs_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }
}
