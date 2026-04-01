package presentation;

import dao.impl.OrderImpl;
import service.OrderServiceImpl;

import java.util.Scanner;

public class OrderManagement {
    public static Scanner sc = new Scanner(System.in);
    public void orderManagement(){
        int choice = -1;
        do {
            System.out.println("\n\n┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ QUẢN LÝ ĐƠN HÀNG ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
            System.out.println("|                                       |                                      |");
            System.out.println("|   1. Xem tất cả đơn hàng              |   2. Xem chi tiết đơn hàng           |");
            System.out.println("|                                       |                                      |");
            System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━╋━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
            System.out.println("|                                       |                                      |");
            System.out.println("|   3. Cập nhật trạng thái              |       0. Quay lại                    |");
            System.out.println("|                                       |                                      |");
            System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┻━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");

            System.out.print("Lựa chọn của bạn: ");
            try {
                choice = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.err.println("Lựa chọn không hợp lệ.");
                continue;
            }

            switch (choice) {
                case 1:
                    new OrderServiceImpl().displayAllOrders(new OrderImpl().getAllOrders());
                    break;
                case 2:
                    new OrderServiceImpl().displayOrderDetails();
                    break;
                case 3:
                    new OrderServiceImpl().updateOrderStatus();
                    break;
                case 0:
                    break;
                default:
                    System.err.println("Lựa chọn không hợp lệ.");
                    break;
            }

        } while (choice != 0);
    }
}
