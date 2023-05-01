package dogbook.service;

import dogbook.model.FriendRequest;
import dogbook.model.Friendship;
import dogbook.repository.FriendRequestRepo;
import dogbook.repository.FriendshipRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@SuppressWarnings("rawtypes")
@Service
public class FriendRequestService {

    @Autowired
    FriendshipRepo friendshipRepo;

    @Autowired
    FriendRequestRepo friendRequestRepo;

    @Autowired
    UserService userService;

    @Autowired
    AuthenticatedUserService authenticatedUserService;

    public ResponseEntity<FriendRequest> sendFriendRequest(Integer recipientId){

        ResponseEntity responseEntity;
        Integer currentUser = authenticatedUserService.getId();

        if (validateRequest(currentUser, recipientId)){
            if (userService.getUserById(recipientId).isPresent()){
                FriendRequest friendRequest = new FriendRequest(currentUser, recipientId);
                friendRequest.setCreateDate(new Date());
                friendRequestRepo.save(friendRequest);
                responseEntity = ResponseEntity.ok(friendRequest);
            } else {
                responseEntity = ResponseEntity.notFound().build();
            }
        }else{
            responseEntity = ResponseEntity.badRequest().build();
        }

        return responseEntity;
    }

    public ResponseEntity<List<FriendRequest>> getSentRequestsByUserID(){
        Integer currentUser = authenticatedUserService.getId();
        return ResponseEntity.ok(friendRequestRepo.findBySenderId(currentUser));
    }

    public ResponseEntity<List<FriendRequest>> getReceivedRequestsByUserID(){
        Integer currentUser = authenticatedUserService.getId();
        return ResponseEntity.ok(friendRequestRepo.findByReceiverId(currentUser));
    }

    public ResponseEntity cancelFriendRequest(Integer requestId){
        ResponseEntity responseEntity;
        Integer currentUser = authenticatedUserService.getId();
        Optional<FriendRequest> friendRequest = getFriendRequest(requestId);

        if (friendRequest.isEmpty()) {
            responseEntity = ResponseEntity.notFound().build();
        } else if (!Objects.equals(friendRequest.get().getSenderId(), currentUser)) {
            System.out.println(friendRequest.get().getSenderId());
            responseEntity = ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } else {
            deleteFriendRequest(requestId);
            responseEntity = ResponseEntity.ok().build();
        }
        return responseEntity;
    }

    public Optional<FriendRequest> getFriendRequest(Integer requestId){
        return friendRequestRepo.findById(requestId);
    }

    public ResponseEntity<FriendRequest> acceptRequest(Integer requestId){

        ResponseEntity responseEntity;
        Integer currentUser = authenticatedUserService.getId();
        Optional<FriendRequest> friendRequest = getFriendRequest(requestId);

        if (friendRequest.isEmpty()) {
            responseEntity = ResponseEntity.notFound().build();
        } else if (!friendRequest.get().getReceiverId().equals(currentUser)) {
            responseEntity = ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } else {
            Friendship newFriendship = new Friendship();
            newFriendship.setCreatedDate(new Date());
            newFriendship.setPrimaryUserId(friendRequest.get().getSenderId());
            newFriendship.setSecondaryUserId(friendRequest.get().getReceiverId());
            friendshipRepo.save(newFriendship);
            deleteFriendRequest(requestId);
            responseEntity = ResponseEntity.ok(newFriendship);
        }
        return responseEntity;

    }

    public ResponseEntity rejectRequest(Integer requestId){
        ResponseEntity responseEntity;
        Integer currentUser = authenticatedUserService.getId();
        Optional<FriendRequest> friendRequest = getFriendRequest(requestId);

        if (friendRequest.isEmpty()) {
            responseEntity = ResponseEntity.notFound().build();
        } else if (!friendRequest.get().getReceiverId().equals(currentUser)) {
            responseEntity = ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } else {
            deleteFriendRequest(requestId);
            responseEntity = ResponseEntity.ok().build();
        }
        return responseEntity;
    }

    public void deleteFriendRequest(Integer requestId){
        friendRequestRepo.deleteById(requestId);
    }

    public boolean validateRequest(Integer senderId, Integer receiverId){
        //Hard stops someone from friending themselves before anything else is done.
        if(senderId.equals(receiverId))
            return false;

        List<FriendRequest> senderRequests = friendRequestRepo.findBySenderId(senderId);
        List<FriendRequest> receiverRequests = friendRequestRepo.findByReceiverId(senderId);
        List<Friendship> friendList = new ArrayList<>();

        friendList.addAll(friendshipRepo.findByPrimaryUserId(senderId));
        friendList.addAll(friendshipRepo.findBySecondaryUserId(senderId));

        for (FriendRequest sentRequests : senderRequests){
            if (sentRequests.getReceiverId().equals(receiverId))
                return false;
        }
        for (FriendRequest receivedRequests : receiverRequests){
            if (receivedRequests.getSenderId().equals(receiverId))
                return false;
        }
        for (Friendship friendship : friendList){
            if (friendship.getPrimaryUserId().equals(receiverId)){
                return false;
            }
            if (friendship.getSecondaryUserId().equals(receiverId)){
                return false;
            }
        }
        return true;
    }
}
