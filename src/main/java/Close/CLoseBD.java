package Close;

import java.sql.SQLException;

import static Connection.ConnectionDB.*;

public class CLoseBD {
    public static void closeDB() throws SQLException
    {
        con.close();
        statmt.close();
        resSet.close();

        System.out.println("Соединения закрыты");
    }
}
