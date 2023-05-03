package Connection;

import java.sql.*;

public class ConnectionDB {
    public static Connection con;
    public static Statement statmt;
    public static ResultSet resSet;
    public static void connectDB() {
        try {
            con = DriverManager.getConnection("jdbc:sqlite:BookShop.s3db"); // Подключение/Создание базы
        } catch (SQLException e) {
            System.out.println("Driver not implemented");;
        }
        System.out.println("Base connected!");
    }
}
