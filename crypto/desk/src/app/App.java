package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Cryptography");
        Parent root = FXMLLoader.load(getClass().getResource("App.fxml"));
        String css = getClass().getResource("App.css").toExternalForm();
        Scene main = new Scene(root, 780, 680);
        main.getStylesheets().add(css);
        primaryStage.setScene(main);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}