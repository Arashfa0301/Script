package ui;

import core.main.Board;
import core.main.User;

public interface ModelAccess {

    void register(String firstName, String lastName, String username, String password) throws RuntimeException;

    User getUser(String username, String password) throws IllegalArgumentException;

    void createBoard(String boardname, String username, String password) throws IllegalArgumentException;

    void removeBoard(String boardname, String username, String password) throws IllegalArgumentException;

    void renameBoard(String oldBoardName, String newBoardName, String username, String password)
            throws IllegalArgumentException;

    void putBoard(Board board, String username, String password) throws IllegalArgumentException;
}