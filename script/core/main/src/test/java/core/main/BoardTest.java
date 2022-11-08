// package core.main;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertFalse;
// import static org.junit.jupiter.api.Assertions.assertThrows;
// import static org.junit.jupiter.api.Assertions.assertTrue;

// import org.junit.jupiter.api.Test;

// import java.util.Arrays;
// import java.util.List;
// import java.util.stream.IntStream;

// public class BoardTest {

// @Test
// public void testConstructor() {
// Board newBoard = new Board("Name", "Description");
// assertEquals("Name", newBoard.getBoardName());
// assertEquals("Description", newBoard.getBoardDescription());
// }

// @Test
// public void testAddNote() {
// // Tests that board is empty by default
// Board board = new Board("Board", "Test");
// assertTrue(board.getBoardElements().isEmpty());

// // Tests that addBoardElement works as intended for a valid note
// Note note = new Note("", "");
// board.addBoardElement(note);
// assertFalse(board.getBoardElements().isEmpty());
// assertEquals(note, board.getBoardElements().get(0));

// // Tests for exception case: note == null
// assertThrows(IllegalArgumentException.class, () -> {
// board.addBoardElement(null);
// });

// // Tests for exception case: Exceeded MAX_NOTES
// // stream with 256
// List<Note> notes = Arrays.asList(
// IntStream.range(1, 256).mapToObj(i -> new Note(String.format("Note %d", i),
// "")).toArray(Note[]::new));
// notes.stream().forEach(n -> board.addBoardElement(n));
// assertThrows(IllegalArgumentException.class, () -> {
// board.addBoardElement(new Note("", ""));
// });
// }

// @Test
// public void testSetName() {
// Board board = new Board("Name", "Description");
// assertEquals("Name", board.getBoardName());
// board.setBoardName("New Name");
// assertEquals("New Name", board.getBoardName());
// }

// @Test
// public void testSetDescription() {
// Board board = new Board("Name", "Description");
// assertEquals("Description", board.getBoardDescription());
// board.setBoardDescription("New Description");
// assertEquals("New Description", board.getBoardDescription());
// }

// @Test
// public void testGetNote() {
// Board board = new Board("Board", "Test");
// Note note1 = new Note("Title1", "");
// Note note2 = new Note("Title2", "");
// board.addBoardElement(note1);
// board.addBoardElement(note2);
// assertEquals(note1, board.getBoardElement("Title1"));
// assertEquals(note2, board.getBoardElement("Title2"));
// }

// @Test
// public void testRemoveNote() {
// Board board = new Board("Board", "Test");
// Note note1 = new Note("Title1", "");
// Note note2 = new Note("Title2", "");
// board.addBoardElement(note1);
// board.addBoardElement(note2);

// // Tests that the board contains both notes
// assertTrue(board.getBoardElements().contains(note1) &&
// board.getBoardElements().contains(note2));

// // Tests that removeNote() removed the intended note
// board.removeNote("Title2");
// assertFalse(board.getBoardElements().contains(note2));

// // Tests that the other note is unaffected by removeNote()
// assertTrue(board.getBoardElements().contains(note1));
// }
// }
