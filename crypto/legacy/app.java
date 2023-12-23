import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;
import lib.Pair;
import lib.asymmetric;
import lib.utils;

public class app {

  public static void main(String[] args) throws Exception {
    // long startTime = System.currentTimeMillis();
    // String msg
    // =fileReader.readFile("/home/empty/Documents/studies/crypto/app.txt");
    // print("number of bytes " + msg.getBytes().length);
    // int paddingSize = (16 - msg.getBytes().length % 16);
    // print("number of padding " + (16 - msg.getBytes().length % 16));
    // for (int i = 0; i < paddingSize; i++) {
    // msg += "0";
    // }
    // SecretKey key = symmetric.keyGen(128);
    // IvParameterSpec iv=symmetric.genIV(128);
    // byte[] cipher = symmetric.encrypt(msg.getBytes(), "ECB", "NoPadding", key,
    // null);
    // fileReader.writeFile(encode(cipher),
    // "/home/empty/Documents/studies/crypto/app.txt");
    // byte[] msg2 = symmetric.decrypt(cipher, "ECB", "NoPadding", key, null);
    // String text = new String(msg2);
    // long endTime = System.currentTimeMillis();
    // print(text);
    // print("Execution time " + (endTime - startTime) + "ms");

    // rsa encryption

    Pair<PrivateKey, PublicKey> KeyPair = asymmetric.KeyGen(2048);
    String msg = "We live in a twilight world and there is no friends at dusk";
    byte[] cipher = asymmetric.encrypt(KeyPair.y, msg.getBytes());
    byte[] msgVerification = asymmetric.decrypt(KeyPair.x, cipher);
    utils.print(utils.encode(cipher));
    utils.print(new String(msgVerification));
  }

}
