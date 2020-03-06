package clients.customer;

import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeListener;

public class CustomerModel
{
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    
    public CustomerModel()
    {

    }
    
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }
    
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(listener);
    }
    
    //pcs.firePropertyChange("value", oldValue, newValue)
}
