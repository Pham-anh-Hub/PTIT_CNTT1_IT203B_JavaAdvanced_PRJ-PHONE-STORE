package service;

import dao.impl.CategoryDAOImpl;
import model.Category;
import service.serviceInterface.ICategoryService;
import utils.CenterFormat;
import java.util.Scanner;
import static utils.CenterFormat.center;

public class CategoryServiceImpl implements ICategoryService {
    public static Scanner sc = new Scanner(System.in);

    // Hiển thị danh sách hãng sản xuất (Apple, Samsung...) dạng bảng
    @Override
    public void displayListCategory(){
        if(new CategoryDAOImpl().getCategories().isEmpty()){
            System.out.println("Danh sách hãng sản xuất - danh mục trống!");
            return;
        }
        System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
        System.out.printf("| %s |\n", CenterFormat.center("DANH SÁCH DANH MỤC", 105));
        System.out.println("|━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━|");
        System.out.printf("| %s | %s | %s |\n", center("Mã danh mục", 12), center("Danh mục", 30), center("Mô tả", 57));
        System.out.println("|━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━|");
        for(Category cate : new CategoryDAOImpl().getAvailableCategories()){
            cate.displayInforCate();
        }
        System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
    }

    //Thêm/Sửa danh mục (Kiểm tra trùng tên, ID tồn tại)
    @Override
    public void addNewCategory(){
        Category newCate = new Category();

        while (true){
            String cateId = "C" + String.format("%03d", new CategoryDAOImpl().getCategories().size() + 1);
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

        boolean addStatus = new CategoryDAOImpl().insertNewCategory(newCate);
        if(addStatus){
            System.out.println("Thêm mới danh mục thành công!");
        }else {
            System.out.println("Thêm mới danh mục thất bại!");
        }
    }

    // Cập nhật danh mục
    @Override
    public void updateCategory(){
        System.out.print("Nhập mã danh mục cần cập nhật: ");
        String udId = sc.nextLine();
        Category udTarget = new CategoryDAOImpl().getCategoryById(udId);
        if(udTarget == null){
            System.out.println("Không tìm thấy danh mục cần cập nhật.");
        }else{
            boolean updateStatus = false;
            System.out.println("Sản phẩm cần cập nhật: ");
            System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
            udTarget.displayInforCate();
            System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
            System.out.print("Cập nhật:\n" +
                    "1. Tên danh mục\n" +
                    "2. Mô tả\n" +
                    "Lựa chọn: ");
            String input = sc.nextLine();
            if (input.isEmpty()) {
                System.out.println("Bạn chưa nhập lựa chọn!");
                return;
            }
            int udChoice = Integer.parseInt(input);
            switch (udChoice){
                case 1:
                    while (true){
                        System.out.print("Mời cập nhật tên danh mục: ");
                        String udCateName = sc.nextLine();
                        if(cateExisted(udCateName)){
                            System.out.println("Tên danh mục đã tồn tại, vui lòng nhập lại");
                        }else{
                            updateStatus = new CategoryDAOImpl().updateCateInfo(udTarget.getCate_id(), udCateName, udTarget.getDescription());
                            break;
                        }
                    }
                    break;
                case 2:
                    while (true){
                        System.out.print("Cập nhật mô tả danh mục: ");
                        String udCateDescript = sc.nextLine();
                        if (udCateDescript.isEmpty()){
                            System.out.println("Vui lòng không để trống nội dung mô tả: ");
                        }else{
                            updateStatus = new CategoryDAOImpl().updateCateInfo(udTarget.getCate_id(), udTarget.getCate_name(), udCateDescript);
                            break;
                        }
                    }
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
                    break;
            }
            if (updateStatus){
                System.out.println("Cập nhật danh mục thành công!!");
            }
        }

    }


    //Xóa danh mục (Xóa mềm)
    @Override
    public void deleteCategory(){
        if(new CategoryDAOImpl().getCategories().isEmpty()){
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            System.out.printf("| %s |\n",CenterFormat.center("- DANH SÁCH DANH MỤC TRỐNG -", 105));
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
            return;
        }
        System.out.print("Nhập mã danh mục muốn xóa: ");
        String targetDeleteId = sc.nextLine();

        Category targetDelete = new CategoryDAOImpl().getCategoryById(targetDeleteId);

        if(targetDelete == null){
            System.err.println("Không tìm thấy danh mục cần xóa");
            return;
        }

        System.out.print("Xác nhận xóa danh mục này (y/n): ");
        boolean confirmDelete = sc.nextLine().equalsIgnoreCase("y");
        if (confirmDelete){
            boolean deleteStatus = new CategoryDAOImpl().deleteCategory(targetDeleteId);
            if(deleteStatus){
                System.out.println("Xóa danh mục thành công");
            }else{
                System.err.println("Xóa danh mục thất bại");
            }
        }else{
            System.err.println("Từ chối xóa danh mục.");
        }


    }


        public static void main(String[] args) {
            new CategoryServiceImpl().updateCategory();
        }

    // Kiểm tra danh mục tồn tại hay chưa
    public boolean cateExisted(String cateName){
        for (Category cate : new CategoryDAOImpl().getCategories()){
            if(cate.getStatus() && cate.getCate_name().equals(cateName)){
                return true;
            }
        }
        return false;
    }



}
