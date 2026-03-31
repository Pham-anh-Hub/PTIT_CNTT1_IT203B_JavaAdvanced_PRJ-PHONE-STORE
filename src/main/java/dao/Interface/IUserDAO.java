package dao.Interface;

import model.User;

import java.util.List;

public interface IUserDAO {
    User findByEmail(String email);
    User findByUsername(String username);
    boolean existsByEmail(String email); // for register
    boolean existsByUsername(String username); // for register
    boolean insertUser(User user); // for register

    boolean updateUser(User user);         // cập nhật hồ sơ
    List<User> getAllCustomers();           // xem danh sách KH
    List<User> searchCustomer(String keyword); // tìm KH
    boolean setActiveStatus(String userId, boolean status); // quản lý trạng thái tài khoản
    User getUserByIdEmail(String id, String email);
}
