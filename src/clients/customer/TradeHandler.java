package clients.customer;

import DBAccess.ProductsManager;
import trade.Basket;
import trade.Product;

import javax.swing.*;
import java.sql.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TradeHandler
{
    private ProductsManager prodManager = new ProductsManager();
    private Basket basket = new Basket();
    private DefaultListModel listModel = new DefaultListModel();

    private final Pattern regexISBN = Pattern.compile("^(?:ISBN(?:-1[03])?:? )?(?=[0-9X]{10}$|(?=(?:[0-9]+[- ]){3})[- 0-9X]{13}$|97[89][0-9]{10}$|(?=(?:[0-9]+[- ]){4})[- 0-9]{17}$)(?:97[89][- ]?)?[0-9]{1,5}[- ]?[0-9]+[- ]?[0-9]+[- ]?[0-9X]$");
    
    public TradeHandler() {      
    }
    
    public Product getProductFromISBN(String isbn) {
        if (!validateISBN(isbn)) {
            System.out.println("TradeHandler::getProduct:: Invalid ISBN number");
            return null;
        }

        try {
            Connection conn = prodManager.getConnection();
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM PRODUCTS WHERE ISBN = ?");
            statement.setString(1, isbn);

            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                String productID = rs.getString("ProductID");
                String title = rs.getString("Title");
                float price = rs.getFloat("Price");
                String author = rs.getString("Author");
                String publisher = rs.getString("Publisher");
                String yearPublished = rs.getString("YearPublished");
                String description = rs.getString("Description");

                Product product = new Product(productID, title, price, author, publisher, yearPublished, description, 1);
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
        Product existingProduct = basket.getProduct(product);
        if (existingProduct != null) {
            this.listModel.removeElement(existingProduct.getLineSummary());
        }
        basket.add(product);
        product = basket.getProduct(product);
        this.listModel.addElement(product.getLineSummary());
    }

    public boolean validateISBN(String ISBN) {
        Matcher matcher = regexISBN.matcher(ISBN);
        if (matcher.matches()) {
            return true;
        }
        return false;
    }

    public DefaultListModel getListModel() {
        return this.listModel;
    }

    public int getBasketSize() {
        return basket.getTotalSize();
    }

    public float getBasketPrice() {
        return basket.getTotalPrice();
    }
}
