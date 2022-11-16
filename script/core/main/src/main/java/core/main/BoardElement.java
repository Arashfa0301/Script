package core.main;

public abstract class BoardElement {

    private String title;

    /**
     * Creates a new board element. <code>title = ""</code> and
     * <code>isPinned = false</code> by default.
     */
    public BoardElement() {
        title = "";
    }

    /**
     * Sets the title of a board element.
     *
     * @param title a String that becomes the title of the board element
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the title of the board element.
     *
     * @return the title of the board element as a String
     */
    public String getTitle() {
        return title;
    }
}
