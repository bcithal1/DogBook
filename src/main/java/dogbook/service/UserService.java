package dogbook.service;

import dogbook.model.Dog;
import dogbook.model.Friendship;
import dogbook.model.Photo;
import dogbook.model.User;
import dogbook.repository.FriendshipRepo;
import dogbook.repository.PhotoRepo;
import dogbook.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepo userRepo;

    @Autowired
    PhotoRepo photoRepo;

    @Autowired
    FriendshipRepo friendshipRepo;

    @Autowired
    AuthenticatedUserService authenticatedUserService;

    public Optional<User> getUserById(Integer id) {
        return userRepo.findById(id);
    }

    public User getUserByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    public User getUserByProviderAccount(String name, String accountId) {
        return userRepo.findByProviderAccount(name, accountId);
    }

    public User createUser(User user) {
        Friendship defaultFriendship = new Friendship();
        userRepo.save(user);
        defaultFriendship.setPrimaryUserId(1);
        defaultFriendship.setSecondaryUserId(user.getId());
        defaultFriendship.setCreatedDate(new Date());
        friendshipRepo.save(defaultFriendship);
        return user;
    }

    public User updateUser(Integer id, User user) {
        Optional<User> userFound = userRepo.findById(id);
        if (userFound.isPresent()) {
            userFound.get().setDisplayName(user.getDisplayName());
            userFound.get().setFullName(user.getFullName());
            userFound.get().setEmail(user.getEmail());
            userFound.get().setDate_of_birth(user.getDate_of_birth());
            userFound.get().setPhoneNumber(user.getPhoneNumber());
            return userRepo.save(userFound.get());
        }

        return null;
    }

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @Transactional
    public void savePhoto(Photo photo) {
        Optional<User> currentUser = getUserById(authenticatedUserService.getId());

        Photo savedPhoto = photoRepo.save(photo);
        currentUser.get().getPhotoIds().add(savedPhoto.getId());
        userRepo.save(currentUser.get());
    }
}
