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

// MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAkkB/Usvt6qasTaD36sFhIt6zx0T7wlJd42Q33FnGS0kexCrQ1/A3mxDiKnaUfLlPrfjLGq6v/eGXHoU5Ufad9qDNqaijm5fyaX2SdV2iu03fIWv5VFi1GScgXPC8rT8GK9Muh9Yl/nLrMYr0VlSG4mdRug/z7eE/XisoK4Tq9uu9W0c5M2HmESJwgdeQ6KfIcLhNONNM45dTygavkVlpDfzH1grfSvCLfIdCc9rNdBya21EONtRGcQuCn/xawNnwePFncX6UXPVym5p/VpC2GHB1QXSD4EnGHxFhbDqvQtKB2rVpR5ZDGJ+wrkh22nldTIL/ot/+aaErRZX2JAYSTwIDAQAB

// MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCSQH9Sy+3qpqxNoPfqwWEi3rPHRPvCUl3jZDfcWcZLSR7EKtDX8DebEOIqdpR8uU+t+Msarq/94ZcehTlR9p32oM2pqKObl/JpfZJ1XaK7Td8ha/lUWLUZJyBc8LytPwYr0y6H1iX+cusxivRWVIbiZ1G6D/Pt4T9eKygrhOr2671bRzkzYeYRInCB15Dop8hwuE0400zjl1PKBq+RWWkN/MfWCt9K8It8h0Jz2s10HJrbUQ421EZxC4Kf/FrA2fB48WdxfpRc9XKbmn9WkLYYcHVBdIPgScYfEWFsOq9C0oHatWlHlkMYn7CuSHbaeV1Mgv+i3/5poStFlfYkBhJPAgMBAAECggEACp8LgCBWSXXMSqD3kEF2A/nHdZ/RZtXjgqmbEvIkIACR7u4rF5uW9jh+LFZLR1mP61MHsQrhth7lgi5rsMOnyir4dKtOInUhAj8/3S0tLcwkecFlLJZ6pHZ+d7F2CmDq2lDubqgDP1zqLsFoKELgPt4BBfG1pd93YifhNXPkOoVdI/WKWnmxywlLQUFPfcjTdlitKqEaciGBewPS0X2E5PIax8ILMe8KM2AQsF2RArfaNwdv1jho5PE4B3V/DjuLu1Uj3+fFw6a+rxf8+8sQfAle28JhkAatqAY+J42mCEmfIflBC1IGPzB3QDImkIfH5cZQRofi6wCcoODXYq/KlQKBgQDOT2vyyF5GWpKu7wG2VttMSLxTcp7WZV2C3PgPE/zozX+ZOP9RTj79SmvK6mKVvWXgq0Bl8xIkbuVMzty7XJUajOtUSLXGA0F3Ws7DvWIvYs3HkUySyAs5ZYzuvwFMu8VZFO3X42ehTGYKnSGrqH6pDn6Xwzm2vJ9hGZTBOYR+XQKBgQC1egeh9oIykQpSSQkuLTMf4AJ00qRj0PrhD6A82+ao1hI20sm4v8RYyiY4jlLXm48CUp16stDIn0FmsymnNJTD6Ok09kwXZcA6Fc+FSPuFYuRHDiLeU/ZG3wMwEn00tE4VrEpgmjUp3ibacm5RD85OgDfFEXmBQs15hMFR/IrQmwKBgA8t/M1SPM2EYIKKh1+5KE2GlIS3TESOFMFKLN4/JD6k0Byf+vkqfqv4S9IUSMintNVBBG374nvXxPtPxSHr//SCJdZ/uxAksS5s+gWg65Z8+JMUd8PdACZq+GGgoCHbNvwE9DMMQlW131Qsl4ufzCH4NQxJiveNDVUBotZMuxjpAoGAdQYU5+9DNAJtinRIdDt3bUWRr+2pTiiTrw/Zf4U6L4OjyTCSHg2jOrJYhT3Z0+kfBhtqK5Ylpklz7hr4cDcq+041HA77MFdP5uTrpgJTF0eW9u75sy6ncDodHyG9FYBznew1Jigqwi3cFh+WXbV4jwyFahIDrUP7pjHiS9EzXqMCgYBo3G3eHfLwxdbfJjP8pGNHhX6CHTZarQKFVKeWX6gtcgvASWYF7OZSbWoe7QB3Dhr84mQiW7yexQbExyh08jwOnXwC+FqDG6MNmqtIKeE4OaG6rieUNPY2fCSmlWcbJ8bAbS4y6/4wmNiyzxp9wAf4LAFbXGYipyIsn7c+2GvYhQ==