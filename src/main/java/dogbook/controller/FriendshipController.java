package dogbook.controller;

import dogbook.model.FriendRequest;
import dogbook.model.Friendship;
import dogbook.model.UserWithDogs;
import dogbook.service.AuthenticatedUserService;
import dogbook.service.FriendshipService;
import dogbook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
public class FriendshipController {

    @Autowired
    FriendshipService friendshipService;

    @GetMapping("api/v1/friendlist/{userId}")
    public ResponseEntity<List<Friendship>> getFriendList(@PathVariable Integer userId) {
        return ResponseEntity.ok(friendshipService.getFriendsList(userId));
    }

    @DeleteMapping("api/v1/friendlist/{friendshipId}")
    public ResponseEntity<Friendship> removeFriend(@PathVariable Integer friendshipId) {
        friendshipService.endFriendship(friendshipId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("api/v1/secdeg/friends")
    public ResponseEntity<List<UserWithDogs>> getFriendsOfFriends() {
        return ResponseEntity.ok(friendshipService.getFriendsofFriendsIds());
    }
}
