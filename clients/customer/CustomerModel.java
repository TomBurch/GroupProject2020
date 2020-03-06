package clients.customer;

import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeListener;

public class CustomerModel
{
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    private String state = "Login";
    
    public CustomerModel()
    {
    }
    
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
}
