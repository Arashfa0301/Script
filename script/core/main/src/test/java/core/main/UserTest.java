package core.main;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UserTest {

    @Test
    public void testConstructor() {
        User user = new User("user");
        assertEquals("user", user.getName());
    }

    @Test
    public void testAddBoard() {
        User user = new User("user");
        user.setBoards(Stream.of(
                new Board("board1", "desc1"),
                new Board("board2", "desc2")).collect(Collectors.toList()));

        Board board3 = new Board("board3", "desc3");
        assertEquals(user.getBoards().size(), 2);
        assertFalse(user.getBoards().contains(board3));
    }

    @Test
    public void testRemoveBoard() {
        User user = new User("user");
        user.setBoards(Stream.of(
                new Board("board1", "desc1"),
                new Board("board2", "desc2")).collect(Collectors.toList()));
        assertTrue(user.getBoards().size() == 2);
        assertTrue(user.getBoards().get(0).getBoardName().equals("board1")
                && user.getBoards().get(1).getBoardName().equals("board2"));

        // Tests that the correct board is removed with removeBoard()
        // Board testBoard = user.getBoard("board2");
        // user.removeBoard("board2");
        // assertFalse(user.getBoards().contains(testBoard));
        // assertTrue(user.getBoards().size() == 1);
        // assertEquals("board1", user.getBoards().get(0).getBoardName());
    }
}
