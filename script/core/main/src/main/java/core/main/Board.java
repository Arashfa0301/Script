package core.main;

import java.util.ArrayList;
import java.util.List;

public class Board {
    public static final int MAX_NOTES = 256;
    private String boardName, description;
    private List<Note> notes = new ArrayList<>();

    public Board(String boardName, String description) {
        checkValidInputString(boardName);
        this.boardName = boardName;
        this.description = description;
    }

    public String getBoardName() {
        return boardName;
    }

    public String getBoardDescription() {
        return description;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setBoardName(String boardName) {
        checkValidInputString(boardName);
        this.boardName = boardName;
    }

    public void setBoardDescription(String description) {
        this.description = description;
    }

    public void addNote(Note note) {
        if (note == null || getNotes().size() == MAX_NOTES) {
            throw new IllegalArgumentException();
        }
        notes.add(note);
    }

    private void checkValidInputString(String inputString) {
        if (inputString.isBlank() || inputString.isEmpty()) {
            throw new IllegalArgumentException("The input string is innvalid");
        }
    }
}
