package presentation;

import service.CategoryServiceImpl;
import service.ProductServiceImpl;

import java.util.Scanner;

public class ProductPresentation {
    public static Scanner sc = new Scanner(System.in);
    public void ProductManagement(){
        int choice = -1;
        do {
            System.out.println(
                    "\n\n\n┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┳━━━━━━━━━━ PRODUCT MANAGEMENT ━━━━━━━━━┳━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓\n" +
                            "|                                       |                                       |                                       |\n" +
                            "|   1. Display product list             |   2. Add new product                  |   3. Update product                   |\n" +
                            "|                                       |                                       |                                       |\n" +
                            "┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━╋━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━╋━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫\n" +
                            "|                                       |                                       |                                       |\n" +
                            "|   4. Delete product                   |   5. Search product                   |   0. Back                             |\n" +
                            "|                                       |                                       |                                       |\n" +
                            "┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┻━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┻━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛"
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
                    new ProductServiceImpl().displayProductList();
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
                case 0:
                    break;
                default:
                    break;
            }
        }while (choice != 0);


    }

    public static void main(String[] args) {
        new ProductPresentation().ProductManagement();
    }
}
