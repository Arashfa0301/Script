package ui;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import java.io.IOException;

public class RegisterControllerTest extends ApplicationTest {

    RegisterController controller;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("Register.fxml"));
        Parent root = loader.load();
        controller = loader.getController();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @Test
    @DisplayName("Tests controller")
    public void testController() {
        assertNotNull(controller);
    }

    @Test
    @DisplayName("Tests register fields and register button visibility")
    public void testRegisterFields() {
        clickOn("#usernameField");
        write("a");
        assertTrue(controller.getRegisterButton().isDisabled(),
                "Button should be disabled when some fields are missing inputs");
        clickOn("#passwordField");
        write("b");
        assertTrue(controller.getRegisterButton().isDisabled(),
                "Button should be disabled when some fields are missing inputs");
        clickOn("#firstNameField");
        write("c");
        assertTrue(controller.getRegisterButton().isDisabled(),
                "Button should be disabled when some fields are missing inputs");
        clickOn("#usernameField");
        press(KeyCode.BACK_SPACE);
        release(KeyCode.BACK_SPACE);
        clickOn("#lastNameField");
        write("d");
        assertTrue(controller.getRegisterButton().isDisabled(),
                "Button should be disabled when some fields are missing inputs");
        clickOn("#usernameField");
        write("a");
        assertFalse(controller.getRegisterButton().isDisabled(),
                "Button should be enabled if all fields contain inputs");
    }
}