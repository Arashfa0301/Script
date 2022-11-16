package core.main;

import java.util.ArrayList;
import java.util.List;

public class Board {
    public static final int MAX_ELEMENTS = 256;
    private String boardName, description;
    private List<Note> notes = new ArrayList<>();
    private List<Checklist> checklists = new ArrayList<>();

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
        return new ArrayList<Note>(notes);
    }

    public List<Checklist> getChecklists() {
        return new ArrayList<Checklist>(checklists);
    }

    public void setBoardName(String boardName) {
        checkValidInputString(boardName);
        this.boardName = boardName;
    }

    public void setBoardDescription(String description) {
        this.description = description;
    }

    public void addNote(Note note) {
        if (note == null && getNotes().size() + getChecklists().size() >= MAX_ELEMENTS) {
            throw new IllegalArgumentException("The number of notes exceed the maximum amount");
        }
        notes.add(note);
    }

    public void addChecklist(Checklist checklist) {
        if (checklist == null && getChecklists().size() + getNotes().size() >= MAX_ELEMENTS) {
            throw new IllegalArgumentException("The number of checklits exceed the maximum amount");
        }
        checklists.add(checklist);
    }

    public void clearCheckLists() {
        checklists.clear();
    }

    public void clearNotes() {
        notes.clear();
    }

    private void checkValidInputString(String input) {
        if (input.isEmpty()) {
            throw new IllegalArgumentException("Innvalid argument");
        }
    }

}
