package core.main;

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
    public String getLine() {
        return line;
    }

    /**
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
    }

    /**
     * Sets the boolean value of checked.
     *
     * @param checked a boolean value to determine if a ChecklistItem is checked or
     *                not
     */
    protected void checked(Boolean checked) {
        this.checked = checked;
    }

}
