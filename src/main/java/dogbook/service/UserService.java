package dogbook.service;

import dogbook.model.User;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.Optional;

public interface UserService {
    Optional<User> getUserById(Integer id);
    User getUserByEmail(String email);
    User getUserByProviderAccount(String name, String accountId);
    User createUser(User user);
    User updateUser(User user);
}
