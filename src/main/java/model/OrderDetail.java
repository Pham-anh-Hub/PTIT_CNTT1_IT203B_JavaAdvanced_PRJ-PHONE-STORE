package model;

import java.math.BigDecimal;
import java.util.PriorityQueue;

public class OrderDetail {
        private String od_id;
        private int quantity;
        private Product product;
        private BigDecimal price;


    public OrderDetail() {
    }

    public OrderDetail(String od_id, int quantity, Product product, BigDecimal price) {
        this.od_id = od_id;
        this.quantity = quantity;
        this.product = product;
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

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }


    public void display() {
        String productName = (product != null) ? product.getProduct_name() : "Unknown";
        BigDecimal total = price.multiply(BigDecimal.valueOf(quantity));
        System.out.printf("| %-20s | %-8d | %-12s | %-12s |\n",
                productName,
                quantity,
                String.format("%,.0f", price),
                String.format("%,.0f", total)
        );
    }
}
