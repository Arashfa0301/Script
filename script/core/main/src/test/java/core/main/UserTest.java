package core.main;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserTest {

    @Test
    @DisplayName("Test constructor")
    public void testConstructor() {
        User user = new User("user", "password", "first", "last");
        assertEquals("user", user.getUsername());
        assertEquals("password", user.getPassword());
        assertEquals("first", user.getFirstName());
        assertEquals("last", user.getLastName());

        assertThrows(IllegalArgumentException.class, () -> {
            new User("", "", "", "");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new User(" ", " ", " ", " ");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new User("\\\\/ ", "dnoijsd", "sdofh", "dailf");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new User("null", "", "null", "null");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new User("null", "   ", "null", "null");
        });
    }

    @Test
    @DisplayName("Test set password")
    public void testSetPassword() {
        User user = new User("test", "1", "test", "test");
        assertEquals("1", user.getPassword());
        user.setPassword("2");
        assertEquals("2", user.getPassword());
    }

    @Test
    @DisplayName("")
    public void testBoardFunctions() {
        User user = new User("test", "test", "test", "test");
        List<Board> boards = new ArrayList<>(
                Arrays.asList(new Board("1", "null", null, null), new Board("2", "null", null, null)));
        user.setBoard(boards);
        assertEquals("1", user.getBoards().get(0).getName());
        assertEquals("2", user.getBoards().get(1).getName());

        user.addBoard("3");
        assertEquals("3", user.getBoards().get(2).getName());

        Board changedBoard = user.getBoards().get(0);
        changedBoard.setName("test");
        user.putBoard(changedBoard);
        assertEquals(changedBoard, user.getBoards().get(0));

        user.renameBoard("test", "testing");
        assertEquals("testing", user.getBoards().get(0).getName());
        assertThrows(IllegalArgumentException.class, () -> {
            user.renameBoard("test", "failedTest");
        });

        user.removeBoard("testing");
        assertThrows(IllegalArgumentException.class, () -> {
            user.removeBoard("testing");
        });
    }
}
