package presentation;

import service.UserServiceImpl;

import java.util.Scanner;

public class CustomerManagement {
    public static Scanner sc = new Scanner(System.in);

    public void customerManagement(){
        int choice = -1;
        do {
            System.out.println("\n\n\n┏━━━━━━━━━━━━━━━━━━━━━━━━━━ QUẢN LÝ KHÁCH HÀNG ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓\n" +
                    "|                                        |                                       |\n" +
                    "|        1. Xem danh sách khách hàng     |    2. Tìm kiếm khách hàng             |\n" +
                    "|                                        |                                       |\n" +
                    "|━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┳━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━|\n" +
                    "|                                        |                                       |\n" +
                    "|        3. Khóa/Mở khóa khách hàng      |       5. Quay lại                     |\n" +
                    "|                                        |                                       |\n" +
                    "┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┻━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
            System.out.print("Lựa chọn của bạn:  ");
            try {
                choice = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Lựa chọn không hợp lệ.");
                continue;
            }

            switch (choice) {
                case 1:
                    new UserServiceImpl().displayAllCustomers();
                    break;
                case 2:
                    new UserServiceImpl().searchCustomer();
                    break;
                case 3:
                    System.out.println("1. Mở khóa tài khoản\n" +
                            "2. Khóa tài khoản\n" +
                            "Lựa chọn: ");
                    try {
                        int admChoice = sc.nextInt();
                        if (admChoice == 1) {
                            new UserServiceImpl().unlockCustomerAccount();
                            break;
                        } else if (admChoice == 2) {
                            new UserServiceImpl().lockCustomerAccount();
                            break;
                        }
                    } catch (NumberFormatException e) {
                        System.err.println("Lựa chọn không hợp lệ\n" + e.toString());
                    }
                case 4:
                    break;
                default:
                    System.err.println("Lựa chọn không hợp lệ");
                    break;
            }
        }while (choice != 4);
    }


}
