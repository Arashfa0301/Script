package data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;

import com.fasterxml.jackson.core.util.VersionUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import core.main.Board;
import core.main.Note;
import core.main.User;

public class ScriptModule extends SimpleModule {
    private static final String FILE_NAME = "boards.json";
    private static final String FILE_PATH = System.getProperty("user.home") + "/resources";
    private static final String NAME = "ScriptModule";
    private static final VersionUtil VERSION_UTIL = new VersionUtil() {
    };

    public ScriptModule() {
        super(NAME, VERSION_UTIL.version());
        addSerializer(Note.class, new NoteSerializer());
        addSerializer(Board.class, new BoardSerializer());
        addSerializer(User.class, new UserSerializer());
        addDeserializer(Note.class, new NoteDeserializer());
        addDeserializer(Board.class, new BoardDeserializer());
        addDeserializer(User.class, new UserDeserilizer());

    }

    public void write(User user) {

        try (FileWriter fw = new FileWriter(FILE_PATH + "/" + FILE_NAME)) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new ScriptModule());
            mapper.writeValue(fw, user);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public User read() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new ScriptModule());
            User user = mapper.readValue(Paths.get(FILE_PATH + "/" + FILE_NAME).toFile(), User.class);
            return user;
        } catch (FileNotFoundException ex) {
            boolean isFileCreated = false;
            File file = new File(FILE_PATH + "/" + FILE_NAME);
            try {
                new File(FILE_PATH).mkdir();
                isFileCreated = file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (isFileCreated) {
                System.out.println("created new file: " + FILE_PATH + "/" + FILE_NAME);
            }
            return new User();
        } catch (IOException e) {
            e.printStackTrace();
            return new User();
        }
    }

    public static void main(String[] args) {
        User user = new User("Arasrsadfasdfljasdlkfjapsldjf");
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
        user.addBoard(board);

        ScriptModule scriptModule = new ScriptModule();
        scriptModule.write(user);
        scriptModule.write(user);
        scriptModule.write(user);
        scriptModule.write(user);
        scriptModule.write(user);
        System.out.println("\n heyyy");
        User user1 = scriptModule.read();
        System.out.println(user1.toString());

    }
}