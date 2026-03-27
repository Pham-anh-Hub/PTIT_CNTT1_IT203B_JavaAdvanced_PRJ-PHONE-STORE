package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyDatabase {
    private static MyDatabase instance;
    
    private static final String connectURL = "jdbc:mysql://localhost/prj_phone_store_java2";
    private static final String username = "root";
    private static final String password = "123456789";

    public MyDatabase() {
    }

    public static MyDatabase getInstance() {
        if(instance == null){
            instance = new MyDatabase();
        }
        return instance;
    }

    public Connection getConnection(){
        Connection conn = null;
        try{
            conn = DriverManager.getConnection(connectURL, username, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return conn;
    }
}
