package core.main;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

public class Note extends BoardElement {

    private String content = "";

    /**
     * Creates a new Note object. Inherits from BoardElement.
     *
     * @see BoardElement#BoardElement()
     */
    public Note() {
        super();
    }

    /**
<<<<<<< HEAD
     * Gets the content of the Note.
     *
     * @return the Note's content as a String
     */
    @JsonGetter("content")
    public String getContent() {
        return content;
    }

    /**
     * Sets the content of the Note.
     *
     * @param content a String that will become the new content of the Note
     */
    @JsonSetter("content")
    public void setContent(String content) {
        this.content = content;
=======
     * Gets the text of the Note.
     *
     * @return the Note's text as a String
     */
    public String getText() {
        return text;
    }

    /**
     * Sets the text of the Note.
     *
     * @param text a String that will become the new text of the Note
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Sets the color of of the Note.
     *
     * @param color a String that changes the Note's color
     * @throws IllegalArgumentException if <code>isValidColor()</code> fails
     * @see Note#isValidColor(String)
     */
    public void setColor(String color) {
        if (!isValidColor(color)) {
            throw new IllegalArgumentException("This is not a valid color");
        }
        this.color = color;
    }

    /**
     * Gets all the valuable colors with their respectable RGB values.
     *
     * @return a Map of all valid colors
     */
    public Map<String, List<Integer>> getSelectableColors() {
        return selectableColors;
    }

    /**
     * Gets the RGB-values of a color.
     *
     * @return a List of the RGB values of a color
     */
    public List<Integer> getColorValues() {
        return getSelectableColors().get(color);
    }

    /**
     * Checks if a color is valid.
     *
     * @param color a String representing a color to check the validity of
     * @return <code>true</code> if <code>selectableColors</code> contains the
     *         String input as a key
     */
    private boolean isValidColor(String color) {
        return getSelectableColors().containsKey(color);
>>>>>>> parent of 1fa8d64... Connect ScriptController with REST Api
    }
}