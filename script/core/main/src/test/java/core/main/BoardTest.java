package core.main;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class BoardTest {

    @Test
    @DisplayName("Test constructor")
    public void testConstructor() {
        Board newBoard = new Board("Name", "Description", new ArrayList<Note>(), new ArrayList<Checklist>());
        assertEquals("Name", newBoard.getBoardName());
        assertEquals("Description", newBoard.getBoardDescription());
    }

    @Test
    @DisplayName("Test add note")
    public void testAddNote() {
        // Tests that board is empty by default
        Board board = new Board("Board", "Test", new ArrayList<Note>(), new ArrayList<Checklist>());
        assertTrue(board.getNotes().isEmpty() && board.getChecklists().isEmpty());

        // Tests that addBoardElement works as intended for a valid note
        Note note = new Note();
        board.addNote(note);
        assertFalse(board.getNotes().isEmpty() && !board.getChecklists().isEmpty());
        assertEquals(note, board.getNotes().get(0));

        // Tests for exception case: note == null
        assertThrows(IllegalArgumentException.class, () -> {
            board.addNote(null);
        });

        // Tests for exception case: Exceeded MAX_NOTES
        // stream with 256
        List<Note> notes = Arrays.asList(
                IntStream.range(1, 100).mapToObj(i -> new Note()).toArray(Note[]::new));
        notes.stream().forEach(n -> board.addNote(n));
        assertThrows(IllegalArgumentException.class, () -> {
            board.addNote(new Note());
        });
    }

    @Test
    @DisplayName("Test set name")
    public void testSetName() {
        Board board = new Board("Name", "Description", new ArrayList<Note>(), new ArrayList<Checklist>());
        assertEquals("Name", board.getBoardName());
        board.setBoardName("New Name");
        assertEquals("New Name", board.getBoardName());
        assertThrows(IllegalArgumentException.class, () -> {
            board.setBoardName("");
        });
        assertThrows(NullPointerException.class, () -> {
            board.setBoardName(null);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            board.setBoardName("123456789012345678901");
        });
    }

    @Test
    @DisplayName("Test set desctription")
    public void testSetDescription() {
        Board board = new Board("Name", "Description", new ArrayList<Note>(), new ArrayList<Checklist>());
        assertEquals("Description", board.getBoardDescription());
        board.setBoardDescription("New Description");
        assertEquals("New Description", board.getBoardDescription());
    }

    @Test
    @DisplayName("Test get notes")
    public void testGetNotes() {
        Board board = new Board("Board", "Test", new ArrayList<Note>(), new ArrayList<Checklist>());
        Note note1 = new Note();
        Note note2 = new Note();
        board.addNote(note1);
        board.addNote(note2);
        assertEquals(note1, board.getNotes().get(0));
        assertEquals(note2, board.getNotes().get(1));
    }

    @Test
    @DisplayName("Test add checklists")
    public void testAddChecklists() {
        // Tests that board is empty by default
        Board board = new Board("Board", "Test", new ArrayList<Note>(), new ArrayList<Checklist>());
        assertTrue(board.getChecklists().isEmpty() && board.getChecklists().isEmpty());

        // Tests that addBoardElement works as intended for a valid Checklist
        Checklist checklist = new Checklist();
        board.addChecklist(checklist);
        assertFalse(board.getChecklists().isEmpty() && !board.getChecklists().isEmpty());
        assertEquals(checklist, board.getChecklists().get(0));

        // Tests for exception case: Checklist == null
        assertThrows(IllegalArgumentException.class, () -> {
            board.addChecklist(null);
        });

        // Tests for exception case: Exceeded MAX_ChecklistS
        // stream with 256
        List<Checklist> checklists = Arrays.asList(
                IntStream.range(1, 100).mapToObj(i -> new Checklist()).toArray(Checklist[]::new));
        checklists.stream().forEach(n -> board.addChecklist(n));
        assertThrows(IllegalArgumentException.class, () -> {
            board.addChecklist(new Checklist());
        });
    }

    @Test
    @DisplayName("Test clear functions")
    public void testClear() {
        Board board = new Board("board", "description", new ArrayList<>(), new ArrayList<>());
        board.addNote(new Note());
        board.addChecklist(new Checklist());
        assertFalse(board.getChecklists().isEmpty() && board.getNotes().isEmpty());
        board.clearCheckLists();
        assertTrue(board.getChecklists().isEmpty());
        assertFalse(board.getNotes().isEmpty());
        board.clearNotes();
        assertTrue(board.getChecklists().isEmpty() && board.getNotes().isEmpty());
    }
}
