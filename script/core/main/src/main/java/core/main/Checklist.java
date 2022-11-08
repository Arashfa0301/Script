package core.main;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Checklist extends BoardElement {

    private List<Boolean> checked;
    private List<String> list;

    public Checklist(String title, List<String> list) {
        setTitle(title);
        this.list = list;
        checked = new ArrayList<>();
        IntStream.range(0, list.size()).forEach(i -> checked.add(false));
    }

    public void addListElement(String text) {
        list.add(text);
        checked.add(false);
    }

    public List<String> getList() {
        return list;
    }

    public void check(int i) {
        checked.set(i, true);
    }

    public void uncheck(int i) {
        checked.set(i, false);
    }

    public boolean isChecked(int i) {
        return checked.get(i);
    }

    public boolean isEmpty() {
        return (getTitle().isBlank() && getList().isEmpty());
    }

    public List<Boolean> getChecked() {
        return checked;
    }

}
