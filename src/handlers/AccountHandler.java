package handlers;

import DBAccess.AccountsManager;
import org.jetbrains.annotations.NotNull;
import trade.Account;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;
import java.sql.*;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class for handling accounts
 */
public class AccountHandler {
    /**
     * UK Government regex pattern for postcodes
     */
    private final Pattern regexPostcode = Pattern.compile("^([Gg][Ii][Rr] 0[Aa]{2})|((([A-Za-z][0-9]{1,2})|(([A-Za-z][A-Ha-hJ-Yj-y][0-9]{1,2})|(([AZa-z][0-9][A-Za-z])|([A-Za-z][A-Ha-hJ-Yj-y][0-9]?[A-Za-z]))))[0-9][A-Za-z]{2})$");
    private final Pattern regexEmail = Pattern.compile("^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");
    private final Pattern regexUsername = Pattern.compile("^[A-Za-z0-9]+(?:[ _-][A-Za-z0-9]+)*$");
    private final Pattern regexPassword = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{5,}$"); //>= 5 characters, 1 letter + 1 num
    /**
     * Manages access to the Accounts table
     */
    private final AccountsManager accManager = new AccountsManager();
    /**
     * Details of logged in account
     */
    private Account account;

    /**
     * Check user and pass against the Accounts table
     *
     * @return True if details correct, else False
     */
    public boolean verifyAccount(String user, String pass) {
        try {
            Connection conn = accManager.getConnection();
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM ACCOUNTS WHERE username = ?");
            statement.setString(1, user);

            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                String passHashSalt = rs.getString("PasswordHash");

                if (verifyPassword(pass, passHashSalt)) {
                    return true;
                }
            } else {
                System.out.println("AccountHandler::verifyAccount:: Username not in database");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("AccountHandler::verifyAccount:: " + e);
        }

        System.out.println("AccountHandler::verifyAccount:: Unable to verify account '" + user + "'");
        return false;
    }

    /**
     * Check if enteredPass matches the passHashSalt
     *
     * @return True if they match, else False
     */
    public boolean verifyPassword(String enteredPass, @NotNull String passHashSalt) {
        //Split PasswordHash entry from accounts into hash + salt
        String passSalt = passHashSalt.substring(passHashSalt.indexOf(":") + 1);
        String passHash = passHashSalt.substring(0, passHashSalt.indexOf(":"));

        byte[] salt = Base64.getDecoder().decode(passSalt);
        String enteredPassHash = Base64.getEncoder().encodeToString(hash(enteredPass, salt));

        return enteredPassHash.equals(passHash);
    }

    /**
     * Delete the given account from the Accounts table
     *
     * @param accountID int
     */
    public void deleteAccount(int accountID) {
        try {
            Connection conn = accManager.getConnection();
            PreparedStatement statement = conn.prepareStatement("DELETE FROM ACCOUNTS WHERE AccountID = ?");
            statement.setInt(1, accountID);

            int result = statement.executeUpdate();

            if (result == 0) {
                return;
            }
        } catch (SQLException e) {
            System.out.println("AccountHandler::makeAccount:: " + e);
        }
        System.out.println("AccountHandler::makeAccount:: Deleted account " + accountID);
    }

    public void updateAccount(int accountID) {
        try {
            Connection conn = accManager.getConnection();
            PreparedStatement statement = conn.prepareStatement("UPDATE ACCOUNTS SET Email = ?, Postcode = ? WHERE AccountID = ?");
            statement.setString(1, account.getEmail());
            statement.setString(2, account.getPostcode());
            statement.setInt(3, accountID);

            int result = statement.executeUpdate();

            if (result == 0) {
                return;
            }
        } catch (SQLException e) {
            System.out.println("AccountHandler::updateAccount:: " + e);
        }
        System.out.println("AccountHandler::updateAccount:: Updated account " + accountID);
    }

    /**
     * Validate all the entered details,
     * if they're good then make a new account in Accounts table
     */
    public String makeAccount(String user, @NotNull String pass, String passConfirm, String postcode, String email) {
        if (!pass.equals(passConfirm)) {
            System.out.println("AccountHandler::makeAccount:: Passwords do not match");
            return "Passwords do not match";
        }

        byte[] salt = generateSalt();
        byte[] passHash = hash(pass, salt);

        String saltString = Base64.getEncoder().encodeToString(salt);
        String hashString = Base64.getEncoder().encodeToString(passHash);

        String hashAndSalt = hashString + ":" + saltString;

        if (!validateUsername(user)) {
            return "Invalid username";
        } else if (!validatePassword(pass)) {
            return "Invalid password";
        } else if (!validatePostcode(postcode)) {
            return "Invalid postcode";
        } else if (!validateEmail(email)) {
            return "Invalid E-mail";
        }

        try {
            Connection conn = accManager.getConnection();
            PreparedStatement statement = conn.prepareStatement("INSERT INTO ACCOUNTS (Username, PasswordHash, Postcode, Email) VALUES (?, ?, ?, ?)");
            statement.setString(1, user);
            statement.setString(2, hashAndSalt);
            statement.setString(3, postcode);
            statement.setString(4, email);

            int result = statement.executeUpdate();

            if (result == 0) {
                return "Error creating account";
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            return "That username is already taken";
        } catch (SQLException e) {
            System.out.println("AccountHandler::makeAccount:: " + e);
            return "Error creating account";
        }
        System.out.println("AccountHandler::makeAccount:: Made new account '" + user + "'");
        return "success";
    }

    /**
     * Find account from the given username
     *
     * @param username String
     */
    public void setAccount(String username) {
        try {
            Connection conn = accManager.getConnection();
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM ACCOUNTS WHERE Username = ?");
            statement.setString(1, username);

            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                int accountID = rs.getInt("AccountID");
                String email = rs.getString("Email");
                String postcode = rs.getString("Postcode");

                account = new Account(accountID, username, email, postcode);
                System.out.println("AccountHandler::SetAccount:: Set account to " + username);
            } else {
                System.out.println("AccountHandler::SetAccount:: Unable to find user");
            }
        } catch (SQLException e) {
            System.out.println("AccountHandler::setAccount:: " + e);
        }
    }

    public boolean setEmail(String email) {
        if (validateEmail(email)) {
            account.setEmail(email);
            return true;
        }
        return false;
    }

    public boolean setPostcode(String postcode) {
        if (validatePostcode(postcode)) {
            account.setPostcode(postcode);
            return true;
        }
        return false;
    }

    public int getAccountID() {
        return account.getAccountID();
    }

    public String getAccountEmail() {
        return account.getEmail();
    }

    public String getAccountPostcode() {
        return account.getPostcode();
    }

    private byte[] hash(String pass, byte[] salt) {
        byte[] passHash = null;

        try {
            PBEKeySpec spec = new PBEKeySpec(pass.toCharArray(), salt, 65536, 128);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            passHash = factory.generateSecret(spec).getEncoded();
            passHash = Base64.getEncoder().encode(passHash);
        } catch (Exception e) {
            System.out.println("AccountHandler::hash:: " + e);
        }

        return passHash;
    }

    private byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);

        salt = Base64.getEncoder().encode(salt);

        return salt;
    }

    //=== Validation methods ===//

    private boolean validateUsername(@NotNull String user) {
        Matcher matcher = regexUsername.matcher(user);
        if (user.length() >= 5 && user.length() <= 32) {
            return matcher.matches();
        }
        return false;
    }

    private boolean validatePassword(@NotNull String pass) {
        Matcher matcher = regexPassword.matcher(pass);
        return matcher.matches();
    }

    private boolean validatePostcode(@NotNull String postcode) {
        Matcher matcher = regexPostcode.matcher(postcode);
        return matcher.matches();
    }

    private boolean validateEmail(@NotNull String email) {
        Matcher matcher = regexEmail.matcher(email);
        return matcher.matches();
    }
}