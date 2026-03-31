package service;

import dao.impl.OrderImpl;
import dao.impl.StatistisDAOImpl;
import model.BestSellerProduct;
import model.CustomerPotential;
import model.Product;
import service.serviceInterface.IStatisticService;
import utils.CenterFormat;

import java.util.List;

public class StatisticService implements IStatisticService {
    @Override
    public void getTop5BestProduct() {
        List<BestSellerProduct> bestProducts = new StatistisDAOImpl().getTop5ProductBestSell();
        System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━ CÁC SẢN PHẨM BÁN CHẠY NHẤT ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
        System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
        System.out.printf("| %s | %s | %s | %s | %s |\n",
                CenterFormat.center("MÃ SẢN PHẨM", 12),
                CenterFormat.center("TÊN SẢN PHẦM", 20),
                CenterFormat.center("HÃNG", 10),
                CenterFormat.center("TỔNG SỐ LƯỢNG MUA", 15),
                CenterFormat.center("TỔNG THÀNH TIỀN", 10)
        );
        for (BestSellerProduct p : bestProducts){
            p.displayInfo();
        }
        System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");;

    }

    @Override
    public void getPotentialCustomers() {
        List<CustomerPotential> customerPotentials = new StatistisDAOImpl().getTopPotentialCustomer();
        System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ CÁC KHÁCH HÀNG MUA SẮM NHIỀU NHẤT ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
        System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
        if (customerPotentials.isEmpty()){
            System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ KHÔNG CÓ KHÁCH HÀNG NÀO THÁNG NÀY ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
            System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
            return;
        }
        System.out.printf("| %s | %s | %s | %s | %s | %s |\n",
                CenterFormat.center("Tên đăng nhập", 12),
                CenterFormat.center("Họ và tên", 20),
                CenterFormat.center("Email", 10),
                CenterFormat.center("Số điện thoai", 15),
                CenterFormat.center("Tổng đơn hàng", 10),
                CenterFormat.center( " ", 12)
        );

        for (CustomerPotential c : customerPotentials){
            c.displayInfo();
        }
        System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
    }

    @Override
    public void showTotalRevenue() {
        new OrderServiceImpl().displayAllOrders(new OrderImpl().getDeliveredOrder());
        System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
        System.out.println("|         TỔNG DOANH THU THÁNG: " + new StatistisDAOImpl().getMonthRevenue() + " VND                                                   |");
        System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");

   }

}
