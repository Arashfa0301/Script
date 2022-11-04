package core.main;

import java.util.List;

public class Checklist extends BoardElement {

    private Boolean isChecked = false;
    private List<String> list;

    public Checklist(String title, List<String> list) {
        setTitle(title);
        this.list = list;
    }

    public void addListElement(String text) {
        list.add(text);
    }

    public List<String> getList() {
        return list;
    }

    public void check() {
        isChecked = true;
    }

    public void uncheck() {
        isChecked = false;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public boolean isEmpty() {
        return (getTitle().isBlank() && getList().isEmpty());
    }

}
