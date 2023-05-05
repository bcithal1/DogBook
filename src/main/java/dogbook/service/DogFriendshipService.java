package dogbook.service;

import dogbook.model.Dog;
import dogbook.model.DogFriendship;
import dogbook.model.DogOwner;
import dogbook.model.Friendship;
import dogbook.repository.DogFriendshipRepo;
import dogbook.repository.DogOwnerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DogFriendshipService {

    @Autowired
    DogFriendshipRepo dogFriendshipRepo;

    @Autowired
    DogService dogService;

    @Autowired
    DogOwnerRepo dogOwnerRepo;

    @Autowired
    AuthenticatedUserService authenticatedUserService;

    public List<DogFriendship> getFriendsList(Integer dogId) {
        List<DogFriendship> friendList = new ArrayList<>();
        Optional<Dog> targetDog = dogService.getDogById(dogId);

        if (targetDog.isPresent()) {
            friendList.addAll(dogFriendshipRepo.findByPrimaryUserId(dogId));
            friendList.addAll(dogFriendshipRepo.findBySecondaryUserId(dogId));
            return friendList;
        } else {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }
    }

    public void endFriendship(Integer friendshipId) {
        Optional<DogFriendship> doomedFriendship = dogFriendshipRepo.findById(friendshipId);

        if (doomedFriendship.isPresent()) {
            if (validateOwner(doomedFriendship.get().getSecondaryUserId())
                    || validateOwner(doomedFriendship.get().getPrimaryUserId())) {
                dogFriendshipRepo.deleteById(friendshipId);
            } else {
                throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED, "This user cannot update this friendship");
            }
        } else {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }
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
