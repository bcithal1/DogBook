package dogbook.service;

import dogbook.enums.AccessLevel;
import dogbook.model.DogOwner;
import dogbook.model.DogTrick;
import dogbook.repository.DogOwnerRepo;
import dogbook.repository.DogRepo;
import dogbook.repository.DogTrickRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Optional;

@Service
public class DogTrickService {

    @Autowired
    DogTrickRepo dogTrickRepo;

    @Autowired
    DogRepo dogRepo;

    @Autowired
    AuthenticatedUserService authenticatedUserService;

    @Autowired
    DogOwnerRepo dogOwnerRepo;


    //add a trick
    public DogTrick createTrick(Integer dogId, String trickName) {
        DogTrick dogTrick = new DogTrick(dogId, trickName);

        if(dogRepo.findById(dogId).isPresent()) {
            if (validateUser(dogId)) {
                return dogTrickRepo.save(dogTrick);
            } else {
                throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
            }
        } else {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Dog not found");
        }
    }

    // displaying trick(s) on dog profile page
    public List<DogTrick> getAllTricks(Integer dogId) {
        return dogTrickRepo.findByDogId(dogId);
    }

    // update trick on dog profile page
    public DogTrick updateTrick(Integer trickId, DogTrick dogTrick) {
        Optional<DogTrick> currentTrick = dogTrickRepo.findById(trickId);

        if (currentTrick.isPresent()) {
            if(!dogTrick.getTrickId().equals(trickId)) {
                throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
            }
             else if (validateUser(currentTrick.get().getDogId())) {
                return dogTrickRepo.save(dogTrick);
            } else {
                throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
            }
        } else {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Trick not found");
        }
    }

    // delete a trick
    public void deleteTrick(Integer trickId){
        Optional<DogTrick> currentTrick = dogTrickRepo.findById(trickId);

        if (currentTrick.isPresent()) {
            if (validateUser(currentTrick.get().getDogId())) {
                dogTrickRepo.deleteById(trickId);
            } else {
                throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
            }
        }
    }

    //method to validate authenticated user
    private boolean validateUser(Integer dogId) {
        boolean userValidated = false;
        Integer currentUser = authenticatedUserService.getId();
        List<DogOwner> validUsers = dogOwnerRepo.findAllByDogId(dogId);
        for (DogOwner owner : validUsers) {
            userValidated = owner.getUserId().equals(currentUser) &&
                    (owner.getAccessLevel() == AccessLevel.PRIMARY_OWNER
                            || owner.getAccessLevel() == AccessLevel.SECONDARY_OWNER);
        }
        return userValidated;
    }
}
