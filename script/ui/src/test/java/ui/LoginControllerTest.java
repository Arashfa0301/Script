package ui;

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

public class LoginControllerTest extends ApplicationTest {

    LoginController controller;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("Login.fxml"));
        Parent root = loader.load();
        this.controller = loader.getController();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @Test
    @DisplayName("Test controller")
    void testController() {
        assertNotNull(controller);
    }

    @Test
    @DisplayName("Test disabled when no username")
    void testEmpty() {
        clickOn("#usernameField");
        write("a");
        press(KeyCode.BACK_SPACE);
        release(KeyCode.BACK_SPACE);
        assertTrue(controller.getLoginButton().isDisabled());
    }

}
