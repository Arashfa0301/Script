package core.main;

import java.util.ArrayList;
import java.util.List;

public class Checklist extends BoardElement {

    private List<ChecklistLine> checklistLines = new ArrayList<>();

    public Checklist() {
        setTitle("");
    }

    /**
     * A getter than returns a copy of the checklistlines list. The use of copy is
     * for security purposes.
     *
     * @return an array of checklines
     *
     */
    public List<ChecklistLine> getChecklistLines() {
        return new ArrayList<ChecklistLine>(checklistLines);
    }

    public void addChecklistLine() {
        checklistLines.add(new ChecklistLine());
    }

    public void setChecklistline(int index, String line) {
        checklistLines.get(index).setLine(line);
    }

    public void setChecklistChecked(int index, Boolean checked) {
        checklistLines.get(index).checked(checked);
    }

    public boolean isEmpty() {
        return (getTitle().isBlank() && checklistLines.isEmpty());
    }

}
