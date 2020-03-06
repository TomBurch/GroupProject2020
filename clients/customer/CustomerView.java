package clients.customer;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class CustomerView implements Observer
{
    private CustomerController controller = null;
    
    public CustomerView(RootPaneContainer rpc)
    {
        Container cp = rpc.getContentPane();
        Container rootWindow = (Container) rpc;
    } 
    
    @Override
    public void update(Observable obs, Object arg) {
        CustomerModel model = (CustomerModel) obs;
    }
    
    public void setController(CustomerController controller) {
        this.controller = controller;
    }
}
