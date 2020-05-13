package clients.customer;

import DBAccess.ProductsManager;
import trade.Basket;
import trade.Product;

import java.sql.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TradeHandler
{
    private ProductsManager prodManager = new ProductsManager();
    private Basket basket = new Basket();

    private final Pattern regexISBN = Pattern.compile("^(?:ISBN(?:-1[03])?:? )?(?=[0-9X]{10}$|(?=(?:[0-9]+[- ]){3})[- 0-9X]{13}$|97[89][0-9]{10}$|(?=(?:[0-9]+[- ]){4})[- 0-9]{17}$)(?:97[89][- ]?)?[0-9]{1,5}[- ]?[0-9]+[- ]?[0-9]+[- ]?[0-9X]$");
    
    public TradeHandler() {      
    }
    
    public Product getProduct(String ISBN) {
        if (!validateISBN(ISBN)) {
            System.out.println("TradeHandler::getProduct:: Invalid ISBN number");
            return null;
        }

        try {
            Connection conn = prodManager.getConnection();
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM PRODUCTS WHERE ISBN = ?");
            statement.setString(1, ISBN);

            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                String productID = rs.getString("ProductID");
                Product product = new Product(productID, 1);
                return product;
            }  else {
                System.out.println("TradeHandler::getProduct:: Product not in database");
                return null;
            }
        } catch (Exception e) {
            System.out.println("TradeHandler::getProduct:: " + e);
        }
        return null;
    }

    public void addProductToBasket(Product product) {
        this.basket.add(product);
        System.out.println(basket);
    }

    public boolean validateISBN(String ISBN) {
        Matcher matcher = regexISBN.matcher(ISBN);
        if (matcher.matches()) {
            return true;
        }
        return false;
    }
}
