package presentation;

import service.StatisticService;

import java.util.Scanner;

public class StatisticManagement {
    public static Scanner sc = new Scanner(System.in);

    public void statisticManagement(){
        int choice = -1;
        do {
            System.out.println("\n\n┏━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ THỐNG KÊ BÁO CÁO ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
            System.out.println("|                                       |                                      |");
            System.out.println("|   1. Xem top 5 sản phẩm bán chạy      |   2. Xem top 3 khách hàng tiềm năng  |");
            System.out.println("|                                       |                                      |");
            System.out.println("┣━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━╋━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┫");
            System.out.println("|                                       |                                      |");
            System.out.println("|   3. Báo cáo doanh thu tháng          |       0. Quay lại                    |");
            System.out.println("|                                       |                                      |");
            System.out.println("┗━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┻━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┛");

            System.out.print("Lựa chọn của bạn: ");
            try {
                choice = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.err.println("Lựa chọn không hợp lệ.");
                continue;
            }
            switch (choice){
                case 1:
                    new StatisticService().getTop5BestProduct();
                    break;
                case 2:
                    new StatisticService().getPotentialCustomers();
                    break;
                case 3:
                    new StatisticService().showTotalRevenue();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ");
                    break;
            }
        }while (choice != 0);
    }
}
