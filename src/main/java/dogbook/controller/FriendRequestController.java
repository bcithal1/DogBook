package dogbook.controller;

import dogbook.model.FriendRequest;
import dogbook.model.Friendship;
import dogbook.service.FriendRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SuppressWarnings("ALL")
@RestController
public class FriendRequestController {


    @Autowired
    FriendRequestService friendRequestService;

    @PostMapping("/api/v1/friendrequest/{recipientId}")
    public ResponseEntity<FriendRequest> sendFriendRequest(@PathVariable Integer recipientId) {
        return ResponseEntity.ok(friendRequestService.sendFriendRequest(recipientId));
    }

    @GetMapping("/api/v1/friendrequest/sent")
    public ResponseEntity<List<FriendRequest>> getAllSentRequests() {
        return ResponseEntity.ok(friendRequestService.getSentRequestsByUserID());
    }

    @GetMapping("api/v1/friendrequest/received")
    public ResponseEntity<List<FriendRequest>> getAllReceivedRequests() {
        return ResponseEntity.ok(friendRequestService.getReceivedRequestsByUserID());
    }

    @DeleteMapping("api/v1/cancelrequest/{requestId}")
    public ResponseEntity<Friendship> deleteFriendRequest(@PathVariable Integer requestId) {
        friendRequestService.cancelFriendRequest(requestId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/api/v1/acceptfriendship/{requestId}")
    public ResponseEntity acceptFriendRequest(@PathVariable Integer requestId) {
        return ResponseEntity.ok(friendRequestService.acceptRequest(requestId));
    }

    @DeleteMapping("api/v1/rejectfriendship/{requestId}")
    public ResponseEntity rejectRequest(@PathVariable Integer requestId) {
        friendRequestService.rejectRequest(requestId);
        return ResponseEntity.ok().build();
    }


}
