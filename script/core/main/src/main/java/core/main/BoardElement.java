package core.main;

<<<<<<< HEAD
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
=======
public abstract class BoardElement {
>>>>>>> parent of 1fa8d64... Connect ScriptController with REST Api

public abstract class BoardElement {

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
<<<<<<< HEAD
    @JsonSetter("title")
=======
>>>>>>> parent of 1fa8d64... Connect ScriptController with REST Api
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the title of the board element.
     *
     * @return the title of the board element as a String
     */
<<<<<<< HEAD
    @JsonGetter("title")
=======
>>>>>>> parent of 1fa8d64... Connect ScriptController with REST Api
    public String getTitle() {
        return title;
    }

    /**
     * Pins the board element, which prioritizes it over other board elements.
     */
<<<<<<< HEAD
    @JsonSetter("isPinned")
    public void setIsPinned(Boolean pinned) {
        isPinned = pinned;
=======
    public void pin() {
        isPinned = true;
    }

    /**
     * Removes the board element's priority over other board elements.
     */
    public void unPin() {
        isPinned = false;
>>>>>>> parent of 1fa8d64... Connect ScriptController with REST Api
    }

    /**
     * Checks if a board element is pinned.
     *
     * @return <code>true</code> if board element is pinned, otherwise
     *         <code>false</code>
     */
<<<<<<< HEAD
    @JsonGetter("isPinned")
=======
>>>>>>> parent of 1fa8d64... Connect ScriptController with REST Api
    public boolean isPinned() {
        return isPinned;
    }

}
