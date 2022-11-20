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

    public static final int MAX_BOARD_COUNT = 100;
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
     * Creates an empty user object.
     */
    public User() {
        username = "";
        password = "";
        firstName = "";
        lastName = "";
        boards = new ArrayList<>();
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

    /**
     * Adds the input board to the user's list of boards.
     *
     * @param boardname a string sepresenting the name of the board
     * @return the user
     * @throws IllegalStateException if the size of <code>boards</code> is already
     *                               at it's limit
     */
    public User addBoard(String boardname) {
        if (boards.size() >= MAX_BOARD_COUNT) {
            throw new IllegalStateException("Amount of boards cannot exceed 100.");
        }
        Board board = new Board(boardname, "", new ArrayList<Note>(), new ArrayList<Checklist>());
        boards.add(board);
        return this;
    }

    /**
     * Updates a board in user's list of boards by the name of input board.
     *
     * @param board a board that will be updated in the user's board list
     * @return the user
     * @see User#checkUserContainsBoard(String)
     */
    public User putBoard(Board board) {
        checkUserContainsBoard(board.getName());
        boards.set(
                boards.indexOf(
                        getBoard(board.getName())),
                board);
        return this;
    }

    /**
     * Removes the inut board from te user's list of boards.
     *
     * @param boardname a string of the board's name
     * @return the user
     * @see User#checkUserContainsBoard(String)
     */
    public User removeBoard(String boardname) throws IllegalArgumentException {
        checkUserContainsBoard(boardname);
        boards.remove(getBoard(boardname));
        return this;
    }

    /**
     * Rename a board in user's list of boards by it's oldname and newname.
     *
     * @param oldBoardName a string of the board's old/current name
     * @param newBoardName a string of the board's new/future name
     * @return the user
     * @see User#checkUserContainsBoard(String)
     */
    public User renameBoard(String oldBoardName, String newBoardName) throws IllegalArgumentException {
        checkUserContainsBoard(oldBoardName);
        getBoard(oldBoardName).setName(newBoardName);
        return this;
    }

    /**
     * Finds and returns the board, from user's list of board, specified by the
     * input.
     *
     * @param boardName a string of the board's name
     * @return board specified by the input boardname
     * @see User#checkUserContainsBoard(String)
     */
    private Board getBoard(String boardname) {
        checkUserContainsBoard(boardname);
        return boards.stream().filter(b -> b.getName().equals(boardname)).findAny()
                .get();
    }

    /**
     * Checks if the specified board exists in the user's list of board.
     *
     * @param boardName a string of the board's name
     * @throws IllegalArgumentException if <code>boardname</code> is not in
     *                                  <code>boards</code>
     */
    private void checkUserContainsBoard(String boardName) {
        if (!boards.stream().anyMatch(b -> b.getName().equals(boardName))) {
            throw new IllegalArgumentException("Board not found.");
        }
    }
}
