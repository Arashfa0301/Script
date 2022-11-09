package core.main;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class User {

    private String name;
    private List<Board> boards = new ArrayList<>();

    public User(String name) {
        if (!Pattern.matches("^[A-Za-z0-9_.]+$", name) || name.isBlank() || name.isEmpty()) {
            throw new IllegalArgumentException("Invalid username");
        }
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Board> getBoards() {
        return boards;
    }

    public void setBoards(List<Board> boards) {
        this.boards = boards;
    }

}
