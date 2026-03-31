package model;

import dao.impl.OrderDetailDAOImpl;
import dao.impl.OrderImpl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Order{
    private String order_id;
    private String customer_id;
    private LocalDateTime order_date;
    private BigDecimal total_amount;
    private String status;
    private List<OrderDetail> orderDetails;


    public Order() {
    }

    public Order(String order_id, String customer_id, LocalDateTime order_date, BigDecimal total_amount, String status) {
        this.order_id = order_id;
        this.customer_id = customer_id;
        this.order_date = order_date;
        this.total_amount = total_amount;
        this.status = status;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public LocalDateTime getOrder_date() {
        return order_date;
    }

    public void setOrder_date(LocalDateTime order_date) {
        this.order_date = order_date;
    }

    public BigDecimal getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(BigDecimal total_amount) {
        this.total_amount = total_amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<OrderDetail> getOrderDetails() {
        return new OrderDetailDAOImpl().getOrderDetailsByOrderId(this.getOrder_id());
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public void displayHeader() {
        System.out.println("---------------------------------------------------------------");
        System.out.printf("| %-18s | %-8s | %-12s | %-12s |\n",
                "Sản phầm", "Số lượng", "Giá", "Thành tiền");
        System.out.println("---------------------------------------------------------------");
    }



    public void printOrder() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        System.out.println("\n========================== HÓA ĐƠN ===========================");
        System.out.println("Mã đơn hàng    : " + order_id);
        System.out.println("Khách hàng     : " + customer_id);
        System.out.println("Ngày đặt hàng  : " + (order_date != null ? order_date.format(dtf) : ""));
        System.out.println("Trạng thái     : " + status);

        displayHeader();


        List<OrderDetail> details = getOrderDetails();
        if (details != null && !details.isEmpty()) {
            for (OrderDetail od : details) {
                od.display();
            }
        } else {
            System.out.println("|                   Hóa đơn hiện tại trống                    |");
        }

        System.out.println("---------------------------------------------------------------");
        System.out.printf("Thành tiền: %,.0f VND\n",
                total_amount != null ? total_amount : BigDecimal.ZERO);
        System.out.println("===============================================================\n");
    }


}
