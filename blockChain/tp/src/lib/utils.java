package lib;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class utils {
    public static String encodeBase64(byte[] text) {
        byte[] encodedText = Base64.getEncoder().encode(text);
        return new String(encodedText);
    }

    public static String decodeBase64(byte[] text) {
        byte[] decodedText = Base64.getDecoder().decode(text);
        return new String(decodedText);
    }

    public static String generateDigest(String text) {
        MessageDigest generator;
        try {
            generator = MessageDigest.getInstance("SHA-256");
            return utils.encodeBase64(generator.digest(text.getBytes()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }
}
