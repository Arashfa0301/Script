package ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.main.Board;
import core.main.Checklist;
import core.main.ChecklistLine;
import core.main.Note;
import core.main.User;
import data.DataHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.robot.Motion;

import java.io.IOException;
import java.util.List;

public class ScriptControllerTest extends ApplicationTest {

    ScriptController controller;
    DataHandler dataHandler;
    User user;

    @Override
    public void start(Stage stage) throws IOException {
        dataHandler = new DataHandler("users");
        user = new User("username", "password", "first", "last");
        dataHandler.removeUser("username");
        dataHandler.write(user);
        Globals.user = user;
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("Script.fxml"));
        Parent root = loader.load();
        this.controller = loader.getController();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @Test
    @DisplayName("Test controller")
    public void testController() {
        assertNotNull(controller);
    }

    @Test
    @DisplayName("Test boards")
    public void testBoards() throws InterruptedException {
        assertTrue(user.getBoards().size() == 0, "No boards should exist yet");
        assertTrue(controller.getNewBoardButton().isDisabled(),
                "New board button should be disabled when field is empty");
        clickOn("#boardName");
        write("testBoard");
        assertFalse(controller.getNewBoardButton().isDisabled(),
                "New board button should be enabled when field has content");
        clickOn("#newBoardButton");
        moveTo("testBoard");
        clickOn();
        assertTrue(user.getBoards().size() == 1, "One board should exist");
        clickOn("#boardDescription");
        write("test_description");
        assertEquals(getSpecificBoard(0).getBoardDescription(),
                "test_description",
                "Description field should be the same as board description");
        assertTrue(getNotesFromUser(0).size() == 0,
                "No notes should exist in board");
        clickOn("#newNoteButton");
        assertTrue(getNotesFromUser(0).size() == 1);
        clickOn("#boardName");
        write("enterBoard");
        press(KeyCode.ENTER);
        assertTrue(user.getBoards().size() == 2);
        moveBy(25, -400, Motion.DIRECT);
        clickOn();
        clickOn("#newNoteButton");
        assertTrue(user.getBoards().size() == 1);
    }

    @Test
    @DisplayName("Test notes")
    public void testNotes() throws InterruptedException {
        clickOn("#boardName");
        write("noteTest");
        clickOn("#newBoardButton");
        clickOn("#noteTest");
        clickOn("#newNoteButton");
        assertTrue(getSpecificNote(0, 0).getTitle().equals("") && getSpecificNote(0, 0).getContent().equals(""));
        moveTo("Title");
        clickOn();
        write("helo");
        moveTo("Notes");
        clickOn();
        write("test");
        clickOn("#newNoteButton");
        assertEquals("helo", getSpecificNote(0, 0).getTitle());
        assertEquals("test", getSpecificNote(0, 0).getContent());
    }

    @Test
    @DisplayName("Test checklists")
    public void testChecklists() throws InterruptedException {
        clickOn("#boardName");
        write("checklistTest");
        clickOn("#newBoardButton");
        clickOn("#checklistTest");
        clickOn("#newChecklistButton");
        assertTrue(getSpecificChecklistLine(0, 0, 0).getLine().equals("")
                && !getSpecificChecklistLine(0, 0, 0).isChecked()
                && getSpecificChecklist(0, 0).getTitle().equals(""));
        moveTo("Title");
        clickOn();
        write("helo");
        moveTo("Add a list element");
        clickOn();
        write("line");
        moveBy(-80, 0, Motion.DIRECT);
        clickOn();
        clickOn("#newChecklistButton");
        assertTrue(getSpecificChecklistLine(0, 0, 0).getLine().equals("line")
                && getSpecificChecklistLine(0, 0, 0).isChecked()
                && getSpecificChecklist(0, 0).getTitle().equals("helo"));
    }

    /**
     * Gets the notes of a given Board. Helper method for ScriptControllerTest.
     *
     * @param boardIndex an integer representing a Board's index
     * @return a List of Notes found within the given Board
     */
    private List<Note> getNotesFromUser(int boardIndex) {
        return user.getBoards().get(boardIndex).getNotes();
    }

    /**
     * Gets a specific Note from a given Board. Helper method for
     * ScriptControllerTest.
     *
     * @param boardIndex an integer representing a Board's index
     * @param noteIndex  an integer representing the desired Note's idex
     * @return the Note specified by the inputs given
     */
    private Note getSpecificNote(int boardIndex, int noteIndex) {
        return user.getBoards().get(boardIndex).getNotes().get(noteIndex);
    }

    /**
     * Gets a specific Board. Helper method for ScriptControllerTest.
     *
     * @param boardIndex an integer representing the desired Board's index
     * @return the Board specified by the inputs given
     */
    private Board getSpecificBoard(int boardIndex) {
        return user.getBoards().get(boardIndex);
    }

    /**
     * Gets a specific ChecklistLine from a given Checklist within a given Board.
     * Helper method for ScriptControllerTest.
     *
     * @param boardIndex     an integer representing a Board's index
     * @param checklistIndex an integer representing a Checklist's index
     * @param checklistLine  an integer representing the desired ChecklistLine's
     *                       index
     * @return a ChecklistLine specified by the inputs given
     */
    private ChecklistLine getSpecificChecklistLine(int boardIndex, int checklistIndex, int checklistLine) {
        return user.getBoards().get(boardIndex).getChecklists().get(checklistIndex).getChecklistLines()
                .get(checklistLine);
    }

    /**
     * Gets a specific Checklist from a given Board. Helper method for
     * ScriptControllerTest.
     *
     * @param boardIndex     an integer representing a Board's index
     * @param checklistIndex an integer representing the desired Checklist's index
     * @return a Checklist specified by the inputs given
     */
    private Checklist getSpecificChecklist(int boardIndex, int checklistIndex) {
        return user.getBoards().get(boardIndex).getChecklists().get(checklistIndex);
    }
}
