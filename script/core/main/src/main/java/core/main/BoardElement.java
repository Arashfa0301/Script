package core.main;

public abstract class BoardElement {

    private static final int TITLE_LIMIT = 23;
    private String title = "";
    private boolean isPinned = false;

    public BoardElement() {
    }

    public void setTitle(String title) {
        if (title.length() > TITLE_LIMIT) {
            throw new IllegalArgumentException("The title length should not exceed 20 characters");
        }
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
