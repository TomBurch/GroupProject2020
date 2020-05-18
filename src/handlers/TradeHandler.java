package handlers;

import DBAccess.ProductsManager;
import trade.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Modified BasketHandler for handling the Trade basket
 */
public class TradeHandler extends BasketHandler {
    /**
     * Regex pattern for verifying ISBNs (10 and 13 digits)
     */
    private final Pattern regexISBN = Pattern.compile("^(?:ISBN(?:-1[03])?:? )?(?=[0-9X]{10}$|(?=(?:[0-9]+[- ]){3})[- 0-9X]{13}$|97[89][0-9]{10}$|(?=(?:[0-9]+[- ]){4})[- 0-9]{17}$)(?:97[89][- ]?)?[0-9]{1,5}[- ]?[0-9]+[- ]?[0-9]+[- ]?[0-9X]$");
    /**
     * Manages access to the Products table
     */
    private final ProductsManager prodManager = new ProductsManager();

    /**
     * @return Product or null
     */
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
                String title = rs.getString("Title");
                float price = rs.getFloat("Price");
                String author = rs.getString("Author");
                String publisher = rs.getString("Publisher");
                String yearPublished = rs.getString("YearPublished");
                String description = rs.getString("Description");

                return new Product(isbn, title, price, author, publisher, yearPublished, description, 1);
            } else {
                System.out.println("TradeHandler::getProduct:: Product not in database");
                return null;
            }
        } catch (Exception e) {
            System.out.println("TradeHandler::getProduct:: " + e);
        }
        return null;
    }

    /**
     * Validate the given String against the ISBN regex pattern
     *
     * @param isbn String
     * @return True if matches pattern, else False
     */
    public boolean validateISBN(String isbn) {
        Matcher matcher = regexISBN.matcher(isbn);
        return matcher.matches();
    }
}