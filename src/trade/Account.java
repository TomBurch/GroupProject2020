package trade;

public class Account {
    private int accountID;
    private String username;
    private String email;
    private String postcode;

    public Account(int accountID, String username, String email, String postcode) {
        this.accountID = accountID;
        this.username = username;
        this.email = email;
        this.postcode = postcode;
    }

    public int getAccountID() {
        return accountID;
    }

    public String getEmail() {
        return email;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }
}
