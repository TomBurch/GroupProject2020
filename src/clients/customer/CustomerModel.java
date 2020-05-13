package clients.customer;

import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeListener;

public class CustomerModel
{
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    private String state = "Login";
    private LoginHandler loginHandler = new LoginHandler();
    private TradeHandler tradeHandler = new TradeHandler();
    
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }
    
    public String getState() {
        return state;
    }
    
    public void setState(String state) {
        String oldValue = this.state;
        String newValue = state;
        this.state = state;
        pcs.firePropertyChange("state", oldValue, newValue);
    }
    
    public boolean verifyAccount(String user, String pass) {
        return loginHandler.verifyAccount(user, pass);
    }
    
    public boolean makeAccount(String user, String pass) {
        return loginHandler.makeAccount(user, pass);
    }
}
