package core.main;


import java.util.List;
import java.util.ArrayList;


public class User {

    String username;
    List<Board> boards =  new ArrayList<>();



    public User(String username){
        this.username =  username;
    }

    public void addNote(Board board){
        boards.add(board);
    }








    public static void main(String[] args) {

    }
}