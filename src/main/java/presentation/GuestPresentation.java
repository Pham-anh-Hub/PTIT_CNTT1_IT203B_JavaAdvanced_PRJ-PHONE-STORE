package presentation;

import service.AuthenticServiceImpl;
import service.CategoryServiceImpl;
import service.ProductServiceImpl;

import java.util.Scanner;

public class GuestPresentation {
    public static Scanner sc = new Scanner(System.in);

    public void GuestPresentationMain(){
        int choice = -1;
        do{
            System.out.println(
                    "\n\n\n┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┳━━━━━━━━━━ FOR GUEST MANAGEMENT ━━━━━━━┳━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓\n" +
                            "|                                       |                                       |                                       |\n" +
                            "|   1. Display product list             |    2. Display Category List           |       3. Register                     |\n" +
                            "|                                       |                                       |                                       |\n" +
                            "┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━╋━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━╋━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫\n" +
                            "|                                       |                                       |                                       |\n" +
                            "|      4. Login                         |    5. Search product                  |   0. Back                             |\n" +
                            "|                                       |                                       |                                       |\n" +
                            "┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┻━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┻━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛"
            );

        System.out.print("Lựa chọn của bạn: ");
        try{
            choice = sc.nextInt();
        }catch (NumberFormatException e){
            System.err.println("Lựa chọn không hợp lệ");
        }
        switch (choice){
            case 1:
                // xem danh sach san pham
                new ProductServiceImpl().displayProductList();
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
            case 6:
                break;
            default:
                break;

        }


        }while(choice != 0);
    }

    public static void main(String[] args) {
        new GuestPresentation().GuestPresentationMain();
    }

}
