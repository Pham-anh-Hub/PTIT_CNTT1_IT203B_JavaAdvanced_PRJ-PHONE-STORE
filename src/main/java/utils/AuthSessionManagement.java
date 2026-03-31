package utils;

import model.User;

public class AuthSessionManagement {
    private static AuthSessionManagement instance;
    private User currentUser = null;

    public AuthSessionManagement() {
    }

    public static AuthSessionManagement getInstance() {
        if (instance == null){
            instance = new AuthSessionManagement();
        }
        return instance;
    }

    public User getCurrentUser() {
        return currentUser;
    }
    // setCurrentUser
    public void login (User currentUser) {
        this.currentUser = currentUser;
    }

    public void logout(){
        this.currentUser = null;
    }


    // Kiểm tra  đăng nhập
    public boolean isLoggedIn() {
        return currentUser != null;
    }

    // Kiểm tra role Admin
    public boolean isAdmin() {
        return isLoggedIn() && currentUser.getRole().equalsIgnoreCase("ADMIN");
    }

    // Kiểm tra role Customer
    public boolean isCustomer() {
        return isLoggedIn() && currentUser.getRole().equalsIgnoreCase("CUSTOMER");
    }
}
