package DBAccess;

import java.sql.*;

/**
 * DBManager for creating and managing access to the Products table
 */
public class ProductsManager extends DBManager
{
    @Override
    protected void setup() {
        try {
            try {
                PreparedStatement dropState = conn.prepareStatement("DROP TABLE PRODUCTS");
                dropState.executeUpdate();
            } catch (SQLException e) {
                System.out.println("AccountsManager::Setup:: " + e);
            }

            if (!checkTable("Products")) {
               System.out.println("ProductsManager::Setup:: Table products doesn't exist");

               PreparedStatement statement = conn.prepareStatement(
                       "CREATE TABLE PRODUCTS ("
                       + "ProductID int NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
                       + "ISBN varchar(17) NOT NULL,"
                       + "Title varchar(50) NOT NULL,"
                       + "Price decimal(5,2) NOT NULL,"
                       + "Author varchar(20),"
                       + "Publisher varchar(15),"
                       + "YearPublished date,"
                       + "Description varchar(100),"
                       + "PRIMARY KEY (ProductID))");
               statement.executeUpdate();

               Statement s = conn.createStatement();
               s.executeUpdate("INSERT INTO Products (ISBN, Title, Price, Author, Publisher, YearPublished, Description) VALUES ('978-3-16-148400-0', 'Example Book 0', 0.50, 'Author 0', 'Publisher 0', '2000-01-10', 'Example description 0')");
               s.executeUpdate("INSERT INTO Products (ISBN, Title, Price, Author, Publisher, YearPublished, Description) VALUES ('978-3-16-148410-0', 'Example Book 1', 1.50, 'Author 1', 'Publisher 1', '2001-02-11', 'Example description 1')");
               s.executeUpdate("INSERT INTO Products (ISBN, Title, Price, Author, Publisher, YearPublished, Description) VALUES ('978-3-16-148420-0', 'Example Book 2', 2.50, 'Author 2', 'Publisher 2', '2002-03-12', 'Example description 2')");
               s.executeUpdate("INSERT INTO Products (ISBN, Title, Price, Author, Publisher, YearPublished, Description) VALUES ('978-3-16-148430-0', 'Example Book 3', 3.50, 'Author 3', 'Publisher 3', '2002-04-13', 'Example description 3')");
               s.executeUpdate("INSERT INTO Products (ISBN, Title, Price, Author, Publisher, YearPublished, Description) VALUES ('978-3-16-148440-0', 'Example Book 4', 4.50, 'Author 4', 'Publisher 4', '2002-05-14', 'Example description 4')");
               s.executeUpdate("INSERT INTO Products (ISBN, Title, Price, Author, Publisher, YearPublished, Description) VALUES ('978-3-16-148450-0', 'Example Book 5', 5.50, 'Author 5', 'Publisher 5', '2002-06-15', 'Example description 5')");
               s.executeUpdate("INSERT INTO Products (ISBN, Title, Price, Author, Publisher, YearPublished, Description) VALUES ('978-3-16-148460-0', 'Example Book 6', 6.50, 'Author 6', 'Publisher 6', '2002-07-16', 'Example description 6')");
               s.executeUpdate("INSERT INTO Products (ISBN, Title, Price, Author, Publisher, YearPublished, Description) VALUES ('978-3-16-148470-0', 'Example Book 7', 7.50, 'Author 7', 'Publisher 7', '2002-08-17', 'Example description 7')");
               s.executeUpdate("INSERT INTO Products (ISBN, Title, Price, Author, Publisher, YearPublished, Description) VALUES ('978-3-16-148480-0', 'Example Book 8', 8.50, 'Author 8', 'Publisher 8', '2002-09-18', 'Example description 8')");
               s.executeUpdate("INSERT INTO Products (ISBN, Title, Price, Author, Publisher, YearPublished, Description) VALUES ('978-3-16-148490-0', 'Example Book 9', 9.50, 'Author 9', 'Publisher 9', '2002-10-19', 'Example description 9')");

               System.out.println("ProductsManager::Setup:: Created table products");
            }
        } catch (SQLException e) {
            System.out.println("ProductsManager::Setup:: " + e);
        }
    }
}