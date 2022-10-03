package core.main;

import java.util.ArrayList;
import java.util.List;

public class User {

    private String name;
    private List<Board> boards = new ArrayList<>();

    public User(String name) {
        this.name = name;
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

    @Override
    public String toString() {
        return boards.stream().map(i -> i.toString()).reduce(name, (i, j) -> {
            return i + j + "\n";
        });
    }

}
