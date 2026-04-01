package presentation;

import model.Customer;
import service.*;
import utils.AuthSessionManagement;

import java.util.Scanner;

public class CustomerPresentation {
    public static Scanner sc = new Scanner(System.in);

    public static void CustomerPresentationMain() {
        int choice = -1;
        while (AuthSessionManagement.getInstance().isLoggedIn()){
            System.out.println("\n\n┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
            System.out.println("|                                                     CUSTOMER MENU                                                     |");
            System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
            System.out.println("|                            |                              |                              |                            |");
            System.out.println("|   1. Xem & mua sản phẩm    |    2. Tìm kiếm sản phẩm      |   3. Danh sách danh mục      |   4. Hủy đơn hàng          |");
            System.out.println("|                            |                              |                              |                            |");
            System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━┳━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┳━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┳━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
            System.out.println("|                            |                              |                              |                            |");
            System.out.println("|   5. Giỏ hàng & đặt hàng   |   6. Xem đơn hàng            |  7. Cập nhật hồ sơ           |    0. Đăng xuất            |");
            System.out.println("|                            |                              |                              |                            |");
            System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━┻━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┻━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┻━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");;

            System.out.print("Lựa chọn của bạn: ");
            try {
                choice = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println(" Lựa chọn không hợp lệ.");
                continue;
            }

            switch (choice) {
                case 1:
                    new OrderServiceImpl().orderDirectly();       // xem + mua ngay
                    break;
                case 2:
                    new ProductServiceImpl().searchProductByName();
                    break;
                case 3:
                    new CategoryServiceImpl().displayListCategory();
                    break;
                case 4:
                    new OrderServiceImpl().cancelOrder();
                    break;
                case 5:
                    new CartManagement().cartMenu();
                    break;
                case 6:
                    // lịch sử đơn
                    new OrderServiceImpl().displayDetailOrders();
                    break;
                case 7:
                    new AuthenticServiceImpl().updateProfile();
                    break;
                case 0:
                    new AuthenticServiceImpl().logout();
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ.");
                    break;
            }

        };
    }

}
