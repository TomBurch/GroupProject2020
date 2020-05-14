package clients.customer;

import javax.swing.*;
import java.util.List;

import trade.Product;

public class CustomerController
{
    private CustomerModel model = null;
    private CustomerView view = null;
    
    public CustomerController(CustomerView view, CustomerModel model)
    {
        this.view = view;
        this.model = model;
    }

    public DefaultListModel getTradeListModel() {
        return model.getTradeListModel();
    }

    public DefaultListModel getSavedListModel() {
        return model.getSavedListModel();
    }

    public void login_loginButtonClicked(String user, String pass) {
        System.out.println("CustomerController:: LoginPanel::loginButton clicked");

        if (model.verifyAccount(user, pass)) {
            model.setState("Main");
        }
    }

    public void login_registerButtonClicked() {
        System.out.println("CustomerController:: LoginPanel::registerButton clicked");
        model.setState("Register");
    }

    public void register_confirmButtonClicked(String user, String pass, String passConfirm, String postcode, String email) {
        System.out.println("CustomerController:: RegisterPanel::confirmButton clicked");

        model.makeAccount(user, pass, passConfirm, postcode, email);
        if (model.verifyAccount(user, pass)) {
            model.setState("Main");
        }
    }

    public void register_cancelButtonClicked() {
        System.out.println("CustomerController:: RegisterPanel::cancelButton clicked");
        model.setState("Login");
    }

    public void home_submitButtonClicked(String isbn) {
        System.out.println("CustomerController:: HomePanel::submitButton clicked");
        model.addProductToBasket(isbn);
    }

    public void trade_tradeButtonClicked() {
        System.out.println("CustomerController:: TradePanel::tradeButton clicked");
        float price = model.getBasketPrice();
        int size = model.getBasketSize();

        if (price >= 10 && (size >= 10 && size <= 100)) {
            System.out.println("Trade allowed");
        } else {
            System.out.println("Trade not allowed");
        }
    }

    public void trade_saveButtonClicked() {
        System.out.println("CustomerController:: TradePanel::saveButton clicked");
        model.saveBasket();
    }

    public void trade_savePopupClicked(List<String> selectedValues) {
        System.out.println("CustomerController:: TradePanel::savePopup clicked");
        selectedValues.forEach(lineSummary -> {
            Product product = model.getProductFromLineSummary(lineSummary);
            model.saveProduct(product);
        });
    }

    public void trade_deletePopupClicked(List<String> selectedValues) {
        System.out.println("CustomerController:: TradePanel::savePopup clicked");
    }
}