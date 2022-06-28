package viewer;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class aaa {
    public static void main(String[] args) {
        convertToSha("abcd");
    }
    private static String convertToSha(String password) {
        String converted = null;
        StringBuilder builder = null;
        
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes("UTF-8"));
            
            
            
            builder = new StringBuilder();
            System.out.println("length: " + hash.length);
            for(int i = 0; i < hash.length; i++) {
                System.out.println(hash[i]);
            }
            
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        
        return null;
    }
}
