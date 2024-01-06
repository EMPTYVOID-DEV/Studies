package app.lib;

import java.util.HashMap;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class error {

    public static boolean verify(String methodStr, String input, String key1, String key2) {

        if (methodStr.equals("HMAC") && key1.isEmpty())
            return alert(10);
        if (methodStr.equals("AFFINE") && (!isNumber(key1) || !isNumber(key2)))
            return alert(2);
        else if (methodStr.equals("CAESAR") && !isNumber(key1))
            return alert(3);
        else if (methodStr.equals("VEGINAR") && !containsOnlyEnglishAlphabets(key1))
            return alert(4);
        else if (methodStr == "AES" && key1.length() != 16)
            return alert(5);
        else if (input.equals(""))
            return alert(1);
        else if ((methodStr.equals("AFFINE") || methodStr.equals("VEGINAR") || methodStr.equals("CAESAR"))
                && !containsOnlyEnglishAlphabets(input))
            return alert(6);
        return true;
    }

    public static HashMap<Integer, String> init() {
        HashMap<Integer, String> errorsMap = new HashMap<Integer, String>();
        errorsMap.put(1, "You need to enter an input");
        errorsMap.put(2,
                "For affine you need to enter both keys and they must be numbers");
        errorsMap.put(3, "Key must be a number.");
        errorsMap.put(4, "Veginar cipher need key that consist of alphabet letters");
        errorsMap.put(5, "AES needs key with lenght equal to 16");
        errorsMap.put(6, "The input needs to contain only alphabet letters with this method");
        errorsMap.put(7, "The second affine key need to be reversible");
        errorsMap.put(8,
                "Invalid rsa public key.You should probably generate the rsa keys using either the key generate route , openssl or openssh.");
        errorsMap.put(9,
                "Invalid rsa private key.You should probably generate the rsa keys using either the key generate route , openssl or openssh.");
        errorsMap.put(10, "Hmac key can be anything but not empty");
        return errorsMap;
    }

    public static boolean alert(Integer alertId) {
        HashMap<Integer, String> errorsMap = globalStore.instance.getErrorMap();
        String errorMessage = errorsMap.get(alertId);
        Alert alert = new Alert(AlertType.ERROR);
        alert.setContentText(errorMessage);
        alert.show();
        return false;
    }

    public static boolean isNumber(String str) {
        if (str.matches("\\d+")) {
            return true;
        }
        return false;
    }

    public static boolean containsOnlyEnglishAlphabets(String str) {
        return str.matches("[a-zA-Z]+");
    }
}
