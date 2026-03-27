package presentation;

import service.CategoryServiceImpl;

import java.util.Scanner;

public class CategoryPresentation {
    public static Scanner sc = new Scanner(System.in);
    public static void categoryManagement(){
        int c_choice = -1;
        do {
            System.out.println("\n\n\n┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┳━━━━━━━━ CATEGORY MANAGEMENT ━━━━━━━┳━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓\n" +
                    "|                                        |                                    |                                        |\n" +
                    "|        1. Display list category        |    2. Add new Category             |     3. Update category information     |\n" +
                    "|                                        |                                    |                                        |\n" +
                    "|━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┻━━━━━━━━━━━━━━━━━┳━━━━━━━━━━━━━━━━━━┻━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━|\n" +
                    "|                                                          |                                                           |\n" +
                    "|                4. Delete category                        |                   5. Back                                 |\n" +
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
