package clients.customer;

import handlers.LoginHandler;
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
            setTradePrice(tradeHandler.getBasketPrice());
        } else {
            setOutput("Product is not currently required");
        }
    }

    public DefaultListModel getListModel() {
        return tradeHandler.getListModel();
    }

    public int getBasketSize() {
        return tradeHandler.getBasketSize();
    }

    public float getBasketPrice() {
        return tradeHandler.getBasketPrice();
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
}