package dao.Interface;

import model.Order;
import model.OrderDetail;

import java.sql.Connection;
import java.util.List;

public interface IOrder {
    List<Order> getAllOrders();
    List<Order> getOrdersByUserId(String userId);
    Order getOrderById(String orderId);
    boolean insertOrderDetail (String orderId, OrderDetail orderDetail, Connection conn);
    boolean insertOrder(Order order, Connection conn);
    boolean updateOrderStatus(String orderId, String status);
}

