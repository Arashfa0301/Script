package core.main;

import java.util.ArrayList;
import java.util.List;

public class Checklist extends BoardElement {

    private List<ChecklistLine> checklistLines = new ArrayList<>();

    public Checklist() {
        setTitle("");
    }

    public void addChecklistLine() {
        checklistLines.add(new ChecklistLine());
    }

    public boolean isEmpty() {
        return (getTitle().isBlank() && checklistLines.isEmpty());
    }

    public List<ChecklistLine> getChecklistLines() {
        return checklistLines;
    }

}
