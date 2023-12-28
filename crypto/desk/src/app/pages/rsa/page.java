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

    public void encrypt() {
        String rawInput = input.getText();
        String rawKey = key.getText();
        try {
            PublicKey key = asymmetric.decodePublic(rawKey);
            byte[] cipher = asymmetric.encrypt(key, rawInput.getBytes());
            output.setText(utils.encode(cipher));
        } catch (Exception e) {
            error.alert(8);
        }
    }

    public void decrypt() {
        String rawInput = input.getText();
        String rawKey = key.getText();
        try {
            PrivateKey key = asymmetric.decodePrivate(rawKey);
            byte[] msg = asymmetric.decrypt(key, utils.decode(rawInput));
            output.setText(new String(msg));
        } catch (Exception e) {
            error.alert(8);
        }
    }
}

// MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAqVkoWzBo/rM2wp0GBJkGYJCyCgGDrqgtwCJcijoYT/dVreVQZhy5HqKq8WLuF8nDD/4zUr+a+tDSAXl4qvl0f4/fGOWl7dXAa36jUWRmX3ga3A5mWiVQvcFxUIOGg2b7oMxgzxgOhb9Pu5ub7TI1uNVo+s7LsFYwj3AZY8NS+4rPip98naTNCl62xSdHydTNZG5HMFISWGXwWLL9aSFo2CsbyRstVIQohBKuq8fsWK1ZbnIzgW5wiBIjPWkRi9qFEpq9zTjuw/LQoUjt2ECBhNyEseKLAva09qQXbTBCb/EfcFQOr+jBTIuGyzXXtVB8LKvijz1RZTfMJiwH/hQOXwIDAQAB

// MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCpWShbMGj+szbCnQYEmQZgkLIKAYOuqC3AIlyKOhhP91Wt5VBmHLkeoqrxYu4XycMP/jNSv5r60NIBeXiq+XR/j98Y5aXt1cBrfqNRZGZfeBrcDmZaJVC9wXFQg4aDZvugzGDPGA6Fv0+7m5vtMjW41Wj6zsuwVjCPcBljw1L7is+Kn3ydpM0KXrbFJ0fJ1M1kbkcwUhJYZfBYsv1pIWjYKxvJGy1UhCiEEq6rx+xYrVlucjOBbnCIEiM9aRGL2oUSmr3NOO7D8tChSO3YQIGE3ISx4osC9rT2pBdtMEJv8R9wVA6v6MFMi4bLNde1UHwsq+KPPVFlN8wmLAf+FA5fAgMBAAECggEADfLQBiLIoyHzwrfYRQytn8qLP5O8WuhzTD4ITUJXuk5u+jHHG4IGJWXUmqlBXyg5LtoLNCegBkSo54Iv1DVTySX6hDx2HE7c2H3Hye5xGC/2VmXBPG+SZO7H/I+4xp3239m1p5kdqRqG4LcCctkN37mEOhRKZq3Sth3PmY3/CcTUrUHQ3lsdByMrasBq/Zsy62j3Aj28UtWs9ZeB+P+Mx/+aN/GVo3XVcVD2qh+rYorF5M0enZ43NKmQCRnJ1pj99QaBSeZH6MPDXBXnMJjh1vSV86eYh9J2iIj6N94s8oEcpHSAYKNFwrBACRmd3zLLHyqY4h0KYyGP3S5OZsr7UQKBgQC6frSpjs0uUNSkZ9CGymK0ynNnFpeWd0+cDCGTBaCpboUOertDmH9bnrdOCCjrFmggkkn4ZvHmr2dkQ60dXrsmrCLjtTGkmDt3ZUF8stgodQy1aEm5FbyB7C1UflLgTjJwJw4nq3/D+69mNZ8RqJcss1mf9yXP3lM5OBEgrZmOOwKBgQDodoFYaKqAK9L8LGd1r5KzFUpW/DHfihwf+gbD/3V4lMmD/CYrUjD1JxGVzGQIE/nj/U57qUqDcyQe7qUlMgPuSWwTXTHKpJIRD5RUGSsmIpJmFmZBl7hthBWxuaKh7v9RZ8KvednXkUMx4LoC/+M4DEHZRudkrzTdWiGQGFtKLQKBgEErB4x8lxxJKJUupcKENEvCtuLeFSiCFrKL1quees7aDOrLXma3gkGMy7cbyNilYsuG8ww3RO58ep+hunuZ38IawrJZXbiLbbRCEMk3Yat5g3Bcue5QNygbVJfC8Mjq6lsgeqTIHARrDZakxCoShIkdPwNUBMWHhgNnoSyPHSqDAoGBAJWfIOWMiKFyk5wn4HeJCD0n6O9P57HfrPaP8SbaF/wCmCnRc6fSgRxphxIJuiDQ6rhp48gIUKOGyqr2I2lrZchLjbpw84lgn3tHscrnYDRtcBkxNaPmSZiDuYro1tqvf7oftNlI7oOnRb6mzFL97Hu4t/Fnh6vwcXA/gPbEFo4JAoGAfZRlr27+B4EWUwpyHonUkIi7+LKWTH58gZYTMsjCsTRohO4pd1ks4cTPFW9LcRDM5OHr/tn/jgotWzh6u668XqI6Shg1NtGST3hk4cseGD5V0E01EDeUlfKvgA0CPMvITftYOlYXVj+8982fkHjJh8ouFspSZ00zF7MDbAXy3A0=