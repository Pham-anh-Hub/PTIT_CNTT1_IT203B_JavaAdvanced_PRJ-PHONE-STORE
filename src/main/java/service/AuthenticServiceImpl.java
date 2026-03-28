package service;

import dao.AdminDAO;
import dao.UserDAOImpl;
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
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━ ĐĂNG NHẬP ━━━━━━━━━━━━━━━━━━━━");
        System.out.print("Username: ");
        String username = sc.nextLine().trim();

        System.out.print("Mật khẩu: ");
        String password = sc.nextLine().trim();

        User user = new UserDAOImpl().findByUsername(username);

        if (user == null || !BCrypt.checkpw(password, user.getPassword())) {
            System.out.println("Tên đăng nhập hoặc mật khẩu chưa đúng.");
            return;
        }

        AuthSessionManagement.getInstance().login(user);
        System.out.println("Đăng nhập thành công! Xin chào, " + (user.getRole().equalsIgnoreCase("admin") ? "Quản trị viên" : "Khách hàng") + " " + user.getUsername());
    }


    // ĐĂNG ký đối với khách h
    @Override
    public void register() {
        System.out.println("━━━━━━━━━━━━━━━━━━━━ ĐĂNG KÝ ━━━━━━━━━━━━━━━━━━━━");
        Customer newCustomer = new Customer();
        newCustomer.setRole("CUSTOMER");

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
                System.out.println("[!] Email không đúng định dạng.");
            } else if (new UserDAOImpl().existsByEmail(email)) {
                System.out.println("[!] Email đã được sử dụng.");
            } else {
                newCustomer.setEmail(email);
                break;
            }
        }

        // Mật khẩu
        while (true) {
            System.out.print("Mật khẩu (ít nhất 6 ký tự): ");
            String password = sc.nextLine().trim();
            if (password.length() < 6) {
                System.out.println("Mật khẩu phải ít nhất 6 ký tự.");
            } else {
                newCustomer.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
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

            if (!phone.matches("^(0[3|5|7|8|9])+([0-9]{8})$")) {
                System.out.println("Số điện thoại không hợp lệ (VD: 0912345678).");
            } else {
                for (User u : new AdminDAO().getUsers()){
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

        // Sinh ID
        newCustomer.setId("CU" + String.format("%04d", (int)(Math.random() * 10000)));

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
            System.out.println("Tạm biệt, " + new AuthSessionManagement().getCurrentUser().getUsername() + "!");
            new AuthSessionManagement().logout();
        }else{
            System.out.println("Tiếp tục các thao tác");
        }


    }
}
