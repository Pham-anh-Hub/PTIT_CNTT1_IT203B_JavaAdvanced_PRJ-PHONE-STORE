package presentation;

import dao.impl.ProductDAOImpl;
import service.AuthenticServiceImpl;
import service.CategoryServiceImpl;
import service.ProductServiceImpl;
import utils.AuthSessionManagement;

import java.util.Scanner;

public class GuestPresentation {
    public static Scanner sc = new Scanner(System.in);

    public static void GuestPresentationMain(){

        int choice = -1;
        do{
            if (AuthSessionManagement.getInstance().isLoggedIn()) break;
            System.out.println(
                    "\n\n\n┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┳━━━━━ SmartPhone Store Management ━━━━━┳━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓\n" +
                            "|                                       |                                       |                                       |\n" +
                            "|   1. Xem danh sách sản phẩm           |    2.Xem danh sách danh mục           |       3. Đăng ký                      |\n" +
                            "|                                       |                                       |                                       |\n" +
                            "┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━╋━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━╋━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫\n" +
                            "|                                       |                                       |                                       |\n" +
                            "|      4. Đăng nhập                     |    5. Tìm kiếm sản phẩm               |       0. Thoát                        |\n" +
                            "|                                       |                                       |                                       |\n" +
                            "┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┻━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┻━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛"
            );

        System.out.print("Lựa chọn của bạn: ");
        try{
            choice = Integer.parseInt(sc.nextLine().trim());
        }catch (NumberFormatException e){
            System.err.println("Lựa chọn không hợp lệ");
            continue;
        }
        switch (choice){
            case 1:
                // xem danh sach san pham
                new ProductServiceImpl().displayProductList(new ProductDAOImpl().getProducts(), "order by brand asc, price desc");
                break;
            case 2:
                // Xem danh sach danh muc - hang
                new CategoryServiceImpl().displayListCategory();
                break;
            case 3:
                // Dang ky tai khoan
                new AuthenticServiceImpl().register();
                break;
            case 4:
                // Dang nhap tai khoan
                new AuthenticServiceImpl().login();
                break;
            case 5:
                new ProductServiceImpl().searchProductByName();
                break;
            case 0:
                System.out.println("=============================================== CẢM ƠN VÀ HẸN GẶP LẠI!! ===============================================");
                break;
            default:
                break;

        }


        }while(choice != 0);
    }
}
