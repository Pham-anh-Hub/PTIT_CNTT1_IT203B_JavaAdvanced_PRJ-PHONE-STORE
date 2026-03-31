package service;

import dao.impl.UserDAOImpl;
import model.Customer;
import model.User;
import service.serviceInterface.IUserService;

import java.util.List;
import java.util.Scanner;

public class UserServiceImpl implements IUserService {
    public static Scanner sc = new Scanner(System.in);

    @Override
    public void displayAllCustomers() {
        List<User> customers = new UserDAOImpl().getAllCustomers();
        System.out.println("━".repeat(30) + " DANH SÁCH KHÁCH HÀNG " + "━".repeat(30));

        if (customers.isEmpty()) {
            System.out.println("[!] Chưa có khách hàng nào.");
            return;
        }

        System.out.println("┏" + "━".repeat(102) + "┓");
        System.out.printf("| %-12s | %-15s | %-25s | %-15s | %-20s |\n",
                "Mã khách hàng", "Tên đăng nhập", "Email", "Số điện thoại", "Trạng thái");
        System.out.println("|" + "━".repeat(102) + "|");

        for (User u : customers) {
            Customer c = (Customer) u;
            System.out.printf("| %-13s | %-15s | %-25s | %-15s | %-20s |\n",
                    c.getId(), c.getUsername(), c.getEmail(),
                    c.getPhone(), c.isActive() ? "Đang hoạt động" : "Không còn hoạt động");
        }
        System.out.println("┗" + "━".repeat(102) + "┛");
    }

    public void searchCustomer() {
        System.out.print("Nhập username hoặc email cần tìm: ");
        String keyword = sc.nextLine().trim();

        List<User> results = new UserDAOImpl().searchCustomer(keyword);
        if (results.isEmpty()) {
            System.out.println("Không tìm thấy khách hàng.");
            return;
        }
        results.forEach(u -> u.displayInfo());
    }

    // khóa tài khoản KH - thay vì xóa
    @Override
    public void lockCustomerAccount() {
        displayAllCustomers();

        String userId;
        while (true){
            System.out.print("Nhập mã khách hàng cần khóa: ");
            userId = sc.nextLine().trim();
            if(userId.isEmpty()){
                System.out.println("Vui lòng không để trống");
            }else{
                break;
            }
        }

        String userEmail;
        while (true){
            System.out.print("Nhập email khách hàng: ");
            userEmail = sc.nextLine().trim();
            if(userEmail.isEmpty()){
                System.out.println("Vui lòng không để trống");
            }else{
                break;
            }
        }

        User user = new UserDAOImpl().getUserByIdEmail(userId, userEmail);
        if (user == null) {
            System.out.println("Khách hàng không tồn tại.");
            return;
        }
        if (!user.isActive()) {
            System.out.println("Tài khoản này đã bị khóa rồi.");
            return;
        }

        System.out.print("Xác nhận khóa tài khoản \"" + user.getUsername() + "\"? (y/n): ");
        if (sc.nextLine().trim().equalsIgnoreCase("Y")) {
            if (new UserDAOImpl().setActiveStatus(userId, false)) {
                System.out.println("Đã khóa tài khoản " + user.getUsername());
            } else {
                System.out.println("Khóa tài khoản thất bại.");
            }
        } else {
            System.out.println("Đã hủy thao tác.");
        }
    }

    // tuơng tự với khóa, cập nhật lại trạng thái tài khoản
    @Override
    public void unlockCustomerAccount() {
        displayAllCustomers();

        String userId;
        while (true){
            System.out.print("Nhập mã khách hàng cần khóa: ");
            userId = sc.nextLine().trim();
            if(userId.isEmpty()){
                System.out.println("Vui lòng không để trống");
            }else{
                break;
            }
        }

        String userEmail;
        while (true){
            System.out.print("Nhập email khách hàng: ");
            userEmail = sc.nextLine().trim();
            if(userEmail.isEmpty()){
                System.out.println("Vui lòng không để trống");
            }else{
                break;
            }
        }

        User user = new UserDAOImpl().getUserByIdEmail(userId, userEmail);
        if (user == null) {
            System.out.println("Khách hàng không tồn tại.");
            return;
        }
        if (user.isActive()) {
            System.out.println("Tài khoản này đang hoạt động bình thường.");
            return;
        }

        System.out.print("Xác nhận mở khóa tài khoản \"" + user.getUsername() + "\"? (y/n): ");
        if (sc.nextLine().trim().equalsIgnoreCase("Y")) {
            if (new UserDAOImpl().setActiveStatus(userId, true)) {
                System.out.println("Đã mở khóa tài khoản " + user.getUsername());
            } else {
                System.out.println("Mở khóa tài khoản thất bại.");
            }
        } else {
            System.out.println("Đã hủy thao tác.");
        }
    }

}
