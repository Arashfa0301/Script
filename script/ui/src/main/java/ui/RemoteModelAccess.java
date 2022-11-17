package ui;

<<<<<<< HEAD
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import core.main.Board;
=======
import com.fasterxml.jackson.databind.ObjectMapper;
>>>>>>> parent of 1fa8d64... Connect ScriptController with REST Api
import core.main.User;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Class that centralizes access to Script API. Makes it easier to support
 * transparent use of a
 * REST API.
 */
public class RemoteModelAccess implements ModelAccess {

    private static final String APPLICATION_JSON = "application/json";

    private static final String ACCEPT_HEADER = "Accept";

    private static final String CONTENT_TYPE_HEADER = "Content-Type";

    private final URI endpointBaseUri = URI.create("http://localhost:8080");

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void register(String firstName, String lastName, String username, String password) throws RuntimeException {
<<<<<<< HEAD
        System.out.println(String.format("[RemoteModelAccess] Creating new user with username %s", username));
=======
>>>>>>> parent of 1fa8d64... Connect ScriptController with REST Api
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(endpointBaseUri.resolve("/auth/register/"))
                .header(ACCEPT_HEADER, APPLICATION_JSON)
                .header(CONTENT_TYPE_HEADER, APPLICATION_JSON)
                .POST(HttpRequest.BodyPublishers
                        .ofString("{\"firstName\":\"" + firstName + "\",\"lastName\":\"" + lastName
                                + "\",\"username\":\"" + username + "\",\"password\":\"" + password + "\"}"))
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 201) {
                throw new RuntimeException("Unexpected response code: " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User getUser(String username, String password) {
<<<<<<< HEAD
        System.out.println(String.format("[RemoteModelAccess] Getting user class for %s", username));
=======
>>>>>>> parent of 1fa8d64... Connect ScriptController with REST Api
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(endpointBaseUri.resolve(String.format("/user/%s/", username)))
                .header(ACCEPT_HEADER, APPLICATION_JSON)
                .header(CONTENT_TYPE_HEADER, APPLICATION_JSON)
                // add basic auth header
                .header("Authorization", "Basic " + Base64.getEncoder()
                        .encodeToString((username + ":" + password).getBytes(StandardCharsets.UTF_8)))
                .GET()
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                throw new IllegalArgumentException("Unexpected response code: " + response.statusCode());
            }
            String responseString = response.body();
            User user = objectMapper.readValue(responseString, User.class);
            return user;
        } catch (IOException | InterruptedException e) {
            throw new IllegalArgumentException(e);
        }
    }
<<<<<<< HEAD

    @Override
    public void createBoard(String boardname, String username, String password) throws IllegalArgumentException {
        System.out.println(String.format("[RemoteModelAccess] Creating board %s for user %s", boardname, username));
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(endpointBaseUri.resolve(String.format("/boards/create/%s/", boardname)))
                .header(ACCEPT_HEADER, APPLICATION_JSON)
                .header(CONTENT_TYPE_HEADER, APPLICATION_JSON)
                // add basic auth header
                .header("Authorization", "Basic " + Base64.getEncoder()
                        .encodeToString((username + ":" + password).getBytes(StandardCharsets.UTF_8)))
                .GET()
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 201) {
                throw new IllegalArgumentException("Unexpected response code: " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void removeBoard(String boardname, String username, String password) throws IllegalArgumentException {
        System.out.println(String.format("[RemoteModelAccess] Removing board %s for user %s", boardname, username));
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(endpointBaseUri.resolve(String.format("/boards/remove/%s/", boardname)))
                .header(ACCEPT_HEADER, APPLICATION_JSON)
                .header(CONTENT_TYPE_HEADER, APPLICATION_JSON)
                // add basic auth header
                .header("Authorization", "Basic " + Base64.getEncoder()
                        .encodeToString((username + ":" + password).getBytes(StandardCharsets.UTF_8)))
                .GET()
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                throw new IllegalArgumentException("Unexpected response code: " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public void putBoard(Board board, String username, String password) throws RuntimeException {
        System.out.println(
                String.format("[RemoteModelAccess] Updating board %s for user %s", board.getBoardName(), username));
        HttpClient client = HttpClient.newHttpClient();
        String boardJson;
        try {
            boardJson = objectMapper.writeValueAsString(board);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        HttpRequest request;
        request = HttpRequest.newBuilder()
                .uri(endpointBaseUri.resolve(String.format("/board/%s/", board.getBoardName())))
                .header(ACCEPT_HEADER, APPLICATION_JSON)
                .header(CONTENT_TYPE_HEADER, APPLICATION_JSON)
                .header("Authorization", "Basic " + Base64.getEncoder()
                        .encodeToString((username + ":" + password).getBytes(StandardCharsets.UTF_8)))
                .PUT(HttpRequest.BodyPublishers
                        .ofString(boardJson))
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 201) {
                throw new RuntimeException("Unexpected response code: " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
=======
>>>>>>> parent of 1fa8d64... Connect ScriptController with REST Api
}