package Write;

import java.sql.SQLException;

import static Connection.ConnectionDB.resSet;
import static Connection.ConnectionDB.statmt;

public class WriteDBWithDialog extends WriteDB{
    public static void writeDB() throws SQLException {
        resSet = statmt.executeQuery("pragma table_info(Authors)"); // get fields of table
        int countFields = 0;
        while(resSet.next()) {
            //String [] fields = resSet.getArray();
            System.out.println(resSet.getString(2));
            //++countFields;
        }
        System.out.println();
        resSet = statmt.executeQuery("SELECT name FROM sqlite_master WHERE type='table';");  //get name of tables
        while(resSet.next()) {
            System.out.println(resSet.getString(1));
        }
    }
}
