package ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.main.Board;
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

public class ScriptControllerTest extends ApplicationTest {

    ScriptController controller;
    ScriptModule dataHandler;
    User user;

    @Override
    public void start(Stage stage) throws IOException {
        dataHandler = new ScriptModule();
        user = new User("test_user");
        dataHandler.removeUser("test_user");
        dataHandler.write(user);
        Globals.user = user;
        Globals.windowHeight = 720;
        Globals.windowWidth = 1280;
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("Script.fxml"));
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
    @DisplayName("Test create boards")
    void testCreateBoards() {
        assertTrue(controller.getBoards().size() == 0, "No boards should exist yet");
        assertTrue(controller.getNewBoardButton().isDisabled(),
                "New board button should be disabled when field is empty");
        clickOn("#boardName");
        write("test_board");
        assertFalse(controller.getNewBoardButton().isDisabled(),
                "New board button should be enabled when field has content");
        clickOn("#newBoardButton");
        assertTrue(controller.getBoards().size() == 1, "One board should exist");
        clickOn("#test_board");
        clickOn("#boardDescription");
        write("test_description");
        assertEquals(controller.getBoards().get(0).getBoardDescription(), "test_description",
                "Description field should be the same as board description");
        assertTrue(controller.getBoards().get(0).getNotes().size() == 0,
                "One note should exist in board");
    }
}
