package data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.main.Board;
import core.main.Note;
import core.main.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.util.FileSystemUtils;

import java.io.File;

public class ScriptModuleTest {

    private static ScriptModule scriptModule;

    @BeforeAll
    public static void initialize() {
        // initialize sciptModule
        scriptModule = new ScriptModule();
    }

    @Test
    @DisplayName("Test making new parentfolder")
    public void testCreateParentFolder() {
        File directory = new File(System.getProperty("user.home")
                + "/resources");
        FileSystemUtils.deleteRecursively(new File(System.getProperty("user.home")
                + "/resources"));
        assertFalse(directory.exists(), "Tests that parentfolder got deleted.");
        scriptModule.getUser("fakeUser");
        assertTrue(directory.exists(),
                "Tests that getUser(String user) that runs the read() function creates the resources directory.");

    }

    @Test
    @DisplayName("Test writing to users.json")
    public void testWrite() {
        File usersFile = new File(System.getProperty("user.home")
                + "/resources");
        FileSystemUtils.deleteRecursively(new File(System.getProperty("user.home")
                + "/resources"));
        assertFalse(usersFile.exists(), "Tests that users.json doesnt exist.");
        scriptModule.write(new User("arash"));
        assertTrue(usersFile.exists(),
                "Tests that getUser(String user) that runs the read() function creates the user.json file ");
        assertEquals(scriptModule.getUser("arash").getName(), "arash");
        Board testBoard = new Board("board1", "des");
        testBoard.addNote(new Note("note1", "des"));
        testBoard.addNote(new Note("note2", "des"));
        testBoard.addNote(new Note("note3", "des"));
        User testUser = new User("testUser");
        testUser.addBoard(testBoard);
        testUser.addBoard(new Board("board2", null));
        scriptModule.write(testUser);
        assertEquals(scriptModule.getUser("testUser").getBoards().size(), 2);
        assertEquals(scriptModule.getUser("testUser").getBoard("board1").getNotes().size(), 3);

        assertThrows(NullPointerException.class, () -> {
            scriptModule.write(null);
        });
        assertThrows(NullPointerException.class, () -> {
            scriptModule.write(new User(null));
        });
        assertThrows(IllegalArgumentException.class, () -> {
            scriptModule.write(new User(""));
        });

    }

    @Test
    @DisplayName("Test getting user")
    public void testGetUser() {
        FileSystemUtils.deleteRecursively(new File(System.getProperty("user.home")
                + "/resources"));
        assertNull(scriptModule.getUser("user"),
                "Tests that no user is returned when user.json file is either empty or not excistent");
        scriptModule.write(new User("user"));
        assertNotNull(scriptModule.getUser("user"),
                "Tests that a user is not exists when a user is written into the json file");
        assertThrows(IllegalArgumentException.class, () -> {
            scriptModule.getUser("");
        });
    }

}
