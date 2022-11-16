package core.main;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

public class NoteTest {

    @Test
    @DisplayName("Test constructors")
    public void testConstructors() {
        Note emptyNote = new Note();

        // Tests if Title of the note is empty.
        assertEquals("", emptyNote.getTitle());

        // Tests if Text of the note is empty.
        assertEquals("", emptyNote.getText());

    }

    @Test
    @DisplayName("Test set text")
    public void testSetText() {
        Note note = new Note();

        // Tests if setText() works from empty string
        note.setText("New text");
        assertEquals("New text", note.getText());

        // Tests if setText() works from a populated string
        note.setText("Other text");
        assertEquals("Other text", note.getText());
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

}
