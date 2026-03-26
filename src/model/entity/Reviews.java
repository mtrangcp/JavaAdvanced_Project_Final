package model.entity;
//CREATE TABLE reviews (
//    id INT PRIMARY KEY AUTO_INCREMENT,
//    user_id INT,
//    item_id INT,
//    rating INT CHECK (rating BETWEEN 1 AND 5),
//    comment TEXT,
//    FOREIGN KEY (user_id) REFERENCES users(id),
//    FOREIGN KEY (item_id) REFERENCES menu_items(id)
//);

public class Reviews {
    private int id;
    private int userId;
    private int itemId;
    private int rating;
    private String comment;

    public Reviews() {
    }

    public Reviews(int id, int userId, int itemId, int rating, String comment) {
        this.id = id;
        this.userId = userId;
        this.itemId = itemId;
        this.rating = rating;
        this.comment = comment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return String.format("Review [UserID: %d | ItemID: %d | Rating: %d* | Comment: %s]",
                userId, itemId, rating, comment);
    }

}
