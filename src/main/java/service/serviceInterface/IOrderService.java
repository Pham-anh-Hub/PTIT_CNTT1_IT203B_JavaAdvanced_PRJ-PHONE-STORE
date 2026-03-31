package service.serviceInterface;

import model.Order;

import java.util.List;

public interface IOrderService {
    void displayAllOrders(List<Order> orders);      // admin xem tất cả đơn
    void displayOwnOrders();       // customer xem đơn của mình
    void displayOrderDetails();   // xem chi tiết 1 đơn
    void updateOrderStatus();     // admin cập nhật trạng thái
}
