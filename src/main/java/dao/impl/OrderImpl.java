package dao.impl;

import dao.Interface.IOrder;
import model.Order;
import model.OrderDetail;
import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderImpl implements IOrder {

    public Order setOrder (ResultSet rs) {
        Order order = new Order();
        try {
            order.setOrder_id(rs.getString("order_id"));
            order.setCustomer_id(rs.getString("id"));
            order.setTotal_amount(rs.getBigDecimal("total_amount"));
            order.setStatus(rs.getString("status"));


            order.setOrder_date(rs.getTimestamp("order_date") != null ? rs.getTimestamp("order_date").toLocalDateTime() : null);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return order;

    }

    @Override
    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        try (Connection conn = MyDatabase.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement("select * from orders order by order_date desc")) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Order order = setOrder(rs);
                orders.add(order);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return orders;
    }

    @Override
    public List<Order> getOrdersByUserId(String userId) {
        List<Order> orders = new ArrayList<>();
        try (Connection conn = MyDatabase.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement("select * from orders where id = ? order by order_date desc")) {

            ps.setString(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) orders.add(setOrder(rs));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return orders;
    }

    @Override
    public Order getOrderById(String orderId) {
        try (Connection conn = MyDatabase.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement("select * from orders where order_id = ?")) {

            ps.setString(1, orderId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                    return setOrder(rs);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public boolean insertOrderDetail( String orderId, OrderDetail orderDetail, Connection conn){
        try (PreparedStatement ps = conn.prepareStatement("insert into orderDetails (od_id, order_id, product_id, quantity, price) values (?, ?, ?, ?, ?)")) {

            ps.setString(1, orderDetail.getOd_id());
            ps.setString(2, orderDetail.getOrder_id());
            ps.setString(3, orderDetail.getProduct_id());
            ps.setInt(4, orderDetail.getQuantity());
            ps.setBigDecimal(5, orderDetail.getPrice());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean insertOrder(Order order, Connection conn) {
        try (PreparedStatement ps = conn.prepareStatement("insert into orders (order_id, id, total_amount, status) values (?, ?, ?, 'PENDING')")) {

            ps.setString(1, order.getOrder_id());
            ps.setString(2, order.getCustomer_id());
            ps.setBigDecimal(3, order.getTotal_amount());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean updateOrderStatus(String orderId, String status) {
        try (Connection conn = MyDatabase.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement("update orders set status = ? where order_id = ?")) {

            ps.setString(1, status);
            ps.setString(2, orderId);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Order> getDeliveredOrder(int targetYear, int targetMonth){
        List<Order> orderDelivered = new ArrayList<>();
        try(Connection conn = MyDatabase.getInstance().getConnection();
        PreparedStatement pstmt = conn.prepareStatement("select * from orders where month(order_date) = ? and year(order_date) = ? and status = 'DELIVERED'")){

            pstmt.setInt(1, targetMonth);
            pstmt.setInt(2, targetYear);


            ResultSet rs = pstmt.executeQuery();
            while (rs.next()){
                Order order = setOrder(rs);
                orderDelivered.add(order);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return orderDelivered;
    }

}
