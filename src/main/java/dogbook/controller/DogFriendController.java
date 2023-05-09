package dogbook.controller;

import dogbook.model.DogFriendship;
import dogbook.model.Friendship;
import dogbook.service.DogFriendshipService;
import dogbook.service.DogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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

    @DeleteMapping("api/v1/puppypals/{friendshipId}")
    public ResponseEntity<Friendship> removeFriend(@PathVariable Integer friendshipId){
        dogFriendshipService.endFriendship(friendshipId);
        return ResponseEntity.ok().build();
    }

}
