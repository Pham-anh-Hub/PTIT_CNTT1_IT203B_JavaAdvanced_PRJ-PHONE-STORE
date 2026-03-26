import utils.MyDatabase;

import java.sql.Connection;

public class main {
    public static void main(String[] args) {
        Connection conn = MyDatabase.getInstance().getConnection();
        if (conn != null){
            System.out.println("Thanh cong");
        }
    }
}
