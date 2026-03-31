package dao.impl;

import dao.Interface.IOrderDetailDAO;
import model.OrderDetail;
import utils.MyDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailDAOImpl implements IOrderDetailDAO {

    @Override
    public List<OrderDetail> getOrderDetailsByOrderId(String orderId) {
        List<OrderDetail> orderDetails = new ArrayList<>();
        try(Connection conn = MyDatabase.getInstance().getConnection();
            PreparedStatement pstmt = conn.prepareStatement("select * from orderDetails where order_id = ?")){

            pstmt.setString(1, orderId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()){
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setOd_id(rs.getString("od_id"));
                orderDetail.setOrder_id(rs.getString("order_id"));
                orderDetail.setProduct_id(rs.getString("product_id"));
                orderDetail.setQuantity(rs.getInt("quantity"));
                orderDetail.setPrice(rs.getBigDecimal("price"));
                orderDetails.add(orderDetail);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return orderDetails;
    }
}
