package ui;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.main.User;
import data.ScriptModule;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
    @DisplayName("Test login")
    void testLogin() {
        clickOn("#loginField");
        write("test_user");
        clickOn("#loginButton");
        ScriptModule scriptModule = new ScriptModule();
        User user = scriptModule.getUser("test_user");
        assertNotNull(user);
    }

    @Test
    @DisplayName("Test empty username")
    void testEmpty() {
        assertTrue(controller.getLoginButton().isDisabled());
    }

}
