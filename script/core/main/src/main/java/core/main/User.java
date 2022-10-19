package core.main;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class User {

    private String name;
    private List<Board> boards = new ArrayList<>();

    public User(String name) {
        if (Pattern.matches("^[A-Za-z0-9_.]+$", name)) {
            this.name = name;
        } else {
            throw new IllegalArgumentException("Invalid username");
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Board> getBoards() {
        return boards;
    }

    public void setBoards(List<Board> boards) {
        this.boards = boards;
    }

    public void addBoard(Board board) {
        boards.add(board);
    }

    public Board getBoard(String boardName) {
        return boards.stream().filter(board -> board.getBoardName().equals(boardName)).findAny().get();
    }

    public void removeBoard(String boardName) {
        boards.remove(getBoard(boardName));
    }

}
