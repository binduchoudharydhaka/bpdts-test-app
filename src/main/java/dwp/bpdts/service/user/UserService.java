package dwp.bpdts.service.user;

import dwp.bpdts.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    List<User> getLondonUsers();
}
