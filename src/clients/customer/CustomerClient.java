package clients.customer;

/**
 * Standalone Customer client
 */
public class CustomerClient {

    public static void main(String[] args) {
        createCustomerGUI();
    }

    private static void createCustomerGUI() {
        CustomerModel model = new CustomerModel();
        CustomerView view = new CustomerView();
        CustomerController controller = new CustomerController(view, model);

        view.setController(controller);
        model.addPropertyChangeListener(view);
    }
}