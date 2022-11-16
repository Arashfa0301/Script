package core.main;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@JsonPropertyOrder({ "username", "firstName", "lastName", "boards" })
public class User {

    private String username;
    private String password;
    private String firstName;
    private String lastName;

    private List<Board> boards = new ArrayList<>();

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public User(@JsonProperty("username") String username, @JsonProperty("password") String password,
            @JsonProperty("firstName") String firstName, @JsonProperty("lastName") String lastName) {
        String[] inputs = new String[] { username, firstName, lastName };
        for (String input : inputs) {
            if (!Pattern.matches("^[A-Za-z0-9_.]+$", input) || input.isBlank() || input.isEmpty()) {
                throw new IllegalArgumentException(String.format("Invalid input: %s", input));
            }
        }
        if (password.isBlank() || password.isEmpty()) {
            throw new IllegalArgumentException("Invalid password");
        }
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @JsonGetter("username")
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @JsonSetter("password")
    public void setPassword(String password) {
        this.password = password;
    }

    @JsonGetter("firstName")
    public String getFirstName() {
        return firstName;
    }

    @JsonGetter("lastName")
    public String getLastName() {
        return lastName;
    }

    public List<Board> getBoards() {
        return boards;
    }

    private Board getBoard(String boardname) throws IllegalArgumentException {
        for (Board board : boards) {
            if (board.getBoardName().equals(boardname)) {
                return board;
            }
        }
        throw new IllegalArgumentException("Board not found");
    }

    public void setBoards(List<Board> boards) {
        this.boards = boards;
    }

    public void addBoard(String boardname) {
        for (Board board : boards) {
            if (board.getBoardName().equals(boardname)) {
                throw new IllegalArgumentException("Board already exists");
            }
        }
        boards.add(new Board(boardname, ""));
    }

    public void removeBoard(String boardname) throws IllegalArgumentException {
        boards.remove(getBoard(boardname));
    }

    public void addNote(String boardname, String listname) throws IllegalArgumentException {
        getBoard(boardname).addNote(new Note());
    }

    public void removeNote(String boardname, String listname, int index) throws IllegalArgumentException {
        getBoard(boardname).getNotes().remove(index);
    }
}
