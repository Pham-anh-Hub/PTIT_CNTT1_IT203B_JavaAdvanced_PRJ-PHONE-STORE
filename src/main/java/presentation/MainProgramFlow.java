package presentation;

import org.mindrot.jbcrypt.BCrypt;
import utils.AuthSessionManagement;

public class MainProgramFlow {
    public static void main(String[] args) {
        AuthSessionManagement newSessionManagement = AuthSessionManagement.getInstance();

        do {
            if(newSessionManagement.getCurrentUser() == null){
                GuestPresentation.GuestPresentationMain();
            } else if (newSessionManagement.isAdmin()) {
                // Hiển thị menu quản lý cho admin
                AdminPresentation.AdminPresentationMain();
            }else if(newSessionManagement.isCustomer()){
                // Hiển thị menu mua sắm cho khách hàng
                CustomerPresentation.CustomerPresentationMain();
            }
        }while (true);

    }
}
