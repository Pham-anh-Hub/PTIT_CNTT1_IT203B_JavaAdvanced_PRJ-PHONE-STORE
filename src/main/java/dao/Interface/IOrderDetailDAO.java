package dao.Interface;

import model.OrderDetail;

import java.util.List;

public interface IOrderDetailDAO {
    public List<OrderDetail> getOrderDetailsByOrderId(String orderId);


}
