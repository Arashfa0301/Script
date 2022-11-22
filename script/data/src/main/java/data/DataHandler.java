package data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import core.main.Board;
import core.main.User;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataHandler {

    private static final String FILE_PATH = System.getProperty("user.home");
    private String fileName;
    private Gson gson;

    /**
     * Constructs a datahandler object.
     *
     * @param fileName A string specifying where this datahandler object will save
     *                 and load data to and from
     */
    public DataHandler(String fileName) {
        this.fileName = fileName;
        gson = new GsonBuilder().setPrettyPrinting().create();
        read();
    }

    /**
     * Reads json data from the this object's file, transforming
     * it's java object using gson and returning the saved
     * users that exist as that file's content.
     *
     * @return a list of saved users if the file exists, an ampty list if not
     * @exception FileNotFoundException returns empty list
     * @exception IOException           returns empty list
     */
    public List<User> read() {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(getFilePath()), Charset.defaultCharset()))) {

            Type userListType = new TypeToken<ArrayList<User>>() {
            }.getType();

            List<User> users = gson.fromJson(reader, userListType);

            if (users == null) {
                return new ArrayList<>();
            }
            return users;
        } catch (FileNotFoundException e) {
            return new ArrayList<>();
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * Saves a user as json data/string to a file.
     *
     * @param user a user object that will be saved
     * @exception FileNotFoundException prints the stacktrace
     * @see DataHandler#checkIfUserIsNull(User)
     * @see DataHandler#checkInvalidInput(String...)
     */
    public void write(User user) {
        checkIfUserIsNull(user);
        checkInvalidInput(user.getUsername());
        List<User> users = read();
        users.removeIf(u -> u.getUsername().equals(user.getUsername()));
        users.add(user);

        try (PrintWriter out = new PrintWriter(
                new OutputStreamWriter(new FileOutputStream(getFilePath()), Charset.defaultCharset()))) {
            out.write(gson.toJson(users));
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Creates a board for a valid saved user.
     *
     * @param boardname a string, specifying the name of the board
     * @param username  a string, specifying which saved user the board will be
     *                  given to
     *
     * @see DataHandler#checkAvailableUser(String)
     * @see DataHandler#checkInvalidInput(String...)
     */
    public void createBoard(String boardname, String username) {
        checkAvailableUser(username);
        checkInvalidInput(boardname);
        write(getUser(username).addBoard(boardname));
    }

    /**
     * Removes an existing board from a valid saved user's list of
     * boards.
     *
     * @param boardname a string, specifying which board will be removed
     * @param username  a string, specifying which saved user the board will be
     *                  removed from
     *
     * @see DataHandler#checkAvailableUser(String)
     * @see DataHandler#checkInvalidInput(String...)
     * @see DataHandler#checkAvailableBoard(String, String)
     */
    public void removeBoard(String boardname, String username) {
        checkAvailableUser(username);
        checkInvalidInput(boardname);
        checkAvailableBoard(username, boardname);
        write(getUser(username).removeBoard(boardname));
    }

    /**
     * Renames an existing board from a valid saved user's list of
     * boards.
     *
     * @param oldBoardname a string for board's current name
     * @param newBoardname a string for board's new name
     * @param username     a string, specifying the user that has the board
     *
     * @see DataHandler#checkAvailableUser(String)
     * @see DataHandler#checkInvalidInput(String...)
     * @see DataHandler#checkAvailableBoard(String, String)
     * @see DataHandler#checkIfUserIsNull(User)
     */
    public void renameBoard(String oldBoardname, String newBoardname, String username) {
        checkAvailableUser(username);
        checkInvalidInput(oldBoardname, newBoardname);
        checkAvailableBoard(username, oldBoardname);
        checkIfUserIsNull(getUser(username));
        write(getUser(username).renameBoard(oldBoardname, newBoardname));
    }

    /**
     * Updates an existing board from a valid saved user's list of
     * boards.
     *
     * @param boardname a string, specifying which board to update
     * @param board     a board object which will replace the current one with the
     *                  same name
     * @param username  a string, specifying the user that has the board
     *
     * @throws NullPointerException if <code>board</code> is <code>null</code>
     * @see DataHandler#checkAvailableUser(String)
     * @see DataHandler#checkInvalidInput(String...)
     */
    public void updateBoard(String boardname, Board board, String username) {
        checkAvailableUser(username);
        if (board == null) {
            throw new NullPointerException("The input argument is not valid");
        }
        checkInvalidInput(boardname);
        write(getUser(username).putBoard(board));
    }

    /**
     * Gets a saved user if it exists.
     *
     * @param username a string, specifying which user find
     * @return a saved user object if it exists, else null
     */
    public User getUser(String username) {
        return read().stream().filter(u -> u.getUsername().equals(username)).findAny().orElse(null);
    }

    /**
     * Checks if a user is a saved user.
     *
     * @param username a string, specifying which user to check
     * @return a boolean representing if the user was found or not
     */
    public Boolean hasUser(String username) {
        return getUser(username) != null;
    }

    /**
     * Removes a user from the saved users list.
     *
     * @param username a string, specifying which user to remove
     * @exception Exception prints stacktrace
     */
    public void removeUser(String username) {
        List<User> users = read();
        users.removeIf(u -> u.getUsername().equals(username));
        try (PrintWriter out = new PrintWriter(
                new OutputStreamWriter(new FileOutputStream(getFilePath()), Charset.defaultCharset()))) {
            out.write(gson.toJson(users));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Returns the file path the data will be
     * saved to.
     *
     * @return a built string, specifying the filepath
     */
    private String getFilePath() {
        return FILE_PATH + "/" + fileName + ".json";
    }

    /**
     * Checks whether the input spirngs are inalid or not.
     *
     * @param strings an array of strings to be be checked
     * @throws IllegalArgumentException if <code>str</code> is either
     *                                  <code>null</code> or <code>isBlank()</code>
     *                                  returns <code>true</code> for all
     *                                  <code>str</code> in
     *                                  <code>strings</code>
     */
    private void checkInvalidInput(String... strings) {
        if (new ArrayList<String>(Arrays.asList(strings)).stream().anyMatch(str -> str == null || str.isBlank())) {
            throw new IllegalArgumentException("The give input is invalid. Either null or empty");
        }
    }

    /**
     * Checks if a user is null.
     *
     * @param user a user object to be checked
     * @throws NullPointerException if <code>user</code> is <code>null</code>
     */
    private void checkIfUserIsNull(User user) {
        if (user == null) {
            throw new NullPointerException("The input user is null");
        }
    }

    /**
     * Checks if a user exists in the list of saved users.
     *
     * @param username a string, specifying the user's username
     * @throws IllegalStateException if the userobject found with
     *                               <code>username</code> is <code>null</code>
     */
    private void checkAvailableUser(String username) {
        if (getUser(username) == null) {
            throw new IllegalStateException("The input user argument does not exists.");
        }
    }

    /**
     * Checks if a board is available in a specified user's list of
     * board.
     *
     * @param username  a string, specifying which user to check the list of board
     *                  of
     * @param boardname a string, specifying which board to check
     * @throws IllegalStateException if <code>boardname</code> is not found in the
     *                               list of boards of <code>username</code>
     *
     */
    private void checkAvailableBoard(String username, String boardname) {
        if (!getUser(username).getBoards().stream().anyMatch(name -> name.getName().equals(boardname))) {
            throw new IllegalStateException("The input board argument does not exist of the given user.");
        }
    }
}