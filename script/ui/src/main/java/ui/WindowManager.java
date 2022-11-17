package ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class WindowManager {
    public void switchScreen(ActionEvent ae, String file) throws IOException {
        Stage stage = (Stage) ((Node) ae.getSource()).getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource(file))));
        stage.show();
    }
}
