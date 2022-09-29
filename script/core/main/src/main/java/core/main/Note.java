package core.main;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.AbstractMap;

public class Note {

    private String title;
    private String text;
    private boolean pinned = false;
    private String color = "white";
    private Map<String, List<Integer>> selectableColors = Stream.of(
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

    /// Create empty note
    public Note() {

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
        this.pinned = true;
    }

    public void unPin() {
        this.pinned = false;
    }

    public boolean isPinned() {
        return this.pinned;
    }

    public void setColor(String color) {
        if (isValidColor(color)) {
            this.color = color;
        } else {
            throw new IllegalArgumentException("This is not a valid color");
        }
    }

    public String getColor() {
        return this.color;
    }

    private boolean isValidColor(String color) {
        if (selectableColors.containsKey(color)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "title:" + title + ", text: " + text;
    }

    public static void main(String[] args) {

    }
}