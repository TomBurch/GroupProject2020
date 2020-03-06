package clients.customer;

import java.security.SecureRandom;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.SecretKeyFactory;


public class LoginHandler
{    
    public LoginHandler()
    {
        
    }

    public boolean verifyAccount(String user, String pass)
    {
        //byte[] salt = generateSalt();
        //byte[] hashPass = hash(pass, salt);
        
        return false;
    }
    
    private byte[] hash(String pass, byte[] salt) {
        byte[] hash = null;
        
        try {
            PBEKeySpec spec = new PBEKeySpec(pass.toCharArray(), salt, 65536, 128);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            hash = factory.generateSecret(spec).getEncoded();
        } catch (Exception e) {
            System.out.println(e);
        }
        
        return hash;
    }
    
    private byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        
        return salt;
    }
}
