package core.main;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
<<<<<<< HEAD
    public void testsetContent() {
=======
    public void testSetText() {
>>>>>>> parent of 1fa8d64... Connect ScriptController with REST Api
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
    }

    @Test
<<<<<<< HEAD
=======
    @DisplayName("Test set color")
    public void testSetColor() {
        Note note = new Note();

        // Test for an illegal color choice
        assertThrows(IllegalArgumentException.class, () -> {
            note.setColor("no color");
        });

    }

    @Test
    @DisplayName("Test get color values")
    public void testGetColorValues() {
        Note note = new Note();

        // Test RGB values for default color
        List<Integer> colorValues = note.getColorValues();
        assertEquals(colorValues.get(0), 255);
        assertEquals(colorValues.get(1), 255);
        assertEquals(colorValues.get(2), 255);

        // Test RGB values for new color
        note.setColor("red");
        colorValues = note.getColorValues();
        assertEquals(colorValues.get(0), 255);
        assertEquals(colorValues.get(1), 0);
        assertEquals(colorValues.get(2), 0);
    }

    @Test
>>>>>>> parent of 1fa8d64... Connect ScriptController with REST Api
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
