package app.pages.aes;

import app.lib.error;
import app.lib.globalStore;
import app.lib.symmetric;
import app.lib.utils;
import java.io.IOException;
import com.github.fxrouter.FXRouter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class page {

    @FXML
    ComboBox<String> mode;

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
        byte[] cipher = null;
        String rawMode = mode.getValue() == null ? "ECB" : mode.getValue().toString();
        if (!error.verify("AES", rawInput, rawKey, null))
            return;

        if (rawMode.equals("ECB")) {
            cipher = symmetric.encrypt(rawInput.getBytes(), "ECB", "PKCS5Padding",
                    symmetric.keyGenStr(rawKey),
                    null);
        } else {
            cipher = symmetric.encrypt(rawInput.getBytes(), "CBC", "PKCS5Padding",
                    symmetric.keyGenStr(rawKey),
                    globalStore.instance.getIv());
        }
        output.setText(utils.encode(cipher));
    }

    public void decrypt() throws Exception {
        String rawInput = input.getText();
        String rawKey = key.getText();
        byte[] msg = null;
        String rawMode = mode.getValue() == null ? "ECB" : mode.getValue().toString();
        if (!error.verify("AES", rawInput, rawKey, null))
            return;

        if (rawMode.equals("ECB")) {
            msg = symmetric.decrypt(utils.decode(rawInput), "ECB", "PKCS5Padding",
                    symmetric.keyGenStr(rawKey),
                    null);
        } else {
            msg = symmetric.decrypt(utils.decode(rawInput), "CBC", "PKCS5Padding",
                    symmetric.keyGenStr(rawKey),
                    globalStore.instance.getIv());
        }
        output.setText(new String(msg));
    }
}
