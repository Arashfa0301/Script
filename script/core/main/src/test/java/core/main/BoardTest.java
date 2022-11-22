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
        assertEquals("Name", newBoard.getName());
        assertEquals("Description", newBoard.getBoardDescription());
    }

    @Test
    @DisplayName("Test add note")
    public void testAddNote() {
        // Tests that board is empty by default
        Board board = new Board("Board", "Test", new ArrayList<Note>(), new ArrayList<Checklist>());
        assertTrue(board.getNotes().isEmpty() && board.getChecklists().isEmpty(),
                "A board should be empty when first created.");

        // Tests that addBoardElement works as intended for a valid note
        Note note = new Note();
        board.addNote(note);
        assertFalse(board.getNotes().isEmpty() && !board.getChecklists().isEmpty(),
                "A note should have been added to the board");
        assertEquals(note, board.getNotes().get(0), "The note in the board should be the same one that was added.");

        // Tests for exception case: note == null
        assertThrows(IllegalArgumentException.class, () -> {
            board.addNote(null);
        }, "A note which is null should not be added.");

        // Tests for exception case: Exceeded MAX_NOTES
        // stream with 100
        List<Note> notes = Arrays.asList(
                IntStream.range(1, 100).mapToObj(i -> new Note()).toArray(Note[]::new));
        notes.stream().forEach(n -> board.addNote(n));
        assertThrows(IllegalStateException.class, () -> {
            board.addNote(new Note());
        }, "Boards should not have more than 100 elements.");
    }

    @Test
    @DisplayName("Test set name")
    public void testSetName() {
        Board board = new Board("Name", "Description", new ArrayList<Note>(), new ArrayList<Checklist>());
        assertEquals("Name", board.getName(),
                "The name of the board should be the same name that was given in the constructor.");
        board.setName("New Name");
        assertEquals("New Name", board.getName(), "The name of the board should be the same as the new name given.");
    }

    @Test
    @DisplayName("Test set desctription")
    public void testSetDescription() {
        Board board = new Board("Name", "Description", new ArrayList<Note>(), new ArrayList<Checklist>());
        assertEquals("Description", board.getBoardDescription(),
                "The description of the board should be the same description that was given in the constructor.");
        board.setBoardDescription("New Description");
        assertEquals("New Description", board.getBoardDescription(),
                "The description of the board should be the same as the new description given.");
    }

    @Test
    @DisplayName("Test get notes")
    public void testGetNotes() {
        Board board = new Board("Board", "Test", new ArrayList<Note>(), new ArrayList<Checklist>());
        Note note1 = new Note();
        Note note2 = new Note();
        board.addNote(note1);
        board.addNote(note2);
        assertEquals(note1, board.getNotes().get(0),
                "The first note should be the same as the first note that was added.");
        assertEquals(note2, board.getNotes().get(1),
                "The second note should be the same as the second note that was added.");
    }

    @Test
    @DisplayName("Test add checklists")
    public void testAddChecklists() {
        // Tests that board is empty by default
        Board board = new Board("Board", "Test", new ArrayList<Note>(), new ArrayList<Checklist>());
        assertTrue(board.getChecklists().isEmpty() && board.getChecklists().isEmpty(),
                "A board should be empty when first created.");

        // Tests that addBoardElement works as intended for a valid Checklist
        Checklist checklist = new Checklist();
        board.addChecklist(checklist);
        assertFalse(board.getChecklists().isEmpty() && !board.getNotes().isEmpty(),
                "A checklist should have been added to the board.");
        assertEquals(checklist, board.getChecklists().get(0),
                "The checklist in the board should be the same one that was added.");

        // Tests for exception case: Checklist == null
        assertThrows(IllegalArgumentException.class, () -> {
            board.addChecklist(null);
        }, "A Checklist that is null should not be added.");

        // Tests for exception case: Exceeded MAX_ChecklistS
        // stream with 100
        List<Checklist> checklists = Arrays.asList(
                IntStream.range(1, 100).mapToObj(i -> new Checklist()).toArray(Checklist[]::new));
        checklists.stream().forEach(n -> board.addChecklist(n));
        assertThrows(IllegalStateException.class, () -> {
            board.addChecklist(new Checklist());
        }, "A board shuld not have more than 100 elements.");
    }

    @Test
    @DisplayName("Test clear functions")
    public void testClear() {
        Board board = new Board("board", "description", new ArrayList<>(), new ArrayList<>());
        board.addNote(new Note());
        board.addChecklist(new Checklist());
        assertFalse(board.getChecklists().isEmpty() && board.getNotes().isEmpty(),
                "The board should contain both a note and a checklist.");
        board.clearCheckLists();
        assertTrue(board.getChecklists().isEmpty(), "Checklists should now be empty.");
        assertFalse(board.getNotes().isEmpty(), "Boards should not be empty at this moment.");
        board.clearNotes();
        assertTrue(board.getChecklists().isEmpty() && board.getNotes().isEmpty(),
                "Both notes and checklists should now be empty.");
    }
}
