package dao.Interface;

import model.User;

import java.math.BigDecimal;
import java.util.List;

public interface IAdminDAO {
    public List<User> getUsers();
    public BigDecimal getTotalRevenue();
}
