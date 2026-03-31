package presentation;

import service.CartServiceImpl;
import service.OrderServiceImpl;

import java.util.Scanner;

public class CartManagement {
    public static Scanner sc = new Scanner(System.in);
    public void cartMenu() {
        int choice = -1;
        do {
            System.out.println("\n┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┳━━━━━━━━━━━THAO TÁC GIỎ HÀNG ━━━━━━━━━━┳━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
            System.out.println("|                                       |                                       |                                       |");
            System.out.println("|   1. Xem giỏ hàng                     |   2. Thêm sản phẩm vào giỏ            |   3. Cập nhật số lượng                |");
            System.out.println("|                                       |                                       |                                       |");
            System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━╋━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━╋━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
            System.out.println("|                                       |                                       |                                       |");
            System.out.println("|   4. Xóa sản phẩm khỏi giỏ            |   5. Đặt hàng từ giỏ                  |   0. Quay lại                         |");
            System.out.println("|                                       |                                       |                                       |");
            System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┻━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┻━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");

            System.out.print("Lựa chọn của bạn: ");
            try {
                choice = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Lựa chọn không hợp lệ.");
                continue;
            }

            switch (choice) {
                case 1:
                    new CartServiceImpl().displayCart();
                    break;
                case 2:
                    new CartServiceImpl().addToCart();
                    break;
                case 3:
                    new CartServiceImpl().updateCartItem();
                    break;
                case 4:
                    new CartServiceImpl().removeFromCart();
                    break;
                case 5:
                    new OrderServiceImpl().orderWithCart();
                    break;
                case 0:
                    System.out.println();
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ.");
                    break;
            }

        } while (choice != 0);
    }
}
