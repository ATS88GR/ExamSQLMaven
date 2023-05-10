package Create;

import java.sql.SQLException;
import static Connection.ConnectionDB.*;

public class CreateDB {
    public static void createDB() {
        try {
            statmt = con.createStatement();
            statmt.execute("CREATE TABLE if not exists 'Authors' ('Id' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, 'Name' NVARCHAR MAX NOT NULL CHECK(trim(name)!=''), 'Surname' nvarchar MAX NOT NULL CHECK(trim(name)!=''), 'CountryID' INT NOT NULL, FOREIGN KEY (CountryID) REFERENCES Country(Id));");
            statmt.execute("CREATE TABLE if not exists 'Books' ('Id' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, 'Name' NVARCHAR MAX NOT NULL CHECK(trim(name)!=''), 'Pages' INT NOT NULL CHECK(pages>0), 'Price' money NOT NULL CHECK(price>0), 'PublishDate' DATE NOT NULL CHECK (publishdate<= CURRENT_DATE), 'AuthorId' INT NOT NULL, 'ThemeId' INT NOT NULL, FOREIGN KEY (AuthorId) REFERENCES Authors (Id), FOREIGN KEY (ThemeId) REFERENCES Themes (Id));");
            statmt.execute("CREATE TABLE if not exists 'Countries' ('Id' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, 'Name' NVARCHAR(50) NOT NULL UNIQUE CHECK(trim(name)!=''));");
            statmt.execute("CREATE TABLE if not exists 'Sales' ('Id' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, 'Price' MONEY NOT NULL CHECK(price>0) , 'Quantity' INT NOT NULL CHECK (quantity>0), 'SaleDate' DATE NOT NULL CHECK(saledate<= CURRENT_DATE) DEFAULT CURRENT_DATE, 'BookId' INT NOT NULL, 'ShopId' INT NOT NULL, FOREIGN KEY (BookId) REFERENCES Books (Id), FOREIGN KEY (ShopId) REFERENCES Shops (Id));");
            statmt.execute("CREATE TABLE if not exists 'Shops' ('Id' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, 'Name' NVARCHAR MAX NOT NULL CHECK(trim(name)!=''), 'CountryId' INT NOT NULL, FOREIGN KEY (CountryId) REFERENCES Countries (Id));");
            statmt.execute("CREATE TABLE if not exists 'Themes' ('Id' INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, 'Name' NVARCHAR(100) NOT NULL CHECK(trim(name) != '') UNIQUE);");
        } catch (SQLException e) {
            System.out.println("Tables were not created");
        }
        System.out.println("Tables created or already exist");
    }
}
