package app;

import java.util.HashMap;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class Controller {
    @FXML
    Button encryptBtn;
    @FXML
    Button decryptBtn;
    @FXML
    TextField input;
    @FXML
    TextField output;
    @FXML
    TextField key1;
    @FXML
    TextField key2;
    @FXML
    ComboBox method;

    Map<Integer, String> errorsMap;

    @FXML
    public void initialize() {
        errorsMap = new HashMap<Integer, String>();
        errorsMap.put(0, "You need to select a method");
        errorsMap.put(1, "You need to enter an input");
        errorsMap.put(2,
                "For affine you need to enter both keys and they must be numbers also the second key need to have an inverse in modulo 26");
        errorsMap.put(3, "Key1 must be a number.");
        errorsMap.put(4, "Veginar cipher need key that consist of alphabet letters");
        errorsMap.put(5, "AES needs key with lenght equal to 16");
        errorsMap.put(6, "The input needs to contain only alphabet letters with this method");
    }

    public void encrypt(ActionEvent event) {
        verify();
    }

    public void decrypt(ActionEvent event) {

    }

    public void verify() {
        String methodStr = method.getValue() == null ? "" : method.getValue().toString();
        if (methodStr == "")
            alert(0);
        else if (methodStr.equals("AFFINE") && (!isNumber(key1.getText()) || !isNumber(key2.getText())))
            alert(2);
        else if ((methodStr.equals("RSA") || methodStr.equals("CAESAR")) && !isNumber(key1.getText()))
            alert(3);
        else if (methodStr.equals("VEGINAR") && !containsOnlyEnglishAlphabets(key1.getText()))
            alert(4);
        else if (methodStr.substring(0, 2) == "AES" && key1.getText().length() != 16)
            alert(5);
        else if (input.getText().equals(""))
            alert(1);
        else if ((methodStr.equals("AFFINE") || methodStr.equals("VEGINAR") || methodStr.equals("CAESAR"))
                && !containsOnlyEnglishAlphabets(input.getText()))
            alert(6);
    }

    public void alert(Integer alertId) {
        String errorMessage = errorsMap.get(alertId);
        Alert alert = new Alert(AlertType.ERROR);
        alert.setContentText(errorMessage);
        alert.show();
    }

    public boolean isNumber(String str) {
        if (str.matches("\\d+")) {
            return true;
        }
        return false;
    }

    public boolean containsOnlyEnglishAlphabets(String str) {
        return str.matches("[a-zA-Z]+");
    }
}
