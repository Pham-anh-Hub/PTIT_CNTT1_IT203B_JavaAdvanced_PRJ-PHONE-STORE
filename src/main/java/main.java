import presentation.CategoryPresentation;
import utils.MyDatabase;

import java.sql.Connection;

public class main {
    public static void main(String[] args) {
        Connection conn = MyDatabase.getInstance().getConnection();
        CategoryPresentation.categoryManagement();
    }
}
