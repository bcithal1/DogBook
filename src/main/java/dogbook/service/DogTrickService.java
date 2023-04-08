package dogbook.service;

import dogbook.enums.AccessLevel;
import dogbook.model.DogOwner;
import dogbook.model.DogTrick;
import dogbook.repository.DogOwnerRepo;
import dogbook.repository.DogRepo;
import dogbook.repository.DogTrickRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
    public ResponseEntity<DogTrick> createTrick(Integer dogId, String trickName) {
        ResponseEntity responseEntity;
        DogTrick dogTrick = new DogTrick(dogId, trickName);

        if(dogRepo.findById(dogId).isPresent()) {

            if (validateUser(dogId)) {
               var tricks = dogTrickRepo.save(dogTrick);
                responseEntity = ResponseEntity.ok(tricks);
            } else {
                responseEntity =  ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        }else {
            responseEntity = ResponseEntity.notFound().build();
        }
        return responseEntity;
    }

    // displaying trick(s) on dog profile page
    public ResponseEntity<List<DogTrick>> getAllTricks(Integer dogId) {
        return ResponseEntity.ok(dogTrickRepo.findByDogId(dogId));
    }

    // update trick on dog profile page
    public ResponseEntity<DogTrick> updateTrick(Integer trickId, DogTrick dogTrick) {
        ResponseEntity responseEntity;
        Optional<DogTrick> currentTrick = dogTrickRepo.findById(trickId);

        if (currentTrick.isPresent()) {
            if (validateUser(currentTrick.get().getDogId())) {
                var tricks = dogTrickRepo.save(dogTrick);

                responseEntity = ResponseEntity.ok(tricks);
            } else {
                responseEntity = ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        } else {
            responseEntity = ResponseEntity.notFound().build();
        }
        return responseEntity;
    }

    // delete a trick
    public ResponseEntity deleteTrick(Integer trickId){
        ResponseEntity responseEntity;
        Optional<DogTrick> currentTrick = dogTrickRepo.findById(trickId);

        if (currentTrick.isPresent()) {
            if (validateUser(currentTrick.get().getDogId())) {
                dogTrickRepo.deleteById(trickId);
                responseEntity = ResponseEntity.ok().build();
            } else {
                responseEntity = ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        } else {
            responseEntity = ResponseEntity.notFound().build();
        }

        return responseEntity;
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
