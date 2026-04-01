package service;

import dao.impl.OrderImpl;
import dao.impl.StatistisDAOImpl;
import model.BestSellerProduct;
import model.CustomerPotential;
import model.Product;
import presentation.StatisticManagement;
import service.serviceInterface.IStatisticService;
import utils.CenterFormat;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class StatisticService implements IStatisticService {
    public static Scanner sc = new Scanner(System.in);

    @Override
    public void getTop5BestProduct() {

        int targetMonth = 0;
        int targetYear = 0;
        while (true){
            System.out.print("Nhập tháng cần thống kê: ");
            try{
                targetMonth = sc.nextInt();
                if(targetMonth < 0 ){
                    System.err.println("Vui lòng nhập tháng hợp lệ!!");
                }else {
                    break;
                }
            }catch (NumberFormatException e){
                System.err.println("Vui lòng nhập tháng hợp lệ!!");
            }
        }
        while (true){
            System.out.print("Nhập năm: ");
            try{
                targetYear = sc.nextInt();
                if(targetYear < 1940 || targetYear > LocalDate.now().getYear()){
                    System.err.println("Vui lòng nhập năm hợp lệ!!");
                }else{
                    break;
                }
            }catch (NumberFormatException e){
                System.err.println("Vui lòng nhập năm hợp lệ!!");
            }
        }


        List<BestSellerProduct> bestProducts = new StatistisDAOImpl().getTop5ProductBestSell(targetMonth, targetYear);
        System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ CÁC SẢN PHẨM BÁN CHẠY NHẤT ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
        System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
        System.out.printf("| %s | %s | %s | %s | %s |\n",
                CenterFormat.center("MÃ SẢN PHẨM", 12),
                CenterFormat.center("TÊN SẢN PHẦM", 20),
                CenterFormat.center("HÃNG", 10),
                CenterFormat.center("TỔNG SỐ LƯỢNG MUA", 17),
                CenterFormat.center(" TỔNG THÀNH TIỀN", 12)
        );
        System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
        if(bestProducts.isEmpty()){
        System.out.println("|                             CHƯA CÓ THÔNG TIN THÁNG NÀY                                 |");
        }else{
            for (BestSellerProduct p : bestProducts){
                p.displayInfo();
            }
        }


        System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");;

    }

    @Override
    public void getPotentialCustomers() {
        int targetMonth = 0;
        int targetYear = 0;
        while (true){
            System.out.print("Nhập tháng muốn thống kê: ");
            try{
                targetMonth = sc.nextInt();
                if(targetMonth < 0 ){
                    System.err.println("Vui lòng nhập tháng hợp lệ!!");
                }else {
                    break;
                }
            }catch (NumberFormatException e){
                System.err.println("Vui lòng nhập tháng hợp lệ!!");
            }
        }
        while (true){
            System.out.print("Nhập năm: ");
            try{
                targetYear = sc.nextInt();
                if(targetYear < 1940 || targetYear > LocalDate.now().getYear()){
                    System.err.println("Vui lòng nhập năm hợp lệ!!");
                }else{
                    break;
                }
            }catch (NumberFormatException e){
                System.err.println("Vui lòng nhập năm hợp lệ!!");
            }
        }


        List<CustomerPotential> customerPotentials = new StatistisDAOImpl().getTopPotentialCustomer(targetMonth, targetYear);
        System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ CÁC KHÁCH HÀNG MUA SẮM NHIỀU NHẤT ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
        System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
        if (customerPotentials.isEmpty()){
            System.out.println("|                                      CHƯA CÓ KHÁCH HÀNG NÀO THÁNG NÀY                                    |");
            System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
            return;
        }
        System.out.printf("| %s | %s | %s | %s | %s | %s |\n",
                CenterFormat.center("Tên đăng nhập", 12),
                CenterFormat.center("Họ và tên", 20),
                CenterFormat.center("Email", 13),
                CenterFormat.center("Số điện thoai", 15),
                CenterFormat.center("Tổng đơn hàng", 13),
                CenterFormat.center( "Tổng doanh thu ", 13)
        );
        System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");


        for (CustomerPotential c : customerPotentials){
            c.displayInfo();
        }
        System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
    }

    @Override
    public void showTotalRevenue() {

        int targetMonth = 0;
        int targetYear = 0;
        while (true){
            System.out.print("Nhập tháng muốn tính doanh thu: ");
            try{
                targetMonth = sc.nextInt();
                if(targetMonth < 0 ){
                    System.err.println("Vui lòng nhập tháng hợp lệ!!");
                }else {
                    break;
                }
            }catch (NumberFormatException e){
                System.err.println("Vui lòng nhập tháng hợp lệ!!");
            }
        }
        while (true){
            System.out.print("Nhập năm: ");
            try{
                targetYear = sc.nextInt();
                if(targetYear < 1940 || targetYear > LocalDate.now().getYear()){
                    System.err.println("Vui lòng nhập năm hợp lệ!!");
                }else{
                    break;
                }
            }catch (NumberFormatException e){
                System.err.println("Vui lòng nhập năm hợp lệ!!");
            }
        }



        BigDecimal totalMonthRevenue = new StatistisDAOImpl().getMonthRevenue(targetYear, targetMonth);
        if (totalMonthRevenue != null){
            System.out.println("\n\n=================================== DANH SÁCH ĐƠN HÀNG THÁNG ======================================");
            new OrderServiceImpl().displayAllOrders(new OrderImpl().getDeliveredOrder(targetYear, targetMonth));
        }
        System.out.println("┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
        if(totalMonthRevenue!= null){
            NumberFormat nf = NumberFormat.getNumberInstance();
            System.out.println("|         TỔNG DOANH THU THÁNG: " + nf.format(totalMonthRevenue) + " VND                                                    |");

        }else {
            System.out.println("|                        CHƯA CÓ THÔNG TIN DOANH THU THÁNG " + targetMonth + "/" + targetYear + "                                 |");
        }
        System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");
   }

}
