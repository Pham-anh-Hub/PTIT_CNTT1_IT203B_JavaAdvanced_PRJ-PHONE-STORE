package service;

import dao.impl.AdminDAOImpl;
import dao.impl.UserDAOImpl;
import model.Customer;
import model.User;
import org.mindrot.jbcrypt.BCrypt;
import service.serviceInterface.IAuthenticService;
import utils.AuthSessionManagement;

import java.util.Scanner;

public class AuthenticServiceImpl implements IAuthenticService{
    public static Scanner sc = new Scanner(System.in);
    @Override
    public void login() {
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ ĐĂNG NHẬP ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.print("Username: ");
        String username = sc.nextLine().trim();

        System.out.print("Mật khẩu: ");
        String password = sc.nextLine().trim();

        User user = new UserDAOImpl().findByUsername(username);

        if (user == null || !BCrypt.checkpw(password, user.getPassword())) {
            System.out.println("Tên đăng nhập hoặc mật khẩu chưa đúng.");
            System.out.println("Bạn chưa có tài khoản? " + ("\u001B[33m" + "3. Đăng ký" + "\u001B[0m"));
            return;
        }

        AuthSessionManagement.getInstance().login(user);
        System.out.println("\n\n=========================================================================================================================");
        System.out.println("\t".repeat(7) + "Đăng nhập thành công! Xin chào, " + "\u001B[32m" + (user.getRole().equalsIgnoreCase("admin") ? "Quản trị viên" : "Khách hàng") + " " + user.getUsername() + "\u001B[0m");
        System.out.println("=========================================================================================================================");

    }


    // ĐĂNG ký đối với khách h
    @Override
    public void register() {
        System.out.println("━━━━━━━━━━━━━━━━━━━━ ĐĂNG KÝ ━━━━━━━━━━━━━━━━━━━━");
        Customer newCustomer = new Customer();
        newCustomer.setRole("CUSTOMER");

        // Sinh ID
        newCustomer.setId("CU" + String.format("%04d", (int)(Math.random() * 10000)));

        // Username
        while (true) {
            System.out.print("Username: ");
            String username = sc.nextLine().trim();
            if (username.isEmpty()) {
                System.out.println("Tên đăng nhập không được để trống.");
            } else if (new UserDAOImpl().existsByUsername(username)) {
                System.out.println("Tên đăng nhập đã tồn tại.");
            } else {
                newCustomer.setUsername(username);
                break;
            }
        }

        // Email
        while (true) {
            System.out.print("Email: ");
            String email = sc.nextLine().trim();
            if (!email.matches("^[\\w.-]+@[\\w.-]+\\.[a-z]{2,}$")) {
                System.out.println("Email không đúng định dạng.");
            } else if (new UserDAOImpl().existsByEmail(email)) {
                System.out.println("Email đã được sử dụng.");
            } else {
                newCustomer.setEmail(email);
                break;
            }
        }

        // Mật khẩu
        while (true) {
            System.out.print("Mật khẩu: ");
            String password = sc.nextLine().trim();
            if (!password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{8,}$")) {
                System.out.println("Mật khẩu phải tối thiểu 8 ký tự và chứa ít nhất 1 chữ cái in hoa, 1 chữ thường và số.");
            } else {
                newCustomer.setPassword(BCrypt.hashpw(password, BCrypt.gensalt())); //
                break;
            }
        }

        // Họ tên
        System.out.print("Họ và tên: ");
        String fullname = sc.nextLine().trim();
        newCustomer.setFullname(fullname.isEmpty() ? "Chưa cập nhật" : fullname);

        // Số điện thoại
        while (true) {
            System.out.print("Số điện thoại: ");
            String phone = sc.nextLine().trim();
            boolean phoneExisted = false;

            if (!phone.matches("^(0[389])+([0-9]{8})$")) {
                System.out.println("Số điện thoại không hợp lệ (VD: 0987654321).");
            } else {
                for (User u : new AdminDAOImpl().getUsers()){
                    if(u instanceof Customer){
                        if(((Customer) u).getPhone().equals(phone)){
                            phoneExisted = true;
                            break;
                        }
                    }
                }
                if(!phoneExisted){
                    newCustomer.setPhone(phone);
                    break;
                }
            }
        }

        // Địa chỉ
        System.out.print("Địa chỉ giao hàng: ");
        String address = sc.nextLine().trim();
        newCustomer.setAddress(address.isEmpty() ? "Chưa cập nhật" : address);

        if (new UserDAOImpl().insertUser(newCustomer)) {
            System.out.println("Đăng ký thành công! Vui lòng đăng nhập.");
        } else {
            System.out.println("Đăng ký thất bại!");
        }
    }

    @Override
    public void logout() {
        System.out.print("Xác nhận đăng xuất (y/n)? ");
        boolean confirmLogout = sc.nextLine().equalsIgnoreCase("y");
        if(confirmLogout){
            System.out.println("Tạm biệt, " + AuthSessionManagement.getInstance().getCurrentUser().getUsername() + "!");
            AuthSessionManagement.getInstance().logout();
        }else{
            System.out.println("Tiếp tục");
        }
    }

    public void updateProfile() {
        Customer current = (Customer) AuthSessionManagement.getInstance().getCurrentUser();  // Lấy thông tin tài khoản hiện tại

        System.out.println("=".repeat(20) + " CẬP NHẬT HỒ SƠ " + "=".repeat(20));

        // Họ tên
        System.out.print("Họ và tên [" + current.getFullname() + "] (Nhập k để giữ thông tin): ");
        String fullname = sc.nextLine().trim();
        if (!fullname.isEmpty() && !fullname.equalsIgnoreCase("k")) {
            current.setFullname(fullname);
        }

        // Email
        while (true) {
            System.out.print("Email : " + current.getEmail() + " (Nhập k để giữ thông tin): ");
            String email = sc.nextLine().trim();
            if (email.equalsIgnoreCase("k")) {
                break; // giữ nguyên
            }
            if (!email.matches("^[a-zA-Z\\d]+@[a-zA-Z\\d]+\\.[a-zA-Z]{2,}$")) {
                System.out.println("Email không đúng định dạng.");
            } else if (new UserDAOImpl().existsByEmail(email)
                    && !email.equalsIgnoreCase(current.getEmail())) {
                System.out.println("Email đã được sử dụng.");
            } else {
                current.setEmail(email);
                break;
            }
        }

        // Số điện thoại
        while (true) {
            System.out.print("Số điện thoại (" + current.getPhone() + ") (Nhập k để giữ thông tin): ");
            String phone = sc.nextLine().trim();
            if (phone.equalsIgnoreCase("k")){
                break; // giữ nguyên
            }
            if (!phone.matches("^0[389]\\d{8}$")) {

                System.out.println("Số điện thoại chưa hợp lệ.");
            } else {
                current.setPhone(phone);
                break;
            }
        }

        // Địa chỉ
        System.out.print("Địa chỉ (" + current.getAddress() + ") (Nhập k để giữ thông tin): ");
        String address = sc.nextLine().trim();
        if (!address.equalsIgnoreCase("k")) current.setAddress(address);

        // Đổi mật khẩu
        System.out.print("Đổi mật khẩu? (Y/N): ");
        if (sc.nextLine().trim().equalsIgnoreCase("Y")) {
            // Xác nhận mật khẩu cũ trước
            System.out.print("Nhập mật khẩu hiện tại: ");
            String oldPassword = sc.nextLine().trim();
            if (!BCrypt.checkpw(oldPassword, current.getPassword())) {
                System.out.println("Mật khẩu hiện tại không đúng.");
            } else {
                while (true) {
                    System.out.print("Mật khẩu mới: ");
                    String newPassword = sc.nextLine().trim();
                    if (!newPassword.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{8,}$")) {
                        System.out.println("Mật khẩu phải tối thiểu 8 ký tự và chứa ít nhất 1 chữ cái in hoa, 1 chữ thường và số.");
                    } else {
                        current.setPassword(BCrypt.hashpw(newPassword, BCrypt.gensalt()));
                        break;
                    }
                }
            }
        }

        // Lưu lại dữ liệu
        if (new UserDAOImpl().updateUser(current)) {
            // Cập nhật
            AuthSessionManagement.getInstance().login(current);
            System.out.println("Cập nhật hồ sơ thành công!");
        } else {
            System.out.println("Cập nhật thất bại!");
        }
    }


}
