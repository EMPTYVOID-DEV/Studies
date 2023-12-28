package app.pages.caesar;

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
    TextField key;

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
        String rawKey = key.getText();
        if (!error.verify("CAESAR", rawInput, rawKey, null))
            return;
        String cipher = classic.ceasar(rawInput, Integer.parseInt(rawKey));
        output.setText(cipher);
    }

    public void decrypt() {
        String rawInput = input.getText();
        String rawKey = key.getText();
        if (!error.verify("CAESAR", rawInput, rawKey, null))
            return;
        String message = classic.decryptCeasar(rawInput, Integer.parseInt(rawKey));
        output.setText(message);
    }
}
