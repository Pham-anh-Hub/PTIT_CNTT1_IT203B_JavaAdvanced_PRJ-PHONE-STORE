package service;

import dao.CategoryDAO;
import model.Category;
import utils.CenterFormat;

import java.util.Scanner;

import static utils.CenterFormat.center;

public class CategoryService {
    public static Scanner sc = new Scanner(System.in);

    // Menu hiển thị menu quản lý - kiểm tra trang thái đăng nhập



    // Hiển thị danh sách hãng sản xuất (Apple, Samsung...) dạng bảng
    public static void displayListCategory(){
        if(CategoryDAO.getCategories().isEmpty()){
            System.out.println("Danh sách hãng sản xuất - danh mục trống!");
            return;
        }
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.printf("| %s |\n",CenterFormat.center("DANH SÁCH DANH MỤC", 98));
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.printf("| %s | %s | %s |\n", center("Mã danh mục", 12), center("Danh mục", 20), center("Mô tả", 60));
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        for(Category cate : CategoryDAO.getCategories()){
            cate.displayInforCate();
        }
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
    }


    //Thêm/Sửa danh mục (Kiểm tra trùng tên, ID tồn tại)
    public static void addNewCategory(){
        Category newCate = new Category();

        while (true){
            String cateId = "C" + String.format("%03d", CategoryDAO.getCategories().size() + 1);
            newCate.setCate_id(cateId);
            break;
        }

        while (true){
            System.out.print("Nhập tên danh mục thêm mới: ");
            String cateName = sc.nextLine();
            if(cateExisted(cateName)){
                System.out.println("Tên danh mục đã tồn tại, vui lòng nhập lại");
            }else{
                newCate.setCate_name(cateName);
                break;
            }
        }

        while (true){
            System.out.print("Nhập mô tả danh mục: ");
            String cateDescript = sc.nextLine();
            if (cateDescript.isEmpty()){
                System.out.println("Vui lòng không để trống nội dung mô tả: ");
            }else{
                newCate.setDescription(cateDescript);
                break;
            }
        }

        boolean addStatus = CategoryDAO.insertNewCategory(newCate);
        if(addStatus){
            System.out.println("Thêm mới danh mục thành công!");
        }else {
            System.out.println("Thêm mới danh mục thất bại!");
        }
    }

    // Cập nhật danh mục


    //Xóa danh mục (Xóa mềm)


    // main - Test
    public static void main(String[] args) {
        displayListCategory();
    }


    // Kiểm tra danh mục tồn tại hay chưa
    public static boolean cateExisted(String cateName){
        for (Category cate : CategoryDAO.getCategories()){
            if(cate.getStatus() && cate.getCate_name().equals(cateName)){
                return true;
            }
        }
        return false;
    }
}
