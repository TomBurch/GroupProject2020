package DBAccess;

import java.sql.*;

public class ProductsManager extends DBManager
{
    public ProductsManager() {
       setup();
    }
    
    @Override
    protected void setup() {
        try {
            //try {
            //    PreparedStatement dropState = conn.prepareStatement("DROP TABLE PRODUCTS");
            //    dropState.executeUpdate();
            //} catch (SQLException e) {
            //    System.out.println("AccountsManager::Setup:: " + e);
            //}

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
               s.executeUpdate("INSERT INTO Products (ISBN, Title, Price) VALUES ('978-3-16-148410-0', 'Example Book 1', 5.50)");
               s.executeUpdate("INSERT INTO Products (ISBN, Title, Price) VALUES ('978-3-16-148420-0', 'Example Book 2', 3.30)");
               s.executeUpdate("INSERT INTO Products (ISBN, Title, Price) VALUES ('978-3-16-148430-0', 'Example Book 3', 1.30)");

               System.out.println("ProductsManager::Setup:: Created table products");
            }
        } catch (SQLException e) {
            System.out.println("ProductsManager::Setup:: " + e);
        }
    }
}
