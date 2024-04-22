package lib;

import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;

public class signature {
    String publicKey;
    String signature;

    public signature(String data) throws Exception {
        Pair<PrivateKey, PublicKey> keys = KeyGen();
        byte[] signByte = sign(keys.x, data.getBytes());
        signature = utils.encodeBase64(signByte);
        publicKey = utils.encodeBase64(keys.y.getEncoded());
    }

    public static Pair<PrivateKey, PublicKey> KeyGen() throws Exception {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048);
        KeyPair key = keyGen.generateKeyPair();
        return new Pair<PrivateKey, PublicKey>(key.getPrivate(), key.getPublic());
    }

    public static byte[] sign(PrivateKey key, byte[] msg)
            throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Signature signGen = Signature.getInstance("SHA256withRSA");
        signGen.initSign(key);
        return signGen.sign();
    }

    @Override
    public String toString() {
        return this.publicKey + "_" + this.signature;
    }
}
