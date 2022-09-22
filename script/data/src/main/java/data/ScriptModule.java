package data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.util.VersionUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import core.main.Board;
import core.main.Note;

public class ScriptModule extends SimpleModule {
    private static final String NAME = "ScriptModule";
    private static final VersionUtil VERSION_UTIL = new VersionUtil() {
    };

    public ScriptModule() {
        super(NAME, VERSION_UTIL.version());
        addSerializer(Board.class, new BoardSerializer());
        addSerializer(Note.class, new NoteSerializer());
        addDeserializer(Board.class, new BoardDeserializer());
        addDeserializer(Note.class, new NoteDeserializer());

    }

    public static void main(String[] args) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new ScriptModule());
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
            System.out.println(mapper.writeValueAsString(board));
            String json = mapper.writeValueAsString(board);
            Board board2 = mapper.readValue(json, Board.class);
            System.out.println(board2.toString());

        } catch (JsonProcessingException e) {
            System.out.println("Virket ikke");
            e.printStackTrace();
        }

    }
}