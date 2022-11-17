package core.main;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

public abstract class BoardElement {

    private static final int TITLE_LIMIT = 23;
    private String title = "";
    private boolean isPinned = false;

    /**
     * Creates a new board element. <code>title = ""</code> and
     * <code>isPinned = false</code> by default.
     */
    public BoardElement() {
    }

    /**
     * Sets the title of a board element.
     *
     * @param title a String that becomes the title of the board element
     */
    @JsonSetter("title")
    public void setTitle(String title) {
        if (title.length() > TITLE_LIMIT) {
            throw new IllegalArgumentException("The title length should not exceed 20 characters");
        }
        this.title = title;
    }

    /**
     * Gets the title of the board element.
     *
     * @return the title of the board element as a String
     */
    @JsonGetter("title")
    public String getTitle() {
        return title;
    }

    /**
     * Pins the board element, which prioritizes it over other board elements.
     */
    @JsonSetter("isPinned")
    public void setIsPinned(Boolean pinned) {
        isPinned = pinned;
    }

    /**
     * Checks if a board element is pinned.
     *
     * @return <code>true</code> if board element is pinned, otherwise
     *         <code>false</code>
     */
    @JsonGetter("isPinned")
    public boolean isPinned() {
        return isPinned;
    }
}
