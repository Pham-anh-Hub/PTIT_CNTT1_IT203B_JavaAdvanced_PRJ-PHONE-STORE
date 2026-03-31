package service.serviceInterface;

import model.Product;

import java.util.List;

public interface IProductService {
    //Hiển thị danh sách sản phẩm
    public void displayProductList(List<Product> productList, String sortOption);

    // Thêm mới sản phẩm
    public void addNewProduct();

    //Sửa thông tin sản phẩm
    public void updateProduct();

    //Xóa thông tin sản phẩm
    public void deleteProduct();

    //Tìm kiếm thông tin sản phẩm theo tên (tìm kiếm tương đối)
    public void searchProductByName();

    // Sắp xếp sản phẩm theo giá tiền tăng/giảm dần
    public void sortProductsByPrice();
}
