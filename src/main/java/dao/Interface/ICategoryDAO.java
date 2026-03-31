package dao.Interface;

import model.Category;

import java.util.List;

public interface ICategoryDAO {
    public List<Category> getCategories();

    public List<Category> getAvailableCategories();

    public Category getCategoryById (String cateId);

    //    Thêm mới danh mục .
    public boolean insertNewCategory(Category newCategory);

    //    Hiển thị danh mục .
    //    Xử lý trong service

    //    Xóa danh mục .
    public boolean deleteCategory(String delCateId);

    //    Cập nhật danh mục .
    public boolean updateCateInfo(String targetUpdateId, String cateName, String descript);
}
