package app.lib;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class hash {

    public static byte[] toDigest(String msg) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(msg.getBytes());
        return hash;
    }

}
