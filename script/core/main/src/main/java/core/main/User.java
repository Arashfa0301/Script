package core.main;


import java.util.List;
import java.util.ArrayList;


public class User {

    String username;
    List<Note> notes =  new ArrayList<>();



    public User(String username){
        this.username =  username;
    }

    public void addNote(Note note){
        notes.add(note);
    }








    public static void main(String[] args) {

    }
}