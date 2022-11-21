package data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.main.Board;
import core.main.Checklist;
import core.main.Note;
import core.main.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataHandlerTest {

    private static final String TEST_FILE_PATH = "dataModuleTest";
    private static DataHandler dataHandler;

    @BeforeEach
    public void setup() throws IOException {
        dataHandler = new DataHandler(TEST_FILE_PATH);
        flushFileContent();
        dataHandler.write(new User("arashfa", "123", "arash", "farzaneh"));
    }

    @Test
    @DisplayName("Test reading from json file")
    public void testRead() throws IOException {
        List<User> users = dataHandler.read();
        assertEquals(1, users.size());
        assertEquals("arashfa", users.get(0).getUsername(), "arashfa");
        assertEquals("123", (users.get(0).getPassword()));
        assertEquals("arash", users.get(0).getFirstName());
        assertEquals("farzaneh", users.get(0).getLastName());
        File myObj = new File(System.getProperty("user.home") + "/" + TEST_FILE_PATH + ".json");
        assertTrue(myObj.exists());
        myObj.delete();
        assertFalse(myObj.exists());
        assertEquals(new ArrayList<>(), dataHandler.read());

    }

    @Test
    @DisplayName("Test writing to json file")
    public void testWrite() throws IOException {
        flushFileContent();
        dataHandler.write(new User("jakobP", "456", "jakob", "punnerud"));
        assertEquals("jakobP", dataHandler.read().get(0).getUsername());
        assertEquals("456", dataHandler.read().get(0).getPassword());
        assertEquals("jakob", dataHandler.read().get(0).getFirstName());
        assertEquals("punnerud", dataHandler.read().get(0).getLastName());

        assertThrows(NullPointerException.class, () -> {
            dataHandler.write(null);
        });
        assertThrows(NullPointerException.class, () -> {
            dataHandler.write(new User(null, null, null, null));
        });
        assertThrows(IllegalArgumentException.class, () -> {
            dataHandler.write(new User("", "", "", ""));
        });
        File myObj = new File(System.getProperty("user.home") + "/" + TEST_FILE_PATH + ".json");
        assertTrue(myObj.exists());
        myObj.delete();

    }

    @Test
    @DisplayName("Test creating board")
    public void testCreateBoard() throws IOException {
        assertThrows(IllegalArgumentException.class, () -> {
            dataHandler.createBoard("", "arashfa");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            dataHandler.createBoard(null, "arashfa");
        });
        assertThrows(IllegalStateException.class, () -> {
            dataHandler.createBoard("test_board", "invalid_username");
        });
        assertEquals(0, dataHandler.read().get(0).getBoards().size());
        dataHandler.createBoard("test_board", "arashfa");
        assertEquals(1, dataHandler.read().get(0).getBoards().size());
        assertEquals("test_board", dataHandler.read().get(0).getBoards().get(0).getName());

    }

    @Test
    @DisplayName("Test removing a board")
    public void testRemoveBoard() {
        assertThrows(IllegalArgumentException.class, () -> {
            dataHandler.removeBoard("", "arashfa");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            dataHandler.removeBoard(null, "arashfa");
        });
        assertThrows(IllegalStateException.class, () -> {
            dataHandler.removeBoard("test_board", "invalid_username");
        });
        dataHandler.createBoard("test_board1", "arashfa");
        dataHandler.createBoard("test_board2", "arashfa");
        assertEquals(2, dataHandler.read().get(0).getBoards().size());
        dataHandler.removeBoard("test_board2", "arashfa");
        assertEquals(1, dataHandler.read().get(0).getBoards().size());
        assertEquals("test_board1", dataHandler.read().get(0).getBoards().get(0).getName());
        assertThrows(IllegalStateException.class, () -> {
            dataHandler.removeBoard("test_board2", "arashfa");
        });
    }

    @Test
    @DisplayName("Test renaming a board")
    public void testRenameBoard() {
        dataHandler.createBoard("test_board1", "arashfa");
        assertEquals(1, dataHandler.read().get(0).getBoards().size());
        assertEquals("test_board1", dataHandler.read().get(0).getBoards().get(0).getName());
        dataHandler.renameBoard("test_board1", "new_test_board1", "arashfa");
        assertEquals(1, dataHandler.read().get(0).getBoards().size());
        assertEquals("new_test_board1", dataHandler.read().get(0).getBoards().get(0).getName());
    }

    @Test
    @DisplayName("Test updating a board")
    public void testUpdateBoard() {
        dataHandler.createBoard("test_board1", "arashfa");
        assertEquals(1, dataHandler.read().get(0).getBoards().size());
        assertEquals(0, dataHandler.read().get(0).getBoards().get(0).getNotes().size());
        assertEquals(0, dataHandler.read().get(0).getBoards().get(0).getChecklists().size());
        Board board = new Board("test_board1", "description", new ArrayList<>(), new ArrayList<>());
        board.addNote(new Note());
        board.addChecklist(new Checklist());
        board.addChecklist(new Checklist());
        dataHandler.updateBoard("test_board1", board, "arashfa");
        assertEquals(1, dataHandler.read().get(0).getBoards().get(0).getNotes().size());
        assertEquals(2, dataHandler.read().get(0).getBoards().get(0).getChecklists().size());
        assertThrows(NullPointerException.class, () -> {
            dataHandler.updateBoard("test_board1", null, "arashfa");
        });
    }

    @Test
    @DisplayName("Test getting user")
    public void testGetUser() {
        assertNull(dataHandler.getUser("invalid_user"));
        assertNotNull(dataHandler.getUser("arashfa"));
        assertEquals("123", dataHandler.getUser("arashfa").getPassword());
    }

    @Test
    @DisplayName("Test if user exists")
    public void testHasUser() {
        assertFalse(dataHandler.hasUser("invalid_user"));
        assertTrue(dataHandler.hasUser("arashfa"));
    }

    private void flushFileContent() throws IOException {
        FileWriter writer = new FileWriter(new File(System.getProperty("user.home")) + "/" + TEST_FILE_PATH + ".json");
        writer.flush();
        writer.close();
    }

}
