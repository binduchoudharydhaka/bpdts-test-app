package dwp.bpdts.controller;

import dwp.bpdts.model.User;
import dwp.bpdts.service.user.UserServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping(value = "/app")
public class UsersRestController {

    private static final Logger logger = LoggerFactory.getLogger(UsersRestController.class);

    @GetMapping(value = "/london/users")
    public List<User> getUsers() {
        long startTime = System.currentTimeMillis();
        List<User> users = new UserServiceImpl().getLondonUsers();
        long endTime = System.currentTimeMillis();
        logger.info("Returning a list of {} users for app in {} ms", users, endTime - startTime);
        return users;
    }

}
