package data;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.TextNode;

import core.main.Board;

public class BoardDeserializer extends JsonDeserializer<Board> {

    private NoteDeserializer noteDeserializer = new NoteDeserializer();

    @Override
    public Board deserialize(JsonParser paser, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        TreeNode treeNode = paser.getCodec().readTree(paser);
        if (treeNode instanceof ObjectNode) {
            ObjectNode objectNode = (ObjectNode) treeNode;
            Board board = new Board();

            // gets the boardName's node and sets it as the board's boardName
            JsonNode boardNameNode = objectNode.get("boardName");
            if (boardNameNode instanceof TextNode) {
                board.setBoardName(((TextNode) boardNameNode).asText());
            }

            // gets the boardName's node and sets it as the board's boardName
            JsonNode descriptionNode = objectNode.get("description");
            if (descriptionNode instanceof TextNode) {
                board.setBoardDescription(((TextNode) descriptionNode).asText());
            }

            // gets the boardName's node and sets it as the board's boardName
            JsonNode notesNode = objectNode.get("notes");
            if (notesNode instanceof ArrayNode) {
                for (JsonNode noteNode : ((ArrayNode) notesNode)) {
                    // TODO: maybe check if item == null later
                    board.addNote(noteDeserializer.deserialize(noteNode));
                }
            }
            return board;
        }
        return null;
    }

}