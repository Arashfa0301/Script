package core.main;

import java.util.ArrayList;
import java.util.List;

public class Board {
    public static final int MAX_ELEMENTS = 256;
    private String boardName, description;
    private List<Note> notes = new ArrayList<>();
    private List<Checklist> checklists = new ArrayList<>();

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

    public List<Checklist> getChecklists() {
        return checklists;
    }

    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }

    public void setBoardDescription(String description) {
        this.description = description;
    }

    public void addNote(Note note) {
        if (note != null && getNotes().size() + getChecklists().size() < MAX_ELEMENTS) {
            notes.add(note);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public BoardElement getNote(String title) {
        return notes.stream().filter(note -> note.getTitle().equals(title)).findAny().get();
    }

    public void removeNote(String title) {
        notes.remove(getNote(title));
    }

    public void addchecklist(Checklist checklist) {
        if (checklist != null && getChecklists().size() + getNotes().size() < MAX_ELEMENTS) {
            checklists.add(checklist);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public BoardElement getChecklist(String title) {
        return checklists.stream().filter(checklist -> checklist.getTitle().equals(title)).findAny().get();
    }

    public void removechecklist(String title) {
        checklists.remove(getChecklist(title));
    }

}
