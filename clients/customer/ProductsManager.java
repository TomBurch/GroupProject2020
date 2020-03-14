package clients.customer;

import java.sql.*;

public class ProductsManager extends DBManager
{
    public ProductsManager() {
       setup();
    }
    
    @Override
    protected void setup() {
        try {
            PreparedStatement dropState = conn.prepareStatement("DROP TABLE PRODUCTS");
            dropState.executeUpdate();
            if (!checkTable("Products")) {
               System.out.println("ProductsManager::Setup:: Table products doesn't exist");
               
               PreparedStatement statement = conn.prepareStatement(
               "CREATE TABLE PRODUCTS ("
               + "ProductID int NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
               + "Price decimal(5,2) NOT NULL,"
               + "ISBN varchar(13) NOT NULL,"
               + "Title varchar(15) NOT NULL," 
               + "Author varchar(20),"
               + "Publisher varchar(15),"
               + "YearPublished date,"
               + "Description varchar(100),"
               + "PRIMARY KEY (ProductID))");
               statement.executeUpdate();
               
               System.out.println("ProductsManager::Setup:: Created table products");
            }
        } catch (SQLException e) {
            System.out.println("ProductsManager::Setup:: " + e);
        }
    }
}
