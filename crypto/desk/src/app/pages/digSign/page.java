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
            error.alert(8);
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

// MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAs1hOCp+9aKuBX2h4WawyWIcO1Za1aIgb1Z4XCnXno7y6m4AAMcP6HdQFq4D9/E6qw6MIJAIzdYCc/RkYUvouKEPg5qUHUz2bYDJyVHQpzqm58KVoSMBvPehEPYDI7lykcw8pOHts4B1RUIJBbszI/dA12T4teNJV9IROVIirCpOJxYXISSBf0834n7ytOVcQASMV8AiLoIDVynK+p6UXfHnDfoeRVFYbC6Nf4Aa4GhT4j9W4wY6jXUxA72Zg+UWTOcXuuCw6pd0XUsOIdzR4j4uhF1Twz12hgEG9g7g97N1mkZLmU+rlPkKWu4fiNxGi0m36thUUcv2MVdAx2X67BwIDAQAB

// MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCzWE4Kn71oq4FfaHhZrDJYhw7VlrVoiBvVnhcKdeejvLqbgAAxw/od1AWrgP38TqrDowgkAjN1gJz9GRhS+i4oQ+DmpQdTPZtgMnJUdCnOqbnwpWhIwG896EQ9gMjuXKRzDyk4e2zgHVFQgkFuzMj90DXZPi140lX0hE5UiKsKk4nFhchJIF/TzfifvK05VxABIxXwCIuggNXKcr6npRd8ecN+h5FUVhsLo1/gBrgaFPiP1bjBjqNdTEDvZmD5RZM5xe64LDql3RdSw4h3NHiPi6EXVPDPXaGAQb2DuD3s3WaRkuZT6uU+Qpa7h+I3EaLSbfq2FRRy/YxV0DHZfrsHAgMBAAECggEAGy5EcabBEZaAnoGaT9jhrnoVnPhg2Q1bzpXNnye8ahvl2kBdhy1RAGlMTLBZjf4sMkdIqhBtVR8fa9p8TcMd8LtM4Bfd0tdQ75Tlr/gFCA3AIQEom3ISyfqkMQAOM9X1lHyVPjqyhwraco2zNgQyaAlAyAJ86MVUAqF0r6og5hKyKIcuI3TQbvNE1aOvk+7itiLmA8gMuuKw0zpIsVaAv81faA6Pd5TJdWm8IMKLjzwldI7YOszPLe9lOCyDg43CLi0nyWjmJuvxjggEN0gXffQd1BupqWoqYQay/ETybsuJxFCguGqJXXEaNcVbaWFsyO8cz/+kG6TnmxDSGgw+wQKBgQDkIjQAebBqEPsvuTdtTobfHii3gg1zRuH/ZUC+uaPnTvtiRwaK1uF9aeH+rS3k4lD0kr7HJnBZrYBbrTMcxWwVX1UdUwgeWIDvb3KhF1yTuqrnjTdb40itrq/xnRUhK3fGCf0IwIPKUfQ1hYneaK33cbK+rbVrOfv+jj92cW31RwKBgQDJQHewklDKCqMo9mJEz6yX55a1u/vutsG64jjYQqrHWNrxDN/srHyKS9U2MeONO/lz+KYVw1m5IIenq35rIeQXcSvPIBMs6wruA103pxMdi8ew4vVrPmHx0PLs58KTv2Z2j3zjv13VCZlgyfV6njwgvkfZ2AkpK5PXKKhmsNrsQQKBgQDLddmtBtqYbhan5GcTfHJ68PndWAbI5dHPJi2NLCGKvP0kFDY4gxZN9K3Spf8FX/ZVeb7d/GE9CaktmhW4tjYEBabyZUgbUFGFuX6hPyWYt2WO53oU4GrEQ8S1FqRulcbXf7cFJDUlrtu/FB72laN5yRRiTKg9PlSJHbJp3xXzJwKBgFfIQOzJxWd7YFO7tH1jQFs57ujySB+Fo9cKWdUqrycowMcn4HxxeBkynpt3A1RVdqd5K6iOfxJa+BDQouHD9KOs/3jm+rY7B5dt7DkcLSBUGhQzyuyuVzaM94kSRjL/54eLauREUn9cIoSvUt3bK0r09I42xzgoA+Jzn5C7T6KBAoGAVw4mEYWdqjwLmtFZzqewHOBjCM2TH+1JWWOS6rlX3pMNO/j4V545PwckcW/o2KdWOuLVbUxTWJwqyXL3Gl/TkxhrnL3AoFpRPK8kjac1Np7f1CLHPhPXGA3b6OWD+j6iAEhLd0TWiVbxVOxs78VwjsCR4hjf8Svzac3CRxD2nUc=