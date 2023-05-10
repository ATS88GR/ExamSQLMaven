package Read;

import java.sql.SQLException;
import java.util.Scanner;

import static Connection.ConnectionDB.*;

public class ReadDB {
   static Scanner sc = new Scanner(System.in);
    public static void readDB() throws ClassNotFoundException, SQLException
    {
        System.out.println("\nSelect the type of query:\n" +
                "1. Query by pages.\n" +
                "2. Query by letters. \n" +
                "3. Query by theme and sold quantity.\n");
        int typeOfQuery = Integer.parseInt(sc.nextLine());
        switch (typeOfQuery){
            case 1: queryByPages();
            case 2: queryByLetter();
            case 3: queryByThemeAndQuantity();
        }
    }

    public static void queryByPages() throws SQLException {
        int countOfBooks = 1;
        resSet = statmt.executeQuery("SELECT Name, Pages FROM Books WHERE Pages > 650");
        System.out.println("Books with count of pages more than 650");
        while(resSet.next())
        {
            String  name = resSet.getString("name");
            int pages = resSet.getInt("pages");
            System.out.println( countOfBooks +". Book: " + name + ", count of pages: " + pages );
            countOfBooks++;
        }
        System.out.println();
    }

    public static void queryByLetter() throws SQLException {
        int countOfBooks = 1;
        resSet = statmt.executeQuery("SELECT Name FROM Books WHERE Name LIKE 'A%' OR Name LIKE 'C%';");
        System.out.println("Books that start with A or C");
        while(resSet.next())
        {
            String  name = resSet.getString("name");
            System.out.println( countOfBooks +". Book: " + name);
            countOfBooks++;
        }
        System.out.println();
    }

    public static void queryByThemeAndQuantity() throws SQLException {
        int countOfBooks = 1;
        resSet = statmt.executeQuery("SELECT Books.Name, Sales.Quantity FROM Books\n" +
                "JOIN Themes ON Themes.Id == Books.ThemeId \n" +
                "JOIN Sales ON Sales.BookId == Books.Id \n" +
                "WHERE Themes.name == 'novel' AND Sales.Quantity>1;");
        System.out.println("Books that selected by theme and quantity");
        while (resSet.next()) {
            String name = resSet.getString("name");
            int pages = resSet.getInt("pages");
            System.out.println(countOfBooks + ". Book: " + name + ", count of pages: " + pages);
            countOfBooks++;
        }
        System.out.println();
    }
}
