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

    public String login_loginButtonClicked(String user, String pass) {
        System.out.println("CustomerController:: LoginPanel::loginButton clicked");
        return model.login(user, pass);
    }

    public void login_registerButtonClicked() {
        System.out.println("CustomerController:: LoginPanel::registerButton clicked");
        model.setState("Terms");
    }

    //=== Register Panel ===//

    public String register_confirmButtonClicked(boolean over18, String user, String pass, String passConfirm, String postcode, String email) {
        System.out.println("CustomerController:: RegisterPanel::confirmButton clicked");
        return model.register(over18, user, pass, passConfirm, postcode, email);
    }

    public void register_cancelButtonClicked() {
        System.out.println("CustomerController:: RegisterPanel::cancelButton clicked");
        model.setState("Terms");
    }

    //=== Terms Panel ===//

    public String terms_confirmButtonClicked(boolean agreed) {
        System.out.println("CustomerController:: TermsPanel::confirmButton clicked");
        if (agreed) {
            model.setState("Register");
            return "success";
        } else {
            return "You must accept the terms and conditions";
        }
    }

    public void terms_cancelButtonClicked() {
        System.out.println("CustomerController:: TermsPanel::cancelButton clicked");
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

    public String account_updateEmailButtonClicked(String newEmail) {
        System.out.println("CustomerController:: AccountPanel::updateEmailButton clicked");
        return model.updateEmail(newEmail);
    }

    public String account_updatePostcodeButtonClicked(String newPostcode) {
        System.out.println("CustomerController:: AccountPanel::updatePostcodeButton clicked");
        return model.updatePostcode(newPostcode);
    }

    public String account_deleteAccountButtonClicked() {
        System.out.println("CustomerController:: AccountPanel::deleteAccountButton clicked");
        return model.deleteAccount();
    }

    public void account_logoutButtonClicked() {
        System.out.println("CustomerController:: AccountPanel::logoutButton clicked");
        model.logout();
    }
}