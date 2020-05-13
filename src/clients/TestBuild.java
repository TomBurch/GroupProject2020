package clients;

import clients.customer.CustomerController;
import clients.customer.CustomerModel;
import clients.customer.CustomerView;

public class TestBuild {
    public static void main(String[] args) {
        startCustomerGUI();
    }

    public static void startCustomerGUI() {
        CustomerModel model = new CustomerModel();
        CustomerView view = new CustomerView();
        CustomerController controller = new CustomerController(view, model);
        
        view.setController(controller);
        model.addPropertyChangeListener(view);
    }
}
