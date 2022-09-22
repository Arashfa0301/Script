package data;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import core.main.Board;
import core.main.User;

public class UserSerializer extends JsonSerializer<User> {

    @Override
    public void serialize(User user, JsonGenerator jGen, SerializerProvider arg2) throws IOException {

        jGen.writeStartObject();

        jGen.writeStringField("name", user.getName());

        jGen.writeArrayFieldStart("boards");
        for (Board board : user.getBoards()) {
            jGen.writeObject(board);
        }
        jGen.writeEndArray();

        jGen.writeEndObject();

    }

}