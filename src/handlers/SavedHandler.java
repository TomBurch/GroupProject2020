package handlers;


import DBAccess.SavedManager;
import trade.Basket;
import trade.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Modified BasketHandler for handling the Saved basket
 */
public class SavedHandler extends BasketHandler {
    /**
     * Manages access to the Saved table
     */
    private final SavedManager savedManager = new SavedManager();

    /**
     * Insert all Products from Saved basket into Saved table
     *
     * @param accountID int
     */
    public void updateUsersSavedBasket(int accountID) {
        clearUsersSavedBasket(accountID);
        Connection conn = savedManager.getConnection();

        Basket basket = getBasket();
        basket.forEach(product -> {
            try {
                PreparedStatement statement = conn.prepareStatement("INSERT INTO SAVED VALUES (?, ?, ?)");
                statement.setInt(1, accountID);
                statement.setString(2, product.getISBN());
                statement.setInt(3, product.getQuantity());

                statement.executeUpdate();
            } catch (SQLException e) {
                System.out.println("SavedHandler::updateUsersSavedBasket:: " + e);
            }
        });
        System.out.println("SavedHandler::updateUsersSavedBasket:: Table updated for " + accountID);
    }

    /**
     * Removed all entries from Saved table with given AccountID
     *
     * @param accountID int
     */
    public void clearUsersSavedBasket(int accountID) {
        try {
            Connection conn = savedManager.getConnection();
            PreparedStatement statement = conn.prepareStatement("DELETE FROM SAVED WHERE AccountID = ?");
            statement.setInt(1, accountID);

            int result = statement.executeUpdate();

            if (result == 0) {
                return;
            }
        } catch (SQLException e) {
            System.out.println("SavedHandler::clearUsersSavedBasket:: " + e);
        }
        System.out.println("SavedHandler::clearUsersSavedBasket:: Table cleared for " + accountID);
    }

    /**
     * Return a Basket made from entries in Saved table under AccountID
     *
     * @param accountID int
     * @return Basket or null
     */
    public Basket getUsersSavedBasket(int accountID) {
        try {
            Connection conn = savedManager.getConnection();
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM SAVED WHERE AccountID = ?");
            statement.setInt(1, accountID);

            ResultSet rsSaved = statement.executeQuery();
            Basket basket = new Basket();

            while (rsSaved.next()) {
                String isbn = rsSaved.getString("ISBN");
                int quantity = rsSaved.getInt("Quantity");
                statement = conn.prepareStatement("SELECT * FROM PRODUCTS WHERE ISBN = ?");
                statement.setString(1, isbn);

                ResultSet rsProducts = statement.executeQuery();
                if (rsProducts.next()) {
                    String title = rsProducts.getString("Title");
                    float price = rsProducts.getFloat("Price");
                    String author = rsProducts.getString("Author");
                    String publisher = rsProducts.getString("Publisher");
                    String yearPublished = rsProducts.getString("YearPublished");
                    String description = rsProducts.getString("Description");

                    Product product = new Product(isbn, title, price, author, publisher, yearPublished, description, quantity);
                    basket.add(product);
                }
            }
            return basket;
        } catch (SQLException e) {
            System.out.println("SavedHandler::");
        }
        return null;
    }
}