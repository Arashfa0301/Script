package data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import core.main.User;

public class ScriptModule {

    // TODO: an idea is to add an interface and let SciptModule extends it
    // So that would only need to change SciptModule when we make the REST-API

    private static final String FILE_NAME = "users";
    private static final String FILE_PATH = System.getProperty("user.home") + "/resources";
    private Gson gson;

    public ScriptModule() {
        gson = new GsonBuilder().setPrettyPrinting().create();

    }

    public void write(User user) {

        try (PrintWriter out = new PrintWriter(getFilePath())) {
            out.write(gson.toJson(user));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public User read() {
        try (BufferedReader reader = new BufferedReader(new FileReader(getFilePath()))) {
            User user = gson.fromJson(reader, User.class);
            return user;
        } catch (FileNotFoundException e) {
            new File(FILE_PATH).mkdir();
            return new User();
        } catch (IOException e) {
            e.printStackTrace();
            return new User();
        }
    }

    private String getFilePath() {
        return FILE_PATH + "/" + FILE_NAME + ".json";

    }

}