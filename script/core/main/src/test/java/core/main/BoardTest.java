package core.main;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class BoardTest {

    @Test
    public void testConstructor() {
        Board newBoard = new Board("Name", "Description");
        assertEquals("Name", newBoard.getBoardName());
        assertEquals("Description", newBoard.getBoardDescription());
    }

    @Test
    public void testAddNote() {
        Board board = new Board("Board", "Test");
        assertTrue(board.getNotes().isEmpty());
        Note note = new Note("", "");
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

    @Test
    public void testGetNote() {
        Board board = new Board("Board", "Test");
        Note note1 = new Note("Title1", "");
        Note note2 = new Note("Title2", "");
        board.addNote(note1);
        board.addNote(note2);
        assertEquals(note1, board.getNote("Title1"));
        assertEquals(note2, board.getNote("Title2"));
    }

    @Test
    public void testRemoveNote() {
        Board board = new Board("Board", "Test");
        Note note1 = new Note("Title1", "");
        Note note2 = new Note("Title2", "");
        board.addNote(note1);
        board.addNote(note2);

        // Tests that the board contains both notes
        assertTrue(board.getNotes().contains(note1) && board.getNotes().contains(note2));

        // Tests that removeNote() removed the intended note
        board.removeNote("Title2");
        assertFalse(board.getNotes().contains(note2));

        // Tests that the other note is unaffected by removeNote()
        assertTrue(board.getNotes().contains(note1));
    }
}
