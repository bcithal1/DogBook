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
        List<Friendship> userFriendList = getFriendsList(1);
        List<Friendship> masterList = new ArrayList<>();
        Set<Integer> uniqueUserIds = new HashSet<>();
        List<UserWithDogs> userWithDogsList = new ArrayList<>();

        for (Friendship friendship : userFriendList) {
            masterList.addAll(getFriendsList(friendship.getSecondaryUserId()));
            masterList.addAll(getFriendsList(friendship.getPrimaryUserId()));
        }
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

        for (Integer userId : uniqueUserIds) {
            User user = userService.getUserById(userId).get();
            List<Dog> dogs = dogService.getAllDogsByUserId(userId);
            userWithDogsList.add(new UserWithDogs(user, dogs));
        }

        return userWithDogsList;
    }

}