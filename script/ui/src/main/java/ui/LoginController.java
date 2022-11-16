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

public class LoginController {

    private RemoteModelAccess remoteModelAccess;

    private WindowManager windowManager;

    @FXML
    private AnchorPane loginAnchor;

    @FXML
    private Button loginButton, swapRegisterButton;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

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
    private void handleLoginButton(ActionEvent ae) throws IOException {
        User user = remoteModelAccess.getUser(usernameField.getText(), passwordField.getText());
        Globals.user = user;
        windowManager.switchScreen(ae, "Script.fxml");
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

    protected Button getLoginButton() {
        return loginButton;
    }

    protected Button getCreateNewUserButton() {
        return createNewUserButton;
    }

}