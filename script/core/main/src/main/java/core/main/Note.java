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
    }

}