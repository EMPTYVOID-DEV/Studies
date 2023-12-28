package app;

import com.github.fxrouter.FXRouter;

import app.lib.globalStore;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setWidth(800);
        primaryStage.setWidth(1280);
        // init the global store
        globalStore.createInstance();
        FXRouter.bind(this, primaryStage, "Crypto App");
        FXRouter.when("home", "./pages/home/page.fxml");
        FXRouter.when("caesar", "./pages/caesar/page.fxml");
        FXRouter.when("aes", "./pages/aes/page.fxml");
        FXRouter.when("rsa", "./pages/rsa/page.fxml");
        FXRouter.when("veginar", "./pages/veginar/page.fxml");
        FXRouter.when("sha256", "./pages/sha256/page.fxml");
        FXRouter.when("hmac", "./pages/hmac/page.fxml");
        FXRouter.when("affine", "./pages/affine/page.fxml");
        FXRouter.when("key generation", "./pages/keyGen/page.fxml");
        FXRouter.when("digital signature", "./pages/digSign/page.fxml");
        FXRouter.goTo("home");
    }

    public static void main(String[] args) {
        launch(args);
    }
}