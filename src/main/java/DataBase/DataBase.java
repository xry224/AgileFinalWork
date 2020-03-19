package DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBase {
    public Connection getConnection() {
        Connection conn = null;
        try {
            String driver = "com.mysql.jdbc.Driver";
            Class.forName(driver);
            String url = "jdbc:mysql://172.19.240.244:3306/agile?useUnicode=true&characterEncoding=UTF-8&serverTimezone=GMT%2B8&useSSL=false";
            //String url="jdbc:mysql://127.0.0.1:3306/agile?useUnicode=true&characterEncoding=UTF-8&serverTimezone=GMT%2B8";
            String username = "root";
            String password = "123456Lsg.";
            //String password = "19971031";
            conn = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
        return conn;
    }

    public void closeConnection(Statement statement, Connection connection) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace(System.out);
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace(System.out);
            }
        }
    }
}
