package model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Product {
    private String product_id;
    private String product_name;
    private String brand;
    private int storage;
    private String color;
    private BigDecimal price;
    private int stock;
    private String description;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    public String category_id;


    public Product() {
    }

    public Product(String product_id, String product_name, String brand, int storage, String color, BigDecimal price, int stock, String description, String category_id) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.brand = brand;
        this.storage = storage;
        this.color = color;
        this.price = price;
        this.stock = stock;
        this.description = description;
        this.created_at = LocalDateTime.now();
        this.updated_at = LocalDateTime.now();
        this.category_id = category_id;
    }


    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getStorage() {
        return storage;
    }

    public void setStorage(int storage) {
        this.storage = storage;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public LocalDateTime getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(LocalDateTime updated_at) {
        this.updated_at = updated_at;
    }

    public void displayInfoProduct() {
        System.out.printf("| %-8s | %-20s | %-10s | %-8s | %-8s | %-12s | %-8s | %-19s | %-19s |\n",
                product_id,
                product_name,
                brand,
                color,
                storage + "GB",
                String.format("%,.0f VND", price),
                stock,
                (created_at != null ? created_at.toString() : "N/A"),
                (updated_at != null ? updated_at.toString() : "N/A")
        );
    }


}
