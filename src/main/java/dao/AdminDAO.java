package dao;

import dao.daoInterface.IAdminDAO;
import model.Admin;
import model.Customer;
import model.User;
import utils.MyDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminDAO implements IAdminDAO {

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
    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        try(Connection conn = MyDatabase.getInstance().getConnection();
            PreparedStatement pstmt = conn.prepareStatement("select * from users where role != 'ADMIN'")){

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()){
                User user = setUser(rs);
                users.add(user);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }
}
