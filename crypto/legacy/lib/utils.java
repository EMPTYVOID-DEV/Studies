package lib;

import java.util.Base64;

public class utils {
    public static String encode(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }

    public static void print(String text) {
        System.out.println(text);
    }

    public static int avalenchEffect(byte[] hash1, byte[] hash2) {
        int difference = 0;
        for (int i = 0; i < hash1.length; i++) {
            if (hash1[i] != hash2[i])
                difference++;
        }
        return difference;
    }
}
