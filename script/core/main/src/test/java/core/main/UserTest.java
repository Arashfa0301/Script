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
        assertEquals("user", user.getUsername(), "Username should be the same as the one given in the constructor.");
        assertEquals("password", user.getPassword(),
                "Password should be the same as the one given in the constructor.");
        assertEquals("first", user.getFirstName(), "Last name should be the same as the one given in the constructor.");
        assertEquals("last", user.getLastName(), "First name should be the same as the one given in the constructor.");

        assertThrows(IllegalArgumentException.class, () -> {
            new User("", "", "", "");
        }, "Empty strings should trigger an exception.");
        assertThrows(IllegalArgumentException.class, () -> {
            new User(" ", " ", " ", " ");
        }, "Blank strings should trigger an exception.");
        assertThrows(IllegalArgumentException.class, () -> {
            new User("\\\\/ ", "dnoijsd", "sdofh", "dailf");
        }, "Strings with illegal symbols should trigger an exception.");
        assertThrows(IllegalArgumentException.class, () -> {
            new User("null", "", "null", "null");
        }, "Empty password should trigger an exception.");
        assertThrows(IllegalArgumentException.class, () -> {
            new User("null", "   ", "null", "null");
        }, "Blank password should trigger an exception.");
    }

    @Test
    @DisplayName("Test set password")
    public void testSetPassword() {
        User user = new User("test", "1", "test", "test");
        assertEquals("1", user.getPassword(), "Password should be 1");
        user.setPassword("2");
        assertEquals("2", user.getPassword(), "Password should now be 2");
    }

    @Test
    @DisplayName("Tests board functions")
    public void testBoardFunctions() {
        User user = new User("test", "test", "test", "test");
        List<Board> boards = new ArrayList<>(
                Arrays.asList(new Board("1", "null", null, null), new Board("2", "null", null, null)));
        user.setBoard(boards);
        assertEquals("1", user.getBoards().get(0).getName(),
                "Name of first board should be the same as the first one added.");
        assertEquals("2", user.getBoards().get(1).getName(),
                "Name of second board should be the same as the second one added.");

        user.addBoard("3");
        assertEquals("3", user.getBoards().get(2).getName(), "Board with name \"3\" should have been added to board.");

        Board changedBoard = user.getBoards().get(0);
        changedBoard.setName("test");
        user.putBoard(changedBoard);
        assertEquals(changedBoard, user.getBoards().get(0), "Name of the first board should now have been changed.");

        user.renameBoard("test", "testing");
        assertEquals("testing", user.getBoards().get(0).getName(),
                "Board with the given old name should now have changed name to the new name.");
        assertThrows(IllegalArgumentException.class, () -> {
            user.renameBoard("test", "failedTest");
        }, "The user should no longer own a board with this name.");

        user.removeBoard("testing");
        assertThrows(IllegalArgumentException.class, () -> {
            user.removeBoard("testing");
        }, "The user should no longer own a board with this name.");
    }
}
