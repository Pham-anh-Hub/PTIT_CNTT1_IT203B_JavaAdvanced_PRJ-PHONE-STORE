package model;

public abstract class User {
    private String id;
    private String username;
    private String email;
    private String password;
    private String role;
    private boolean status;

    public User() {
    }

    public User(String id, String username, String email, String password, String role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.status = true;
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

    public boolean isActive() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void displayInfo() {
        System.out.print("- Mã người dùng: " + id + "\n- Tên người dùng: " + username + "\n- Email: " + email + "\n- Mật khẩu: " + (password.substring(0, 4) + password.substring(0, 4) + "*".repeat(password.length() - 4)) + "\n- Vai trò: " + role);
    }
}
