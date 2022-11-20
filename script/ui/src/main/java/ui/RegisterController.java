package ui;

import io.github.palexdev.materialfx.controls.MFXPasswordField;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;

public class RegisterController {

    private RemoteModelAccess remoteModelAccess;

    private WindowManager windowManager;

    @FXML
    private AnchorPane loginAnchor;

    @FXML
    private TextField usernameField, firstNameField, lastNameField, invalidField;

    @FXML
    private MFXPasswordField passwordField;

    @FXML
    private Button registerButton;

    @FXML
    private void initialize() {
        loginAnchor.setPrefSize(Globals.windowWidth, Globals.windowHeight);
        remoteModelAccess = new RemoteModelAccess();
        windowManager = new WindowManager();
        createWindowSizeListener();
    }

    @FXML
    private void swapToLoginScreen(ActionEvent ae) throws IOException {
        windowManager.switchScreen(ae, "Login.fxml");
    }

    @FXML
    private void checkInput() {
        registerButton.setDisable(usernameField.getText().isBlank() || passwordField.getText().isBlank()
                || firstNameField.getText().isBlank() || lastNameField.getText().isBlank());
    }

    @FXML
    private void handleRegisterNewUser(ActionEvent ae) throws IOException {
        try {
            remoteModelAccess.register(firstNameField.getText(), lastNameField.getText(), usernameField.getText(),
                    passwordField.getText());
            Globals.user = remoteModelAccess.getUser(usernameField.getText(), passwordField.getText());
            windowManager.switchScreen(ae, "Script.fxml");
        } catch (RuntimeException e) {
            System.out.println(e);
            TranslateTransition transition = new TranslateTransition();
            transition.setDuration(Duration.seconds(2));
            transition.setNode(invalidField);
            transition.setAutoReverse(false);
            invalidField.setVisible(true);
            transition.setOnFinished(event -> invalidField.setVisible(false));
            transition.play();
        }
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