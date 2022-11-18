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

    private List<Board> boards;

    /**
     * Creates a User object if all input paramaters are valid.
     *
     * @param username  a String that will be the User's username
     * @param password  a String that will be the User's password
     * @param firstName a String that will be the User's first name
     * @param lastName  a String that will be the User's last name
     * @throws IllegalArgumentException if
     *                                  <code>!Pattern.matches("^[A-Za-z0-9_.]+$"</code>,
     *                                  <code>isBlank()</code> or
     *                                  <code>isEmpty()</code> is true for the
     *                                  String input of either
     *                                  <code>username</code>,
     *                                  <code>firstName</code> or
     *                                  <code>lastName</code>
     * @throws IllegalArgumentException if <code>isBlank()</code> or
     *                                  <code>isEmpty()</code> is <code>true</code>
     *                                  for the inputted String of
     *                                  <code>password</code>
     */
    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public User(@JsonProperty("username") String username, @JsonProperty("password") String password,
            @JsonProperty("firstName") String firstName, @JsonProperty("lastName") String lastName) {
        String[] inputs = new String[] { username, firstName, lastName };
        for (String input : inputs) {
            if (!Pattern.matches("^[A-Za-z0-9_.]+$", input) || input.isBlank()) {
                throw new IllegalArgumentException(String.format("Invalid input: %s", input));
            }
        }
        if (password.isBlank()) {
            throw new IllegalArgumentException("Invalid password");
        }
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.boards = new ArrayList<>();
    }

    /**
     * Gets the User's username.
     *
     * @return the User's username as a String
     */
    @JsonGetter("username")
    public String getUsername() {
        return username;
    }

    @JsonSetter("boards")
    public void setBoard(List<Board> boards) {
        this.boards = boards;
    }

    /**
     * Gets the User's password.
     *
     * @return the User's password as a String
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the User's password.
     *
     * @param password a String that will become the User's new password
     */
    @JsonSetter("password")
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the User's first name.
     *
     * @return the User's first name as a String
     */
    @JsonGetter("firstName")
    public String getFirstName() {
        return firstName;
    }

    /**
     * Gets the User's last name.
     *
     * @return the User's last name as a String
     */
    @JsonGetter("lastName")
    public String getLastName() {
        return lastName;
    }

    /**
     * Gets the User's boards.
     *
     * @return the User's boards as a List
     */
    public List<Board> getBoards() {
        return new ArrayList<Board>(boards);
    }

    public void addBoard(String boardname) throws IllegalArgumentException {
        boards.add(new Board(boardname, "", new ArrayList<Note>(), new ArrayList<Checklist>()));
    }

    public void putBoard(Board board) throws IllegalArgumentException {
        checkUserContainsBoard(board.getBoardName());
        boards.set(
                boards.indexOf(
                        getBoard(board.getBoardName())),
                board);
    }

    public void removeBoard(String boardname) throws IllegalArgumentException {
        checkUserContainsBoard(boardname);
        boards.remove(getBoard(boardname));
    }

    public void renameBoard(String oldBoardname, String newBoardname) throws IllegalArgumentException {
        checkUserContainsBoard(oldBoardname);
        getBoard(oldBoardname).setBoardName(newBoardname);
    }

    private Board getBoard(String boardname) throws IllegalArgumentException {
        return boards.stream().filter(b -> b.getBoardName().equals(boardname)).findAny()
                .orElseThrow(IllegalArgumentException::new);
    }

    private void checkUserContainsBoard(String boardName) {
        if (!boards.stream().anyMatch(b -> b.getBoardName().equals(boardName))) {
            throw new IllegalArgumentException("Board not found");
        }
    }

}
