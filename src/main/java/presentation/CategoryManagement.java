package presentation;

import service.CategoryServiceImpl;

import java.util.Scanner;

public class CategoryManagement {
    public static Scanner sc = new Scanner(System.in);
    public void categoryManagement(){
        int c_choice = -1;
        do {
            System.out.println("-".repeat(123));
            System.out.println("\n\n\n┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┳━━━━━━━━ QUẢN LÝ DANH MỤC ━━━━━━━━━━┳━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓\n" +
                    "|                                        |                                    |                                        |\n" +
                    "|        1. Xem danh sách danh mục       |    2. Thêm mới danh mục            |     3. Cập nhật thông tin danh mục     |\n" +
                    "|                                        |                                    |                                        |\n" +
                    "|━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┻━━━━━━━━━━━━━━━━━┳━━━━━━━━━━━━━━━━━━┻━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━|\n" +
                    "|                                                          |                                                           |\n" +
                    "|                4. Xóa danh mục                           |                   5. Quay lại                             |\n" +
                    "|                                                          |                                                           |\n" +
                    "┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┻━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
            System.out.print("Lựa chọn của bạn:  ");

            try {
                c_choice = sc.nextInt();
            } catch (NumberFormatException e) {
                System.err.println("Lựa chọn không hợp lệ! ");
            }
            switch (c_choice){
                case 1:
                    // Hiển thị danh sách danh mục
                    new CategoryServiceImpl().displayListCategory();
                    break;
                case 2:
                    //Thêm mới danh mục
                    new CategoryServiceImpl().addNewCategory();
                    break;
                case 3:
                    // Cập nhật
                    new CategoryServiceImpl().updateCategory();
                    break;
                case 4:
                    // Xóa Danh mục
                    new CategoryServiceImpl().deleteCategory();
                    break;
                case 5:
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
                    break;
            }

        }while (c_choice != 5);
    }
}
