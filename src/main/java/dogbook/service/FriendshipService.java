package dogbook.service;

import dogbook.model.Friendship;
import dogbook.model.User;
import dogbook.repository.FriendshipRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


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

    public ResponseEntity<List<Friendship>> getFriendsList(Integer userId) {
        ResponseEntity responseEntity;
        List<Friendship> friendList = new ArrayList<>();
        Optional<User> targetUser = userService.getUserById(userId);

        if (targetUser.isPresent()) {
            friendList.addAll(friendshipRepo.findByPrimaryUserId(userId));
            friendList.addAll(friendshipRepo.findBySecondaryUserId(userId));
            responseEntity = ResponseEntity.ok(friendList);
        } else {
            responseEntity = ResponseEntity.notFound().build();
        }
        return responseEntity;
    }

    public ResponseEntity<Friendship> endFriendship(Integer friendshipId) {
        ResponseEntity responseEntity;
        Integer currentUser = authenticatedUserService.getId();
        Optional<Friendship> doomedFriendship = friendshipRepo.findById(friendshipId);

        if (doomedFriendship.isPresent()) {
            if ((doomedFriendship.get().getFirstUser().equals(currentUser) || doomedFriendship.get().getSecondUser().equals(currentUser))) {
                responseEntity = ResponseEntity.ok().build();
                friendshipRepo.deleteById(friendshipId);
            } else {
                responseEntity = ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        } else {
            responseEntity = ResponseEntity.notFound().build();
        }

        return responseEntity;
    }


}
