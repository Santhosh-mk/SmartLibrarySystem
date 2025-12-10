package repository;

import model.K2600128_User;
import java.util.ArrayList;
import java.util.List;

public class K2600128_UserRepository {

    private final List<K2600128_User> users = new ArrayList<>();

    public void addUser(K2600128_User user) {
        users.add(user);
    }

    // New method: get user by ID
    public K2600128_User getUserById(String userId) {
        for (K2600128_User u : users) {
            if (u.getUserId().equals(userId)) {
                return u;
            }
        }
        return null; // Not found
    }

    // Optional: get all users
    public List<K2600128_User> getAllUsers() {
        return users;
    }
}
