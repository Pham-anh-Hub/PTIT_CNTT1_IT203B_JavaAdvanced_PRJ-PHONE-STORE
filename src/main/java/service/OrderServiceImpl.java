package service;

import dao.impl.CartDAOImpl;
import dao.impl.OrderDetailDAOImpl;
import dao.impl.OrderImpl;
import dao.impl.ProductDAOImpl;
import model.Cart;
import model.Order;
import model.OrderDetail;
import model.Product;
import service.serviceInterface.IOrderService;
import utils.AuthSessionManagement;
import utils.CenterFormat;
import utils.MyDatabase;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OrderServiceImpl implements IOrderService {
    public static Scanner sc = new Scanner(System.in);

    // Lấy id người dùng hiện tại
    private String getCurrentUserId() {
        return AuthSessionManagement.getInstance().getCurrentUser().getId();
    }

    @Override
    public void displayAllOrders(List<Order> orders) {
        System.out.println("┏" + "━".repeat(97) + "┓");
        System.out.printf("| %s | %s | %s | %s | %s | %s |\n",
                CenterFormat.center("Mã đơn", 8),
                CenterFormat.center("Mã KH", 8),
                CenterFormat.center("Tổng tiền", 18),
                CenterFormat.center("Số sản phẩm"   , 12),       //thêm cột này
                CenterFormat.center("Trạng thái", 12),
                CenterFormat.center("Thời gian tạo", 22));
        System.out.println("|" + "━".repeat(97) + "|");
        if (orders.isEmpty()){
            System.out.println("|                                                                                                 |");
            System.out.println("|                                     Danh sánh đơn hàng trống                                    |");
            System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
            return;
        }

        for (Order o : orders) {
            // Đếm tổng số lượng sản phẩm trong đơn
            int totalQuantity = new OrderDetailDAOImpl().getOrderDetailsByOrderId(o.getOrder_id()).stream()
                    .mapToInt(OrderDetail::getQuantity)
                    .sum();

            System.out.printf("| %s | %s | %s | %s | %s | %s |\n",
                    CenterFormat.center(o.getOrder_id(), 8),
                    CenterFormat.center(o.getCustomer_id(), 8),
                    CenterFormat.center(String.format("%,.0f VND", o.getTotal_amount()), 18),
                    CenterFormat.center(String.valueOf(totalQuantity), 12),
                    CenterFormat.center(o.getStatus(), 12),
                    CenterFormat.center(o.getOrder_date() != null
                            ? o.getOrder_date().toString() : "_", 22));
        }
        System.out.println("┗" + "━".repeat(97) + "┛");
    }
    @Override
    public void displayOwnOrders() {
        List<Order> customerOrders = new OrderImpl().getOrdersByUserId(getCurrentUserId());

        if(customerOrders.isEmpty()){
            System.out.println("|                                                                                                 |");
            System.out.println("|                                     Danh sánh đơn hàng trống                                    |");
            System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
            return;
        }

        System.out.println("=============== DANH SÁCH ĐƠN HÀNG ===============");
        displayAllOrders(customerOrders);
    }

    public void displayDetailOrders(){
        displayOwnOrders();
        String orderId = "";
        while (true){
            System.out.print("Nhập mã đơn hàng xem chi tiết: ");
            orderId = sc.nextLine();
            if(orderId.isEmpty()){
                System.out.println("Vui lòng không để trống!");
                System.out.println("Hủy xem chi tiết? (y/n)");
                if(sc.nextLine().equalsIgnoreCase("y")){
                    return;
                }
            }else{
                Order target = new OrderImpl().getOrderById(orderId);
                if(target == null){
                    System.out.println("Đơn hàng không tồn tại, vui lòng kiểm tra lại mã");
                }else{
                    target.printOrder();
                }
            }
        }
    }

    @Override
    public void displayOrderDetails() {
        // Admin xem tất cả đơn hàng, Customer chỉ xem của mình
        if (AuthSessionManagement.getInstance().isAdmin()) {
            displayAllOrders(new OrderImpl().getAllOrders());
        } else {
            displayOwnOrders();
        }

        System.out.print("Nhập mã đơn hàng muốn xem chi tiết: ");
        String orderId = sc.nextLine().trim();

        Order order = new OrderImpl().getOrderById(orderId);
        if (order == null) {
            System.out.println("Đơn hàng không tồn tại.");
            return;
        }

        // Lấy chi tiết đơn từ DB
        List<OrderDetail> details = new OrderDetailDAOImpl().getOrderDetailsByOrderId(orderId);

        System.out.println("┏" + "━".repeat(79) + "┓");
        System.out.printf("| %-73s |\n", " Mã đơn: " + orderId + "  |  Trạng thái: " + order.getStatus() + "  |  Tổng: " + String.format("%,.0f VND", order.getTotal_amount()));
        System.out.println("|" + "━".repeat(79) + "|");
        System.out.printf("| %-16s | %-25s | %8s | %15s |\n", "Mã SP", "Tên sản phẩm", "SL", "Thành tiền");
        System.out.println("|" + "━".repeat(79) + "|");

        for (OrderDetail d : details) {
            Product p = new ProductDAOImpl().getProductById(d.getProduct_id());
            String productName = p != null ? p.getProduct_name() : "_";
            System.out.printf("| %-16s | %-25s | %8d | %15s |\n",
                    d.getProduct_id(),
                    productName,
                    d.getQuantity(),
                    String.format("%,.0f VND", new ProductDAOImpl().getProductById(d.getProduct_id()).getPrice()));
        }

        System.out.println("┗" + "━".repeat(75) + "┛");
    }

    @Override
    public void updateOrderStatus() {
        displayAllOrders(new OrderImpl().getAllOrders());

        System.out.print("Nhập mã đơn hàng cần cập nhật: ");
        String orderId = sc.nextLine().trim();

        Order order = new OrderImpl().getOrderById(orderId);
        if (order == null) {
            System.out.println("Đơn hàng không tồn tại.");
            return;
        }

        // Không cho cập nhật đơn đã DELIVERED hoặc CANCELLED
        if (order.getStatus().equals("DELIVERED") || order.getStatus().equals("CANCELLED")) {
            System.out.println("Đơn hàng đã hoàn thành rồi.");
            return;
        }

        System.out.println("Trạng thái hiện tại: " + order.getStatus());
        System.out.println("1. SHIPPING  (Đang giao)");
        System.out.println("2. DELIVERED (Đã giao)");
        System.out.println("3. CANCELLED (Hủy đơn)");
        System.out.print("Chọn trạng thái mới: ");
        int sts = Integer.parseInt(sc.nextLine().trim());

        String newStatus = order.getStatus();
        switch (sts) {
            case 1:
                newStatus = "SHIPPING";
                break;
            case 2:
                newStatus = "DELIVERED";
                break;
            case 3:
                newStatus = "CANCELLED";
                break;
            default:
                break;
        };

        if (newStatus == null) {
            System.out.println("Lựa chọn không hợp lệ.");
            return;
        }

        if (new OrderImpl().updateOrderStatus(orderId, newStatus)) {
            System.out.println("Cập nhật đơn " + orderId + " : " + newStatus);
        } else {
            System.out.println("Cập nhật thất bại.");
        }
    }

    //  Xem danh sách và đặt hàng ngay
    public void orderDirectly() {
        String userId = getCurrentUserId();

        ProductDAOImpl productDAO = new ProductDAOImpl();
        OrderImpl orderDAO = new OrderImpl();

        new ProductServiceImpl().displayProductList(productDAO.getProducts(), "order by brand asc, price desc");

        System.out.print("Nhập mã sản phẩm muốn mua (0 để thoát): ");
        String productId = sc.nextLine().trim();

        if(productId.equalsIgnoreCase("0")){

        }

        Product p = productDAO.getProductById(productId);
        if (p == null) {
            System.out.println("Sản phẩm không tồn tại.");
            return;
        }
        if (p.getStock() <= 0) {
            System.out.println("Sản phẩm này đã hết hàng.");
            return;
        }

        int quantity;
        while (true) {
            System.out.print("Số lượng muốn mua: ");
            try {
                quantity = Integer.parseInt(sc.nextLine().trim());
                if (quantity <= 0) {
                    System.out.println("Số lượng phải lớn hơn 0.");
                } else if (quantity > p.getStock()) {
                    System.out.println("Số lượng vượt quá tồn kho.");
                } else break;
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập số hợp lệ.");
            }
        }

        BigDecimal total = p.getPrice().multiply(BigDecimal.valueOf(quantity));

        System.out.printf("Xác nhận mua: %s x%d = %,.0f VND ? (Y/N): ",
                p.getProduct_name(), quantity, total);

        if (!sc.nextLine().trim().equalsIgnoreCase("Y")) {
            System.out.println("Đã hủy.");
            return;
        }

        Connection conn = null;

        try {
            conn = MyDatabase.getInstance().getConnection();
            conn.setAutoCommit(false);

            // tạo order
            String orderId = "OR" + String.format("%04d", (int)(Math.random() * 10000));

            Order newOrder = new Order();
            newOrder.setOrder_id(orderId);
            newOrder.setCustomer_id(userId);
            newOrder.setTotal_amount(total);
            newOrder.setStatus("PENDING");

            if (!orderDAO.insertOrder( newOrder, conn)) {
                throw new RuntimeException("Insert Order failed");
            }

            // taoj order detail
            String odId = "OD" + String.format("%04d", (int)(Math.random() * 10000));

            OrderDetail detail = new OrderDetail();
            detail.setOd_id(odId);
            detail.setOrder_id(orderId);
            detail.setProduct_id(productId);
            detail.setQuantity(quantity);
            detail.setPrice(total);

            if (!orderDAO.insertOrderDetail(odId, detail, conn)) {
                throw new RuntimeException("Thêm sản phẩm thất bại");
            }

            // update stock
            p.setStock(p.getStock() - quantity);

            if (!productDAO.updateProductInfo(p, conn)) {
                throw new RuntimeException("Cập nhật tồn kho thất bại");
            }

            // commit
            conn.commit();
            System.out.println("Đặt hàng thành công!");

        } catch (Exception e) {
            try {
                if (conn != null) conn.rollback(); // rollback
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            System.out.println("Đặt hàng thất bại! Đã rollback.");
            e.printStackTrace();

        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    // Xem giỏ hàng và đặt hàng (dùng lại takeOrder() có sẵn)
    public void orderWithCart() {
        String userId = getCurrentUserId();
        List<Cart> cartItems = new CartDAOImpl().getProductsCartByUserId(userId);



        if (cartItems.isEmpty()) {
            System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
            System.out.println("|                                                                                                 |");
            System.out.println("|                                     Danh sánh đơn hàng trống                                    |");
            System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
            return;
        }

        new CartServiceImpl().displayCart();

        List<Cart> selectedItems = new ArrayList<>();

        while (true) {
            System.out.print("Nhập mã sản phẩm muốn thanh toán (nhập 'y' để xác nhận): ");
            String input = sc.nextLine().trim();

            if (input.equalsIgnoreCase("y")) {
                if (selectedItems.isEmpty()) {
                    System.out.println("Bạn chưa chọn sản phẩm nào!\n\t\t Xác nhận thoát (y/n): ");
                    if (sc.nextLine().trim().equalsIgnoreCase("y")) break;
                    continue;
                }
                break;
            }

            // Kiểm tra mã SP có trong giỏ không
            Cart found = cartItems.stream()
                    .filter(c -> c.getProduct_id().equalsIgnoreCase(input))
                    .findFirst().orElse(null);

            if (found == null) {
                System.out.println("Mã sản phẩm không tồn tại trong giỏ hàng.");
                continue;
            }

            // Kiểm tra đã chọn chưa
            boolean alreadySelected = selectedItems.stream()
                    .anyMatch(c -> c.getProduct_id().equalsIgnoreCase(input));
            if (alreadySelected) {
                System.out.println("Sản phẩm này đã được chọn.");
                continue;
            }

            // Nhập số lượng
            Product p = new ProductDAOImpl().getProductById(found.getProduct_id());
            int selectQuantity = 0;
            int inputQuantity = 0;
            int finalQuantity = 0;
            while (true) {
                System.out.print("Số lượng muốn mua (Giỏ hàng đang có " + found.getQuantity() + "): " );
                try {
                     inputQuantity = Integer.parseInt(sc.nextLine().trim());
                     finalQuantity = found.getQuantity() + inputQuantity;
                    if (inputQuantity <= 0) {
                        System.out.println("Số lượng phải lớn hơn 0.");
                    } else if (finalQuantity > p.getStock()) {
                        System.out.println("Tổng số lượng vượt quá tồn kho (" + p.getStock() + ").");
                    } else {
                        selectQuantity = finalQuantity;
                        break;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Vui lòng nhập số hợp lệ.");
                }
            }


            Cart selectedItem = new Cart();
            selectedItem.setProduct_id(found.getProduct_id());
            selectedItem.setQuantity(selectQuantity);

            selectedItems.add(selectedItem);

            System.out.println("Đã chọn: " + selectQuantity + " x "+ p.getProduct_name() + " = " + String.format("%,.0f VND", p.getPrice().multiply(BigDecimal.valueOf(selectQuantity))));
        }

        if (selectedItems.isEmpty()) return;

        // Hiển thị tóm tắt
        System.out.println("\n=============== Sản phẩm đã chọn ================");
        BigDecimal previewTotal = BigDecimal.ZERO;
        for (Cart item : selectedItems) {
            Product product = new ProductDAOImpl().getProductById(item.getProduct_id());
            BigDecimal subtotal = product.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
            previewTotal = previewTotal.add(subtotal);
            System.out.printf("\t%-25s x %d = %,.0f VND\n",
                    product.getProduct_name(), item.getQuantity(), subtotal);
        }
        System.out.printf("\t\tTổng: %,.0f VND\n", previewTotal);

        takeOrder(selectedItems);
    }

    public void takeOrder(List<Cart> cartItems) {
        String userId = getCurrentUserId();

        // Kiểm tra tồn kho + tính tiền
        BigDecimal total = BigDecimal.ZERO;
        for (Cart item : cartItems) {
            Product p = new ProductDAOImpl().getProductById(item.getProduct_id());

            total = total.add(p.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        }

        // Xác nhận
        System.out.print("Xác nhận đặt hàng? (y/n): ");
        if (!sc.nextLine().trim().equalsIgnoreCase("y")) {
            System.out.println("Đã hủy đặt hàng.");
            return;
        }

        Connection conn = null;
        try{
            conn = MyDatabase.getInstance().getConnection();
            conn.setAutoCommit(false);

            // Tạo Order
            String orderId = "OR" + String.format("%04d", (int)(Math.random() * 10000));
            Order newOrder = new Order();
            newOrder.setOrder_id(orderId);
            newOrder.setCustomer_id(userId);
            newOrder.setTotal_amount(total);
            newOrder.setStatus("PENDING");

            if (!new OrderImpl().insertOrder(newOrder, conn)) {
                System.out.println("Đặt hàng thất bại!");
                return;
            }

            // Tạo OrderDetail + giảm tồn kho
            for (Cart item : cartItems) {
                Product p = new ProductDAOImpl().getProductById(item.getProduct_id());

                String odId = "OD" + String.format("%04d", (int)(Math.random() * 10000));
                OrderDetail detail = new OrderDetail();
                detail.setOd_id(odId);
                detail.setOrder_id(orderId);
                detail.setProduct_id(item.getProduct_id());
                detail.setQuantity(item.getQuantity());
                detail.setPrice(p.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));

                new OrderImpl().insertOrderDetail(orderId, detail, conn);

                // Giảm tồn kho
                p.setStock(p.getStock() - item.getQuantity());
                new ProductDAOImpl().updateProductInfo(p, conn);
            }


            for (Cart item : cartItems) {
                new CartDAOImpl().removeFromCart(userId, item.getProduct_id());
            }

            conn.commit();// commit và thông báo
            System.out.println("Đặt hàng thành công!");
            System.out.println("    Mã đơn hàng : " + orderId);
            System.out.println("    Tổng tiền   : " + String.format("%,.0f VND", total));
            System.out.println("    Trạng thái  : PENDING (Chờ xử lý)");



        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            System.out.println("Đặt hàng thất bại!");
            System.err.println(e.toString());
        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException e) {
                System.err.println(e.toString());
            }
        }


    }

    public void cancelOrder(){
        displayAllOrders(new OrderImpl().getAllOrders());

        System.out.print("Nhập mã đơn hàng muốn hủy: ");
        String orderId = sc.nextLine().trim();

        Order order = new OrderImpl().getOrderById(orderId);
        if (order == null) {
            System.out.println("Đơn hàng không tồn tại.");
            return;
        }

        // Không cho cập nhật đơn đã DELIVERED hoặc CANCELLED
        if (order.getStatus().equals("DELIVERED") || order.getStatus().equals("CANCELLED")) {
            System.out.println("Đơn hàng đã hoàn thành rồi.");
        }else if (order.getStatus().equals("SHIPPING")){
            System.out.println("Đơn hàng đã được chuyển đi, không thể hủy đơn hàng");
        }else{
            order.printOrder();
            System.out.print("Xác nhận hủy đơn hàng này(y/n): ");
            String choice = sc.nextLine();
            if(choice.equalsIgnoreCase("y")){
                if (new OrderImpl().updateOrderStatus(order.getOrder_id(), "CANCELLED")){
                    System.out.println("Đã hủy đơn hàng");
                }else{
                    System.out.println("Hủy đơn hàng thất bại");
                }
            }else {
                System.out.println("Hủy thao tác hủy đơn");
            }
        }
    }

}
