package service;

import dao.OrderDetailDAO;
import exception.AppException;
import model.constants.OrderDetailStatus;
import model.entity.OrderDetail;

import java.util.List;

public class OrderDetailService {
    private final OrderDetailDAO orderDetailDAO;
    private final OrderService orderService;

    public OrderDetailService(OrderDetailDAO orderDetailDAO,
                              OrderService orderService) {
        this.orderDetailDAO = orderDetailDAO;
        this.orderService = orderService;
    }

    // 1. Chef xem danh sách (chỉ lấy APPROVED)
    public List<OrderDetail> getPendingItems() {
        return orderDetailDAO.findPendingApprovedItems();
    }

    // 2. Update trạng thái (Chef)
    public void updateStatus(int id) {
        OrderDetail od = orderDetailDAO.findById(id);

        if (od == null) {
            throw new AppException("Không tìm thấy món");
        }

        OrderDetailStatus nextStatus;

        switch (od.getStatus()) {
            case PENDING:
                nextStatus = OrderDetailStatus.COOKING;
                break;
            case COOKING:
                nextStatus = OrderDetailStatus.READY;
                break;
            case READY:
                nextStatus = OrderDetailStatus.SERVED;
                break;
            default:
                throw new AppException("Không thể cập nhật thêm");
        }

        boolean ok = orderDetailDAO.updateStatus(id, nextStatus);

        if (!ok) {
            throw new AppException("Cập nhật thất bại");
        }

        // 🔥 auto check DONE
        orderService.updateOrderIfDone(od.getOrderId());
    }

    // 3. Customer hủy món
    public void cancelItem(int id) {
        OrderDetail od = orderDetailDAO.findById(id);

        if (od == null) {
            throw new AppException("Không tìm thấy món");
        }

        if (od.getStatus() != OrderDetailStatus.PENDING) {
            throw new AppException("Chỉ được hủy khi đang PENDING");
        }

        boolean ok = orderDetailDAO.updateStatus(id, OrderDetailStatus.CANCELLED);

        if (!ok) {
            throw new AppException("Hủy thất bại");
        }
    }

    // 4. Customer xem order của mình
    public List<OrderDetail> getByOrder(int orderId) {
        return orderDetailDAO.findByOrder(orderId);
    }
}



//package service;
//
//import dao.OrderDetailDAO;
//import exception.AppException;
//import model.constants.OrderDetailStatus;
//import model.entity.OrderDetail;
//
//import java.util.List;
//
//public class OrderDetailService {
//    private final OrderDetailDAO orderDetailDAO;
//
//    public OrderDetailService(OrderDetailDAO orderDetailDAO) {
//        this.orderDetailDAO = orderDetailDAO;
//    }
//
//    public List<OrderDetail> getPendingItems() {
//        return orderDetailDAO.findByStatus(OrderDetailStatus.PENDING);
//    }
//
//    public void updateStatus(int id) {
//        OrderDetail od = orderDetailDAO.findById(id);
//
//        if (od == null) {
//            throw new AppException("Không tìm thấy món");
//        }
//
//        OrderDetailStatus nextStatus;
//
//        switch (od.getStatus()) {
//            case PENDING:
//                nextStatus = OrderDetailStatus.COOKING;
//                break;
//            case COOKING:
//                nextStatus = OrderDetailStatus.READY;
//                break;
//            case READY:
//                nextStatus = OrderDetailStatus.SERVED;
//                break;
//            default:
//                throw new AppException("Không thể cập nhật thêm");
//        }
//
//        boolean ok = orderDetailDAO.updateStatus(id, nextStatus);
//
//        if (!ok) {
//            throw new AppException("Cập nhật thất bại");
//        }
//    }
//
//
//}
