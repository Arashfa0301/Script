package springboot.server;

import core.main.Board;
import core.main.User;
import data.DataHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@CrossOrigin
@RestController
public class ApiController {

    private DataHandler datahandler = new DataHandler("users");
    private InMemoryUserDetailsManager inMemoryUserDetailsManager;

    @Autowired
    public void securityController(InMemoryUserDetailsManager inMemoryUserDetailsManager) {
        this.inMemoryUserDetailsManager = inMemoryUserDetailsManager;
    }

    /**
     * Gets the specified user.
     *
     * @param username a string, the username of the user
     * @return User a user object
     * @throws ResponseStatusException if the user does not exist
     *
     * @see User
     */
    @GetMapping(path = "/user/{username}")
    public User getUser(@PathVariable("username") String username) {
        // get authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.getName().equals(username)) {
            return datahandler.getUser(username);
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    "You are not authorized to access this resource");
        }
    }

    /**
     * Creates the specified board.
     *
     * @param boardName a string, the id of the board
     *
     * @see Board
     * @see DataHandler#createBoard(String, String)
     */
    @GetMapping("/boards/create/{boardName}")
    @ResponseStatus(HttpStatus.CREATED)
    public void createBoard(@PathVariable("boardName") String boardName) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        datahandler.createBoard(boardName, authentication.getName());
    }

    /**
     * Deletes the specified board.
     *
     * @param boardName a string, the id of the board
     * 
     * @see DataHandler#deleteBoard(String, String)
     */
    @GetMapping("/boards/remove/{boardName}")
    @ResponseStatus(HttpStatus.OK)
    public void removeBoard(@PathVariable("boardName") String boardName) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        datahandler.removeBoard(boardName, authentication.getName());
    }

    /**
     * Renames the specified board.
     *
     * @param boardName    a string, the id of the board
     * @param newBoardName a string, the new id of the board
     *
     * @see DataHandler#renameBoard(String, String, String)
     */
    @PostMapping("/boards/rename/{boardName}/{newBoardName}")
    @ResponseStatus(HttpStatus.OK)
    public void renameBoard(@PathVariable("boardName") String boardName,
            @PathVariable("newBoardName") String newBoardName) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        datahandler.renameBoard(boardName, newBoardName, authentication.getName());
    }

    /**
     * Replace the specified board with the new board.
     *
     * @param boardName a string, the id of the board
     * @param board     a board object, the new board
     *
     * @see DataHandler#updateBoard(String, Board, String)
     */
    @PutMapping("/board/{boardName}")
    @ResponseStatus(HttpStatus.CREATED)
    public void putBoardNotes(@PathVariable("boardName") String boardName,
            @RequestBody Board board) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        datahandler.updateBoard(boardName, board, authentication.getName());
    }

    /**
     * Registers a new user.
     *
     * @param user a user object
     * @throws ResponseStatusException if the user already exists
     * 
     * @see User
     * @see DataHandler#registerUser(User)
     */
    @PostMapping("/auth/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void postBody(@RequestBody User user) {
        // TODO: change userExists name
        if (datahandler.hasUser(user.getUsername())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists");
        }
        datahandler.write(user);
        inMemoryUserDetailsManager
                .createUser(org.springframework.security.core.userdetails.User.withDefaultPasswordEncoder()
                        .username(user.getUsername())
                        .password(user.getPassword())
                        .roles("USER")
                        .build());
    }

    /**
     * Deletes the specified user.
     *
     * @param username a string, the username of the user
     * @throws ResponseStatusException if the user does not exist
     * 
     * @see DataHandler#removeUser(String)
     * @see InMemoryUserDetailsManager#deleteUser(String)
     */
    @DeleteMapping("/user/{username}/delete")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable("username") String username) {
        // get authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getName().equals(username)) {
            datahandler.removeUser(username);
            inMemoryUserDetailsManager.deleteUser(username);
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not authorized to delete this user");
        }
    }

}