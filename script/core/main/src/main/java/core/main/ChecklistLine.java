package core.main;

<<<<<<< HEAD
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

=======
>>>>>>> parent of 1fa8d64... Connect ScriptController with REST Api
public class ChecklistLine {
    private String line = "";
    private Boolean checked = false;

    /**
     * Creates a new ChecklistLine object, <code>line = ""</code> and
     * <code>checked = false</code> by default.
     */
    public ChecklistLine() {
    }

    /**
     * Gets the text of the ChecklistLine object.
     *
     * @return the text of the ChecklistLine object as a String
     */
<<<<<<< HEAD
    @JsonGetter("line")
=======
>>>>>>> parent of 1fa8d64... Connect ScriptController with REST Api
    public String getLine() {
        return line;
    }

    /**
<<<<<<< HEAD
     * Sets the text of a ChecklistLine.
     *
     * @param line a String to set as text
     */
    @JsonSetter("line")
    protected void setLine(String line) {
        this.line = line;
    }

    /**
     * Checks if the ChecklistLine object is checked or not.
     *
     * @return the boolean value of the ChecklistLine's <code>checked</code> boolean
     */
    @JsonGetter("checked")
    public Boolean getChecked() {
        return checked;
=======
     * Checks if the ChecklistLine object is checked or not.
     *
     * @return the boolean value of the ChecklistLine's <code>checked</code> boolean
     */
    public Boolean getChecked() {
        return checked;
    }

    /**
     * Sets the text of a ChecklistLine.
     *
     * @param line a String to set as text
     */
    protected void setLine(String line) {
        this.line = line;
>>>>>>> parent of 1fa8d64... Connect ScriptController with REST Api
    }

    /**
     * Sets the boolean value of checked.
     *
     * @param checked a boolean value to determine if a ChecklistItem is checked or
     *                not
     */
<<<<<<< HEAD
    @JsonSetter("checked")
    protected void setChecked(Boolean checked) {
=======
    protected void checked(Boolean checked) {
>>>>>>> parent of 1fa8d64... Connect ScriptController with REST Api
        this.checked = checked;
    }

}
