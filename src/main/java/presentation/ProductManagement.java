package presentation;

import dao.impl.ProductDAOImpl;
import service.ProductServiceImpl;

import java.util.Scanner;

public class ProductManagement {
    public static Scanner sc = new Scanner(System.in);
    public void productManagement(){
        int choice = -1;
        do {
            System.out.println(
                    "\n\n\n┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┳━━━━━━━━━━━ QUẢN LÝ SẢN PHẨM ━━━━━━━━━━┳━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓\n" +
                            "|                                       |                                       |                                       |\n" +
                            "|   1. Xem danh sách sản phẩm           |   2. Thêm sản phẩm mới                |   3. Cập nhật thông tin sản phẩm      |\n" +
                            "|                                       |                                       |                                       |\n" +
                            "┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┳━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┳━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┳━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫\n" +
                            "|                              |                             |                             |                            |\n" +
                            "|   4. Xóa sản phẩm            |   5. Tìm kiếm sản phẩm      | 6. Sắp xếp theo giá tiền    |     0. Quay lại            |\n" +
                            "|                              |                             |                             |                            |\n" +
                            "┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┻━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┻━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┻━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛"
            );

            System.out.print("Lựa chọn của bạn: ");
            try {
                choice = Integer.parseInt(sc.nextLine().trim());
            } catch (Exception e) {
                System.out.println("Lựa chọn không hợp lệ.");
                continue;
            }

            switch (choice){
                case 1:
                    new ProductServiceImpl().displayProductList(new ProductDAOImpl().getProducts(), "order by brand asc, price desc");
                    System.out.println("-".repeat(158));
                    break;
                case 2:
                    new ProductServiceImpl().addNewProduct();
                    System.out.println("-".repeat(158));
                    break;
                case 3:
                    new ProductServiceImpl().updateProduct();
                    System.out.println("-".repeat(158));
                    break;
                case 4:
                    new ProductServiceImpl().deleteProduct();
                    System.out.println("-".repeat(158));
                    break;
                case 5:
                    new ProductServiceImpl().searchProductByName();
                    break;
                case 6:
                    new ProductServiceImpl().sortProductsByPrice();
                    break;
                case 0:
                    break;
                default:
                    break;
            }
        }while (choice != 0);


    }

}
