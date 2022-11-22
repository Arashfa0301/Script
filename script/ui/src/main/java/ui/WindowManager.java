package ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

/**
 * Switches screen to and from available screens.
 *
 */
public class WindowManager {
    public void switchScreen(ActionEvent event, String file) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource(file))));
        if (Globals.scriptController != null) {
            stage.setOnHidden(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    if (Globals.scriptController != null) {
                        if (Globals.scriptController.getCurrentBoard() != null) {
                            Globals.scriptController.saveBoard(Globals.scriptController.getCurrentBoard());
                        }
                    }
                }
            });
        }
        stage.show();
    }
}
