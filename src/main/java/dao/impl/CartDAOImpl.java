package dao.impl;

import dao.Interface.ICartDAO;
import model.Cart;
import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartDAOImpl implements ICartDAO {

    public Cart setCart(ResultSet rs){
        Cart cart = new Cart();
        try {
            cart.setCart_id(rs.getInt("cart_id"));
            cart.setUser_id(rs.getString("user_id"));
            cart.setProduct_id(rs.getString("product_id"));
            cart.setQuantity(rs.getInt("quantity"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return cart;
    }

    // Lấy danh sách sản phẩm trong giỏ của 1 user theo id
    @Override
    public List<Cart> getProductsCartByUserId(String userId) {
        List<Cart> cartItems = new ArrayList<>();
        try (Connection conn = MyDatabase.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement("select * from cart where user_id = ?")) {

            ps.setString(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                cartItems.add(setCart(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cartItems;
    }

    // Thêm sản phẩm vào giỏ hàng
    // Nếu sản phẩm đã có trong giỏ → tăng số lượng
    // Nếu chưa có → thêm mới
    @Override
    public boolean addToCart(String userId, String productId, int quantity) {
        try (Connection conn = MyDatabase.getInstance().getConnection();
             CallableStatement cs = conn.prepareCall("call pr_add_to_cart(?, ?, ?)")) {

            cs.setString(1, userId);
            cs.setString(2, productId);
            cs.setInt(3, quantity);

            return cs.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean updateQuantity(String userId, String productId, int quantity) {

        try (Connection conn = MyDatabase.getInstance().getConnection();
             CallableStatement cs = conn.prepareCall("call pr_update_cart_quantity(?, ?, ?)")) {

            cs.setString(1, userId);
            cs.setString(2, productId);
            cs.setInt(3, quantity);

            return cs.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean removeFromCart(String userId, String productId) {

        try (Connection conn = MyDatabase.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement("delete from cart where user_id = ? and product_id = ?")) {

            ps.setString(1, userId);
            ps.setString(2, productId);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean clearCart(String userId) {

        try (Connection conn = MyDatabase.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement("delete from cart where user_id = ?")) {

            ps.setString(1, userId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
