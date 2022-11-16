package core.main;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        orderLines();
        return checklistLines;
    }

    public void orderLines() {
        checklistLines = Stream.concat(
                checklistLines.stream().filter(line -> !line.getChecked()).collect(Collectors.toList()).stream(),
                checklistLines.stream().filter(line -> line.getChecked()).collect(Collectors.toList())
                        .stream())
                .collect(Collectors.toList());
    }

    public void removeChecklistLine(int i) {
        orderLines();
        checklistLines.remove(i);
    }

}
