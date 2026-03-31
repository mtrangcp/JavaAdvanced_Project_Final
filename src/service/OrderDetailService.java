package service;

import dao.OrderDetailDAO;
import exception.AppException;
import model.constants.OrderDetailStatus;
import model.entity.OrderDetail;

import java.util.List;

public class OrderDetailService {
    private final OrderDetailDAO orderDetailDAO;
    private final OrderService orderService;

    public OrderDetailService(OrderDetailDAO orderDetailDAO, OrderService orderService) {
        this.orderDetailDAO = orderDetailDAO;
        this.orderService = orderService;
    }

    public List<OrderDetail> getActiveItems() {
        return orderDetailDAO.findByStatuses(
                OrderDetailStatus.APPROVED,
                OrderDetailStatus.COOKING,
                OrderDetailStatus.READY
        );
    }

    public void updateStatus(int id) {
        OrderDetail od = orderDetailDAO.findById(id);

        if (od == null) {
            throw new AppException("Không tìm thấy món");
        }

        OrderDetailStatus current = od.getStatus();
        OrderDetailStatus nextStatus;

        switch (current) {
            case APPROVED:
                nextStatus = OrderDetailStatus.COOKING;
                break;
            case COOKING:
                nextStatus = OrderDetailStatus.READY;
                break;
            case READY:
                nextStatus = OrderDetailStatus.SERVED;
                break;
            default:
                throw new AppException("Món này không thể cập nhật trạng thái");
        }

        boolean ok = orderDetailDAO.updateStatus(id, nextStatus);
        if (!ok) {
            throw new AppException("Cập nhật thất bại");
        }
        orderService.updateOrderIfDone(od.getOrderId());
    }

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

    public List<OrderDetail> getByOrder(int orderId) {
        return orderDetailDAO.findByOrder(orderId);
    }
}