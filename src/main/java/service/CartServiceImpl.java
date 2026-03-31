package service;

import dao.impl.CartDAOImpl;
import dao.impl.OrderImpl;
import dao.impl.ProductDAOImpl;
import model.*;
import service.serviceInterface.ICartService;
import utils.AuthSessionManagement;
import utils.CenterFormat;
import utils.MyDatabase;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class CartServiceImpl implements ICartService {
    public static Scanner sc = new Scanner(System.in);

    @Override
    public void displayCart() {
        List<Cart> cartItems = new CartDAOImpl().getProductsCartByUserId(AuthSessionManagement.getInstance().getCurrentUser().getId());

        System.out.println("┏" + "━".repeat(91) + "┓");
        System.out.printf("| %s |\n", CenterFormat.center("GIỎ HÀNG CỦA BẠN", 89));
        System.out.println("|" + "━".repeat(91) + "|");

        if (cartItems.isEmpty()) {
            System.out.printf("| %-78s |\n", " Giỏ hàng trống!");
            System.out.println("┗" + "━".repeat(91) + "┛");
            return;
        }

        System.out.printf("| %-12s | %-25s | %10s | %15s | %15s |\n",
                "Mã SP", "Tên sản phẩm", "Số lượng", "Đơn giá", "Thành tiền");
        System.out.println("|" + "━".repeat(91) + "|");

        BigDecimal total = BigDecimal.ZERO; // khoi tao tong
        for (Cart item : cartItems) {
            Product p = new ProductDAOImpl().getProductById(item.getProduct_id());
            if (p != null) {
                BigDecimal subtotal = p.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
                total = total.add(subtotal);
                System.out.printf("| %-12s | %-25s | %10d | %15s | %15s |\n",
                        p.getProduct_id(),
                        p.getProduct_name(),
                        item.getQuantity(),
                        String.format("%,.0f VND", p.getPrice()),
                        String.format("%,.0f VND", subtotal));
            }
        }

        System.out.println("|" + "━".repeat(91) + "|");
        System.out.printf("| %-60s %28s |\n",
                "Thành tiền :",
                String.format("%,.0f VND", total));
    System.out.println("┗" + "━".repeat(91) + "┛");
    }

    @Override
    public void addToCart() {
        new ProductServiceImpl().displayProductList(new ProductDAOImpl().getProducts(), "order by brand asc, price desc");;

        System.out.print("Nhập mã sản phẩm muốn thêm vào giỏ hàng: ");
        String productId = sc.nextLine().trim();

        Product product = new ProductDAOImpl().getProductById(productId);
        if (product == null) {
            System.out.println("Sản phẩm không tồn tại hoặc không còn bán.");
            return;
        }

        if (product.getStock() <= 0) {
            System.out.println("Sản phẩm đã hết hàng.");
            return;
        }

        while (true) {
            System.out.print("Số lượng muốn mua : ");
            try {
                int quantity = Integer.parseInt(sc.nextLine().trim());
                if (quantity <= 0) {
                    System.out.println("Số lượng chưa hợp lệ.");
                } else if (quantity > product.getStock()) {
                    System.out.println("Số tồn kho không đủ.");
                } else {
                    if (new CartDAOImpl().addToCart(AuthSessionManagement.getInstance().getCurrentUser().getId(), productId, quantity)) {
                        System.out.println("Đã thêm " + quantity + "  " + product.getProduct_name()
                                 + " vào giỏ hàng!");
                    }
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập số hợp lệ.");
            }
        }
    }

    @Override
    public void updateCartItem() {
        displayCart();

        List<Cart> cartItems = new CartDAOImpl().getProductsCartByUserId(AuthSessionManagement.getInstance().getCurrentUser().getId());
        if (cartItems.isEmpty()) return;

        System.out.print("Nhập mã sản phẩm cần cập nhật số lượng: ");
        String productId = sc.nextLine().trim();

        Product product = new ProductDAOImpl().getProductById(productId);
        if (product == null) {
            System.out.println("Sản phầm không tồn tại, vui lòng kiểm tra lại mã sản phẩm");
            return;
        }

        while (true) {
            System.out.print("Số lượng cập nhật (tồn kho: " + product.getStock() + "): ");
            try {
                int quantity = Integer.parseInt(sc.nextLine().trim());
                if (quantity <= 0) {
                    System.out.println("Số lượng chưa hợp lệ, vui lòng kiểm tra lại.");
                } else if (quantity > product.getStock()) {
                    System.out.println("Số lượng tồn kho không đủ.");
                } else {
                    if (new CartDAOImpl().updateQuantity(AuthSessionManagement.getInstance().getCurrentUser().getId(), productId, quantity)) {
                        System.out.println("Cập nhật số lượng thành công.");
                    }
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập số hợp lệ.");
            }
        }
    }

    @Override
    public void removeFromCart() {
        displayCart();

        List<Cart> cartItems = new CartDAOImpl().getProductsCartByUserId(AuthSessionManagement.getInstance().getCurrentUser().getId());
        if (cartItems.isEmpty()) return;

        System.out.print("Nhập mã sản phẩm cần xóa: ");
        String productId = sc.nextLine().trim();

        System.out.print("Xác nhận xóa? (y/n): ");
        if (sc.nextLine().trim().equalsIgnoreCase("y")) {
            if (new CartDAOImpl().removeFromCart(AuthSessionManagement.getInstance().getCurrentUser().getId(), productId)) {
                System.out.println("Đã xóa sản phẩm khỏi giỏ hàng.");
            } else {
                System.out.println(" Xóa thất bại, mã sản phẩm không có trong giỏ.");
            }
        } else {
            System.out.println("Đã hủy thao tác.");
        }
    }

    @Override
    public void checkout() {
        List<Cart> cartItems = new CartDAOImpl().getProductsCartByUserId(AuthSessionManagement.getInstance().getCurrentUser().getId());

        if (cartItems.isEmpty()) {
            System.out.println("Giỏ hàng trống, không thể đặt hàng.");
            return;
        }

        // Kiểm tra tồn kho từng sản phẩm
        BigDecimal total = BigDecimal.ZERO;
        for (Cart item : cartItems) {
            Product p = new ProductDAOImpl().getProductById(item.getProduct_id());
            if (p == null) {
                System.out.println("Sản phẩm " + item.getProduct_id() + " không còn tồn tại.");
                return;
            }
            if (p.getStock() < item.getQuantity()) {
                System.out.println("Sản phẩm \"" + p.getProduct_name()
                        + "\" chỉ còn " + p.getStock() + ". Vui lòng cập nhật giỏ hàng.");
                return;
            }
            total = total.add(p.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        }

        // xác nhận
        displayCart();
        System.out.print("Xác nhận đặt hàng? (Y/N): ");
        if (!sc.nextLine().trim().equalsIgnoreCase("Y")) {
            System.out.println("Đã hủy đặt hàng.");
            return;
        }

        Connection conn = null;

        try{
            conn = MyDatabase.getInstance().getConnection();
            conn.setAutoCommit(false);
            // tạo Order
            String orderId = "OD" + String.format("%04d", (int)(Math.random() * 10000));
            Order newOrder = new Order();
            newOrder.setOrder_id(orderId);
            newOrder.setCustomer_id(AuthSessionManagement.getInstance().getCurrentUser().getId());
            newOrder.setTotal_amount(total);
            newOrder.setStatus("PENDING");

            if (!new OrderImpl().insertOrder(newOrder, conn)) {
                System.out.println("Đặt hàng thất bại!");
                return;
            }

            // Bước 4: Tạo OrderDetail + giảm stock từng sản phẩm
            for (Cart item : cartItems) {
                Product p = new ProductDAOImpl().getProductById(item.getProduct_id());

                OrderDetail detail = new OrderDetail();
                detail.setOd_id(orderId);
                detail.setProduct_id(item.getProduct_id());
                detail.setQuantity(item.getQuantity());
                detail.setPrice(p.getPrice());
                new OrderImpl().insertOrderDetail(newOrder.getOrder_id(),detail, conn);

                // Giảm stock
                p.setStock(p.getStock() - item.getQuantity());
                new ProductDAOImpl().updateProductInfo(p, conn);
            }

            // Bước 5: Xóa giỏ hàng sau khi đặt thành công
            new CartDAOImpl().clearCart(AuthSessionManagement.getInstance().getCurrentUser().getId());

            System.out.println("Đặt hàng thành công!");
            System.out.println("    Mã đơn hàng : " + orderId);
            System.out.println("    Tổng tiền   : " + String.format("%,.0f VND", total));
            System.out.println("    Trạng thái  : PENDING (Chờ xử lý)");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    }

