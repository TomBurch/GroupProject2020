package clients.customer;

import DBAccess.ProductsManager;

import java.sql.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TradeHandler
{
    ProductsManager prodManager = new ProductsManager();

    Pattern regexISBN = Pattern.compile("^(?:ISBN(?:-1[03])?:? )?(?=[0-9X]{10}$|(?=(?:[0-9]+[- ]){3})[- 0-9X]{13}$|97[89][0-9]{10}$|(?=(?:[0-9]+[- ]){4})[- 0-9]{17}$)(?:97[89][- ]?)?[0-9]{1,5}[- ]?[0-9]+[- ]?[0-9]+[- ]?[0-9X]$");
    
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

    public boolean validateISBN(String ISBN) {
        Matcher matcher = regexISBN.matcher(ISBN);
        if (matcher.matches()) {
            return true;
        }
        return false;
    }
}
