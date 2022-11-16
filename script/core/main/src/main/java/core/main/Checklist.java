package core.main;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Checklist extends BoardElement {

    private List<ChecklistLine> checklistLines = new ArrayList<>();

    /**
     * Creates a Checklist object. Title is <code>""</code> by default.
     */
    public Checklist() {
        super();
    }

    /**
     * A getter than returns a copy of the checklistlines list. The use of copy is
     * for security purposes.
     *
     * @return an array of checklines
     *
     */
    public List<ChecklistLine> getChecklistLines() {
        orderLines();
        return new ArrayList<ChecklistLine>(checklistLines);
    }

    /**
     * Adds a ChecklistLine to the list of ChecklistLines.
     */
    public void addChecklistLine() {
        checklistLines.add(new ChecklistLine());
    }

    /**
     * Changes the content of a ChecklistLine to the given index.
     *
     * @param index an integer that corresponds to the index of the ChecklistLine
     *              that should be changed
     * @param line  a String that will become the new content of the ChecklistLine
     */
    public void setChecklistline(int index, String line) {
        checklistLines.get(index).setLine(line);
    }

    /**
     * Changes the checked boolean value of checked for the ChecklistLine to the
     * given index.
     *
     * @param index   an integer that corresponds to the index of the ChecklistLine
     *                that should be changed
     * @param checked the boolean value that checked will have
     */
    public void setChecklistChecked(int index, Boolean checked) {
        checklistLines.get(index).checked(checked);

    }

    /**
     * Checks if a Checklist is empty.
     *
     * @return <code>true</code> if <code>isBlank()</code> returns <code>true</code>
     *         for <code>title</code>, and <code>isEmpty()</code> returns
     *         <code>true</code> for the list <code>checklistLines</code>
     */
    public boolean isEmpty() {
        return getTitle().isBlank() && checklistLines.isEmpty();
    }

    public void orderLines() {
        checklistLines = Stream.concat(
                checklistLines.stream().filter(line -> !line.isChecked()).collect(Collectors.toList()).stream(),
                checklistLines.stream().filter(line -> line.isChecked()).collect(Collectors.toList())
                        .stream())
                .collect(Collectors.toList());

    }

    /**
     * Removes the ChecklistLine found at the given index.
     *
     * @param i an integer which determines which index in the list checklistLines
     *          to remove
     */
    public void removeChecklistLine(int i) {
        orderLines();
        checklistLines.remove(i);
    }

}
