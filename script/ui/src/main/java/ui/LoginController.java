package ui;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;

public class LoginController {

    private RemoteModelAccess remoteModelAccess;

    private WindowManager windowManager;

    @FXML
    private AnchorPane loginAnchor;

    @FXML
    private Button loginButton, swapRegisterButton;

    @FXML
    private TextField usernameField, invalidField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void initialize() {
        loginAnchor.setPrefSize(Globals.windowWidth, Globals.windowHeight);
        remoteModelAccess = new RemoteModelAccess();
        windowManager = new WindowManager();
        createWindowSizeListener();
    }

    @FXML
    private void handleLoginButton(ActionEvent ae) throws IOException {
        try {
            Globals.user = remoteModelAccess.getUser(usernameField.getText(), passwordField.getText());
            windowManager.switchScreen(ae, "Script.fxml");
        } catch (Exception e) {
            TranslateTransition transition = new TranslateTransition();
            transition.setDuration(Duration.seconds(2));
            transition.setNode(invalidField);
            transition.setAutoReverse(false);
            invalidField.setVisible(true);
            transition.setOnFinished(event -> invalidField.setVisible(false));
            transition.play();
        }
    }

    @FXML
    private void swapToRegisterScreen(ActionEvent ae) throws IOException {
        windowManager.switchScreen(ae, "Register.fxml");
    }

    @FXML
    private void handleEnter(KeyEvent ke) {
        if (ke.getCode().equals(KeyCode.ENTER)) {
            if (!loginButton.isDisabled()) {
                loginButton.fire();
            }
        }
    }

    @FXML
    private void checkInput() {
        loginButton.setDisable(passwordField.getLength() == 0 || usernameField.getLength() == 0);
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