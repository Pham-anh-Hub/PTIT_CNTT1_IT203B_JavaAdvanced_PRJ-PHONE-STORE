package dao.Interface;

import model.Cart;

import java.util.List;

public interface ICartDAO {
    List<Cart> getProductsCartByUserId(String userId);
    boolean addToCart(String userId, String productId, int quantity);
    boolean updateQuantity(String userId, String productId, int quantity);
    boolean removeFromCart(String userId, String productId);
    boolean clearCart(String userId);
}
