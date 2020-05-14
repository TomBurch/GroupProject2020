package clients.customer;

import handlers.LoginHandler;
import handlers.SavedHandler;
import handlers.TradeHandler;
import trade.Product;

import javax.swing.*;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeListener;

public class CustomerModel
{
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    private String state = "Login";
    private LoginHandler loginHandler = new LoginHandler();
    private TradeHandler tradeHandler = new TradeHandler();
    private SavedHandler savedHandler = new SavedHandler();
    
    public boolean verifyAccount(String user, String pass) {
        return loginHandler.verifyAccount(user, pass);
    }
    
    public void makeAccount(String user, String pass, String passConfirm, String postcode, String email) {
        loginHandler.makeAccount(user, pass, passConfirm, postcode, email);
    }

    public void addProductToBasket(String isbn) {
        Product product = tradeHandler.getProductFromISBN(isbn);

        if (product != null) {
            tradeHandler.addProductToBasket(product);

            setOutput(product.getDetails());
            setTradeList(tradeHandler.getListModel());
            setTradePrice(tradeHandler.getBasketPrice());
        } else {
            setOutput("Product is not currently required");
        }
    }

    public void deleteProductFromTrade(Product product) {
        tradeHandler.deleteProductFromBasket(product);
        setTradeList(tradeHandler.getListModel());
        setTradePrice(tradeHandler.getBasketPrice());
    }

    public Product getProductFromLineSummary(String lineSummary) {
        return tradeHandler.getProductFromLineSummary(lineSummary);
    }

    public DefaultListModel getTradeListModel() {
        return tradeHandler.getListModel();
    }

    public DefaultListModel getSavedListModel() {
        return savedHandler.getListModel();
    }

    public int getBasketSize() {
        return tradeHandler.getBasketSize();
    }

    public float getBasketPrice() {
        return tradeHandler.getBasketPrice();
    }

    public void saveBasket() {
        savedHandler.addBasket(tradeHandler.getBasket());
        tradeHandler.clearBasket();
        setTradeList(tradeHandler.getListModel());
        setTradePrice(tradeHandler.getBasketPrice());
        setSavedList(savedHandler.getListModel());
    }

    public void saveProduct(Product product) {
        savedHandler.addProductToBasket(product);
        tradeHandler.deleteProductFromBasket(product);
        setTradeList(tradeHandler.getListModel());
        setTradePrice(tradeHandler.getBasketPrice());
        setSavedList(savedHandler.getListModel());
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    public void setState(String state) {
        String oldValue = this.state;
        String newValue = state;
        this.state = state;
        System.out.println("CustomerModel::setState:: New state: " + state);
        pcs.firePropertyChange("state", oldValue, newValue);
    }

    public void setOutput(String newValue) {
        pcs.firePropertyChange("output", null, newValue);
    }

    public void setTradePrice(float newValue) {
        pcs.firePropertyChange("tradePrice", null, newValue);
    }

    public void setTradeList(DefaultListModel newValue) {
        pcs.firePropertyChange("tradeList", null, newValue);
    }

    public void setSavedList(DefaultListModel newValue) {
        pcs.firePropertyChange("savedList", null, newValue);
    }
}