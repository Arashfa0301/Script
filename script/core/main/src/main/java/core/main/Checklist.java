package core.main;

import java.util.List;

public class Checklist extends BoardElement {

    private List<String> checkItems;

    public Checklist(String title, List<String> checkItems) {
        setTitle(title);
        this.checkItems = checkItems;
    }

    public void addListElement(String text) {
        checkItems.add(text);
    }

    public List<String> getCheckItems() {
        return checkItems;
    }

    public boolean isEmpty() {
        return (getTitle().isBlank() && getCheckItems().isEmpty());
    }

}
