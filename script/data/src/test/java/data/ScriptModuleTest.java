package data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import core.main.Board;
import core.main.Note;

public class ScriptModuleTest {
    
    private static ObjectMapper mapper;

    @BeforeAll
    public static void setUp() {
        mapper = new ObjectMapper();
        mapper.registerModule(new ScriptModule());
    }

    private final static String boardWithNotes = "{\"boardName\":\"BappoIverri\",\"description\":\"Chadest group ever\",\"notes\":[{\"title\":\"Arash\",\"text\":\"the alpha\"},{\"title\":\"Iver\",\"text\":\"the leader\"},{\"title\":\"Jakob\",\"text\":\"the crackhead\"},{\"title\":\"Viljan\",\"text\":\"the genius\"},{\"title\":\"Andreas\",\"text\":\"the mob boss\"}]}"; 

    @Test
    public void testSerializers() {
        Board board = new Board("BappoIverri", "Chadest group ever");
        Note note1 = new Note("Arash", "the alpha");
        Note note2 = new Note("Iver", "the leader");
        Note note3 = new Note("Jakob", "the crackhead");
        Note note4 = new Note("Viljan", "the genius");
        Note note5 = new Note("Andreas", "the mob boss");
        board.addNote(note1);
        board.addNote(note2);
        board.addNote(note3);
        board.addNote(note4);
        board.addNote(note5);
        try {
           assertEquals(boardWithNotes, mapper.writeValueAsString(board));
        } catch (JsonProcessingException e) {
            fail();
        }

    }

    private void checkNote(Note note, String title, String text) {
        assertEquals(title, note.getTitle());
        assertEquals(text, note.getText());
    }

    @Test
    public void testDeserializers() {
        try {
            Board board = mapper.readValue(boardWithNotes, Board.class);
            assertEquals("BappoIverri", board.getBoardName());
            assertEquals("Chadest group ever", board.getBoardDescription());
            List<Note> notes = board.getNotes();
            Iterator<Note> noteIt = notes.iterator();
            assertTrue(noteIt.hasNext());
            checkNote(noteIt.next(), "Arash", "the alpha");
            assertTrue(noteIt.hasNext());
            checkNote(noteIt.next(), "Iver", "the leader");
            assertTrue(noteIt.hasNext());
            checkNote(noteIt.next(), "Jakob", "the crackhead");
            assertTrue(noteIt.hasNext());
            checkNote(noteIt.next(), "Viljan", "the genius");
            assertTrue(noteIt.hasNext());
            checkNote(noteIt.next(), "Andreas", "the mob boss");
            assertFalse(noteIt.hasNext());
        } catch (JsonMappingException e) {
            fail();
        } catch (JsonProcessingException e) {
            fail();
        }
    }

}
