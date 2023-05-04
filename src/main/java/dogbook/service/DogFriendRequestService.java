package dogbook.service;

import dogbook.model.*;
import dogbook.repository.DogFriendRequestRepo;
import dogbook.repository.DogFriendshipRepo;
import dogbook.repository.DogOwnerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    public DogFriendRequest sendFriendRequest(Integer recipientId, Integer senderId) {

//        Integer currentUser = authenticatedUserService.getId();

        if (validateRequest(senderId, recipientId)){
            if (dogService.getDogById(recipientId).isPresent()){
                DogFriendRequest dogFriendRequest = new DogFriendRequest(senderId, recipientId);
                dogFriendRequest.setCreateDate(new Date());
                return dogFriendRequestRepo.save(dogFriendRequest);
            } else {
                throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
            }
        }else{
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Malformed request");
        }
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
            if (sentRequests.getReceiverId().equals(receiverId))
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
            if (owner.getId().equals(currentUser))
                return true;
        }
        return false;
    }
}
