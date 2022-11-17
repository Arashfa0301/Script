package core.main;

public class BoardElement {

    private String title;
    private boolean isPinned = false;

    public BoardElement() {
        title = "";
        isPinned = false;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void pin() {
        isPinned = true;
    }

    public void unPin() {
        isPinned = false;
    }

    public boolean isPinned() {
        return isPinned;
    }

}
