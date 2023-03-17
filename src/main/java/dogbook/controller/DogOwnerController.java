package dogbook.controller;

import dogbook.model.Dog;
import dogbook.model.DogOwner;
import dogbook.service.AuthenticatedUserService;
import dogbook.service.DogOwnerService;
import dogbook.service.DogService;
import dogbook.service.UserService;
import dogbook.enums.AccessLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class DogOwnerController {

    @Autowired
    DogService dogService;
    @Autowired
    DogOwnerService dogOwnerService;
    @Autowired
    UserService userService;
    @Autowired
    AuthenticatedUserService authenticatedUserService;

    @GetMapping("/api/v1/dogs/{id}/owners")
    public ResponseEntity<List<DogOwner>> getAllDogOwnersByDogId(@PathVariable Integer id){
        Optional<Dog> dog = dogService.getDogById(id);
        if(dog.isPresent()){
            return ResponseEntity.ok(dog.get().getOwners());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/api/v1/dogs/{id}/owners")
    public ResponseEntity<DogOwner> addDogOwner(@PathVariable Integer id, @RequestBody DogOwner dogOwner){
        // NOTE(Trystan): Another primary owner cannot be added.
        if(dogOwner.getAccessLevel() == AccessLevel.PRIMARY_OWNER){
            return ResponseEntity.badRequest().build();
        }

        Integer userId = authenticatedUserService.getId();
        Optional<Dog> dog = dogService.getDogById(id);
        if(dog.isPresent()){
            if(userId != dog.get().getPrimaryOwnerId()){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            var savedDogOwner = dogOwnerService.addOwner(dogOwner);

            if(savedDogOwner != null){
                return ResponseEntity.status(HttpStatus.CREATED).body(savedDogOwner);
            } else {
                return ResponseEntity.badRequest().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/api/v1/dogs/{dogId}/owners/{ownerId}")
    public ResponseEntity<Dog> deleteDog(@PathVariable Integer dogId, @PathVariable Integer ownerId){
        Integer userId = authenticatedUserService.getId();
        Optional<Dog> dog = dogService.getDogById(dogId);
        if(dog.isPresent()){
            // NOTE(Trystan): You must be the primary to delete users
            // However you cannot delete yourself. (You'd have to delete the dog entirely)
            if(userId != dog.get().getPrimaryOwnerId()){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            if(userId == ownerId){
                return ResponseEntity.badRequest().build();
            }

            var dogOwner = dog.get().getOwnerFromOwnerId(ownerId);
            dogOwner.ifPresent(owner -> dogOwnerService.removeOwner(owner));

            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
