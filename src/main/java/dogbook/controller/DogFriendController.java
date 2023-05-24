package dogbook.controller;

import dogbook.model.Dog;
import dogbook.model.DogFriendRequest;
import dogbook.model.DogFriendship;
import dogbook.model.Friendship;
import dogbook.service.DogFriendshipService;
import dogbook.service.DogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DogFriendController {

    @Autowired
    DogService dogService;

    @Autowired
    DogFriendshipService dogFriendshipService;

    @GetMapping("api/v1/puppypals/{dogId}")
    public ResponseEntity<List<DogFriendship>> getFriendList(@PathVariable Integer dogId) {
        return ResponseEntity.ok(dogFriendshipService.getFriendsList(dogId));
    }

    @PostMapping("api/v1/puppypals/multidog")
    public ResponseEntity<List<List<DogFriendship>>> getFriendListMultiDog(@RequestParam List<Dog> dogList){
        return ResponseEntity.ok(dogFriendshipService.getFriendsListForListOfDogs(dogList));
    }

    @DeleteMapping("api/v1/puppypals/{friendshipId}")
    public ResponseEntity<Friendship> removeFriend(@PathVariable Integer friendshipId) {
        dogFriendshipService.endFriendship(friendshipId);
        return ResponseEntity.ok().build();
    }
}
