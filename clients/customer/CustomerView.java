package clients.customer;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

public class CustomerView implements PropertyChangeListener
{
    private CustomerController controller = null;
    
    public CustomerView(RootPaneContainer rpc)
    {
        Container cp = rpc.getContentPane();
        Container rootWindow = (Container) rpc;
    } 
    
    @Override
    public void propertyChange(PropertyChangeEvent event) {
        //event.getNewValue();
        //event.getOldValue();
        //event.getPropertyName();
    }
    
    public void setController(CustomerController controller) {
        this.controller = controller;
    }
}
