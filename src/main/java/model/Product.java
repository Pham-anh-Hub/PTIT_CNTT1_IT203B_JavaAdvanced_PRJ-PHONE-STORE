package model;

import dao.CategoryDAOImpl;
import utils.CenterFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
    private boolean isActive;


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
        this.isActive = true;
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

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void displayInfoProduct() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        System.out.printf("| %s | %s | %s | %s | %s | %s | %s | %s | %s | %s |\n",
                CenterFormat.center(product_id, 12),
                CenterFormat.center(product_name, 20),
                CenterFormat.center(new CategoryDAOImpl().getCategoryById(category_id).getCate_name(), 10),
                CenterFormat.center(brand, 10),
                CenterFormat.center(color, 8),
                CenterFormat.center(storage + " GB", 10),
                CenterFormat.center(String.format("%,.0f VND", price), 14),
                CenterFormat.center(String.valueOf(stock), 7),
                CenterFormat.center(created_at != null ? dtf.format(created_at) : "_", 19),
                CenterFormat.center(updated_at != null ? dtf.format(updated_at) : "_", 19)
        );

//        // In mô tả
//        String desc = (description != null && !description.isBlank()) ? description : "Không có mô tả";
//        System.out.printf("|  %-156s|\n", " Mô tả: " + desc);
//        System.out.println("|" + "─".repeat(158) + "|");
    }


}
