package app.pages.hmac;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import com.github.fxrouter.FXRouter;

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
    TextField input;

    @FXML
    TextArea output;

    @FXML
    Button hash;

    public void goHome(ActionEvent event) throws IOException {
        FXRouter.goTo("home");
    }

    public void toDigest() throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException {
        String rawInput = input.getText();
        String rawKey = key.getText();
        if (rawInput.isEmpty()) {
            error.alert(1);
            return;
        }
        Mac mac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secrectKey = new SecretKeySpec(rawKey.getBytes("UTF-8"), "HmacSHA256");
        mac.init(secrectKey);
        byte[] digest = mac.doFinal(rawInput.getBytes());
        output.setText(utils.encode(digest));
    }
}