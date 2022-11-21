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

    public DataHandler(String fileName) {

        this.fileName = fileName;
        gson = new GsonBuilder().setPrettyPrinting().create();
        read();
    }

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

    public void createBoard(String boardname, String username) {
        checkAvailableUser(username);
        checkInvalidInput(boardname);
        write(getUser(username).addBoard(boardname));
    }

    public void removeBoard(String boardname, String username) {
        checkAvailableUser(username);
        checkInvalidInput(boardname);
        checkAvailableBoard(username, boardname);
        write(getUser(username).removeBoard(boardname));
    }

    public void renameBoard(String oldBoardname, String newBoardname, String username) {
        checkAvailableUser(username);
        checkInvalidInput(oldBoardname, newBoardname);
        checkAvailableBoard(username, oldBoardname);
        checkIfUserIsNull(getUser(username));
        write(getUser(username).renameBoard(oldBoardname, newBoardname));
    }

    public void updateBoard(String boardname, Board board, String username) {
        checkAvailableUser(username);
        if (board == null) {
            throw new NullPointerException("The input argument is not valid");
        }
        checkInvalidInput(boardname);
        write(getUser(username).putBoard(board));
    }

    private String getFilePath() {
        return FILE_PATH + "/" + fileName + ".json";
    }

    public User getUser(String username) {
        return read().stream().filter(u -> u.getUsername().equals(username)).findAny().orElse(null);
    }

    public Boolean hasUser(String username) {
        return getUser(username) != null;
    }

    public void removeUser(String user) {
        List<User> users = read();
        users.removeIf(u -> u.getUsername().equals(user));
        try (PrintWriter out = new PrintWriter(
                new OutputStreamWriter(new FileOutputStream(getFilePath()), Charset.defaultCharset()))) {
            out.write(gson.toJson(users));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void checkInvalidInput(String... strings) {
        if (new ArrayList<String>(Arrays.asList(strings)).stream().anyMatch(s -> s == null || s.isBlank())) {
            throw new IllegalArgumentException("The give input is invalid. Either null or empty");
        }
    }

    private void checkIfUserIsNull(User user) {
        if (user == null) {
            throw new NullPointerException("The input user is null");
        }
    }

    private void checkAvailableUser(String username) {
        if (getUser(username) == null) {
            throw new IllegalStateException("The input user argument does not exists.");
        }
    }

    private void checkAvailableBoard(String username, String boardname) {
        if (!getUser(username).getBoards().stream().anyMatch(name -> name.getName().equals(boardname))) {
            throw new IllegalStateException("The input board argument does not exist of the given user.");
        }
    }
}