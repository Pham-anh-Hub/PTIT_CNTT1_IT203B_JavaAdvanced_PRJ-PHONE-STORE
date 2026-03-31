package dao.impl;

import dao.Interface.ICategoryDAO;
import model.Category;
import utils.MyDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAOImpl implements ICategoryDAO {

//    Thêm mới danh mục .
//    Hiển thị danh mục .
//    Xóa danh mục .
//    Cập nhật danh mục .

    //    Hiển thị danh mục .
    // Lay danh sach danh muc tu DB
    @Override
    public List<Category> getCategories(){
        List<Category> categories = new ArrayList<>();
        try(Connection conn = MyDatabase.getInstance().getConnection();
            PreparedStatement pstmt = conn.prepareStatement("select * from categories;")){

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()){
                Category newCate = new Category(
                        rs.getString("cate_id"),
                        rs.getString("cate_name"),
                        rs.getString("description")
                );
                categories.add(newCate);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return categories;
    }

    @Override
    public List<Category> getAvailableCategories() {
        List<Category> categories = new ArrayList<>();
        try(Connection conn = MyDatabase.getInstance().getConnection();
            PreparedStatement pstmt = conn.prepareStatement("select * from categories where status = true")){

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()){
                Category newCate = new Category(
                        rs.getString("cate_id"),
                        rs.getString("cate_name"),
                        rs.getString("description")
                );
                categories.add(newCate);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return categories;
    }

    // Lấy ra danh mục theo id
    @Override
    public Category getCategoryById (String cateId){
        Category cateTarget = null;
        try(Connection conn = MyDatabase.getInstance().getConnection();
        PreparedStatement pstmt = conn.prepareStatement("select * from categories where cate_id = ?")){

            pstmt.setString(1, cateId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()){
                cateTarget = new Category();
                cateTarget.setCate_id(rs.getString("cate_id"));
                cateTarget.setCate_name(rs.getString("cate_name"));
                cateTarget.setDescription(rs.getString("description"));
                cateTarget.setStatus(rs.getBoolean("status"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cateTarget;
    }

    // Thêm mới danh mục .
    @Override
    public boolean insertNewCategory(Category newCategory){
        try(Connection conn = MyDatabase.getInstance().getConnection();
        PreparedStatement pstmt = conn.prepareStatement("insert into categories VALUES (?, ?, ?, true)")){

            pstmt.setString(1, newCategory.getCate_id());
            pstmt.setString(2, newCategory.getCate_name());
            pstmt.setString(3, newCategory.getDescription());

            int insertStatus = pstmt.executeUpdate();
            if(insertStatus > 0 ){
                return true;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    //    Xóa danh mục .
    @Override
    public boolean deleteCategory(String delCateId){
            // Kiểm tra danh mục còn hay không / còn hoạt dộng hay không trong service
            try(Connection conn = MyDatabase.getInstance().getConnection();
            PreparedStatement pstmt = conn.prepareStatement("update categories set status = false where cate_id = ? && status = true")){
                pstmt.setString(1, delCateId);
                int statusUpdate = pstmt.executeUpdate();
                if(statusUpdate > 0){
                    return true;
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        return false;
    }

    //    Cập nhật danh mục .
    @Override
    public boolean updateCateInfo(String targetUpdateId, String cateName, String descript){
        try (Connection conn = MyDatabase.getInstance().getConnection();
        PreparedStatement pstmt = conn.prepareStatement("update categories set cate_name = ?, description = ? where cate_id = ?")){

            pstmt.setString(1, cateName);
            pstmt.setString(2, descript);
            pstmt.setString(3, targetUpdateId);

            int statusUpdate = pstmt.executeUpdate();
            if(statusUpdate > 0){
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }



}
