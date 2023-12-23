package lib;

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
        if (math.elucdian(k1, 26) != 1) {
            throw new Exception("k1 is irreversible");
        }
        String res = "";
        msg = msg.toUpperCase();
        for (int i = 0; i < msg.length(); i++) {
            int nextPos = alpt.indexOf(msg.charAt(i)) * k1 + k2;
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
            int pos = (alpt.indexOf(cipher.charAt(i)) - key);
            while (pos < 0) {
                pos += 26;
            }
            res = res + alpt.charAt(pos);
        }
        return res;
    }

    public static String decryptAffine(String cipher, int k1, int k2) {
        int invK1 = math.extendedEuclideean(k1, 26)[1];
        cipher = cipher.toUpperCase();
        String res = "";
        for (int i = 0; i < cipher.length(); i++) {
            int pos = (alpt.indexOf(cipher.charAt(i)) - k2) * invK1;
            while (pos < 0) {
                pos += 26;
            }
            res = res + alpt.charAt(pos);
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

    public static Pair<matrix, matrix> encryptHill(String msg) {
        matrix a = matrix.toMatrix(msg).toModular();
        matrix key = matrix.generateKey(a.getRowDimension()).toModular();
        matrix cipher = new matrix(key.times(a).getArray()).toModular();
        return new Pair<matrix, matrix>(key, cipher);
    }

    public static matrix decryptHill(Pair<matrix, matrix> info) throws Exception {
        double[][] msgMatrix = matrix.modularInverse(info.x).times(info.y).getArray();
        return new matrix(msgMatrix).toModular();
    }

}
