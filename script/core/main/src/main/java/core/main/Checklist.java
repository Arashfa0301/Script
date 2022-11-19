package core.main;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Checklist extends BoardElement {
    public static final int MAX_LINE_COUNT = 40;
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
    @JsonGetter("checklistLines")
    public List<ChecklistLine> getChecklistLines() {
        Collections.sort(checklistLines, new Comparator<ChecklistLine>() {

            @Override
            public int compare(ChecklistLine o1, ChecklistLine o2) {
                return (o1.isChecked().equals(o2.isChecked())) ? 1 : o1.isChecked() ? 1 : -1;
            }

        });
        return new ArrayList<ChecklistLine>(checklistLines);
    }

    @JsonSetter("checklistLines")
    public void setlistLines(ArrayList<ChecklistLine> checklistLines) {
        this.checklistLines = checklistLines;
    }

    /**
     * Adds a ChecklistLine to the list of ChecklistLines.
     *
     * @throws IllegalStateException if the size <code>checklistLines</code> is at
     *                               it's limit
     */
    public void addChecklistLine() {
        if (checklistLines.size() >= MAX_LINE_COUNT) {
            throw new IllegalStateException("Lines can't exceed the max line count.");
        }
        checklistLines.add(new ChecklistLine());
    }

    /**
     * Changes the content of a ChecklistLine to the given index.
     *
     * @param index an integer that corresponds to the index of the ChecklistLine
     *              that should be changed
     * @param line  a String that will become the new content of the ChecklistLine
     * @see Checklist#checkIndexOutofbounds(int)
     */
    public void setChecklistline(int index, String line) {
        checkIndexOutofbounds(index);
        checklistLines.get(index).setLine(line);
    }

    /**
     * Changes the checked boolean value of checked for the ChecklistLine to the
     * given index.
     *
     * @param index   an integer that corresponds to the index of the ChecklistLine
     *                that should be changed
     * @param checked the boolean value that checked will have
     * @see Checklist#checkIndexOutofbounds(int)
     */
    public void setChecklistChecked(int index, Boolean checked) {
        checkIndexOutofbounds(index);
        checklistLines.get(index).setChecked(checked);
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

    /**
     * Removes the ChecklistLine found at the given index.
     *
     * @param index which determines which index in the list checklistLines
     *              to remove
     * @see Checklist#checkIndexOutofbounds(int)
     */
    public void removeChecklistLine(int index) {
        checkIndexOutofbounds(index);
        checklistLines.remove(index);
    }

    private void checkIndexOutofbounds(int index) {
        if (index >= checklistLines.size() || index < 0) {
            throw new IllegalArgumentException("The input argument index is out of range.");
        }
    }

}
