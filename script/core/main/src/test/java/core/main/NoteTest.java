package core.main;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import java.util.List;

public class NoteTest {

    @Test
    public void testConstructors() {
        Note emptyNote = new Note("", "");

        // Tests if Title of the note is empty.
        assertEquals("", emptyNote.getTitle());

        // Tests if Text of the note is empty.
        assertEquals("", emptyNote.getText());

        // Creates a new Note with predetermined text
        Note customNote = new Note("New Title", "This is a new note");

        // Tests if Title of the same as expected.
        assertEquals("New Title", customNote.getTitle());

        // Tests if Text of the note is same as expected.
        assertEquals("This is a new note", customNote.getText());
    }

    @Test
    public void testSetText() {
        Note note = new Note("", "");

        // Tests if setText() works from empty string
        note.setText("New text");
        assertEquals("New text", note.getText());

        // Tests if setText() works from a populated string
        note.setText("Other text");
        assertEquals("Other text", note.getText());
    }

    @Test
    public void testSetTitle() {
        Note note = new Note("", "");

        // Tests if setTitle() works from empty string
        note.setTitle("New Title");
        assertEquals("New Title", note.getTitle());

        // Tests if setTitle() works from populated string
        note.setTitle("Other Title");
        assertEquals("Other Title", note.getTitle());
    }

    @Test
    public void testSetColor() {
        Note note = new Note("", "");

        // Test for default color
        assertTrue(note.getColor().equals("white"));

        // Test for an illegal color choice
        assertThrows(IllegalArgumentException.class, () -> {
            note.setColor("no color");
        });

        // Test for legal color choice
        note.setColor("red");
        assertTrue(note.getColor().equals("red"));
    }

    @Test
    void testGetColorValues() {
        Note note = new Note("", "");

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
}
