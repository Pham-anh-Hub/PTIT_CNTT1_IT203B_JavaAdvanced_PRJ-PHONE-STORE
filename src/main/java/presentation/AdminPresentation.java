package presentation;

import model.Admin;
import model.Customer;
import service.AuthenticServiceImpl;
import utils.AuthSessionManagement;

import java.util.Scanner;

public class AdminPresentation {
    public static Scanner sc = new Scanner(System.in);

    public static void AdminPresentationMain(){
        int choice = -1;
        do {
            System.out.println("-".repeat(100));

            System.out.println("\n┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┳━━━━━━━━━━━━━ ADMIN MENU ━━━━━━━━━━━━━━┳━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
            System.out.println("|                                       |                                       |                                       |");
            System.out.println("|   1. Quản lý sản phẩm                 |   2. Quản lý danh mục                 |   3. Quản lý khách hàng               |");
            System.out.println("|                                       |                                       |                                       |");
            System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
            System.out.println("|                                       |                                       |                                       |");
            System.out.println("|   4. Quản lý đơn hàng                 |   5. Thống kê & Báo cáo               |   0. Đăng xuất                        |");
            System.out.println("|                                       |                                       |                                       |                            |");
            System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┻━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┻━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");;

            System.out.print("Lựa chọn của bạn: ");
            try{
                choice = sc.nextInt();
            }catch (NumberFormatException e ){
                System.err.println("Lựa chọn không hợp lệ");
                continue;
            }

            switch (choice){
                case 1:
                    // qly san pham
                    new ProductManagement().productManagement();
                    break;
                case 2:
                    // qly danh muc
                    new CategoryManagement().categoryManagement();
                    break;
                case  3:
                    // qly khach hang
                    new CustomerManagement().customerManagement();
                    break;
                case 4:
                    // qly don hang
                    new OrderManagement().orderManagement();
                    break;
                case 5:
                    new StatisticManagement().statisticManagement();
                    break;
                case 0:
                    new AuthenticServiceImpl().logout();
                    break;
                default:
                    break;
            }

        }while (choice != 6);
    }
}
