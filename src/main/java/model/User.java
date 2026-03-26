package model;

public abstract class User {
    private String id;
    private String username;
    private String email;
    private String password;
    private String role;

    public User() {
    }

    public User(String id, String username, String email, String password, String role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void displayInfo() {
        System.out.print("Mã người dùng: " + id + "\nTên người dùng: " + username + "\nEmail: " + email + "\nMật khẩu: " + (password.substring(0, 4) + password.substring(4, password.length())) + "\nVai trò: " + role);
    }
}
