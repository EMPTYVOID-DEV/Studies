package app.pages.keyGen;

import java.io.IOException;
import java.security.PrivateKey;
import java.security.PublicKey;

import com.github.fxrouter.FXRouter;

import app.lib.asymmetric;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import app.lib.Pair;

public class page {
    @FXML
    TextArea publicKey, privateKey;

    @FXML
    Button generate;

    public void goHome(ActionEvent event) throws IOException {
        FXRouter.goTo("home");
    }

    public void generate() throws Exception {
        Pair<PrivateKey, PublicKey> keys = asymmetric.KeyGen(2048);
        Pair<String, String> encodedKeys = asymmetric.encodeKeys(keys);
        publicKey.setText(encodedKeys.x);
        privateKey.setText(encodedKeys.y);
    }
}
