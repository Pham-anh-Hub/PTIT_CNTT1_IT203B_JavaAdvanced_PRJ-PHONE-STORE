package service.serviceInterface;

public interface ICategoryService {
        // Hiển thị danh sách
        void displayListCategory();

        // Thêm mới danh mục
        void addNewCategory();

        // Cập nhật danh mục
        void updateCategory();

        // Xóa danh mục (soft delete)
        void deleteCategory();

        // Kiểm tra tên danh mục tồn tại
        boolean cateExisted(String cateName);
}
