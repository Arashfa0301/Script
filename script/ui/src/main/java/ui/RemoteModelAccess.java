package ui;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import core.main.Board;
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

    /**
     * Registers a new user with given username, password, first name and last name
     * using the REST Api.
     *
     * @param username  the username of the user
     * @param password  the password of the user
     * @param firstName the first name of the user
     * @param lastName  the last name of the user
     * @throws RuntimeException if the method recieves wrong status code and the
     *                          user could not be created
     * 
     * @see HttpRequest
     * @see HttpResponse
     */
    @Override
    public void register(String firstName, String lastName, String username, String password) throws RuntimeException {
        System.out.println(String.format("[RemoteModelAccess] Creating new user with username %s", username));
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

    /**
     * Gets the user with given username and password using the REST Api.
     *
     * @param username the username of the user
     * @param password the password of the user
     * @return User a user object
     * @throws IllegalArgumentException if the method recieves wrong status code and
     *                                  the user could not be recieved
     * 
     * @see HttpRequest
     * @see HttpResponse
     * @see User
     */
    @Override
    public User getUser(String username, String password) {
        System.out.println(String.format("[RemoteModelAccess] Getting user class for %s", username));
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

    /**
     * Creates a board for the specified user (by username and password) in the
     * system using the REST Api.
     *
     * @param boardname the name of the board
     * @param username  the username of the user
     * @param password  the password of the user
     * @throws IllegalArgumentException if the method recieves wrong status code and
     *                                  the board could not be created
     * 
     * @see HttpRequest
     * @see HttpResponse
     */
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

    /**
     * Removes a board for the specified user (by username and password) in the
     * system using the REST Api.
     *
     * @param boardname the name of the board
     * @param username  the username of the user
     * @param password  the password of the user
     * @throws IllegalArgumentException if the method recieves wrong status code and
     *                                  the board could not be removed
     * 
     * @see HttpRequest
     * @see HttpResponse
     */
    @Override
    public void removeBoard(String boardname, String username, String password) throws IllegalArgumentException {
        System.out.println(String.format("[RemoteModelAccess] Removing board %s for user %s", boardname, username));
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(endpointBaseUri.resolve(String.format("/boards/remove/%s/", boardname)))
                .header(ACCEPT_HEADER, APPLICATION_JSON)
                .header(CONTENT_TYPE_HEADER, APPLICATION_JSON)
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

    /**
     * Renames a board for the specified user (by username and password) in the
     * system using the REST Api.
     *
     * @param oldBoardName the name of the board
     * @param newBoardName the name of the board
     * @param username     the username of the user
     * @param password     the password of the user
     * @throws RuntimeException if the method recieves wrong status code and
     *                          the board could not be renamed
     * 
     * @see HttpRequest
     * @see HttpResponse
     */
    @Override
    public void renameBoard(String oldBoardName, String newBoardName, String username, String password)
            throws IllegalArgumentException {
        System.out.println(String.format("[RemoteModelAccess] Renaming board %s to %s", oldBoardName, newBoardName));
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(endpointBaseUri.resolve(String.format("/boards/rename/%s/%s/", oldBoardName, newBoardName)))
                .header(ACCEPT_HEADER, APPLICATION_JSON)
                .header(CONTENT_TYPE_HEADER, APPLICATION_JSON)
                .header("Authorization", "Basic " + Base64.getEncoder()
                        .encodeToString((username + ":" + password).getBytes(StandardCharsets.UTF_8)))
                .POST(HttpRequest.BodyPublishers
                        .ofString(""))
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                throw new RuntimeException("Unexpected response code: " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Puts (updates) a board object for the specified user (by username and
     * password) in the
     * system using the REST Api.
     *
     * @param board    the board object
     * @param username the username of the user
     * @param password the password of the user
     * @throws RuntimeException if the method recieves wrong status code and
     *                          the board could not be updated
     * 
     * @see HttpRequest
     * @see HttpResponse
     */
    @Override
    public void putBoard(Board board, String username, String password) throws RuntimeException {
        System.out.println(
                String.format("[RemoteModelAccess] Updating board %s for user %s", board.getName(), username));
        HttpClient client = HttpClient.newHttpClient();
        String boardJson;
        try {
            boardJson = objectMapper.writeValueAsString(board);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        HttpRequest request;
        request = HttpRequest.newBuilder()
                .uri(endpointBaseUri.resolve(String.format("/board/%s/", board.getName())))
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
}