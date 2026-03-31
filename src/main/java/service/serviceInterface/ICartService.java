package service.serviceInterface;

public interface ICartService {
    // xem giỏ hàng
    void displayCart();

    // thêm sản phẩm vào giỏ
    void addToCart();

    // cập nhật số lượng
    void updateCartItem();

    // xóa 1 sản phẩm
    void removeFromCart();

    // đặt hàng → tạo Order
    void checkout();
}
