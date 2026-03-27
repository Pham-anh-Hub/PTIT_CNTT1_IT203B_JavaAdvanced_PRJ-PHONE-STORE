package dao.daoInterface;

import model.Product;

import java.util.List;

public interface ProductDAO {
    // Quản lý Sản phẩm

//    Lấy danh sách sản phẩm
    public List<Product> getProducts();


//    Lấy sản phẩm theo id
    public Product getProductById(String pId);

//    Thêm mới sản phẩm
    public boolean insertNewProduct(Product newProduct);

//    Sửa thông tin sản phẩm
    public boolean updateProductInfo(Product updateProduct);

//    Xóa thông tin sản phẩm
    public boolean deleteProductInfo(String productId);

//    Hiển thị danh sách sản phẩm


//    Tìm kiếm thông tin sản phẩm theo tên (tìm kiếm tương đối)
    public List<Product> searchProductWithName(String productName);
}
