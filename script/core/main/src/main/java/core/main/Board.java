package core.main;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private static final int MAX_NOTES = 6;
    private String boardName, description;
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
        if (note != null && getNotes().size() < MAX_NOTES) {
            notes.add(note);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public Note getNote(String noteTitle) {
        return notes.stream().filter(note -> note.getTitle().equals(noteTitle)).findAny().get();
    }

    public void removeNote(String noteTitle) {
        notes.remove(getNote(noteTitle));
    }
}
