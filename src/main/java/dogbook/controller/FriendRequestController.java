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
    public ResponseEntity sendFriendRequest(@PathVariable Integer recipientId) {
        return friendRequestService.sendFriendRequest(recipientId);
    }

    @GetMapping("/api/v1/friendrequest/sent")
    public ResponseEntity<List<FriendRequest>> getAllSentRequests() {
        return friendRequestService.getSentRequestsByUserID();
    }

    @GetMapping("api/v1/friendrequest/received")
    public ResponseEntity<List<FriendRequest>> getAllReceivedRequests() {
        return friendRequestService.getReceivedRequestsByUserID();
    }

    @DeleteMapping("api/v1/cancelrequest/{requestId}")
    public ResponseEntity<Friendship> deleteFriendRequest(@PathVariable Integer requestId) {
        return friendRequestService.cancelFriendRequest(requestId);
    }

    @PutMapping("/api/v1/acceptfriendship/{requestId}")
    public ResponseEntity acceptFriendRequest(@PathVariable Integer requestId) {
        return friendRequestService.acceptRequest(requestId);
    }

    @DeleteMapping("api/v1/rejectfriendship/{requestId}")
    public ResponseEntity rejectRequest(@PathVariable Integer requestId) {
        return friendRequestService.rejectRequest(requestId);
    }


}
