package service;


import dao.CategoryDAOImpl;
import dao.ProductDAOImpl;
import dao.daoInterface.ProductDAO;
import model.Category;
import model.Product;
import service.serviceInterface.IProductService;
import utils.CenterFormat;

import javax.swing.plaf.PanelUI;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class ProductServiceImpl implements IProductService {
    public static Scanner sc = new Scanner(System.in);

    public static void displayHeader(List<Product> products, String header){
        System.out.println("┏" + "━".repeat(158) + "┓");
        System.out.printf("| %s |\n", CenterFormat.center(header.toUpperCase(), 156));
        System.out.println("|" + "━".repeat(158) + "|");

        if (products.isEmpty()) {
            System.out.printf("| %s |\n", CenterFormat.center("DANH SÁCH SẢN PHẨM TRỐNG", 156));
            System.out.println("┗" + "━".repeat(158) + "┛");
            return; // thoát sớm, không in tiếp
        }
        System.out.printf("| %s | %s | %s | %s | %s | %s | %s | %s | %s | %s |\n",
                CenterFormat.center("Mã sản phẩm", 12),
                CenterFormat.center("Tên sản phẩm", 20),
                CenterFormat.center("Danh mục", 10),
                CenterFormat.center("Hãng", 10),
                CenterFormat.center("Màu", 8),
                CenterFormat.center("Dung lượng", 10),
                CenterFormat.center("Giá tiền", 14),
                CenterFormat.center("Tồn kho", 5),
                CenterFormat.center("Thời gian tạo", 19),
                CenterFormat.center("Thời gian sửa", 19)
        );
        System.out.println("|" + "━".repeat(158) + "|");
    }


    @Override
    public void displayProductList() {
        int limitRecord = 6;
        int currentPage = 1;
        int totalProducts = new ProductDAOImpl().getProducts().size();
        int pages = (int) Math.ceil((double) totalProducts / limitRecord);
        int pageChoice = 1;

        do {
            List<Product> products = new ProductDAOImpl().getProductsForPagination(limitRecord, currentPage);
            displayHeader(products, "danh sách sản phẩm");

            for (Product product : products) {
                product.displayInfoProduct();
            }

            System.out.print("┗" + "━".repeat(158) + "┛\n");

            StringBuilder pagination = new StringBuilder();

            for (int i = 1; i <= pages; i++) {
                if (currentPage == i){
                    pagination.append("\u001B[31m" + " [" + i + "] " + "\u001B[0m");
                }else{
                    pagination.append(" [" + i + "]");
                }
            }
            System.out.println(CenterFormat.center(String.valueOf(pagination), 160));

            System.out.print("\nXem thêm: ");
            try {
                pageChoice = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                pageChoice = 0;
            }

            // Validate trang hợp lệ
            if (pageChoice < 1 || pageChoice > pages) break;
            currentPage = pageChoice;

        }while(pageChoice >= 1 && pageChoice <= pages);

    }

    @Override
    public void addNewProduct() {
        System.out.println("============================================== THÊM MỚI SẢN PHẨM ==========================================");

        Product newProduct = new Product();

        // ===== Danh mục =====
        new CategoryServiceImpl().displayListCategory();
        while (true) {
            System.out.print("Chọn mã danh mục: ");
            String pCateId = sc.nextLine().trim();
            Category target = new CategoryDAOImpl().getCategoryById(pCateId);
            if (target == null) {
                System.out.println("Danh mục không tồn tại, vui lòng chọn lại.");
            } else {
                newProduct.setCategory_id(pCateId);
                break;
            }
        }

        // sinh ID
        while (true) {
            String newId = "PR" + String.format("%04d", (int)(Math.random() * 10000));

            Product existedProduct = new ProductDAOImpl().getProductById(newId);

            if (existedProduct == null) {
                newProduct.setProduct_id(newId);
                break;
            }
        }

        // Tên sản phẩm
        while (true) {
            System.out.print("Nhập tên sản phẩm: ");
            String name = sc.nextLine().trim();

            if (name.isEmpty()) {
                System.out.println("Vui lòng không để trống tên máy.");
                continue;
            }

            boolean existed = new ProductDAOImpl().getProducts().stream().anyMatch(p -> p.getProduct_name().equalsIgnoreCase(name));

            if (existed) {
                System.out.println("Tên máy đã tồn tại.");
            } else {
                newProduct.setProduct_name(name);
                break;
            }
        }

        // hãng máy
        while (true) {
            System.out.print("Nhập hãng máy: ");
            String brand = sc.nextLine().trim();

            if (brand.isEmpty()) {
                System.out.println("Vui lòng không để trống hãng máy.");
            } else {
                newProduct.setBrand(brand);
                break;
            }
        }

        // Màu
        while (true) {
            System.out.print("Nhập màu máy: ");
            String color = sc.nextLine().trim();

            if (color.isEmpty()) {
                System.out.println("Vui lòng không để trống màu máy.");
            } else {
                newProduct.setColor(color);
                break;
            }
        }

        // Dung lượng
        while (true) {
            System.out.print("Nhập dung lượng máy: ");
            try {
                int storage = Integer.parseInt(sc.nextLine().trim());

                if (storage <= 0) {
                    System.out.println("Vui lòng nhập dung lượng máy hợp lệ (> 0)");
                } else {
                    newProduct.setStorage(storage);
                    break;
                }
            } catch (Exception e) {
                System.out.println("Dung lượng máy được nhập chưa hợp lệ.");
            }
        }

        // Giá
        while (true) {
            System.out.print("Nhập giá máy: ");
            try {
                BigDecimal price = new BigDecimal(sc.nextLine().trim());

                if (price.compareTo(BigDecimal.ZERO) <= 0) {
                    System.out.println("Vui lòng nhập giá máy hợp lệ .");
                } else {
                    newProduct.setPrice(price);
                    break;
                }
            } catch (Exception e) {
                System.out.println("Giá máy được nhập chưa hợp lệ ");
            }
        }

        // Tồn kho
        while (true) {
            System.out.print("Số lượng nhập kho: ");
            try {
                int stock = Integer.parseInt(sc.nextLine().trim());

                if (stock < 0) {
                    System.out.println("Vui lòng nhập số lượng máy hợp lệ");
                } else {
                    newProduct.setStock(stock);
                    break;
                }
            } catch (Exception e) {
                System.out.println("Số lượng máy được nhập chưa hợp lệ ");
            }
        }

        //
        System.out.print("Nhập mô tả : ");
        String desc = sc.nextLine().trim();
        newProduct.setDescription(desc.isEmpty() ? "" : desc);

        //
        boolean result = new ProductDAOImpl().insertNewProduct(newProduct);

        if (result) {
            System.out.println("Thêm thành công! Máy mới: " + newProduct.getProduct_id() + " - " + newProduct.getProduct_name());
        } else {
            System.out.println("Thêm thất bại!");
        }
    }

    @Override
    public void updateProduct() {
        displayProductList();

        System.out.print("Nhập mã sản phẩm cần cập nhật: ");
        String pId = sc.nextLine().trim();

        Product target = new ProductDAOImpl().getProductById(pId);
        if (target == null) {
            System.out.println("Sản phẩm không tồn tại hoặc đã bị xóa.");
            return;
        }
        displayHeader(new ProductDAOImpl().getProducts(), "sản phẩm cần cập nhật");
        target.displayInfoProduct();
        System.out.print("┗" + "━".repeat(158) + "┛\n");

        int choice = -1;
        do {
            System.out.println("\nChọn mục cần cập nhật:");
            System.out.println("1. Danh mục");
            System.out.println("2. Tên sản phẩm");
            System.out.println("3. Hãng");
            System.out.println("4. Màu");
            System.out.println("5. Dung lượng");
            System.out.println("6. Giá");
            System.out.println("7. Tồn kho");
            System.out.println("8. Mô tả");
            System.out.println("0. Lưu và thoát");

            System.out.print("Lựa chọn: ");
            try {
                choice = Integer.parseInt(sc.nextLine().trim());
            } catch (Exception e) {
                System.out.println("Lựa chọn không hợp lệ.");
                continue;
            }

            switch (choice) {
                case 1:
                    new CategoryServiceImpl().displayListCategory();
                    System.out.print("Nhập mã danh mục mới: ");
                    String cateId = sc.nextLine().trim();
                    if (new CategoryDAOImpl().getCategoryById(cateId) != null) {
                        target.setCategory_id(cateId);
                    } else {
                        System.out.println("[!] Danh mục không tồn tại.");
                    }
                    break;

                case 2:
                    System.out.print("Nhập tên sản phẩm mới: ");
                    String name = sc.nextLine().trim();
                    boolean existed = new ProductDAOImpl().getProducts().stream().anyMatch(p -> p.getProduct_name().equalsIgnoreCase(name) && !p.getProduct_id().equals(pId));
                    if (name.isEmpty()) {
                        System.out.println("Vui lòng không để trống.");
                    } else if (existed) {
                        System.out.println("Tên máy đã tồn tại.");
                    } else {
                        target.setProduct_name(name);
                    }
                    break;

                case 3:
                    System.out.print("Nhập hãng mới: ");
                    String brand = sc.nextLine().trim();
                    if (!brand.isEmpty()) target.setBrand(brand);
                    break;

                case 4:
                    System.out.print("Nhập màu mới: ");
                    String color = sc.nextLine().trim();
                    if (!color.isEmpty()) target.setColor(color);
                    break;

                case 5:
                    System.out.print("Nhập dung lượng mới: ");
                    try {
                        int storage = Integer.parseInt(sc.nextLine().trim());
                        if (storage > 0) target.setStorage(storage);
                        else System.out.println("Vui lòng nhập dung lượng hợp lệ (> 0).");
                    } catch (Exception e) {
                        System.out.println("Dung lượng chưa hợp lệ.");
                    }
                    break;

                case 6:
                    System.out.print("Nhập giá mới: ");
                    try {
                        BigDecimal price = new BigDecimal(sc.nextLine().trim());
                        if (price.compareTo(BigDecimal.ZERO) > 0)
                            target.setPrice(price);
                        else System.err.println("Vui lòng nhập giá hợp lệ (Phải > 0)");
                    } catch (Exception e) {
                        System.out.println("Giá cập nhật chưa hợp lệ.");
                    }
                    break;

                case 7:
                    System.out.print("Nhập tồn kho mới: ");
                    try {
                        int stock = Integer.parseInt(sc.nextLine().trim());
                        if (stock >= 0) target.setStock(stock);
                        else System.out.println("Vui lòng nhập tồn kho hợp lệ (Phải >= 0)");
                    } catch (Exception e) {
                        System.out.println("Tồn kho cập nhật chưa hợp lệ.");
                    }
                    break;

                case 8:
                    System.out.print("Nhập mô tả mới: ");
                    String desc = sc.nextLine().trim();
                    target.setDescription(desc);
                    break;

                case 0:
                    target.setUpdated_at(LocalDateTime.now());
                    if (new ProductDAOImpl().updateProductInfo(target)) {
                        System.out.println("Cập nhật thành công!");
                    } else {
                        System.out.println("Cập nhật thất bại!");
                    }
                    break;

                default:
                    System.out.println("Lựa chọn không hợp lệ.");
            }

        } while (choice != 0);
    }

    @Override
    public void deleteProduct() {
        displayProductList();

        System.out.print("Nhập mã sản phẩm cần xóa: ");
        String pId = sc.nextLine().trim();

        Product target = new ProductDAOImpl().getProductById(pId);
        if (target == null) {
            System.out.println(" Sản phẩm không tồn tại hoặc đã bị xóa.");
            return;
        }

        System.out.print("Xác nhận xóa sản phẩm " + target.getProduct_name() + "? (y/n): ");
        String confirm = sc.nextLine().trim();

        if (confirm.equalsIgnoreCase("y")) {
            if (new ProductDAOImpl().deleteProductInfo(pId)) {
                System.out.println("Xóa sản phẩm thành công!");
            } else {
                System.out.println("Xóa sản phẩm thất bại!");
            }
        } else {
            System.out.println("Hủy thao tác xóa.");
        }
    }

    @Override
    public void searchProductByName() {
        System.out.print("Nhập tên sản phẩm cần tìm: ");
        String keyword = sc.nextLine().trim();

        if (keyword.isEmpty()) {
            System.out.println("Từ khóa cần tìm không được để trống.");
            return;
        }

        List<Product> results = new ProductDAOImpl().searchProductWithName(keyword);
        displayHeader(results, "KẾT QUẢ TÌM KIẾM: \"" + keyword + "\"");

        for (Product p : results) {
            p.displayInfoProduct();
        }

        System.out.println("┗" + "━".repeat(158) + "┛");
    }

}
