package dwp.bpdts.service.user;

import dwp.bpdts.model.User;
import dwp.bpdts.model.Users;
import dwp.bpdts.service.geo.GeoService;
import dwp.bpdts.service.geo.GeoServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static java.util.Comparator.comparingLong;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

public class UserServiceImpl implements UserService {

    GeoService geoService = new GeoServiceImpl();

    public static final double DISTANCE_MILES = 50.0;
    public static final String BASE_URL = "https://bpdts-test-app.herokuapp.com/";
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public List<User> getLondonUsers() {
        Users usersNearLondon = getUsersNearLondon();
        Users usersInLondon = getUsersInLondon();

        return Stream.of(usersInLondon.getUsers(), usersNearLondon.getUsers())
                .flatMap(Collection::stream)
                .collect(collectingAndThen(
                        toCollection(() -> new TreeSet<>(comparingLong(User::getId))),
                        ArrayList::new));
    }

    private Users getUsersInLondon(){
        return getUserList("city/London/users");
    }

    private Users getUserList(String api) {
        String usersURL = BASE_URL + api;

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<User>> usersResponse = restTemplate.exchange(usersURL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });

        Users userList = new Users(usersResponse.getBody());

        logger.info("Returning a list of {} users for api \"{}\"", userList.size(), api);
        return userList;
    }

    private Users getUsersNearLondon() {
        Users users = getUserList("users");

        Users usersNearLondon = new Users();
        usersNearLondon.setUsers(
                users.getUsers().stream()
                        .filter(user -> geoService.getUserDistance(user) <= DISTANCE_MILES)
                        .collect(Collectors.toList())
        );
        return usersNearLondon;
    }
}
