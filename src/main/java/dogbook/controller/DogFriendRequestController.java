package dogbook.controller;

import dogbook.model.DogFriendRequest;
import dogbook.model.DogFriendship;
import dogbook.model.FriendRequest;
import dogbook.model.Friendship;
import dogbook.service.DogFriendRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DogFriendRequestController {

    @Autowired
    DogFriendRequestService dogFriendRequestService;

    @PostMapping("/api/v1/dogfriend/{recipientId}/{senderId}")
    public ResponseEntity<DogFriendRequest> sendFriendRequest(@PathVariable Integer recipientId,
                                                              @PathVariable Integer senderId) {
        return ResponseEntity.ok(dogFriendRequestService.sendFriendRequest(recipientId, senderId));
    }

    @GetMapping("/api/v1/friendrequest/sent/{dogId}")
    public ResponseEntity<List<DogFriendRequest>> getAllSentRequests(@PathVariable Integer dogId) {
        return ResponseEntity.ok(dogFriendRequestService.getSentRequestsByDogID(dogId));
    }

    @GetMapping("api/v1/friendrequest/received/{dogId}")
    public ResponseEntity<List<DogFriendRequest>> getAllReceivedRequests(@PathVariable Integer dogId) {
        return ResponseEntity.ok(dogFriendRequestService.getReceivedRequestsByUserID(dogId));
    }

    @DeleteMapping("api/v1/cancelrequest/{requestId}")
    public ResponseEntity<DogFriendship> deleteFriendRequest(@PathVariable Integer requestId) {
        dogFriendRequestService.cancelFriendRequest(requestId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/api/v1/acceptfriendship/{requestId}")
    public ResponseEntity acceptFriendRequest(@PathVariable Integer requestId) {
        return ResponseEntity.ok(dogFriendRequestService.acceptRequest(requestId));
    }

    @DeleteMapping("api/v1/rejectfriendship/{requestId}")
    public ResponseEntity rejectRequest(@PathVariable Integer requestId) {
        dogFriendRequestService.rejectRequest(requestId);
        return ResponseEntity.ok().build();
    }

}