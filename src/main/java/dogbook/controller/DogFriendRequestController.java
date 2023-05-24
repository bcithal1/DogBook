package dogbook.controller;

import dogbook.model.*;
import dogbook.service.DogFriendRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
public class DogFriendRequestController {

    @Autowired
    DogFriendRequestService dogFriendRequestService;

    @PostMapping("/api/v1/puppypals/{recipientId}/{senderId}")
    public ResponseEntity<DogFriendRequest> sendDogFriendRequest(@PathVariable Integer recipientId,
                                                              @PathVariable Integer senderId) {
        return ResponseEntity.ok(dogFriendRequestService.sendFriendRequest(recipientId, senderId));
    }

    @PostMapping("api/v1/puppypalreqs/multidog")
    public ResponseEntity<List<List<DogFriendRequest>>> getFriendListMultiDog(@RequestParam List<Dog> dogList){
        return ResponseEntity.ok(dogFriendRequestService.getMultiPuppyPalRequests(dogList));
    }

    @GetMapping("/api/v1/puppypals/sent/{dogId}")
    public ResponseEntity<List<DogFriendRequest>> getAllSentRequests(@PathVariable Integer dogId) {
        return ResponseEntity.ok(dogFriendRequestService.getSentRequestsByDogID(dogId));
    }

    @GetMapping("api/v1/puppypals/received/{dogId}")
    public ResponseEntity<List<DogFriendRequest>> getAllReceivedRequests(@PathVariable Integer dogId) {
        return ResponseEntity.ok(dogFriendRequestService.getReceivedRequestsByUserID(dogId));
    }

    @DeleteMapping("api/v1/cancelpuppypalrequest/{requestId}")
    public ResponseEntity<DogFriendship> deleteDogFriendRequest(@PathVariable Integer requestId) {
        dogFriendRequestService.cancelFriendRequest(requestId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/api/v1/acceptpuppypal/{requestId}")
    public ResponseEntity acceptDogFriendRequest(@PathVariable Integer requestId) {
        return ResponseEntity.ok(dogFriendRequestService.acceptRequest(requestId));
    }

    @DeleteMapping("api/v1/rejectpuppypal/{requestId}")
    public ResponseEntity rejectDogFriendRequest(@PathVariable Integer requestId) {
        dogFriendRequestService.rejectRequest(requestId);
        return ResponseEntity.ok().build();
    }

}