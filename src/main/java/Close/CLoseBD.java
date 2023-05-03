package Close;

import java.sql.SQLException;

import static Connection.ConnectionDB.*;

public class CLoseBD {
    public static void closeDB()
    {
        try {
            con.close();
            statmt.close();
            resSet.close();
        } catch (SQLException e) {
            System.out.println("Connections were not closed");;
        }
        System.out.println("Connections were closed");
    }
}
