package data;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;

import core.main.Note;

public class NoteDeserializer extends JsonDeserializer<Note> {

    @Override
    public Note deserialize(JsonParser paser, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {

        TreeNode treeNode = paser.getCodec().readTree(paser);
        return deserialize((JsonNode) treeNode);
    }

    public Note deserialize(JsonNode jsonNode) {
        if (jsonNode instanceof ObjectNode) {
            ObjectNode objectNode = (ObjectNode) jsonNode;
            Note note = new Note();

            // gets the title node and sets it as the note's title
            JsonNode titleNode = objectNode.get("title");
            if (titleNode instanceof TextNode) {
                note.setTitle(((TextNode) titleNode).asText());
            }

            // gets the text node and sets it as the note's text
            JsonNode textNode = objectNode.get("text");
            if (textNode instanceof TextNode) {
                note.setText(((TextNode) textNode).asText());
            }
            return note;

        }
        return null;
    }

}