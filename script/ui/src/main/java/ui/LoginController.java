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
    private MFXPasswordField passwordField;

    /**
     * Initializes the controller every time the sceen is changed to Login.fxml.
     *
     * @see LoginController#createWindowSizeListener()
     */
    @FXML
    private void initialize() {
        loginAnchor.setPrefSize(Globals.windowWidth, Globals.windowHeight);
        remoteModelAccess = new RemoteModelAccess();
        windowManager = new WindowManager();

        // Creates window size listeners
        loginAnchor.widthProperty().addListener((obs, oldVal, newVal) -> {
            Globals.windowWidth = (double) newVal;
        });
        loginAnchor.heightProperty().addListener((obs, oldVal, newVal) -> {
            Globals.windowHeight = (double) newVal;
        });
    }

    /**
     * Changes the sceen to Script.fxml when the user logs in.
     *
     * @param event an action event that is passed to
     *              {@link WindowManager#switchScreen(ActionEvent, String) } method
     *              that tells the WindowManager class which screen to change to
     * @throws IOException when attempting to access a file that does not exist at
     *                     the specified location
     */
    @FXML
    private void handleLoginButton(ActionEvent event) throws IOException {
        try {
            Globals.user = remoteModelAccess.getUser(usernameField.getText(), passwordField.getText());
            windowManager.switchScreen(event, "Script.fxml");
        } catch (Exception e) {
            System.out.println(e);
            TranslateTransition transition = new TranslateTransition();
            transition.setDuration(Duration.seconds(2));
            transition.setNode(invalidField);
            transition.setAutoReverse(false);
            invalidField.setVisible(true);
            transition.setOnFinished(ev -> invalidField.setVisible(false));
            transition.play();
        }
    }

    /**
     * Changes the scene to Register.fxml when the user presses on register a new
     * user button.
     *
     * @param event an action event that is passed to
     *              {@link WindowManager#switchScreen(ActionEvent, String) } method
     *              that tells the WindowManager class which screen to change to
     * @throws IOException when attempting to access a file that does not exist at
     *                     the specified location
     */
    @FXML
    private void swapToRegisterScreen(ActionEvent event) throws IOException {
        windowManager.switchScreen(event, "Register.fxml");
    }

    /**
     * Disables the login button when the login info are invalid.
     */
    @FXML
    private void checkInput() {
        loginButton.setDisable(passwordField.getLength() == 0 || usernameField.getLength() == 0);
    }

    /**
     * Returns the logic button component. This function is a helper function for
     * test classes.
     *
     * @return a Button fxml component
     */
    protected Button getLoginButton() {
        return loginButton;
    }

    /**
     * Returns the create new user button component. This function is a helper
     * function for test classes.
     *
     * @return a Button fxml component
     */
    protected Button getCreateNewUserButton() {
        return swapRegisterButton;
    }

    /**
     * Returns the invalid message field component. This function is a helper
     * function for test classes.
     *
     * @return a TextField fxml component
     */
    protected TextField getInvalidField() {
        return invalidField;
    }

    /**
     * Returns the password field component. This function is a helper function for
     * test classes.
     *
     * @return a MFXPasswordField fxml component
     */
    protected MFXPasswordField getPasswordField() {
        return passwordField;
    }
}