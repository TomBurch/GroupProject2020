
package clients.customer;

import java.security.SecureRandom;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.SecretKeyFactory;
import java.util.Base64;
import java.sql.*;


public class LoginHandler
{    
    AccountsManager accManager = new AccountsManager();
    
    public LoginHandler() {        
    }

    public boolean verifyAccount(String user, String pass) {
        try {  
            Connection conn = accManager.getConnection();
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM ACCOUNTS WHERE username = ?");
            statement.setString(1, user);
            
            ResultSet rs = statement.executeQuery();
            rs.next();
            
            String passHashSalt = rs.getString("PasswordHash");
            
            if (verifyPassword(pass, passHashSalt)) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("LoginHandler::verifyAccount:: " + e);
        }
        
        System.out.println("LoginHandler::verifyAccount:: Unable to verify account '" + user + "'");
        return false;
    }
    
    public boolean verifyPassword(String enteredPass, String passHashSalt) {
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
    
    public boolean makeAccount(String user, String pass) {
        byte[] salt = generateSalt();
        byte[] passHash = hash(pass, salt);
           
        String saltString = Base64.getEncoder().encodeToString(salt);
        String hashString = Base64.getEncoder().encodeToString(passHash);
        
        String hashAndSalt = hashString + ":" + saltString;
        
        if (!validateUsername(user) || !validatePassword(pass)) {
            System.out.println("LoginHandler::makeAccount:: Invalid username or password");
            return false;
        }
        
        try {
            Connection conn = accManager.getConnection();
            PreparedStatement statement = conn.prepareStatement("INSERT INTO ACCOUNTS (Username, PasswordHash) VALUES (?, ?)");
            statement.setString(1, user);
            statement.setString(2, hashAndSalt);
        
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

    private boolean validateUsername(String user) {
        if (user.length() >= 5) {
            return true;
        }
        return false;
    }
    
    private boolean validatePassword(String pass) {
        if (pass.length() >= 5 && pass.length() <= 15) {
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
