package core.main;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class NoteTest {

    @Test
    public void testConstructors() {
        Note emptyNote = new Note();
        assertEquals(null, emptyNote.getTitle()); // Checks if Title of the note is empty.
        assertEquals(null, emptyNote.getText()); // Checks if Text of the note is empty.
        Note customNote = new Note("New Title", "This is a new note");
        assertEquals("New Title", customNote.getTitle()); // Checks if Title of the same as expected.
        assertEquals("This is a new note", customNote.getText()); // Checks if Text of the note is same as expected.
    }

    @Test
    public void testSetText() {
        Note note = new Note();
        note.setText("New text");
        assertEquals("New text", note.getText());
        note.setText("Other text");
        assertEquals("Other text", note.getText());
    }

    @Test
    public void testSetTitle() {
        Note note = new Note();
        note.setTitle("New Title");
        assertEquals("New Title", note.getTitle());
        note.setTitle("Other Title");
        assertEquals("Other Title", note.getTitle());
    }
}
