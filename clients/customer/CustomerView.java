package clients.customer;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Write a description of class CustomerClass here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class CustomerView implements Observer
{
    public CustomerView(RootPaneContainer rpc)
    {
        Container cp = rpc.getContentPane();
        Container rootWindow = (Container) rpc;
    } 
    
    public void update(Observable obs, Object arg) {
        CustomerModel model = (CustomerModel) obs;
    }
}
