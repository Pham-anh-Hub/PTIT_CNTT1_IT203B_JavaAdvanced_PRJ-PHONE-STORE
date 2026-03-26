package model;

public class Customer extends User{
    private String fullname;
    private String phone;
    private String address;

    public Customer() {
    }

    public Customer(String id, String username, String email, String password, String role, String fullname, String email1, String phone, String address) {
        super(id, username, email, password, role);
        this.fullname = fullname;
        this.phone = phone;
        this.address = address;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.print("\nTên: " + fullname + "\nSố điện thoại: " + phone + "\nĐịa chỉ: " + address);
    }
}
