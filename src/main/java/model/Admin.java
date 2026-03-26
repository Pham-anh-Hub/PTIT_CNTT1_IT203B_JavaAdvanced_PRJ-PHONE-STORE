package model;

public class Admin extends User{
    public Admin() {
    }

    public Admin(String id, String username, String email, String password, String role) {
        super(id, username, email, password, role);
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
    }
}
