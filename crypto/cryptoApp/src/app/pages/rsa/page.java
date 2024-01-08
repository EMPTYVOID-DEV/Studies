package app.pages.rsa;

import app.lib.error;
import app.lib.utils;
import app.lib.asymmetric;
import java.io.IOException;
import java.security.PrivateKey;
import java.security.PublicKey;
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

    public void encrypt() throws Exception {
        String rawInput = input.getText();
        String rawKey = key.getText();
        PublicKey key = null;
        try {
            key = asymmetric.decodePublic(rawKey);

        } catch (Exception e) {
            error.alert(8);
            return;
        }
        if (rawInput.isEmpty()) {
            error.alert(1);
            return;
        }
        byte[] cipher = asymmetric.encrypt(key, rawInput.getBytes());
        output.setText(utils.encode(cipher));
    }

    public void decrypt() throws Exception {
        String rawInput = input.getText();
        String rawKey = key.getText();
        PrivateKey key = null;
        try {
            key = asymmetric.decodePrivate(rawKey);
        } catch (Exception e) {
            error.alert(9);
            return;
        }
        if (rawInput.isEmpty()) {
            error.alert(1);
            return;
        }
        byte[] msg = asymmetric.decrypt(key, utils.decode(rawInput));
        output.setText(new String(msg));
    }
}
