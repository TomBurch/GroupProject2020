
package clients.customer;

import DBAccess.AccountsManager;
import org.jetbrains.annotations.NotNull;

import java.security.SecureRandom;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.SecretKeyFactory;
import java.util.Base64;
import java.sql.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class LoginHandler
{    
    AccountsManager accManager = new AccountsManager();

    Pattern regexPostcode = Pattern.compile("^([Gg][Ii][Rr] 0[Aa]{2})|((([A-Za-z][0-9]{1,2})|(([A-Za-z][A-Ha-hJ-Yj-y][0-9]{1,2})|(([AZa-z][0-9][A-Za-z])|([A-Za-z][A-Ha-hJ-Yj-y][0-9]?[A-Za-z]))))[0-9][A-Za-z]{2})$");
    Pattern regexEmail = Pattern.compile("^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");
    
    public LoginHandler() {        
    }

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
                System.out.println("LoginHandler::verifyAccount:: Username not in database");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("LoginHandler::verifyAccount:: " + e);
        }
        
        System.out.println("LoginHandler::verifyAccount:: Unable to verify account '" + user + "'");
        return false;
    }
    
    public boolean verifyPassword(String enteredPass, @NotNull String passHashSalt) {
        //Split PasswordHash entry from accounts into hash + salt
        String passSalt = passHashSalt.substring(passHashSalt.indexOf(":") + 1);
        String passHash = passHashSalt.substring(0, passHashSalt.indexOf(":"));
        
        byte[] salt = Base64.getDecoder().decode(passSalt); 
        String enteredPassHash = Base64.getEncoder().encodeToString(hash(enteredPass, salt));

        if (enteredPassHash.equals(passHash)) {
            return true;
        }
        return false;
    }
    
    public boolean makeAccount(String user, String pass, String passConfirm, String postcode, String email) {
        if (!pass.equals(passConfirm)) {
            System.out.println("LoginHandler::makeAccount:: Passwords do not match");
            return false;
        }

        byte[] salt = generateSalt();
        byte[] passHash = hash(pass, salt);
           
        String saltString = Base64.getEncoder().encodeToString(salt);
        String hashString = Base64.getEncoder().encodeToString(passHash);
        
        String hashAndSalt = hashString + ":" + saltString;
        
        if (!validateUsername(user) || !validatePassword(pass)) {
            System.out.println("LoginHandler::makeAccount:: Invalid username or password");
            return false;
        } else if (!validatePostcode(postcode)) {
            System.out.println("LoginHandler::makeAccount:: Invalid postcode");
            return false;
        } else if (!validateEmail(email)) {
            System.out.println("LoginHandler::makeAccount:: Invalid email");
            return false;
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
                return false;
            }  
        } catch (SQLException e) {
            System.out.println("LoginHandler::makeAccount:: " + e);
        }
        System.out.println("LoginHandler::makeAccount:: Made new account '" + user + "'");
        return true;
    }    

    private boolean validateUsername(@NotNull String user) {
        if (user.length() >= 5) {
            return true;
        }
        return false;
    }
    
    private boolean validatePassword(@NotNull String pass) {
        if (pass.length() >= 5 && pass.length() <= 15) {
            return true;
        }
        return false;
    }

    private boolean validatePostcode(@NotNull String postcode) {
        Matcher matcher = regexPostcode.matcher(postcode);
        if (matcher.matches()) {
            return true;
        }
        return false;
    }

    private boolean validateEmail(@NotNull String email) {
        Matcher matcher = regexEmail.matcher(email);
        if (matcher.matches()) {
            return true;
        }
        return false;
    }

    private byte[] hash(String pass, byte[] salt) {
        byte[] passHash = null;
        
        try {
            PBEKeySpec spec = new PBEKeySpec(pass.toCharArray(), salt, 65536, 128);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            passHash = factory.generateSecret(spec).getEncoded();
            passHash = Base64.getEncoder().encode(passHash);
        } catch (Exception e) {
            System.out.println("LoginHandler::hash:: " + e);
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
}
