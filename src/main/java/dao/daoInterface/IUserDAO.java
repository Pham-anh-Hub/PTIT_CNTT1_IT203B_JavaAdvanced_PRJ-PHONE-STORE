package dao.daoInterface;

import model.User;

public interface IUserDAO {
    User findByEmail(String email);
    User findByUsername(String username);
    boolean existsByEmail(String email); // for register
    boolean existsByUsername(String username); // for register
    boolean insertUser(User user); // for register
}
