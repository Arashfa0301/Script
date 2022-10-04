package ui;

import java.io.IOException;

import core.main.User;
import data.ScriptModule;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;

public class LoginController {

    private ScriptModule scriptModule;

    @FXML
    private AnchorPane loginAnchor;

    @FXML
    private Button loginButton;

    @FXML
    private TextField loginField;

    @FXML
    private void initialize() {
        if (!(Globals.windowHeight == 0))
            loginAnchor.setPrefSize(Globals.windowWidth, Globals.windowHeight);
        scriptModule = new ScriptModule();
        createWindowSizeListener();
    }

    @FXML
    private void handleLoginButton(ActionEvent ae) throws IOException {
        if (!loginField.getText().isBlank()) {
            User user = scriptModule.getUser(loginField.getText());
            if (!(user == null)) {
                Globals.user = user;
                switchScreen(ae, "Script.fxml");
            } else
                newUser(ae);
        }
    }

    @FXML
    private void handleEnter(KeyEvent ke) {
        if (ke.getCode().equals(KeyCode.ENTER)) {
            loginButton.fire();
        }
    }

    private void newUser(ActionEvent ae) throws IOException {
        User user = new User(loginField.getText());
        scriptModule.write(user);
        Globals.user = user;
        switchScreen(ae, "Script.fxml");
    }

    private void switchScreen(ActionEvent ae, String file) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(file));
        Stage stage = (Stage) ((Node) ae.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void checkIfBlankUsername() {
        loginButton.setDisable(loginField.getText().isBlank() ? true : false);
    }

    private void createWindowSizeListener() {
        loginAnchor.widthProperty().addListener((obs, oldVal, newVal) -> {
            Globals.windowWidth = (double) newVal;
        });
        loginAnchor.heightProperty().addListener((obs, oldVal, newVal) -> {
            Globals.windowHeight = (double) newVal;
        });
    }
}