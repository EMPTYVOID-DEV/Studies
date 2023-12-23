package lib;

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

    public static void main(String[] args) throws Exception {
        String msg = fileReader.readFile("/home/empty/Documents/studies/crypto/legacy/hash.txt");
        Pair<PrivateKey, PublicKey> keys = KeyGen(2048);
        byte[] sign = sign(keys.x, msg.getBytes());
        byte[] builtSign = builtInSing(keys.x, msg.getBytes());
        utils.print("sign " + utils.encode(sign) + "\n");
        utils.print("built in sign " + utils.encode(builtSign) + "\n" + builtInVerify(keys.y, builtSign));
    }

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
