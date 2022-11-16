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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@CrossOrigin
@RestController
public class ApiController {

    private DataHandler datahandler = new DataHandler();
    private InMemoryUserDetailsManager inMemoryUserDetailsManager;

    @Autowired
    public void securityController(InMemoryUserDetailsManager inMemoryUserDetailsManager) {
        this.inMemoryUserDetailsManager = inMemoryUserDetailsManager;
    }

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

    @PostMapping("/boards/create/{boardName}")
    @ResponseStatus(HttpStatus.CREATED)
    public void createBoard(@PathVariable("boardName") String boardName) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        datahandler.createBoard(boardName, authentication.getName());
    }

    @PostMapping("/boards/remove/{boardName}")
    @ResponseStatus(HttpStatus.CREATED)
    public void removeBoard(@PathVariable("boardName") String boardName) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        datahandler.removeBoard(boardName, authentication.getName());
    }

    @PostMapping("/boards/edit/{boardName}")
    @ResponseStatus(HttpStatus.CREATED)
    public void putBoard(@RequestBody Board board) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        datahandler.removeBoard(boardName, authentication.getName());
    }

    @PostMapping("/auth/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void postBody(@RequestBody User user) {
        if (datahandler.userExists(user.getUsername())) {
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

    @GetMapping("/test")
    public String test() {
        return "Success!";
    }
}