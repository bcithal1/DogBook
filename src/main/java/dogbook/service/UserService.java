package dogbook.service;

import dogbook.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> getUserById(Integer id);
    User getUserByEmail(String email);
    User getUserByProviderAccount(String name, String accountId);
    User createUser(User user);
    User updateUser(Integer id, User user);

    List<User> getAllUsers();
}
