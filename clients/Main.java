package clients;

import clients.customer.CustomerController;
import clients.customer.CustomerModel;
import clients.customer.CustomerView;

import javax.swing.*;
import java.awt.*;


public class Main
{
    public static void Main(String[] args) {
        new Main().begin();
    }

    public void begin() {
        createCustomerGUI();
    }
    
    public void createCustomerGUI() {               
        CustomerModel model = new CustomerModel();
        CustomerView view = new CustomerView();
        CustomerController controller = new CustomerController(view, model);
        
        view.setController(controller);
        model.addPropertyChangeListener(view);
        
        view.setVisible(true);
    }
}
