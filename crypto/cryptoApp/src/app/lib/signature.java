package app.lib;

import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.util.Arrays;

import javax.crypto.Cipher;

public class signature {

    public static Pair<PrivateKey, PublicKey> KeyGen(int size) throws Exception {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(size);
        KeyPair key = keyGen.generateKeyPair();
        return new Pair<PrivateKey, PublicKey>(key.getPrivate(), key.getPublic());
    }

    public static byte[] sign(PrivateKey key, byte[] msg) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        byte[] msgHash = hash.toDigest(new String(msg));
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(msgHash);
    }

    public static boolean verify(PublicKey key, byte[] sign, byte[] msg) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] signHash = cipher.doFinal(sign);
        byte[] msgHash = hash.toDigest(new String(msg));
        return Arrays.equals(signHash, msgHash);
    }

    public static byte[] builtInSing(PrivateKey key, byte[] msg)
            throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Signature signGen = Signature.getInstance("SHA256withRSA");
        signGen.initSign(key);
        return signGen.sign();
    }

    public static boolean builtInVerify(PublicKey key, byte[] sign)
            throws InvalidKeyException, NoSuchAlgorithmException, SignatureException {
        Signature signGen = Signature.getInstance("SHA256withRSA");
        signGen.initVerify(key);
        return signGen.verify(sign);
    }
}
