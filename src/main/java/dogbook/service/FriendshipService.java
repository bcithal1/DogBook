package dogbook.service;

import dogbook.model.Dog;
import dogbook.model.Friendship;
import dogbook.model.User;
import dogbook.model.UserWithDogs;
import dogbook.repository.FriendshipRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;


import java.util.*;
import java.util.stream.Collectors;

@Service
public class FriendshipService {

    @Autowired
    FriendshipRepo friendshipRepo;

    @Autowired
    UserService userService;

    @Autowired
    DogService dogService;

    @Autowired
    DogOwnerService dogOwnerService;

    @Autowired
    AuthenticatedUserService authenticatedUserService;

    public List<Friendship> getFriendsList(Integer userId) {
        List<Friendship> friendList = new ArrayList<>();
        Optional<User> targetUser = userService.getUserById(userId);

        if (targetUser.isPresent()) {
            friendList.addAll(friendshipRepo.findByPrimaryUserId(userId));
            friendList.addAll(friendshipRepo.findBySecondaryUserId(userId));
            return friendList;
        } else {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }
    }

    public void endFriendship(Integer friendshipId) {
        Integer currentUser = authenticatedUserService.getId();
        Optional<Friendship> doomedFriendship = friendshipRepo.findById(friendshipId);

        if (doomedFriendship.isPresent()) {
            if ((doomedFriendship.get().getPrimaryUserId().equals(currentUser) || doomedFriendship.get().getSecondaryUserId().equals(currentUser))) {
                friendshipRepo.deleteById(friendshipId);
            } else {
                throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED, "This user cannot update this friendship");
            }
        } else {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }
    }

    public List<UserWithDogs> getFriendsofFriendsIds() {
        Integer currentUser = authenticatedUserService.getId();
        User currentUserObject = userService.getUserById(currentUser).get();
        List<Friendship> userFriendList = getFriendsList(currentUser);
        List<Friendship> masterList = new ArrayList<>();
        Set<Integer> uniqueUserIds = new HashSet<>();
        List<UserWithDogs> userWithDogsList = new ArrayList<>();

        // Retrieve friends of friends
        for (Friendship friendship : userFriendList) {
            masterList.addAll(getFriendsList(friendship.getSecondaryUserId()));
            masterList.addAll(getFriendsList(friendship.getPrimaryUserId()));
        }

        // Retrieve unique user IDs
        for (Friendship friendship : masterList) {
            Integer primaryId = friendship.getPrimaryUserId();
            Integer secondaryId = friendship.getSecondaryUserId();

            if (!primaryId.equals(currentUser)) {
                uniqueUserIds.add(primaryId);
            }

            if (!secondaryId.equals(currentUser)) {
                uniqueUserIds.add(secondaryId);
            }
        }

        // Fetch dogs for each user (including current user)
        for (Integer userId : uniqueUserIds) {
            User user = userService.getUserById(userId).get();
            List<Dog> dogs = dogService.getAllDogsByUserId(userId);
            userWithDogsList.add(new UserWithDogs(user, dogs));
        }

        List<Dog> currentUserDogs = dogService.getAllDogsByUserId(currentUser);
        userWithDogsList.add(new UserWithDogs(currentUserObject, currentUserDogs));

        return userWithDogsList;
    }
    public List<User> getMutualFriendList(Integer targetUserId){
        Integer currentUser = authenticatedUserService.getId();
        List<Integer> userAFriends = new ArrayList<>();
        List<Integer> userBFriends = new ArrayList<>();
        List<User> mutualFriendList = new ArrayList<>();
        List<Friendship> tmpArr = new ArrayList<>();

        tmpArr = friendshipRepo.findByPrimaryUserId(currentUser);
        for (Friendship friendship : tmpArr){
         userAFriends.add(friendship.getSecondaryUserId());
        }

        tmpArr = friendshipRepo.findBySecondaryUserId(currentUser);
        for (Friendship friendship : tmpArr){
            userAFriends.add(friendship.getPrimaryUserId());
        }

        tmpArr = friendshipRepo.findByPrimaryUserId(targetUserId);
        for (Friendship friendship : tmpArr){
            userBFriends.add(friendship.getSecondaryUserId());
        }

        tmpArr = friendshipRepo.findBySecondaryUserId(targetUserId);
        for (Friendship friendship : tmpArr){
            userBFriends.add(friendship.getPrimaryUserId());
        }

        userAFriends.retainAll(userBFriends);
        for (Integer userId : userAFriends){
            mutualFriendList.add(userService.getUserById(userId).get());
        }

        return mutualFriendList;
    }

}