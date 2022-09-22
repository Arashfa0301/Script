package data;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import core.main.Note;

public class NoteSerializer extends JsonSerializer<Note> {

    @Override
    public void serialize(Note note, JsonGenerator jGen, SerializerProvider serializers) throws IOException {

        jGen.writeStartObject();
        jGen.writeStringField("title", note.getTitle());
        jGen.writeStringField("test", note.getText());
        jGen.writeEndObject();
    }

}