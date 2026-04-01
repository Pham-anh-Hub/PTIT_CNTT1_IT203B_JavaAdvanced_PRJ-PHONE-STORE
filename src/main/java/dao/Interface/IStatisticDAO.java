package dao.Interface;

import dao.impl.ProductDAOImpl;
import model.BestSellerProduct;
import model.CustomerPotential;
import model.Product;

import java.math.BigDecimal;
import java.util.List;

public interface IStatisticDAO {
    // lấy thống kê 5 sản phẩm bán chạy nhất
    public List<BestSellerProduct> getTop5ProductBestSell(int month, int year);

    // lấy thống kê khách hàng tiềm năng
    public List<CustomerPotential> getTopPotentialCustomer(int month, int year);

    // thống kê doanh thu
    public BigDecimal getMonthRevenue(int year, int month);

}
