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

    public void setLine(String line) {
        this.line = line;
    }

    public void checked(Boolean b) {
        checked = b;
    }

}
