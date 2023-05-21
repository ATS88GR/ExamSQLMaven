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
                "3. Query by theme and sold quantity.\n" +
                "4. Query by word.\n" +
                "5. Query by cost of page.\n" +
                "6. Query by count of words in title.\n" +
                "7. Query by many tables #1.\n" +
                "8. Query by aggregation functions.\n" +
                "9. Query by themes and pages.\n" +
                "10. Query by Authors, books and pages.\n" +
                "11. Query by programming book with max number of pages.\n" +
                "12. Query by themes and average number of pages.\n" +
                "13. Query by themes of books, sorted by number of pages.\n" +
                "14. Query by sold books.\n" +
                "15. Query by profit.");
        int typeOfQuery = Integer.parseInt(sc.nextLine());
        switch (typeOfQuery){
            case 1: queryByPages();
            case 2: queryByLetter();
            case 3: queryByThemeAndQuantity();
            case 4: queryByWords();
            case 5: queryByCostOfPage();
            case 6: queryByCountOfWords();
            case 7: queryByManyTables1();
            case 8: queryByAgrFunctions();
            case 9: queryByThemesAndPages();
            case 10: queryByAuthorBooksPages();
            case 11: queryByThemeAndMaxPage();
            case 12: queryByThemesAndAvgPages();
            case 13: queryByPagesAndThemes();
            case 14: queryBySoldBooks();
            case 15: queryByProfit();
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
        resSet = statmt.executeQuery("SELECT Books.Name AS BookName, sum (Sales.Quantity) AS CountOfSales FROM Books " +
                "JOIN Themes ON Themes.Id == Books.ThemeId " +
                "JOIN Sales ON Sales.BookId == Books.Id  " +
                "WHERE Themes.name == 'novel' " +
                "GROUP BY Books.Name " +
                "HAVING CountOfSales >1;");
        System.out.println("Books that selected by theme and quantity of sales");
        while (resSet.next()) {
            String name = resSet.getString("BookName");
            System.out.println(countOfBooks + ". Book: " + name);
            countOfBooks++;
        }
        System.out.println();
    }

    public static void queryByWords() throws SQLException {
        int countOfBooks = 1;
        resSet = statmt.executeQuery("SELECT Name FROM Books WHERE Name LIKE '%Windows%' AND Name NOT LIKE '%98%';");
        System.out.println("Title of books that contains word Windows, but not contains number 98");
        while(resSet.next())
        {
            String  name = resSet.getString("name");
            System.out.println( countOfBooks +". Book: " + name);
            countOfBooks++;
        }
        System.out.println();
    }

    public static void queryByCostOfPage() throws SQLException {
        int countOfBooks = 1;
        resSet = statmt.executeQuery("SELECT 'Title: ' || Books.Name || ', theme: ' || Themes.Name || ', author: ' || Authors.Name || ' ' || Authors.Surname AS Info FROM Books \n" +
                "                JOIN Themes ON Themes.Id == Books.ThemeId " +
                "                JOIN Sales ON Sales.BookId == Books.Id " +
                "                JOIN Authors ON Authors.Id == Books.AuthorId " +
                "                GROUP BY Books.Name" +
                "HAVING SUM (Sales.Price*Sales.Quantity/Books.Pages)/sum(Sales.Quantity) > 0.1;");
        System.out.println("Books that selected by theme and quantity of sales");
        while (resSet.next()) {
            String name = resSet.getString("BookName");
            System.out.println(countOfBooks + ". Book: " + name);
            countOfBooks++;
        }
        System.out.println();
    }
    public static void queryByCountOfWords() throws SQLException {
        int countOfBooks = 1;
        resSet = statmt.executeQuery("SELECT Name FROM Books " +
                "WHERE (SELECT length(name) - length(replace(name, ' ', '')) + 1) == 4;");
        System.out.println("Title of books that contains four words");
        while(resSet.next())
        {
            String  name = resSet.getString("name");
            System.out.println( countOfBooks +". Book: " + name);
            countOfBooks++;
        }
        System.out.println();
    }

    public static void queryByManyTables1() throws SQLException {
        int countOfBooks = 1;
        resSet = statmt.executeQuery("SELECT Books.Name AS BookName, sum (Sales.Quantity) AS CountOfSales FROM Books, Shops, Countries" +
                "                JOIN Themes ON Themes.Id == Books.ThemeId" +
                "                JOIN Sales ON Sales.BookId == Books.Id  " +
                "JOIN Authors On Authors.Id == Books.AuthorId " +
                "                WHERE Sales.ShopId == Shops.Id " +
                "AND Shops.CountryId == Countries.Id " +
                "AND Countries.Name != 'Russia' " +
                "AND Books.Name NOT Like '%q%'" +
                "AND Themes.name != 'programming' " +
                "AND (SELECT Authors.Name || ' ' || Authors.Surname) != 'Franz Kafka' " +
                "AND Sales.Price > 10 AND Sales.Price < 20 " +
                "                GROUP BY Books.Name " +
                "                HAVING CountOfSales >1;");
        System.out.println("Books that selected by theme and quantity of sales");
        while (resSet.next()) {
            String name = resSet.getString("BookName");
            int countOfSales = resSet.getInt("CountOfSales");
            System.out.println(countOfBooks + ". Book: " + name + ", count of sales: " + countOfSales);
            countOfBooks++;
        }
        System.out.println();
    }
    public static void queryByAgrFunctions() throws SQLException {
        resSet = statmt.executeQuery("SELECT count (DISTINCT Authors.Id) AS CountOfAuthors, count (DISTINCT Books.Name) AS CountOfBooks, avg (Sales.Price) AS AveragePriceOfSales, avg (Books.Pages) AS AveragePagesOfBooks FROM Books " +
                "JOIN Sales ON Sales.BookId == Books.Id " +
                "JOIN Authors On Authors.Id == Books.AuthorId;");
        System.out.println("Partial statistics");
        while (resSet.next()) {
            int countOfAuthors = resSet.getInt("CountOfAuthors");
            int countOfBooks = resSet.getInt("CountOfBooks");
            double avgPrice = (double) (Math.round(resSet.getDouble("AveragePriceOfSales") * 100))/100;
            int avgPage = (int) Math.round(resSet.getDouble("AveragePagesOfBooks"));
            System.out.println("Count Of Authors: " + countOfAuthors + "\nCount of books: " + countOfBooks +
                    "\nAverage price of sales: " + avgPrice + "\nAverage count of Pages: " + avgPage);
        }
        System.out.println();
    }
    public static void queryByThemesAndPages() throws SQLException {
        int countOfThemes = 1;
        resSet = statmt.executeQuery("SELECT Themes.Name, sum (Books.Pages) AS CountOfPages FROM Themes " +
                "JOIN Books ON Themes.Id == Books.ThemeId " +
                "GROUP BY Themes.Name;");
        System.out.println("All pages of books, sorted by themes");
        while(resSet.next())
        {
            String  name = resSet.getString("name");
            int countOfPages = resSet.getInt("CountOfPages");
            System.out.println( countOfThemes +". Theme: " + name + ", count of pages: " + countOfPages);
            countOfThemes++;
        }
        System.out.println();
    }
    public static void queryByAuthorBooksPages() throws SQLException {
        int countOfAuthors = 1;
        resSet = statmt.executeQuery("SELECT Authors.Name || ' ' || Authors.Surname AS Author, count (Books.Name) AS CountOfBooks, sum (Books.Pages) AS CountOfPages FROM Authors " +
                "                JOIN Books ON Authors.Id == Books.AuthorId " +
                "GROUP BY Authors.Name;");
        System.out.println("All books and their pages, sorted by authors");
        while(resSet.next())
        {
            String  name = resSet.getString("Author");
            int countOfBooks = resSet.getInt("CountOfBooks");
            int countOfPages = resSet.getInt("CountOfPages");
            System.out.println( countOfAuthors +". Author: " + name + ", count of books: " + countOfBooks + ", count of pages: " + countOfPages);
            countOfAuthors++;
        }
        System.out.println();
    }
    public static void queryByThemeAndMaxPage() throws SQLException {
        resSet = statmt.executeQuery("SELECT Books.Name AS Book, MAX (Books.Pages) AS MaxPage FROM Books " +
                "                JOIN Themes ON Themes.Id == Books.ThemeId " +
                "WHERE Themes.Name == 'programming';");
        System.out.println("The programming book with max number of pages");
        while(resSet.next())
        {
            String  name = resSet.getString("Book");
            int maxNumPages = resSet.getInt("MaxPage");
            System.out.println( "The book: " + name + ", number of pages: " + maxNumPages);
        }
        System.out.println();
    }
    public static void queryByThemesAndAvgPages() throws SQLException {
        int countOfThemes = 1;
        resSet = statmt.executeQuery("SELECT Themes.Name AS Theme, AVG (Books.Pages) AS AvgPages FROM Books " +
                "                JOIN Themes ON Themes.Id == Books.ThemeId " +
                "GROUP BY Themes.Name " +
                "HAVING AvgPages > 400;");
        System.out.println("Themes of books, with average number of pages more than 400");
        while(resSet.next())
        {
            String  theme = resSet.getString("theme");
            int countOfPages = resSet.getInt("AvgPages");
            System.out.println( countOfThemes +". Theme: " + theme + ", average number of pages: " + countOfPages);
            countOfThemes++;
        }
        System.out.println();
    }
    public static void queryByPagesAndThemes() throws SQLException {
        int countOfThemes = 1;
        resSet = statmt.executeQuery("SELECT Themes.Name AS Theme, sum (Books.Pages) AS SumOfPages FROM Books " +
                "                JOIN Themes ON Themes.Id == Books.ThemeId " +
                "WHERE Books.Pages > 400 AND Themes.Id IN(1,5,8) " +
                "GROUP BY Themes.Name;");
        System.out.println("Themes of books, sorted by number of pages");
        while(resSet.next())
        {
            String theme = resSet.getString("Theme");
            int sumOfPages = resSet.getInt("SumOfPages");
            System.out.println( countOfThemes +". Theme: " + theme + ", sum of pages: " + sumOfPages);
            countOfThemes++;
        }
        System.out.println();
    }
    public static void queryBySoldBooks() throws SQLException {
        int countOfSoldBooks = 1;
        resSet = statmt.executeQuery("SELECT Books.Name AS Book, Countries.Name AS Country, Shops.Name AS Shop, Sales.SaleDate AS SaleDate, Sales.Quantity FROM Books, Countries, Shops " +
                "                JOIN Sales ON Sales.BookId == Books.Id " +
                "WHERE Shops.CountryId == Countries.Id " +
                "AND Sales.ShopId == Shops.Id;");
        System.out.println("Information about sold books");
        while(resSet.next())
        {
            String bookName = resSet.getString("Book");
            String country = resSet.getString("Country");
            String shop = resSet.getString("Shop");
            String saleDate = resSet.getString("SaleDate");
            int saleQuantity = resSet.getInt("Quantity");
            System.out.println( countOfSoldBooks +". Name: " + bookName + ", sale country: " + country + ", the shop name: " +
                    shop + ", the date of sale: " + saleDate + ", quantity of sales: " + saleQuantity);
            countOfSoldBooks++;
        }
        System.out.println();
    }
    public static void queryByProfit() throws SQLException {
        int countOfShops = 1;
        resSet = statmt.executeQuery("SELECT Shops.Name AS Shop, SUM ((Sales.Price - Books.Price)* Sales.Quantity) AS Profit FROM Shops, Books " +
                "                JOIN Sales ON Sales.ShopId == Shops.Id " +
                "WHERE Sales.BookId == Books.Id " +
                "GROUP BY Shop " +
                "ORDER BY Profit DESC;");
        System.out.println("Shops, sorted by biggest profit");
        while(resSet.next())
        {
            String shop = resSet.getString("Shop");
            double profit = (double) (Math.round(resSet.getDouble("Profit") * 100)) /100;
            System.out.println( countOfShops +". Shop: " + shop + ", profit: " + profit);
            countOfShops++;
        }
        System.out.println();
    }
}
