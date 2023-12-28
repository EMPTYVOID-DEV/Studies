package app.pages.affine;

import app.lib.error;
import app.lib.classic;
import java.io.IOException;
import com.github.fxrouter.FXRouter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class page {

    @FXML
    TextField key1;

    @FXML
    TextField key2;

    @FXML
    TextField input;

    @FXML
    TextArea output;

    @FXML
    Button encrypt;

    @FXML
    Button decrypt;

    public void goHome(ActionEvent event) throws IOException {
        FXRouter.goTo("home");
    }

    public void encrypt() {
        String rawInput = input.getText();
        String rawKey1 = key1.getText();
        String rawKey2 = key2.getText();
        if (!error.verify("AFFINE", rawInput, rawKey1, rawKey2))
            return;
        try {
            String cipher = classic.affine(rawInput, Integer.parseInt(rawKey1), Integer.parseInt(rawKey2));
            output.setText(cipher);
        } catch (Exception e) {
            error.alert(7);
        }
    }

    public void decrypt() {
        String rawInput = input.getText();
        String rawKey1 = key1.getText();
        String rawKey2 = key2.getText();
        if (!error.verify("AFFINE", rawInput, rawKey1, rawKey2))
            return;
        try {
            String message = classic.decryptAffine(rawInput, Integer.parseInt(rawKey1), Integer.parseInt(rawKey2));
            output.setText(message);
        } catch (Exception e) {
            System.out.println(e);
            error.alert(7);
        }
    }
}
