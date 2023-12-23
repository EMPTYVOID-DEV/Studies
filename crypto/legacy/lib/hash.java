
package lib;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class hash {
    public static void main(String[] args) throws NoSuchAlgorithmException {

        String msg = fileReader.readFile("/home/empty/Documents/studies/crypto/legacy/hash.txt");
        String msg2 = fileReader.readFile("/home/empty/Documents/studies/crypto/legacy/hash2.txt");
        byte[] hash1 = toDigest(msg);
        byte[] hash2 = toDigest(msg2);
        utils.print(utils.encode(hash2));
        utils.print(utils.encode(hash1));
        utils.print("" + utils.avalenchEffect(hash1, hash2));
    }

    public static byte[] toDigest(String msg) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(msg.getBytes());
        return hash;
    }

}
