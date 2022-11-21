package core.main;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

public abstract class BoardElement {

    private static final int TITLE_LIMIT = 23;
    private String title = "";

    /**
     * Creates a new board element. <code>title = ""</code>
     */
    public BoardElement() {
    }

    /**
     * Sets the title of a board element.
     *
     * @param title a String that becomes the title of the board element
     * @throws IllegalArgumentException if the size of the input string
     *                                  <code>title</code> exceeds the
     *                                  maximun allowed amount
     */
    @JsonSetter("title")
    public void setTitle(String title) {
        if (title.length() > TITLE_LIMIT) {
            throw new IllegalArgumentException("The title length should not exceed 23 characters.");
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

}
