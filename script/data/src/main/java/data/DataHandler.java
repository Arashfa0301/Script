package data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import core.main.Board;
import core.main.User;

import java.io.BufferedReader;
import java.io.File;
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

    private static final String FILE_NAME = "users";
    private static final String FILE_PATH = System.getProperty("user.home");
    private Gson gson;

    public DataHandler() {
        gson = new GsonBuilder().setPrettyPrinting().create();
        read();
    }

    public void write(User user) {
        checkIfUserIsNull(user);
        checkNullPointException(user.getUsername());
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
        checkNullPointException(boardname, username);
        checkIfUserIsNull(getUser(username));
        write(getUser(username).addBoard(boardname));
    }

    public void removeBoard(String boardname, String username) {
        checkNullPointException(boardname, username);
        checkIfUserIsNull(getUser(username));
        write(getUser(username).removeBoard(boardname));
    }

    public void renameBoard(String oldBoardname, String newBoardname, String username) {
        checkNullPointException(oldBoardname, newBoardname, username);
        checkIfUserIsNull(getUser(username));
        write(getUser(username).renameBoard(oldBoardname, newBoardname));
    }

    public void updateBoard(String boardname, Board board, String username) {
        if (board == null) {
            throw new NullPointerException("The input argument is not valid");
        }
        User user = getUser(username);
        user.putBoard(board);
        write(user);
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
            if (new File(FILE_PATH).mkdir()) {
                return new ArrayList<>();
            } else {
                return new ArrayList<>();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private String getFilePath() {
        return FILE_PATH + "/" + FILE_NAME + ".json";
    }

    public User getUser(String username) {
        return read().stream().filter(u -> u.getUsername().equals(username)).findAny().orElse(null);
    }

    public Boolean userExists(String username) {
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

    private void checkNullPointException(String... strings) {
        if (new ArrayList<String>(Arrays.asList(strings)).stream().anyMatch(s -> s == null || s.isBlank())) {
            throw new IllegalArgumentException("The give input is invalid. Either null or empty");
        }
    }

    private void checkIfUserIsNull(User user) {
        if (user == null) {
            throw new NullPointerException("The input user is null");
        }
    }

}