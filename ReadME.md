# TỔNG THỂ KIẾN TRÚC DỰ ÁN
## 1. Model 
 - Chứa các lớp đại diện cho các thực thể từ Database 
 - Mỗi lớp chứa các thuộc tính, getter/setter, 1 số phương thức đơn giản: toString(), display().
## 2. DAO - Data Access Object
 - **Làm việc với database**
   - Thực hiện CRUD
   - Query SQL
   - Mapping ResultSet 
   - **VD:**
   public class ProductDAO {

     public List<Product> findAll() {
        // SELECT * FROM products
     }

     public void insert(Product p) {
        // INSERT INTO products
     }
   }
 - **Không:**
   - Viết business logic
   - không in ra console
   
## 3. Service - Business Logic
 - Xử lý nghiệp vụ:
   - Đặt hàng 
   - Tính tổng tiền
 - **Không:** 
   - Viết sql trực tiếp
   - không in UI
## 4. Presentation - UI
- Thực hiện:
  - Menu
  - Nhập dữ liệu
  - Hiển thị dữ liệu
- **Không:**
  - Viết SQL trực tiếp
  - Thực hiện logic phức tạp
## 5. Utils - Tiện ích
  - Lưu trữ các tiện ích/thư viện dùng chung cho toàn hệ thống
    - Kết nối DB - JDBC
    - Bcript - mã hóa mật khẩu 