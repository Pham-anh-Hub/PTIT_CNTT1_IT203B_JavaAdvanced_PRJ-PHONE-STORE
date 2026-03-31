package model;

import dao.impl.ProductDAOImpl;

import java.math.BigDecimal;

public class OrderDetail {
        private String od_id;
        private int quantity;
        private String product_id;
        private String order_id;
        private BigDecimal price;


    public OrderDetail() {
    }

    public OrderDetail(String od_id, int quantity, String product_id, String order_id, BigDecimal price) {
        this.od_id = od_id;
        this.quantity = quantity;
        this.product_id = product_id;
        this.order_id = order_id;
        this.price = price;
    }

    public String getOd_id() {
        return od_id;
    }

    public void setOd_id(String od_id) {
        this.od_id = od_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void display() {
        String productName = (new ProductDAOImpl().getProductById(product_id) != null) ? new ProductDAOImpl().getProductById(product_id).getProduct_name() : "Unknown";
        BigDecimal total = price.multiply(BigDecimal.valueOf(quantity));
        System.out.printf("| %-18s | %-8d | %-12s | %-12s |\n",
                productName,
                quantity,
                String.format("%,.0f", price),
                String.format("%,.0f", total)
        );
    }
}
