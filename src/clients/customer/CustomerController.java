package clients.customer;

import java.util.List;

import org.jetbrains.annotations.NotNull;

public class CustomerController {
    private CustomerModel model;
    private CustomerView view;
    
    public CustomerController(CustomerView view, CustomerModel model) {
        this.view = view;
        this.model = model;
    }

    //=== Login Panel ===//

    public void login_loginButtonClicked(String user, String pass) {
        System.out.println("CustomerController:: LoginPanel::loginButton clicked");
        model.login(user, pass);
    }

    public void login_registerButtonClicked() {
        System.out.println("CustomerController:: LoginPanel::registerButton clicked");
        model.setState("Register");
    }

    //=== Register Panel ===//

    public void register_confirmButtonClicked(String user, String pass, String passConfirm, String postcode, String email) {
        System.out.println("CustomerController:: RegisterPanel::confirmButton clicked");
        model.register(user, pass, passConfirm, postcode, email);
    }

    public void register_cancelButtonClicked() {
        System.out.println("CustomerController:: RegisterPanel::cancelButton clicked");
        model.setState("Login");
    }

    //=== Home Panel ===//

    public void home_checkButtonClicked(String isbn) {
        System.out.println("CustomerController:: HomePanel::checkButton clicked");
        model.checkProduct(isbn);
    }

    public void home_tradeButtonClicked(String isbn) {
        System.out.println("CustomerController:: HomePanel::tradeButton clicked");
        model.addProductToTrade(isbn);
    }

    //=== Trade Panel ===//

    public String trade_tradeButtonClicked() {
        System.out.println("CustomerController:: TradePanel::tradeButton clicked");
        return model.processTrade();
    }

    public void trade_saveButtonClicked() {
        System.out.println("CustomerController:: TradePanel::saveButton clicked");
        model.saveBasket();
    }

    public void trade_savePopupClicked(@NotNull List<String> selectedValues) {
        System.out.println("CustomerController:: TradePanel::savePopup clicked");
        model.saveSelectedValues(selectedValues);
    }

    public void trade_deletePopupClicked(@NotNull List<String> selectedValues) {
        System.out.println("CustomerController:: TradePanel::deletePopup clicked");
        model.deleteTradeSelectedValues(selectedValues);
    }

    //=== Saved Panel ===//

    public void saved_tradePopupClicked(@NotNull List<String> selectedValues) {
        System.out.println("CustomerController:: SavedPanel::tradePopup clicked");
        model.tradeSelectedValues(selectedValues);
    }

    public void saved_deletePopupClicked(@NotNull List<String> selectedValues) {
        System.out.println("CustomerController:: SavedPanel::deletePopup clicked");
        model.deleteSavedSelectedValues(selectedValues);
    }

    //=== Account Panel ===//

    public String account_deleteAccountButtonClicked() {
        System.out.println("CustomerController:: AccountPanel::deleteAccountButton clicked");
        return model.deleteAccount();
    }
}