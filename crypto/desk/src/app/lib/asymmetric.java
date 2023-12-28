package app.lib;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

public class asymmetric {

    public static Pair<String, String> encodeKeys(Pair<PrivateKey, PublicKey> keys) throws NoSuchAlgorithmException {
        byte[] privateEncoded = keys.x.getEncoded();
        byte[] publicEncoded = keys.y.getEncoded();
        return new Pair<String, String>(utils.encode(publicEncoded), utils.encode(privateEncoded));
    }

    public static PublicKey decodePublic(String encodedPublic)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory factory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec publicEncoded = new X509EncodedKeySpec(utils.decode(encodedPublic));
        PublicKey publicKey = factory.generatePublic(publicEncoded);
        return publicKey;
    }

    public static PrivateKey decodePrivate(String encodedPrivate)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        PKCS8EncodedKeySpec privateEncoded = new PKCS8EncodedKeySpec(utils.decode(encodedPrivate));
        KeyFactory factory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = factory.generatePrivate(privateEncoded);
        return privateKey;
    }

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
