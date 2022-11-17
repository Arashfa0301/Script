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

    public Note() {
        super();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setColor(String color) {
        if (!isValidColor(color)) {
            throw new IllegalArgumentException("This is not a valid color");
        }
        this.color = color;
    }

    public Map<String, List<Integer>> getSelectableColors() {
        return selectableColors;
    }

    public List<Integer> getColorValues() {
        return getSelectableColors().get(color);
    }

    private boolean isValidColor(String color) {
        return getSelectableColors().containsKey(color);
    }
}