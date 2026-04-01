package dao.impl;

import dao.Interface.IStatisticDAO;
import model.BestSellerProduct;
import model.CustomerPotential;
import model.Product;
import presentation.CustomerPresentation;
import utils.MyDatabase;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StatistisDAOImpl implements IStatisticDAO {

    // lấy thống kê 5 sản phẩm bán chạy nhất
    @Override
    public List<BestSellerProduct> getTop5ProductBestSell(int month, int year){
        List<BestSellerProduct> products = new ArrayList<>();
        try (Connection conn = MyDatabase.getInstance().getConnection();
             CallableStatement cstmt = conn.prepareCall("call pr_get_top5_bestproduct(?,?)")){

            cstmt.setInt(1, month);
            cstmt.setInt(2, year);

            ResultSet rs = cstmt.executeQuery();
            while (rs.next()){
                BestSellerProduct product = new BestSellerProduct();
                product.setProduct_id(rs.getString("product_id"));
                product.setProduct_name(rs.getString("product_name"));
                product.setBrand(rs.getString("brand"));
                product.setTotal_quantity(rs.getInt("total_quantity"));
                product.setTotal_revenue(rs.getBigDecimal("total_revenue"));

                products.add(product);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return products;
    }

    // lấy thống kê khách hàng tiềm năng
    public List<CustomerPotential> getTopPotentialCustomer(int month, int year){
        List<CustomerPotential> customerPotentials = new ArrayList<>();

        try(Connection conn = MyDatabase.getInstance().getConnection();
        CallableStatement cstmt = conn.prepareCall("call pr_get_top3_potential_customer(?,?)")){

            cstmt.setInt(1, month);
            cstmt.setInt(2, year);

            ResultSet rs = cstmt.executeQuery();
            while (rs.next()){
                CustomerPotential potential = new CustomerPotential();
                // u.username, u.fullname, u.email, u.phone, count(ord.order_id) as total_orders, sum(ord.total_amount) as total_paid
                potential.setFullname(rs.getString("fullname"));
                potential.setEmail(rs.getString("email"));
                potential.setUsername(rs.getString("username"));
                potential.setPhone(rs.getString("phone"));
                potential.setTotal_orders(rs.getInt("total_orders"));
                potential.setTotal_paid(rs.getInt("total_paid"));

                customerPotentials.add(potential);
            }


        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return customerPotentials;
    }

    @Override
    public BigDecimal getMonthRevenue(int targetYear, int targetMonth) {
        try(Connection conn = MyDatabase.getInstance().getConnection();
            PreparedStatement pstmt = conn.prepareStatement("select sum(total_amount) as total_revenue from orders where month(order_date) = ? and year(order_date) = ? and status = 'DELIVERED';")){

            pstmt.setInt(1, targetMonth);
            pstmt.setInt(2, targetYear);

            ResultSet rs  = pstmt.executeQuery();

            if(rs.next()){
                return rs.getBigDecimal("total_revenue");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
