package data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
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
import java.util.List;

public class DataHandler {

    // TODO: an idea is to add an interface and let SciptModule extends it
    // So that would only need to change SciptModule when we make the REST-API

    private static final String FILE_NAME = "users";
    private static final String FILE_PATH = System.getProperty("user.home") + "/resources";
    private Gson gson;

    public DataHandler() {
        gson = new GsonBuilder().setPrettyPrinting().create();
        read();
    }

    public void write(User user) {
        List<User> users = read();
        if (users.stream().anyMatch(u -> u.getName().equals(user.getName()))) {
            users.removeIf(u -> u.getName().equals(user.getName()));
        }
        users.add(user);

        try (PrintWriter out = new PrintWriter(
                new OutputStreamWriter(new FileOutputStream(getFilePath()), Charset.defaultCharset()))) {
            out.write(gson.toJson(users));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
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

    public User getUser(String user) {
        return read().stream().filter(u -> u.getName().equals(user)).findAny().orElse(null);
    }
}