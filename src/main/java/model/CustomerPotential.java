package model;

import utils.CenterFormat;

import java.text.NumberFormat;

public class CustomerPotential {
    private String username;
    private String fullname;
    private String email;
    private String phone;
    private int total_orders;
    private int total_paid;

    public CustomerPotential() {
    }

    public CustomerPotential(String username, String fullname, String email, String phone, int total_orders, int total_paid) {
        this.username = username;
        this.fullname = fullname;
        this.email = email;
        this.phone = phone;
        this.total_orders = total_orders;
        this.total_paid = total_paid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getTotal_orders() {
        return total_orders;
    }

    public void setTotal_orders(int total_orders) {
        this.total_orders = total_orders;
    }

    public int getTotal_paid() {
        return total_paid;
    }

    public void setTotal_paid(int total_paid) {
        this.total_paid = total_paid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void displayInfo(){
        NumberFormat nf = NumberFormat.getNumberInstance();
        System.out.printf("| %s | %s | %s | %s | %s | %s |\n",
                CenterFormat.center(username, 12),
                CenterFormat.center(fullname, 20),
                CenterFormat.center(email, 10),
                CenterFormat.center(phone, 15),
                CenterFormat.center(String.valueOf(total_orders), 10),
                CenterFormat.center(nf.format(total_paid) + " VND", 12)
        );
    }
}
