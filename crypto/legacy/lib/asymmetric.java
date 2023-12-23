package lib;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import javax.crypto.Cipher;

public class asymmetric {

    public static Pair<PrivateKey, PublicKey> KeyGen(int size) throws Exception {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(size);
        KeyPair key = keyGen.generateKeyPair();
        return new Pair<PrivateKey, PublicKey>(key.getPrivate(), key.getPublic());
    }

    public static byte[] encrypt(PublicKey key, byte[] msg) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(msg);
    }

    public static byte[] decrypt(PrivateKey key, byte[] msg) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(msg);
    }

}
