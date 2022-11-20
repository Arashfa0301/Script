package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App.
 */
public class ScriptApp extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        // add icon
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("Login.fxml"));
        Parent parent = fxmlLoader.load();
        stage.setScene(new Scene(parent));
        Image icon = new Image(getClass().getResourceAsStream("/images/icon.png"));
        stage.getIcons().add(icon);
        stage.show();
        stage.setTitle("Script");
    }

    public static void main(String[] args) {
        launch();
    }
}