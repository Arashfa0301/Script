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

public class DataHandlerTest {

    private static DataHandler datahandler;

    @BeforeAll
    public static void initialize() {
        // initialize sciptModule
        datahandler = new DataHandler();
    }

    @Test
    @DisplayName("Test making new parentfolder")
    public void testCreateParentFolder() {
        File directory = new File(System.getProperty("user.home")
                + "/resources");
        FileSystemUtils.deleteRecursively(new File(System.getProperty("user.home")
                + "/resources"));
        assertFalse(directory.exists(), "Tests that parentfolder got deleted.");
        datahandler.getUser("fakeUser");
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
        datahandler.write(new User("arash"));
        assertTrue(usersFile.exists(),
                "Tests that getUser(String user) that runs the read() function creates the user.json file ");
        assertEquals(datahandler.getUser("arash").getName(), "arash");
        Board testBoard = new Board("board1", "des");
        testBoard.addNote(new Note("note1", "des"));
        testBoard.addNote(new Note("note2", "des"));
        testBoard.addNote(new Note("note3", "des"));
        User testUser = new User("testUser");
        testUser.addBoard(testBoard);
        testUser.addBoard(new Board("board2", null));
        datahandler.write(testUser);
        assertEquals(datahandler.getUser("testUser").getBoards().size(), 2);
        assertEquals(datahandler.getUser("testUser").getBoard("board1").getNotes().size(), 3);

        assertThrows(NullPointerException.class, () -> {
            datahandler.write(null);
        });
        assertThrows(NullPointerException.class, () -> {
            datahandler.write(new User(null));
        });
        assertThrows(IllegalArgumentException.class, () -> {
            datahandler.write(new User(""));
        });

    }

    @Test
    @DisplayName("Test getting user")
    public void testGetUser() {
        FileSystemUtils.deleteRecursively(new File(System.getProperty("user.home")
                + "/resources"));
        assertNull(datahandler.getUser("user"),
                "Tests that no user is returned when user.json file is either empty or not excistent");
        datahandler.write(new User("user"));
        assertNotNull(datahandler.getUser("user"),
                "Tests that a user is not exists when a user is written into the json file");
        assertThrows(IllegalArgumentException.class, () -> {
            datahandler.getUser("");
        });
    }

}
