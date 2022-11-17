package core.main;

<<<<<<< HEAD
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSetter;

=======
>>>>>>> parent of 1fa8d64... Connect ScriptController with REST Api
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Checklist extends BoardElement {

    private List<ChecklistLine> checklistLines = new ArrayList<>();
<<<<<<< HEAD

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
        orderLines();
        return new ArrayList<ChecklistLine>(checklistLines);
    }

    @JsonSetter("checklistLines")
    public void setlistLines(ArrayList<ChecklistLine> checklistLines) {
        this.checklistLines = checklistLines;
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
=======

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
>>>>>>> parent of 1fa8d64... Connect ScriptController with REST Api
     * Changes the checked boolean value of checked for the ChecklistLine to the
     * given index.
     *
     * @param index   an integer that corresponds to the index of the ChecklistLine
     *                that should be changed
     * @param checked the boolean value that checked will have
     */
    public void setChecklistChecked(int index, Boolean checked) {
<<<<<<< HEAD
        checklistLines.get(index).setChecked(checked);
=======
        checklistLines.get(index).checked(checked);
>>>>>>> parent of 1fa8d64... Connect ScriptController with REST Api
    }

    /**
     * Checks if a Checklist is empty.
     *
     * @return <code>true</code> if <code>isBlank()</code> returns <code>true</code>
     *         for <code>title</code>, and <code>isEmpty()</code> returns
     *         <code>true</code> for the list <code>checklistLines</code>
     */
    public boolean isEmpty() {
        return (getTitle().isBlank() && checklistLines.isEmpty());
    }

    /**
     * Orders the lines in a Checklist depending on wether or not their
     * <code>checked</code> boolean is <code>true</code> or <code>false</code>.
     */
    public void orderLines() {
        checklistLines = Stream.concat(
                checklistLines.stream().filter(line -> !line.getChecked()).collect(Collectors.toList()).stream(),
                checklistLines.stream().filter(line -> line.getChecked()).collect(Collectors.toList())
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
