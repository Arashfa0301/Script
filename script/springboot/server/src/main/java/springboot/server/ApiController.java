package springboot.server;

import core.main.User;
import data.DataHandler;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class ApiController {

    private DataHandler datahandler = new DataHandler();

    @GetMapping(path = "/user/{user}")
    public User getUser(@PathVariable("user") String user) {
        return datahandler.getUser(user);
    }
}