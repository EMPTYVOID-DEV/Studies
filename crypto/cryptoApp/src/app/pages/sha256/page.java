package app.pages.sha256;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import com.github.fxrouter.FXRouter;

import app.lib.utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class page {

    @FXML
    TextField input;

    @FXML
    TextArea output;

    @FXML
    Button hash;

    public void goHome(ActionEvent event) throws IOException {
        FXRouter.goTo("home");
    }

    public void hash() throws NoSuchAlgorithmException {
        String rawInput = input.getText();
        byte[] digest = app.lib.hash.toDigest(rawInput);
        output.setText(utils.encode(digest));
    }
}
