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
    
    public void setState(String state) {
        model.setState(state);
        System.out.println("New state: " + state);
    }
    
    public boolean verifyAccount(String user, String pass) {
        return model.verifyAccount(user, pass);
    }
}
