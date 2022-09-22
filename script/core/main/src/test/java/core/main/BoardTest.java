package core.main;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class BoardTest {

    @Test
    public void testConstructor(){
        Board newBoard = new Board("Name", "Description");
        assertEquals("Name", newBoard.getBoardName());
        assertEquals("Description", newBoard.getBoardDescription());
    }

    @Test
    public void testAddNote() {
        Board board = new Board("Board", "Test");
        assertTrue(board.getNotes().isEmpty());
        Note note = new Note();
        board.addNote(note);
        assertFalse(board.getNotes().isEmpty());
        assertEquals(note, board.getNotes().get(0));
    }

    @Test
    public void testSetName() {
        Board board = new Board("Name", "Description");
        assertEquals("Name", board.getBoardName());
        board.setBoardName("New Name");
        assertEquals("New Name", board.getBoardName());
    }

    @Test
    public void testSetDescription() {
        Board board = new Board("Name", "Description");
        assertEquals("Description", board.getBoardDescription());
        board.setBoardDescription("New Description");
        assertEquals("New Description", board.getBoardDescription());
    }
}
