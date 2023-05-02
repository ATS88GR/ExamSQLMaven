package Connection;

import java.sql.*;

public class ConnectionDB {
    public static Connection con;
    public static Statement statmt;
    public static ResultSet resSet;
    public static void conn() throws SQLException {
        con = DriverManager.getConnection("jdbc:sqlite:BookShop.s3db"); // Подключение/Создание базы
        System.out.println("База Подключена!");
    }
}
