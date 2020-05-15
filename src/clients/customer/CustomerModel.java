package clients.customer;

import handlers.LoginHandler;
import handlers.SavedHandler;
import handlers.TradeHandler;
import org.jetbrains.annotations.NotNull;
import trade.Product;

import javax.swing.*;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeListener;
import java.util.List;

public class CustomerModel {
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    /**Initial GUI panel*/
    private String state = "Login";

    private LoginHandler loginHandler = new LoginHandler();
    private TradeHandler tradeHandler = new TradeHandler();
    private SavedHandler savedHandler = new SavedHandler();

    /**
     * Make a new account and log in
     */
    public void register(String user, String pass, String passConfirm, String postcode, String email) {
        loginHandler.makeAccount(user, pass, passConfirm, postcode, email);
        login(user, pass);
    }

    /**
     * Verify login details and move to Main panel
     */
    public void login(String user, String pass) {
        if (loginHandler.verifyAccount(user, pass)) {
            setState("Main");
        }
    }

    /**
     * Add a product to the Trade basket
     * @param isbn  ISBN of the product
     */
    public void addProductToTrade(String isbn) {
        Product product = checkProduct(isbn);

        if (product != null) {
            tradeHandler.addProductToBasket(product);
            setTradeList(tradeHandler.getListModel());
            setTradePrice(tradeHandler.getBasketPrice());
        }
    }

    /**
     * Check if a Product with given ISBN exists in Products table
     * @param isbn
     * @return  Product if it exists, else null
     */
    public Product checkProduct(String isbn) {
        Product product = tradeHandler.getProductFromISBN(isbn);

        if (product != null) {
            setHomeOutput(product.getDetails());
            return product;
        } else {
            setHomeOutput("Product is not currently required");
            return null;
        }
    }

    /**
     * Delete selected savedList products from the Saved basket
     * @param selectedValues    List of selected products (lineSummary)
     */
    public void deleteSavedSelectedValues(@NotNull List<String> selectedValues) {
        selectedValues.forEach(lineSummary -> {
            Product product = savedHandler.getProductFromLineSummary(lineSummary);
            savedHandler.deleteProductFromBasket(product);
        });
        setSavedList(savedHandler.getListModel());
    }

    /**
     * Delete selected tradeList products from the Trade basket
     * @param selectedValues    List of selected products (lineSummary)
     */
    public void deleteTradeSelectedValues(@NotNull List<String> selectedValues) {
        selectedValues.forEach(lineSummary -> {
           Product product = tradeHandler.getProductFromLineSummary(lineSummary);
           tradeHandler.deleteProductFromBasket(product);
        });
        setTradeList(tradeHandler.getListModel());
    }

    /**
     * Move selected tradeList products from the Trade basket to the Saved basket
     * @param selectedValues    List of selected products (lineSummary)
     */
    public void saveSelectedValues(@NotNull List<String> selectedValues) {
        selectedValues.forEach(lineSummary -> {
            Product product = tradeHandler.getProductFromLineSummary(lineSummary);
            savedHandler.addProductToBasket(product);
            tradeHandler.deleteProductFromBasket(product);
        });
        setSavedList(savedHandler.getListModel());
        setTradeList(tradeHandler.getListModel());
        setTradePrice(tradeHandler.getBasketPrice());
    }

    /**
     * Move selected savedList products from the Saved basket to the Trade basket
     * @param selectedValues    List of selected products (lineSummary)
     */
    public void tradeSelectedValues(@NotNull List<String> selectedValues) {
        selectedValues.forEach(lineSummary -> {
            Product product = savedHandler.getProductFromLineSummary(lineSummary);
            tradeHandler.addProductToBasket(product);
            savedHandler.deleteProductFromBasket(product);
        });
        setTradeList(tradeHandler.getListModel());
        setTradePrice(tradeHandler.getBasketPrice());
        setSavedList(savedHandler.getListModel());
    }

    /**
     * Move all products in the Trade basket to the Saved basket
     */
    public void saveBasket() {
        savedHandler.addBasket(tradeHandler.getBasket());
        tradeHandler.clearBasket();
        setTradeList(tradeHandler.getListModel());
        setTradePrice(tradeHandler.getBasketPrice());
        setSavedList(savedHandler.getListModel());
    }

    //=== To be replaced ===//

    /**
     * Get total price of the Trade basket (sum of Product.quantity * Product.price)
     * @return  float - Basket price
     */
    public float getBasketPrice() {
        return tradeHandler.getBasketPrice();
    }

    /**
     * Get total size of the Trade basket (sum of Product.quantity)
     * @return  int - Basket size
     */
    public int getTradeBasketSize() {
        return tradeHandler.getBasketSize();
    }

    //=== PropertyChange methods ===//

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    /**
     * Change the active Panel
     * @param state
     */
    public void setState(String state) {
        String oldValue = this.state;
        String newValue = state;
        this.state = state;
        System.out.println("CustomerModel::setState:: New state: " + state);
        pcs.firePropertyChange("state", oldValue, newValue);
    }

    /**
     * Change the HomePanel output text
     * @param newValue
     */
    public void setHomeOutput(String newValue) {
        pcs.firePropertyChange("homeOutput", null, newValue);
    }

    /**
     * Change the TradePanel priceLabel text
     * @param newValue
     */
    public void setTradePrice(float newValue) {
        pcs.firePropertyChange("tradePrice", null, newValue);
    }

    /**
     * Change the TradePanel tradeList contents
     * @param newValue
     */
    public void setTradeList(DefaultListModel newValue) {
        pcs.firePropertyChange("tradeList", null, newValue);
    }

    /**
     * Changed the SavedPanel savedList contents
     * @param newValue
     */
    public void setSavedList(DefaultListModel newValue) {
        pcs.firePropertyChange("savedList", null, newValue);
    }
}