package clients.customer;

import handlers.AccountHandler;
import handlers.SavedHandler;
import handlers.TradeHandler;
import org.jetbrains.annotations.NotNull;
import trade.Basket;
import trade.Product;

import javax.swing.*;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeListener;
import java.util.List;

public class CustomerModel {
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    /**Initial GUI panel*/
    private String state = "Login";

    private AccountHandler accountHandler = new AccountHandler();
    private TradeHandler tradeHandler = new TradeHandler();
    private SavedHandler savedHandler = new SavedHandler();

    /**
     * Make a new account and log in
     */
    public void register(String user, String pass, String passConfirm, String postcode, String email) {
        accountHandler.makeAccount(user, pass, passConfirm, postcode, email);
        login(user, pass);
    }

    /**
     * Verify login details and move to Main panel
     */
    public void login(String user, String pass) {
        if (accountHandler.verifyAccount(user, pass)) {
            accountHandler.setAccount(user);
            setAccountEmail(accountHandler.getAccountEmail());
            setAccountPostcode(accountHandler.getAccountPostcode());

            Basket savedBasket = savedHandler.getUsersSavedBasket(accountHandler.getAccountID());
            if (savedBasket != null) {
                savedHandler.addBasket(savedBasket);
                setSavedList(savedHandler.getListModel());
            }
            setState("Main");
        }
    }

    public void logout() {
        setState("Login");

        savedHandler.clearBasket();
        setSavedList(savedHandler.getListModel());
        tradeHandler.clearBasket();
        setTradeList(tradeHandler.getListModel());
        setTradePrice((tradeHandler.getBasketPrice()));
        setResetView();
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
     * @param isbn  String
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
     * Delete the current account
     * @return String - Success/Fail message for dialog
     */
    public String deleteAccount() {
        try {
            // Delete any saved user info
            savedHandler.clearBasket();
            savedHandler.updateUsersSavedBasket(accountHandler.getAccountID());
            accountHandler.deleteAccount(accountHandler.getAccountID());
            logout();

            return "Account successfully deleted";
        } catch (Exception e) {
            System.out.println("CustomerModel::deleteAcount:: " + e);
            return "Error deleting account";
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
        savedHandler.updateUsersSavedBasket(accountHandler.getAccountID());
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
        savedHandler.updateUsersSavedBasket(accountHandler.getAccountID());
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
        savedHandler.updateUsersSavedBasket(accountHandler.getAccountID());
        setTradeList(tradeHandler.getListModel());
        setTradePrice(tradeHandler.getBasketPrice());
        setSavedList(savedHandler.getListModel());
    }

    /**
     * Attempt to trade the contents of the Trade basket (currently non-functional)
     * @return String - Success/Fail message for dialog
     */
    public String processTrade() {
        float price = tradeHandler.getBasketPrice();
        int size = tradeHandler.getBasketSize();

        if (price >= 10 && (size >= 10 && size <= 100)) {
            return "Trade successful";
        } else {
            return "Trade failed\nMust contain between 10 and 100 products worth over £10";
        }
    }

    /**
     * Move all products in the Trade basket to the Saved basket
     */
    public void saveBasket() {
        savedHandler.addBasket(tradeHandler.getBasket());
        savedHandler.updateUsersSavedBasket(accountHandler.getAccountID());
        tradeHandler.clearBasket();
        setTradeList(tradeHandler.getListModel());
        setTradePrice(tradeHandler.getBasketPrice());
        setSavedList(savedHandler.getListModel());
    }

    public String updateEmail(String newEmail) {
        if (accountHandler.setEmail(newEmail)) {
            accountHandler.updateAccount(accountHandler.getAccountID());
            return "E-Mail updated";
        } else {
            return "Invalid E-Mail";
        }
    }

    public String updatePostcode(String newPostcode) {
        if (accountHandler.setPostcode(newPostcode)) {
            accountHandler.updateAccount(accountHandler.getAccountID());
            return "Postcode updated";
        } else {
            return "Invalid postcode";
        }
    }

    //=== PropertyChange methods ===//

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    /**
     * Change the active Panel
     * @param state String
     */
    public void setState(String state) {
        String oldValue = this.state;
        this.state = state;
        System.out.println("CustomerModel::setState:: New state: " + state);
        pcs.firePropertyChange("state", oldValue, state);
    }

    /**
     * Change the HomePanel output text
     * @param newValue  String
     */
    public void setHomeOutput(String newValue) {
        pcs.firePropertyChange("homeOutput", null, newValue);
    }

    /**
     * Change the TradePanel priceLabel text
     * @param newValue  float
     */
    public void setTradePrice(float newValue) {
        pcs.firePropertyChange("tradePrice", null, newValue);
    }

    /**
     * Change the TradePanel tradeList contents
     * @param newValue  DefaultListModel
     */
    public void setTradeList(DefaultListModel newValue) {
        pcs.firePropertyChange("tradeList", null, newValue);
    }

    /**
     * Change the SavedPanel savedList contents
     * @param newValue  DefaultListModel
     */
    public void setSavedList(DefaultListModel newValue) {
        pcs.firePropertyChange("savedList", null, newValue);
    }

    /**
     * Change the AccountPanel emailEntry contents
     * @param newValue  String
     */
    public void setAccountEmail(String newValue) {
        pcs.firePropertyChange("accountEmail", null, newValue);
    }

    /**
     * Change the AccountPanel postcodeEntry contents
     * @param newValue  String
     */
    public void setAccountPostcode(String newValue) {
        pcs.firePropertyChange("accountPostcode", null, newValue);
    }

    /**
     * Reset CustomerView components
     */
    public void setResetView() {
        pcs.firePropertyChange("resetView", null, null);
    }
}