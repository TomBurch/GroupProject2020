package clients.customer;

public class CustomerController
{
    private CustomerModel model = null;
    private CustomerView view = null;
    
    public CustomerController(CustomerView view, CustomerModel model)
    {
        this.view = view;
        this.model = model;
    }
}
