package app.lib;

public class classic {

    static final String alpt = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static String ceasar(String msg, int key) {
        String res = "";
        msg = msg.toUpperCase();
        for (int i = 0; i < msg.length(); i++) {
            int nextPos = alpt.indexOf(msg.charAt(i)) + key;
            res = res + alpt.charAt(nextPos % 26);
        }
        return res;
    }

    public static String affine(String msg, int k1, int k2) throws Exception {

        if (math.elucdian(k2, 26) != 1) {
            throw new Exception("k2 is irreversible");
        }
        String res = "";
        msg = msg.toUpperCase();
        for (int i = 0; i < msg.length(); i++) {
            int nextPos = alpt.indexOf(msg.charAt(i)) * k2 + k1;
            res = res + alpt.charAt(nextPos % 26);
        }
        return res;
    }

    public static String veignar(String msg, String key) {
        String res = "";
        key = key.toUpperCase();
        msg = msg.toUpperCase();
        for (int i = 0; i < msg.length(); i++) {
            int nextPos = alpt.indexOf(msg.charAt(i))
                    + alpt.indexOf(key.charAt(i % key.length()));
            res = res + alpt.charAt(nextPos % 26);
        }
        return res;
    }

    public static String decryptCeasar(String cipher, int key) {
        String res = "";
        res = res.toUpperCase();
        for (int i = 0; i < cipher.length(); i++) {
            int pos = alpt.indexOf(cipher.charAt(i)) - key;
            while (pos < 0) {
                pos += 26;
            }
            res = res + alpt.charAt(pos);
        }
        return res;
    }

    public static String decryptAffine(String cipher, int k1, int k2) throws Exception {
        if (math.elucdian(k2, 26) != 1) {
            throw new Exception("k2 is irreversible");
        }
        int invK2 = math.extendedEuclideean(k2, 26)[1];
        cipher = cipher.toUpperCase();
        String res = "";
        for (int i = 0; i < cipher.length(); i++) {
            int pos = (alpt.indexOf(cipher.charAt(i)) - k1) * invK2;
            while (pos < 0) {
                pos += 26;
            }
            res = res + alpt.charAt(pos % 26);
        }
        return res;
    }

    public static String decryptVeignar(String cipher, String key) {
        String res = "";
        key = key.toUpperCase();
        cipher = cipher.toUpperCase();
        for (int i = 0; i < cipher.length(); i++) {
            int pos = alpt.indexOf(cipher.charAt(i))
                    - alpt.indexOf(key.charAt(i % key.length()));
            while (pos < 0) {
                pos += 26;
            }
            res = res + alpt.charAt(pos);
        }
        return res;
    }

}
