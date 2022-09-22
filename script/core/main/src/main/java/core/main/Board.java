package core.main;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private String boardName;
    private String description;
    private List<Note> notes = new ArrayList<>();

    public Board(String boardName, String description) {
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
        this.boardName = boardName;
    }

    public void setBoardDescription(String description) {
        this.description = description;
    }

    public void addNote(Note note) {
        notes.add(note);
    }

}
