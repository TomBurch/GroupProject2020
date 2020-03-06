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
        JFrame window = new JFrame();
        window.setTitle("Customer Client");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        CustomerModel model = new CustomerModel();
        CustomerView view = new CustomerView(window);
        CustomerController controller = new CustomerController(view, model);
        view.setController(controller);
        model.addObserver(view);
        
        window.pack();
        window.setVisible(true);
    }
}
