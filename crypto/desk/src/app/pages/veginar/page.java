package app.pages.veginar;

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
        if (!error.verify("VEGINAR", rawInput, rawKey, null))
            return;
        String cipher = classic.veignar(rawInput, rawKey);
        output.setText(cipher);
    }

    public void decrypt() {
        String rawInput = input.getText();
        String rawKey = key.getText();
        if (!error.verify("VEGINAR", rawInput, rawKey, null))
            return;
        String message = classic.decryptVeignar(rawInput, rawKey);
        output.setText(message);
    }
}
