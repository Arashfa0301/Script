package core.main;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private String boardName, description;
    private List<Note> notes = new ArrayList<>();
    private final int MAX_NOTES = 256;

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
        if (note != null && getNotes().size() < MAX_NOTES)
            notes.add(note);
    }

    public Note getNote(String noteTitle) {
        return notes.stream().filter(note -> note.getTitle().equals(noteTitle)).findAny().get();
    }

    public void removeBoard(String noteTitle) {
        notes.remove(getNote(noteTitle));
    }

    @Override
    public String toString() {
        return notes.stream().map(i -> i.toString()).reduce("", (i, j) -> {
            return i + j + "\n";
        });
    }
}
