package dogbook.service;

import dogbook.model.FriendRequest;
import dogbook.model.Friendship;
import dogbook.repository.FriendRequestRepo;
import dogbook.repository.FriendshipRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

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

    public FriendRequest sendFriendRequest(Integer recipientId){

        Integer currentUser = authenticatedUserService.getId();

        if (validateRequest(currentUser, recipientId)){
            if (userService.getUserById(recipientId).isPresent()){
                FriendRequest friendRequest = new FriendRequest(currentUser, recipientId);
                friendRequest.setCreateDate(new Date());
                return friendRequestRepo.save(friendRequest);
            } else {
                throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
            }
        }else{
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Malformed request");
        }
    }

    public List<FriendRequest> getSentRequestsByUserID(){
        Integer currentUser = authenticatedUserService.getId();
        return friendRequestRepo.findBySenderId(currentUser);
    }

    public List<FriendRequest> getReceivedRequestsByUserID(){
        Integer currentUser = authenticatedUserService.getId();
        return friendRequestRepo.findByReceiverId(currentUser);
    }

    public void cancelFriendRequest(Integer requestId){
        Integer currentUser = authenticatedUserService.getId();
        Optional<FriendRequest> friendRequest = getFriendRequest(requestId);

        if (friendRequest.isEmpty()) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        } else if (!Objects.equals(friendRequest.get().getSenderId(), currentUser)) {
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
        } else {
            deleteFriendRequest(requestId);
        }
    }

    public Optional<FriendRequest> getFriendRequest(Integer requestId){
        return friendRequestRepo.findById(requestId);
    }

    public Friendship acceptRequest(Integer requestId){

        ResponseEntity responseEntity;
        Integer currentUser = authenticatedUserService.getId();
        Optional<FriendRequest> friendRequest = getFriendRequest(requestId);

        if (friendRequest.isEmpty()) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        } else if (!friendRequest.get().getReceiverId().equals(currentUser)) {
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
        } else {
            Friendship newFriendship = new Friendship();
            newFriendship.setCreatedDate(new Date());
            newFriendship.setPrimaryUserId(friendRequest.get().getSenderId());
            newFriendship.setSecondaryUserId(friendRequest.get().getReceiverId());
            deleteFriendRequest(requestId);
            return friendshipRepo.save(newFriendship);
        }

    }

    public void rejectRequest(Integer requestId){

        Integer currentUser = authenticatedUserService.getId();
        Optional<FriendRequest> friendRequest = getFriendRequest(requestId);

        if (friendRequest.isEmpty()) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        } else if (!friendRequest.get().getReceiverId().equals(currentUser)) {
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
        } else {
            deleteFriendRequest(requestId);
        }
    }

    public void deleteFriendRequest(Integer requestId){
        friendRequestRepo.deleteById(requestId);
    }

    private boolean validateRequest(Integer senderId, Integer receiverId){
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
