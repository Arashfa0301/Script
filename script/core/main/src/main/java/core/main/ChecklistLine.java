package core.main;

public class ChecklistLine {
    private String line = "";
    private Boolean checked = false;

    public ChecklistLine() {
    }

    public String getLine() {
        return line;
    }

    public Boolean getChecked() {
        return checked;
    }

    protected void setLine(String line) {
        this.line = line;
    }

    protected void checked(Boolean checked) {
        this.checked = checked;
    }

}
