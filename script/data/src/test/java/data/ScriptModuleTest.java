package data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import com.fasterxml.jackson.core.JsonProcessingException;
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
           assertEquals("{\"boardName\":\"BappoIverri\",\"description\":\"Chadest group ever\",\"notes\":[{\"title\":\"Arash\",\"test\":\"the alpha\"},{\"title\":\"Iver\",\"test\":\"the leader\"},{\"title\":\"Jakob\",\"test\":\"the crackhead\"},{\"title\":\"Viljan\",\"test\":\"the genius\"},{\"title\":\"Andreas\",\"test\":\"the mob boss\"}]}", mapper.writeValueAsString(board));
        } catch (JsonProcessingException e) {
            fail();
        }
    }

}
