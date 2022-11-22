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

    /**
     * Initializes the controller every time The sceen is changed to Register.fxml.
     *
     * @see RegisterController#createWindowSizeListener()
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
     * Changes the sceen to Register.fxml when the user presses on register new user
     * button.
     *
     * @param event an action event that is passed to
     *              {@link WindowManager#switchScreen(ActionEvent, String) } method
     *              that tells the WindowManager class which screen to change to
     * @throws IOException when attempting to access a file that does not exist at
     *                     the specified location
     */
    @FXML
    private void swapToLoginScreen(ActionEvent event) throws IOException {
        windowManager.switchScreen(event, "Login.fxml");
    }

    @FXML
    private void checkInput() {
        registerButton.setDisable(usernameField.getText().isBlank() || passwordField.getText().isBlank()
                || firstNameField.getText().isBlank() || lastNameField.getText().isBlank());
    }

    /**
     * Registers a new user when the register button is pressed.
     *
     * @param event an action event that is passed to
     *              {@link WindowManager#switchScreen(ActionEvent, String) } method
     *              that tells the WindowManager class which screen to change to
     * @throws IOException when attempting to access a file that does not exist at
     *                     the specified location
     */
    @FXML
    private void handleRegisterNewUser(ActionEvent event) throws IOException {
        try {
            remoteModelAccess.register(firstNameField.getText(), lastNameField.getText(), usernameField.getText(),
                    passwordField.getText());
            Globals.user = remoteModelAccess.getUser(usernameField.getText(), passwordField.getText());
            windowManager.switchScreen(event, "Script.fxml");
        } catch (RuntimeException e) {
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
     * Returns the register button component. This function is a helper
     * function for test classes.
     *
     * @return a Button fxml component
     */
    protected Button getRegisterButton() {
        return registerButton;
    }

    /**
     * Returns the username field component. This function is a helper
     * function for test classes.
     *
     * @return a TextField fxml component
     */
    protected TextField getUsernameField() {
        return usernameField;
    }

    /**
     * Returns the invalid messege field component. This function is a helper
     * function for test classes.
     *
     * @return a TextField fxml component
     */
    protected TextField getInvalidField() {
        return invalidField;
    }
}