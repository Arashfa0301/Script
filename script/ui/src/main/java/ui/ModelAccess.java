package ui;

<<<<<<< HEAD
import core.main.Board;
=======
>>>>>>> parent of 1fa8d64... Connect ScriptController with REST Api
import core.main.User;

public interface ModelAccess {

    void register(String firstName, String lastName, String username, String password) throws RuntimeException;

    User getUser(String username, String password) throws IllegalArgumentException;
<<<<<<< HEAD

    void createBoard(String boardname, String username, String password) throws IllegalArgumentException;

    void removeBoard(String boardname, String username, String password) throws IllegalArgumentException;

    void putBoard(Board board, String username, String password) throws IllegalArgumentException;
=======
>>>>>>> parent of 1fa8d64... Connect ScriptController with REST Api
}