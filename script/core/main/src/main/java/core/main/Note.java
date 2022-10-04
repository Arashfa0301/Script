package core.main;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.AbstractMap;

public class Note {

    private String title, text;
    private boolean isPinned = false;
    private String color = "white";
    private final Map<String, List<Integer>> selectableColors = Stream.of(
            new AbstractMap.SimpleImmutableEntry<>("red", Arrays.asList(120, 120, 120)),
            new AbstractMap.SimpleImmutableEntry<>("blue", Arrays.asList(100, 100, 100)))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    // Maybe implement checkboxes

    /// Create note with already existing title and text (When loading in already
    /// existing notes maybe?)
    public Note(String title, String text) {
        this.title = title;
        this.text = text;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
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

    public void setColor(String color) {
        if (isValidColor(color)) {
            this.color = color;
            return;
        }
        throw new IllegalArgumentException("This is not a valid color");

    }

    public String getColor() {
        return color;
    }

    private boolean isValidColor(String color) {
        return selectableColors.containsKey(color);
    }

    @Override
    public String toString() {
        return "title:" + title + ", text: " + text;
    }
}