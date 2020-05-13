package clients.customer;

public class CustomerClient {
    public CustomerClient() {
    }

    public static void Main(String[] args) {
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