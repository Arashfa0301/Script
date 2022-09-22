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

import core.main.User;

public class UserDeserilizer extends JsonDeserializer<User> {

    private BoardDeserializer boardDeserializer = new BoardDeserializer();

    @Override
    public User deserialize(JsonParser parser, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        TreeNode treeNode = parser.getCodec().readTree(parser);
        if (treeNode instanceof ObjectNode) {
            ObjectNode objectNode = (ObjectNode) treeNode;
            User user = new User();

            // gets the name's node and sets it as the user's name
            JsonNode nameNode = objectNode.get("name");
            if (nameNode instanceof TextNode) {
                user.setName(((TextNode) nameNode).asText());
            }

            // gets the boards's node and sets it as the user's boards
            JsonNode boardsNode = objectNode.get("boards");
            if (boardsNode instanceof ArrayNode) {
                for (JsonNode boardNode : ((ArrayNode) boardsNode)) {
                    // TODO: maybe check if item == null later
                    user.addBoard((boardDeserializer.deserialize(boardNode)));
                }
            }
            return user;
        }
        return null;
    }

}
