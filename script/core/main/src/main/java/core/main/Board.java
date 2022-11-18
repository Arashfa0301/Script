package core.main;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Board {
    public static final int MAX_ELEMENTS = 100;
    private String boardName, description;
    private List<Note> notes = new ArrayList<>();
    private List<Checklist> checklists = new ArrayList<>();

    /**
     * Creates a Board object.
     *
     * @param boardName   A String that becomes the name of the board
     * @param description A short description of the contents of the board
     */
    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public Board(@JsonProperty("boardName") String boardName, @JsonProperty("description") String description,
            @JsonProperty("notes") List<Note> notes, @JsonProperty("checklists") List<Checklist> checklists) {
        checkValidInputString(boardName);
        this.boardName = boardName;
        this.description = description;
        this.notes = notes;
        this.checklists = checklists;
    }

    /**
     * Obtains the name of a board.
     *
     * @return the name of the board
     */
    public String getBoardName() {
        return boardName;
    }

    /**
     * Obtains the board's description.
     *
     * @return the description of the board
     */
    public String getBoardDescription() {
        return description;
    }

    /**
     * Gets all the notes that the board contains.
     *
     * @return a list of all Note objects contained in the board.
     */
    public List<Note> getNotes() {
        return new ArrayList<Note>(notes);
    }

    /**
     * Gets all the checklists that the board contains.
     *
     * @return a list of all Checklist objects contained in the board.
     */
    public List<Checklist> getChecklists() {
        return new ArrayList<Checklist>(checklists);
    }

    /**
     * Sets the name of the board. Validates the String input with
     * <code>checkValidInputString</code>.
     *
     * @param boardName a String that will become the new name of the board.
     * @see Board#checkValidInputString()
     */
    public void setBoardName(String boardName) {
        checkValidInputString(boardName);
        this.boardName = boardName;
    }

    /**
     * Method for setting the description of the board.
     *
     * @param description a String that will become the new description of the
     *                    board.
     */
    public void setBoardDescription(String description) {
        this.description = description;
    }

    /**
     * Adds a note to the board.
     *
     * @param note a Note object to add to the board
     * @throws IllegalArgumentException if <code>note == null</code> if the board
     *                                  already contains the maximum amount of
     *                                  elements
     */
    public void addNote(Note note) {
        checkAddBoardElement(note);
        notes.add(note);
    }

    /**
     * Adds a checklist to the board.
     *
     * @param checklist a Checklist object to add to the board
     * @throws IllegalArgumentException if <code>checklist == null</code> or the
     *                                  board already contains the maximum amount of
     *                                  elements
     */
    public void addChecklist(Checklist checklist) {
        checkAddBoardElement(checklist);
        checklists.add(checklist);
    }

    /**
     * Clears the list containing checklists.
     */
    public void clearCheckLists() {
        checklists.clear();
    }

    /**
     * Clears the list containing checklists.
     */
    public void clearNotes() {
        notes.clear();
    }

    /**
     * Checks if a String is valid.
     *
     * @param input a String to be checked
     * @throws IllegalArgumentException if <code>isEmpty()</code> returns
     *                                  <code>true</code> for <code>input</code>
     */
    private void checkValidInputString(String input) {
        if (input.isEmpty() || input.length() > 20) {
            throw new IllegalArgumentException("Invalid argument");
        }
    }

    /**
     * Checks if the boardElement is empty and if there exists free space for it in
     * either of the lists.
     *
     * @param input a BoardElement to be checked
     * @throws IllegalArgumentException if <code>input</code> is
     *                                  <code>null</code> or
     *                                  <code>getChecklists().size()</code> and
     *                                  <code>getNotes().size()</code> is bigger
     *                                  than <code>MAX_ELEMENTS</code>
     */
    private void checkAddBoardElement(BoardElement boardElement) {
        if (boardElement == null || getChecklists().size() + getNotes().size() >= MAX_ELEMENTS) {
            throw new IllegalArgumentException("The number of checklits exceed the maximum amount");
        }
    }

}
