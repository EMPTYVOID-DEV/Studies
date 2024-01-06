package app.lib;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.security.SecureRandom;

public class symmetric {

    public static IvParameterSpec genIV(int size) throws Exception {
        SecureRandom random = new SecureRandom();
        byte[] iv = new byte[size / 8]; // 16 bytes for AES
        random.nextBytes(iv);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        return ivParameterSpec;
    }

    public static SecretKey keyGenStr(String keyStr) {
        byte[] decodedKey = keyStr.getBytes();
        return new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
    }

    public static SecretKey keyGen(int size) throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(size);
        return keyGen.generateKey();
    }

    public static byte[] encrypt(byte[] msg, String mode, String paddString, SecretKey key, IvParameterSpec iv)
            throws Exception {
        Cipher cipher = Cipher.getInstance("AES/" + mode + "/" + paddString);
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);
        return cipher.doFinal(msg);
    }

    public static byte[] decrypt(byte[] msg, String mode, String paddString, SecretKey key, IvParameterSpec iv)
            throws Exception {
        Cipher cipher = Cipher.getInstance("AES/" + mode + "/" + paddString);
        cipher.init(Cipher.DECRYPT_MODE, key, iv);
        return cipher.doFinal(msg);
    }

}
