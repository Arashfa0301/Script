package core.main;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

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
    @JsonGetter("line")
    public String getLine() {
        return line;
    }

    /**
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
    public Boolean isChecked() {
        return checked;
    }

    /**
     * Sets the boolean value of checked.
     *
     * @param checked a boolean value to determine if a ChecklistItem is checked or
     *                not
     */
    @JsonSetter("checked")
    protected void setChecked(Boolean checked) {
        this.checked = checked;
    }
}
