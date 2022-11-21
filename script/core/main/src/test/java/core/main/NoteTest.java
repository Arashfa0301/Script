package core.main;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class NoteTest {

    @Test
    @DisplayName("Test constructors")
    public void testConstructors() {
        Note emptyNote = new Note();

        // Tests if Title of the note is empty.
        assertEquals("", emptyNote.getTitle());

        // Tests if Text of the note is empty.
        assertEquals("", emptyNote.getContent());

    }

    @Test
    @DisplayName("Test set text")
    public void testsetContent() {
        Note note = new Note();

        // Tests if setContent() works from empty string
        note.setContent("New text");
        assertEquals("New text", note.getContent());

        // Tests if setContent() works from a populated string
        note.setContent("Other text");
        assertEquals("Other text", note.getContent());
    }

    @Test
    @DisplayName("Test set title")
    public void testSetTitle() {
        Note note = new Note();

        // Tests if setTitle() works from empty string
        note.setTitle("New Title");
        assertEquals("New Title", note.getTitle());

        // Tests if setTitle() works from populated string
        note.setTitle("Other Title");
        assertEquals("Other Title", note.getTitle());

        assertThrows(IllegalArgumentException.class, () -> {
            note.setTitle("123456789012345678901234");
        });
    }

    @Test
    @DisplayName("Test pin")
    public void testPin() {
        Note note = new Note();

        // Test that note is not pinned by default
        assertFalse(note.isPinned());

        // Tests that pin() works as intended
        note.setIsPinned(true);
        assertTrue(note.isPinned());

        // Tests that unPin() works as intended
        note.setIsPinned(false);
        assertFalse(note.isPinned());
    }
}
