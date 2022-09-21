package core.main;

import java.util.ArrayList;
import java.util.List;

public class Board {
    
    private List<Note> notes =  new ArrayList<>();
    private String boardName;

    public Board(String boardName) {
        this.boardName = boardName;
    }

    public String getBoardName() {
        return boardName;
    }

    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }

    public void addNote(Note note) {
        notes.add(note);
    }

    
}
