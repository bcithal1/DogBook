package dogbook.controller;

import dogbook.model.DogFriendRequest;
import dogbook.model.FriendRequest;
import dogbook.repository.DogFriendRequestRepo;
import dogbook.service.DogFriendRequestService;
import dogbook.service.FriendRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DogFriendRequestController {

    @Autowired
    DogFriendRequestService dogFriendRequestService;

    @PostMapping("/api/v1/dogfriend/{recipientId}/{senderId}")
    public ResponseEntity<DogFriendRequest> sendFriendRequest(@PathVariable Integer recipientId,
                                                              @PathVariable Integer senderId) {
        return ResponseEntity.ok(dogFriendRequestService.sendFriendRequest(recipientId, senderId));
    }

}