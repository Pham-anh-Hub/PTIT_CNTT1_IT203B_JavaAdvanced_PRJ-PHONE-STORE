package model;

import dao.impl.CategoryDAOImpl;
import utils.CenterFormat;

import java.math.BigDecimal;
import java.text.NumberFormat;

public class BestSellerProduct {
    // p.product_id, p.product_name, p.brand, sum(quantity) as quantity, sum(p.price) as total_revenue
    private String product_id;
    private String brand;
    private String product_name;
    private int total_quantity;
    private BigDecimal total_revenue;

    public BestSellerProduct() {
    }

    public BestSellerProduct(String product_id, String brand, String product_name, int total_quantity, BigDecimal total_revenue) {
        this.product_id = product_id;
        this.brand = brand;
        this.product_name = product_name;
        this.total_quantity = total_quantity;
        this.total_revenue = total_revenue;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public int getTotal_quantity() {
        return total_quantity;
    }

    public void setTotal_quantity(int total_quantity) {
        this.total_quantity = total_quantity;
    }

    public BigDecimal getTotal_revenue() {
        return total_revenue;
    }

    public void setTotal_revenue(BigDecimal total_revenue) {
        this.total_revenue = total_revenue;
    }

    public void displayInfo(){
        NumberFormat nf = NumberFormat.getNumberInstance();
        System.out.printf("| %s | %s | %s | %s | %s |\n",
                CenterFormat.center(product_id, 12),
                CenterFormat.center(product_name, 20),
                CenterFormat.center(brand, 10),
                CenterFormat.center(String.valueOf(total_quantity), 15),
                CenterFormat.center(nf.format(total_revenue) + " VND", 10)
        );
    }
}
