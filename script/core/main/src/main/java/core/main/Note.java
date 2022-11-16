package core.main;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Note extends BoardElement {

    private static final Map<String, List<Integer>> selectableColors = Stream.of(
            new AbstractMap.SimpleImmutableEntry<>("red", Arrays.asList(255, 0, 0)),
            new AbstractMap.SimpleImmutableEntry<>("blue", Arrays.asList(0, 0, 255)),
            new AbstractMap.SimpleImmutableEntry<>("coral", Arrays.asList(255, 99, 80)),
            new AbstractMap.SimpleImmutableEntry<>("khaki", Arrays.asList(240, 230, 140)),
            new AbstractMap.SimpleImmutableEntry<>("aquamarine", Arrays.asList(102, 205, 170)),
            new AbstractMap.SimpleImmutableEntry<>("sea green", Arrays.asList(32, 178, 170)),
            new AbstractMap.SimpleImmutableEntry<>("powder blue", Arrays.asList(176, 224, 230)),
            new AbstractMap.SimpleImmutableEntry<>("yellow", Arrays.asList(255, 255, 0)),
            new AbstractMap.SimpleImmutableEntry<>("green", Arrays.asList(0, 128, 0)),
            new AbstractMap.SimpleImmutableEntry<>("orchid", Arrays.asList(218, 112, 214)),
            new AbstractMap.SimpleImmutableEntry<>("violet red", Arrays.asList(199, 21, 133)),
            new AbstractMap.SimpleImmutableEntry<>("lemon chiffon", Arrays.asList(255, 250, 205)),
            new AbstractMap.SimpleImmutableEntry<>("misty rose", Arrays.asList(255, 228, 225)),
            new AbstractMap.SimpleImmutableEntry<>("alice blue", Arrays.asList(240, 248, 255)),
            new AbstractMap.SimpleImmutableEntry<>("white", Arrays.asList(255, 255, 255)))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    private String text = "";
    private String color = "white";

    /**
     * Creates a new Note object. Inherits from BoardElement.
     *
     * @see BoardElement#BoardElement()
     */
    public Note() {
        super();
    }

    /**
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
    }
}