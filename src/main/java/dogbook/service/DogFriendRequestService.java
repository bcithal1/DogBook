package dogbook.service;

import dogbook.model.*;
import dogbook.repository.DogFriendRequestRepo;
import dogbook.repository.DogFriendshipRepo;
import dogbook.repository.DogOwnerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.*;

@Service
public class DogFriendRequestService {

    @Autowired
    AuthenticatedUserService authenticatedUserService;

    @Autowired
    DogFriendRequestRepo dogFriendRequestRepo;

    @Autowired
    DogFriendshipRepo dogFriendshipRepo;

    @Autowired
    DogOwnerRepo dogOwnerRepo;

    @Autowired
    DogService dogService;

    public List<List<DogFriendRequest>> getMultiPuppyPalRequests(List<Dog> dogList) {
        List<List<DogFriendRequest>> allRequestList = new ArrayList<>();

        for (Dog dog : dogList){
            allRequestList.add(getReceivedRequestsByUserID(dog.getId()));
        }

        return allRequestList;
    }

    public DogFriendRequest sendFriendRequest(Integer senderId, Integer recipientId) {

        if (validateRequest(senderId, recipientId)){
            if (dogService.getDogById(recipientId).isPresent()){
                DogFriendRequest dogFriendRequest = new DogFriendRequest(senderId, recipientId);
                dogFriendRequest.setCreateDate(new Date());
                return dogFriendRequestRepo.save(dogFriendRequest);
            } else {
                throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
            }
        }else{
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
        }
    }

    public void deleteFriendRequest(Integer requestId){
        dogFriendRequestRepo.deleteById(requestId);
    }

    public void cancelFriendRequest(Integer requestId){
        Optional<DogFriendRequest> dogFriendRequest = dogFriendRequestRepo.findById(requestId);

        if (dogFriendRequest.isEmpty()) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        } else if (validateOwner(dogFriendRequest.get().getSenderId())) {
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
        } else {
            deleteFriendRequest(requestId);
        }
    }

    public void rejectRequest(Integer requestId){

        Optional<DogFriendRequest> dogFriendRequest = dogFriendRequestRepo.findById(requestId);

        if (dogFriendRequest.isEmpty()) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        } else if (validateOwner(dogFriendRequest.get().getReceiverId())) {
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
        } else {
            deleteFriendRequest(requestId);
        }
    }

    public DogFriendship acceptRequest(Integer requestId){

        Optional<DogFriendRequest> dogFriendRequest = dogFriendRequestRepo.findById(requestId);

        if (dogFriendRequest.isEmpty()) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        } else if (validateOwner(dogFriendRequest.get().getReceiverId())) {
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
        } else {
            DogFriendship newFriendship = new DogFriendship();
            newFriendship.setCreatedDate(new Date());
            newFriendship.setPrimaryUserId(dogFriendRequest.get().getSenderId());
            newFriendship.setSecondaryUserId(dogFriendRequest.get().getReceiverId());
            deleteFriendRequest(requestId);
            return dogFriendshipRepo.save(newFriendship);
        }

    }

    public List<DogFriendRequest> getSentRequestsByDogID(Integer dogId){
        return dogFriendRequestRepo.findBySenderId(dogId);
    }

    public List<DogFriendRequest> getReceivedRequestsByUserID(Integer dogId){
        return dogFriendRequestRepo.findByReceiverId(dogId);
    }

    private boolean validateRequest(Integer senderId, Integer receiverId) {

        //Hard stops someone from having their dog friend themselves
        if (senderId.equals(receiverId))
            return false;

        //Hard stops a non-owner from sending the request.
        if (!validateOwner(senderId))
            return false;

        List<DogFriendRequest> senderRequests = dogFriendRequestRepo.findBySenderId(senderId);
        List<DogFriendRequest> receiverRequests = dogFriendRequestRepo.findByReceiverId(senderId);
        List<DogFriendship> friendList = new ArrayList<>();

        friendList.addAll(dogFriendshipRepo.findByPrimaryUserId(senderId));
        friendList.addAll(dogFriendshipRepo.findBySecondaryUserId(senderId));

        for (DogFriendRequest sentRequests : senderRequests) {
            if (sentRequests.getReceiverId().equals(senderId))
                return false;
        }
        for (DogFriendRequest receivedRequests : receiverRequests) {
            if (receivedRequests.getSenderId().equals(receiverId))
                return false;
        }
        for (DogFriendship friendship : friendList) {
            if (friendship.getPrimaryUserId().equals(receiverId)) {
                return false;
            }
            if (friendship.getSecondaryUserId().equals(receiverId)) {
                return false;
            }
        }
        return true;
    }

    private boolean validateOwner(Integer dogId) {
        List<DogOwner> ownerInfo = dogOwnerRepo.findAllByDogId(dogId);
        Integer currentUser = authenticatedUserService.getId();

        for (DogOwner owner : ownerInfo) {
            if (owner.getUserId().equals(currentUser))
                return true;
        }
        return false;
    }
}
