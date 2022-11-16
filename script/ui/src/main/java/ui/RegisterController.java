package ui;

import core.main.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class RegisterController {

    private RemoteModelAccess remoteModelAccess;

    private WindowManager windowManager;

    @FXML
    private AnchorPane loginAnchor;

    @FXML
    private TextField usernameField, firstNameField, lastNameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button registerButton;

    @FXML
    private void initialize() {
        if (!(Globals.windowHeight == 0)) {
            loginAnchor.setPrefSize(Globals.windowWidth, Globals.windowHeight);
        }
        remoteModelAccess = new RemoteModelAccess();
        windowManager = new WindowManager();
        createWindowSizeListener();
    }

    @FXML
    private void swapToLoginScreen(ActionEvent ae) throws IOException {
        windowManager.switchScreen(ae, "Login.fxml");
    }

    @FXML
    private void handleEnter(KeyEvent ke) {
        if (ke.getCode().equals(KeyCode.ENTER)) {
            if (!registerButton.isDisabled()) {
                registerButton.fire();
            }
        }
    }

    @FXML
    private void checkInput() {
        registerButton.setDisable(usernameField.getLength() == 0 || passwordField.getLength() == 0
                || firstNameField.getLength() == 0 || lastNameField.getLength() == 0);
    }

    @FXML
    private void handleRegisterNewUser(ActionEvent ae) throws IOException {
        try {
            remoteModelAccess.register(firstNameField.getText(), lastNameField.getText(), usernameField.getText(),
                    passwordField.getText());
            User user = remoteModelAccess.getUser(usernameField.getText(), passwordField.getText());
            Globals.user = user;
            windowManager.switchScreen(ae, "Script.fxml");
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
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