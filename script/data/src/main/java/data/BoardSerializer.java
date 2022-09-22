package data;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import core.main.Board;
import core.main.Note;

public class BoardSerializer extends JsonSerializer<Board> {

    @Override
    public void serialize(Board board, JsonGenerator jGen, SerializerProvider arg2) throws IOException {

        jGen.writeStartObject();

        jGen.writeStringField("boardName", board.getBoardName());
        jGen.writeStringField("description", board.getBoardDescription());

        jGen.writeArrayFieldStart("notes");
        for (Note note : board.getNotes()) {
            jGen.writeObject(note);
        }
        jGen.writeEndArray();

        jGen.writeEndObject();

    }

}