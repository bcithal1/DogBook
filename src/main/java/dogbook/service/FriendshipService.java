package dogbook.service;

import dogbook.model.Friendship;
import dogbook.model.User;
import dogbook.repository.FriendshipRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FriendshipService {

    @Autowired
    FriendshipRepo friendshipRepo;

    @Autowired
    UserService userService;

    @Autowired
    AuthenticatedUserService authenticatedUserService;

    public List<Friendship> getFriendsList(Integer userId) {
        List<Friendship> friendList = new ArrayList<>();
        Optional<User> targetUser = userService.getUserById(userId);

        if (targetUser.isPresent()) {
            friendList.addAll(friendshipRepo.findByPrimaryUserId(userId));
            friendList.addAll(friendshipRepo.findBySecondaryUserId(userId));
            return friendList;
        } else {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }
    }

    public void endFriendship(Integer friendshipId) {
        Integer currentUser = authenticatedUserService.getId();
        Optional<Friendship> doomedFriendship = friendshipRepo.findById(friendshipId);

        if (doomedFriendship.isPresent()) {
            if ((doomedFriendship.get().getPrimaryUserId().equals(currentUser) || doomedFriendship.get().getSecondaryUserId().equals(currentUser))) {
                friendshipRepo.deleteById(friendshipId);
            } else {
                throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED, "This user cannot update this friendship");
            }
        } else {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }
    }


}
