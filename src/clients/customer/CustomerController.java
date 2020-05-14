package clients.customer;

import trade.Product;

import javax.swing.*;

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
        System.out.println("CustomerController::setState:: New state: " + state);
    }
    
    public boolean verifyAccount(String user, String pass) {
        return model.verifyAccount(user, pass);
    }
    
    public boolean makeAccount(String user, String pass, String passConfirm, String postcode, String email) {
        return model.makeAccount(user, pass, passConfirm, postcode, email);
    }

    public Product getProduct(String ISBN) {
        return model.getProduct(ISBN);
    }

    public void addProductToBasket(Product product) {
        model.addProductToBasket(product);
    }

    public DefaultListModel getListModel() {
        return model.getListModel();
    }

    public int getBasketSize() {
        return model.getBasketSize();
    }
}
