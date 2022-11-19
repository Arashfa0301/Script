package core.main;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Board {
    public static final int MAX_ELEMENTS = 100;
    private String name, description;
    private List<Note> notes = new ArrayList<>();
    private List<Checklist> checklists = new ArrayList<>();

    /**
     * Creates a Board object.
     *
     * @param name        A String that becomes the name of the board
     * @param description A short description of the contents of the board
     * @param notes       A list of notes that become the notes of the board
     * @param checklists  A list of checklists that become the checklists of the
     *                    board
     * @throws NullPointerException if either <code>notes</code> or
     *                              <code>checklists</code> is <code>null</code>
     */
    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public Board(@JsonProperty("boardName") String name, @JsonProperty("description") String description,
            @JsonProperty("notes") List<Note> notes, @JsonProperty("checklists") List<Checklist> checklists) {
        this.name = name;
        this.description = description;
        this.notes = notes;
        this.checklists = checklists;
    }

    /**
     * Obtains the name of a board.
     *
     * @return the name of the board
     */
    public String getName() {
        return name;
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
     * @param name a String that will become the new name of the board.
     */
    public void setBoardName(String name) {
        this.name = name;
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
     * @see Board#checkAddBoardElement(BoardElement)
     */
    public void addNote(Note note) {
        checkAddBoardElement(note);
        notes.add(note);
    }

    /**
     * Adds a checklist to the board.
     *
     * @param checklist a Checklist object to add to the board
     * @see Board#checkAddBoardElement(BoardElement)
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
            throw new IllegalArgumentException("The number of checklits exceed the maximum amount.");
        }
    }
}
