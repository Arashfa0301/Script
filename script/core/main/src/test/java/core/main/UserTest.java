package core.main;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    @DisplayName("Test set boards")
    public void testSetBoards() {
        User user = new User("user", "password", "first", "last");
        user.setBoards(Stream.of(
                new Board("board1", "desc1", new ArrayList<Note>(), new ArrayList<Checklist>()),
                new Board("board2", "desc2", new ArrayList<Note>(), new ArrayList<Checklist>()))
                .collect(Collectors.toList()));

        Board board3 = new Board("board3", "desc3", new ArrayList<Note>(), new ArrayList<Checklist>());
        assertEquals(user.getBoards().size(), 2);
        assertFalse(user.getBoards().contains(board3));
        assertTrue(user.getBoards().get(0).getBoardName().equals("board1")
                && user.getBoards().get(1).getBoardName().equals("board2"));
    }

    @Test
    @DisplayName("Test set password")
    public void testSetPassword() {
        User user = new User("test", "1", "test", "test");
        assertEquals("1", user.getPassword());
        user.setPassword("2");
        assertEquals("2", user.getPassword());
    }
}
