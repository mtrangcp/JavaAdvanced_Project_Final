package model.constants;

public enum ItemStatus {
    PENDING, // Khách vừa gọi, chờ xử lý
    COOKING, // Đầu bếp đang chế biến
    READY,   // Đã nấu xong, chờ phục vụ
    SERVED   // Đã mang ra cho khách

}
