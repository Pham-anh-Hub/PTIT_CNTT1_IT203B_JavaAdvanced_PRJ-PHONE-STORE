package dao.impl;

import dao.Interface.IUserDAO;
import model.Admin;
import model.Customer;
import model.User;
import utils.MyDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements IUserDAO {

    // Map ResultSet → Admin hoặc Customer tùy role
    private User setUser(ResultSet rs) throws SQLException {
        String role = rs.getString("role");
        User user;

        if (role.equals("ADMIN")) {
            user = new Admin(
                    rs.getString("id"),
                    rs.getString("username"),
                    rs.getString("email"),
                    rs.getString("password"),
                    role
            );
        } else {
            Customer customer = new Customer();
            customer.setId(rs.getString("id"));
            customer.setUsername(rs.getString("username"));
            customer.setEmail(rs.getString("email"));
            customer.setPassword(rs.getString("password"));
            customer.setRole(role);
            customer.setFullname(rs.getString("fullname"));
            customer.setPhone(rs.getString("phone"));
            customer.setAddress(rs.getString("address"));
            customer.setStatus(rs.getBoolean("is_active"));
            user = customer;
        }
        return user;
    }

    @Override
    public User findByEmail(String email) {
        String sql = "select * from users where email = ? and is_active = true";
        try (Connection conn = MyDatabase.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return setUser(rs);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public User findByUsername(String username) {
        String sql = "select * from users where username = ? and is_active = true";
        try (Connection conn = MyDatabase.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return setUser(rs);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public boolean existsByEmail(String email) {
        String sql = "select 1 from users where email = ?";
        try (Connection conn = MyDatabase.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            return ps.executeQuery().next();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean existsByUsername(String username) {
        String sql = "select 1 from users where username = ?";
        try (Connection conn = MyDatabase.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            return ps.executeQuery().next();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean insertUser(User user) {
        String sql = "insert into users (id, username, email, password, role, fullname, phone, address, is_active) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, true)";
        try (Connection conn = MyDatabase.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user.getId());
            ps.setString(2, user.getUsername());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPassword());  // đã hash = BCrypt
            ps.setString(5, user.getRole());

            // fullname, phone, address chỉ có ở Customer
            if (user instanceof Customer c) {
                ps.setString(6, c.getFullname());
                ps.setString(7, c.getPhone());
                ps.setString(8, c.getAddress());
            } else {
                ps.setNull(6, Types.VARCHAR);
                ps.setNull(7, Types.VARCHAR);
                ps.setNull(8, Types.VARCHAR);
            }

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Lấy tất cả khách hàng
    @Override
    public List<User> getAllCustomers() {
        List<User> customers = new ArrayList<>();
        String sql = "select * from users where role = 'CUSTOMER' order by id asc";
        try (Connection conn = MyDatabase.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) customers.add(setUser(rs));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return customers;
    }

    // Tìm kiếm khách hàng theo username hoặc email
    @Override
    public List<User> searchCustomer(String keyword) {
        List<User> results = new ArrayList<>();
        String sql = "select * from users where role = 'CUSTOMER' and (username like ? or email like ?)";
        try (Connection conn = MyDatabase.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + keyword + "%");
            ps.setString(2, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) results.add(setUser(rs));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return results;
    }

    // Khóa / mở tài khoản
    @Override
    public boolean setActiveStatus(String userId, boolean status) {
        String sql = "update users set is_active = ? where id = ?";
        try (Connection conn = MyDatabase.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setBoolean(1, status);
            ps.setString(2, userId);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Cập nhật hồ sơ cá nhân (Customer tự cập nhật)
    @Override
    public boolean updateUser(User user) {
        try (Connection conn = MyDatabase.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement("update users set fullname = ?, email = ?, phone = ?, address = ? where id = ?")) {

            if (user instanceof Customer c) {

                ps.setString(1, c.getFullname());
                ps.setString(2, c.getEmail());
                ps.setString(3, c.getPhone());
                ps.setString(4, c.getAddress());
            }
            ps.setString(5, user.getId());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User getUserByIdEmail(String id, String email) {
        try(Connection conn = MyDatabase.getInstance().getConnection();
        PreparedStatement pstmt = conn.prepareStatement("select * from users where id = ? and email = ?")){

            pstmt.setString(1, id);
            pstmt.setString(2, email);

            ResultSet rs = pstmt.executeQuery();

            if(rs.next()){
                return setUser(rs);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
