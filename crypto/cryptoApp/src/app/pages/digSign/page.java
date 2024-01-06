package app.pages.digSign;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SignatureException;

import com.github.fxrouter.FXRouter;

import app.lib.asymmetric;
import app.lib.error;
import app.lib.utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class page {
    @FXML
    TextField key;

    @FXML
    TextField message;

    @FXML
    TextArea signature;

    @FXML
    Button sign;

    @FXML
    Button verify;

    public void goHome(ActionEvent event) throws IOException {
        FXRouter.goTo("home");
    }

    public void sign() throws InvalidKeyException, NoSuchAlgorithmException, SignatureException {
        String rawInput = message.getText();
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
        byte[] cipher = app.lib.signature.builtInSing(key, rawInput.getBytes());
        signature.setText(utils.encode(cipher));
    }

    public void verify() throws InvalidKeyException, NoSuchAlgorithmException, SignatureException {
        String rawInput = message.getText();
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
        boolean valid = app.lib.signature.builtInVerify(key, utils.decode(rawInput));
        if (valid)
            signature.setText("valid signature");
        else
            signature.setText("unvalid signature");
    }

}