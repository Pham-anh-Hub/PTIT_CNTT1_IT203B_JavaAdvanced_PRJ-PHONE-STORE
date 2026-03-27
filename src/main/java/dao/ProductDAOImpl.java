package dao;

import dao.daoInterface.ProductDAO;
import model.Product;
import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOImpl implements ProductDAO {
    @Override
    public List<Product> getProducts() {
       List<Product> products = new ArrayList<>();
       try(Connection conn = MyDatabase.getInstance().getConnection();
           PreparedStatement pstmt = conn.prepareStatement("select * from products where cate_id in (select cate_id from categories where status = true) && is_active = true")) {

           ResultSet rs = pstmt.executeQuery();
           while (rs.next()){
               Product product = new Product();
               product.setProduct_id(rs.getString("product_id"));
               product.setProduct_name(rs.getString("product_name"));
               product.setBrand(rs.getString("brand"));
               product.setColor(rs.getString("color"));
               product.setStorage(rs.getInt("storage"));
               product.setStock(rs.getInt("stock"));
               product.setPrice(rs.getBigDecimal("price"));
               product.setDescription(rs.getString("description"));
               product.setCreated_at(rs.getTimestamp("created_at").toLocalDateTime());
               product.setUpdated_at(null);
               product.setCategory_id(rs.getString("cate_id"));
               products.add(product);
           }
       } catch (SQLException e) {
           throw new RuntimeException(e);
       }
       return products;
    }


    public List<Product> getAllProducts() {
        List<Product> productsResult = new ArrayList<>();
        try(Connection conn = MyDatabase.getInstance().getConnection();
            PreparedStatement pstmt = conn.prepareStatement("select * from products")) {

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()){
                Product product = new Product();
                product.setProduct_id(rs.getString("product_id"));
                product.setProduct_name(rs.getString("product_name"));
                product.setBrand(rs.getString("brand"));
                product.setColor(rs.getString("color"));
                product.setStorage(rs.getInt("storage"));
                product.setStock(rs.getInt("stock"));
                product.setPrice(rs.getBigDecimal("price"));
                product.setDescription(rs.getString("description"));
                product.setCreated_at(rs.getTimestamp("created_at").toLocalDateTime());
                product.setUpdated_at(null);
                product.setCategory_id(rs.getString("cate_id"));
                productsResult.add(product);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return productsResult;
    }

    @Override
    public Product getProductById(String pId) {
        Product targetProduct = null;

        try(Connection conn = MyDatabase.getInstance().getConnection();
        PreparedStatement pstmt = conn.prepareStatement("select * from products where product_id = ? && cate_id in (select cate_id from categories where status = true)  && is_active = true")){

            pstmt.setString(1, pId);

            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                targetProduct = new Product();
                targetProduct.setProduct_id(rs.getString("product_id"));
                targetProduct.setProduct_name(rs.getString("product_name"));
                targetProduct.setBrand(rs.getString("brand"));
                targetProduct.setColor(rs.getString("color"));
                targetProduct.setStorage(rs.getInt("storage"));
                targetProduct.setStock(rs.getInt("stock"));
                targetProduct.setPrice(rs.getBigDecimal("price"));
                targetProduct.setDescription(rs.getString("description"));
                targetProduct.setCreated_at(rs.getTimestamp("created_at").toLocalDateTime());
                targetProduct.setUpdated_at(null);
                targetProduct.setCategory_id(rs.getString("cate_id"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return targetProduct;
    }

    @Override
    public boolean insertNewProduct(Product newProduct) {
        if (newProduct == null){
            return false;
        }

        try(Connection conn = MyDatabase.getInstance().getConnection();
        PreparedStatement pstmt = conn.prepareStatement("insert into products values (?, ?, ?, ?, ?, ?, ?, ?, ?, now(), NULL, true)")){

            // ID, TÊN, HÃNG, MÀU, dung lượng, giá, stock, mô tả, mã danh mục, ..
            pstmt.setString(1, newProduct.getProduct_id());
            pstmt.setString(2, newProduct.getProduct_name());
            pstmt.setString(3, newProduct.getBrand()); // có thể chính là danh mục
            pstmt.setString(4, newProduct.getColor());
            pstmt.setInt(5, newProduct.getStorage());;
            pstmt.setBigDecimal(6, newProduct.getPrice());
            pstmt.setInt(7, newProduct.getStock());
            pstmt.setString(8, newProduct.getDescription());
            pstmt.setString(9, newProduct.getCategory_id());

            int statusInsert = pstmt.executeUpdate();
            if (statusInsert > 0){
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public boolean updateProductInfo(Product updateProduct) {
        // chuyển vào service
        if(!(new CategoryDAOImpl().getCategoryById(updateProduct.category_id).getStatus())){
            return false;
        }
        //-------------------

        try(Connection conn = MyDatabase.getInstance().getConnection();
        PreparedStatement pstmt = conn.prepareStatement(
        "update products set product_name = ?, brand = ?, color = ?, storage = ?, price = ?, stock = ?, description = ?, cate_id = ?, updated_at = NOW() where product_id = ?")){

            pstmt.setString(1, updateProduct.getProduct_name()); // productName
            pstmt.setString(2, updateProduct.getBrand());// brand
            pstmt.setString(3, updateProduct.getColor()); // color
            pstmt.setInt(4, updateProduct.getStorage()); // storage
            pstmt.setBigDecimal(5, updateProduct.getPrice()); // price
            pstmt.setInt(6, updateProduct.getStock()); // stock
            pstmt.setString(7, updateProduct.getDescription()); // descipt
            pstmt.setString(8, updateProduct.getCategory_id()); // cate_id

            pstmt.setString(9, updateProduct.getProduct_id());

            int statusUpdate = pstmt.executeUpdate();
            return statusUpdate > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteProductInfo(String productId) {
        try(Connection conn = MyDatabase.getInstance().getConnection();
            PreparedStatement pstmt = conn.prepareStatement("update products set is_active = false where product_id = ?")){

            pstmt.setString(1, productId);

            int resultDelete = pstmt.executeUpdate();
            return resultDelete > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Product> searchProductWithName(String keyword) {
        List<Product> resultSearch = new ArrayList<>();
        try(Connection conn = MyDatabase.getInstance().getConnection();
        PreparedStatement pstmt = conn.prepareStatement("select * from products where product_name like ? ")){

            pstmt.setString(1, "%" + keyword + "%");

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()){
                Product product = new Product();
                product.setProduct_id(rs.getString("product_id"));
                product.setProduct_name(rs.getString("product_name"));
                product.setBrand(rs.getString("brand"));
                product.setColor(rs.getString("color"));
                product.setStorage(rs.getInt("storage"));
                product.setStock(rs.getInt("stock"));
                product.setPrice(rs.getBigDecimal("price"));
                product.setDescription(rs.getString("description"));
                product.setCreated_at(rs.getTimestamp("created_at") == null ? null : rs.getTimestamp("created_at").toLocalDateTime());
                product.setUpdated_at(rs.getTimestamp("updated_at") == null ? null : rs.getTimestamp("updated_at").toLocalDateTime());
                product.setCategory_id(rs.getString("cate_id"));
                product.setActive(rs.getBoolean("is_active"));
                resultSearch.add(product);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultSearch;
    }
}
