package model.entity;

//CREATE TABLE reviews (
//    id INT PRIMARY KEY AUTO_INCREMENT,
//    user_id INT NOT NULL,
//    item_id INT NOT NULL,
//    rating INT CHECK (rating BETWEEN 1 AND 5),
//    comment TEXT,
//    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
//
//    FOREIGN KEY (user_id) REFERENCES users(id),
//    FOREIGN KEY (item_id) REFERENCES menu_items(id)
//);

import java.sql.Timestamp;

public class Review {
    private int id;
    private int userId;
    private int itemId;
    private int rating;
    private String comment;
    private Timestamp createdAt;

    public Review() {
    }

    public Review(int id, int userId, int itemId, int rating, String comment, Timestamp createdAt) {
        this.id = id;
        this.userId = userId;
        this.itemId = itemId;
        setRating(rating);
        this.comment = comment;
        this.createdAt = createdAt;
    }

    public Review(int userId, int itemId, int rating, String comment) {
        this.userId = userId;
        this.itemId = itemId;
        setRating(rating);
        this.comment = comment;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be 1-5");
        }
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public String[] toRow() {
        return new String[]{
                String.valueOf(id),
                String.valueOf(userId),
                String.valueOf(itemId),
                String.valueOf(rating),
                comment == null ? "" : comment,
                createdAt == null ? "" : createdAt.toString()
        };
    }

    public static String[] getHeaders() {
        return new String[]{
                "ID", "USER ID", "ITEM ID", "RATING", "COMMENT", "CREATED AT"
        };
    }
}
