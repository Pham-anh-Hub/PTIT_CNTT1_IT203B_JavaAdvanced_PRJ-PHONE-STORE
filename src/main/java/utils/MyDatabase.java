package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyDatabase {
    private static MyDatabase instance;

    private static Connection conn;
    private static final String connectURL = "jdbc:mysql://localhost/prj_phone_store_java2";
    private static final String username = "root";
    private static final String password = "123456789";

    public MyDatabase() {
        try{
            conn = DriverManager.getConnection(connectURL, username, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static MyDatabase getInstance() {
        if(instance == null){
            instance = new MyDatabase();
        }
        return instance;
    }

    public Connection getConnection(){
        try{
            if (conn.isClosed() || conn == null){
                conn = DriverManager.getConnection(connectURL, username, password);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return conn;
    }
}
