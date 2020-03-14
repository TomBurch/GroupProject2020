package clients.customer;

import java.sql.*;

public class TradeHandler
{
    ProductsManager prodManager = new ProductsManager();
    
    public TradeHandler() {      
    }
    
    public boolean checkProduct(String ISBN) {
        try {
            Connection conn = prodManager.getConnection();
            //PreparedStatement statement = conn.prepareStatement("INSERT INTO PRODUCTS (Username, PasswordHash) VALUES (?, ?)");
            //int result = statement.executeUpdate();
            int result = 0;
        
            if (result == 0) {
                return false;
            }  
        } catch (Exception e) {
            System.out.println("TradeHandler::checkProduct:: " + e);
        }
        return true;
    }
}
