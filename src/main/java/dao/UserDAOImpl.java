package dao;

import dao.daoInterface.IUserDAO;
import model.Admin;
import model.Customer;
import model.User;
import utils.MyDatabase;

import java.sql.*;

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
}
