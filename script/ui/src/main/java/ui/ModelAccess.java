package ui;

import core.main.User;

public interface ModelAccess {

    void register(String firstName, String lastName, String username, String password) throws RuntimeException;

    User getUser(String username, String password) throws IllegalArgumentException;
}