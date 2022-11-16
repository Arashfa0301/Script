package core.main;

import java.util.ArrayList;
import java.util.List;

public class Board {
    public static final int MAX_ELEMENTS = 256;
    private String boardName, description;
    private List<Note> notes = new ArrayList<>();
    private List<Checklist> checklists = new ArrayList<>();

    /**
     * Creates a Board object.
     *
     * @param boardName   A String that becomes the name of the board
     * @param description A short description of the contents of the board
     */
    public Board(String boardName, String description) {
        checkValidInputString(boardName);
        this.boardName = boardName;
        this.description = description;
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
        if (note == null || getNotes().size() + getChecklists().size() >= MAX_ELEMENTS) {
            throw new IllegalArgumentException("The number of notes exceed the maximum amount");
        }
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
        if (checklist == null || getChecklists().size() + getNotes().size() >= MAX_ELEMENTS) {
            throw new IllegalArgumentException("The number of checklits exceed the maximum amount");
        }
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
        if (input.isEmpty()) {
            throw new IllegalArgumentException("Invalid argument");
        }
    }

}
