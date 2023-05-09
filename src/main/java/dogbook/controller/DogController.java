package dogbook.controller;

import dogbook.model.breedResponse.BreedEntry;
import dogbook.model.breedResponse.BreedInfo;
import dogbook.model.Dog;
import dogbook.model.DogOwner;
import dogbook.model.Photo;
import dogbook.model.User;
import dogbook.service.AuthenticatedUserService;
import dogbook.service.DogOwnerService;
import dogbook.service.DogService;
import dogbook.service.PhotoService;
import dogbook.service.UserService;
import dogbook.enums.AccessLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class DogController {

    @Autowired
    DogService dogService;
    @Autowired
    DogOwnerService dogOwnerService;
    @Autowired
    UserService userService;
    @Autowired
    PhotoService photoService;
    @Autowired
    AuthenticatedUserService authenticatedUserService;

    @GetMapping("/api/v1/dogs")
    public ResponseEntity<List<Dog>> getAllDogsByUserId(@RequestParam(required = false) Integer ownerId) {
        if (ownerId != null) {
            Optional<User> user = userService.getUserById(ownerId);
            if (user.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(dogService.getAllDogsByUserId(ownerId));
        }


        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("/api/v1/dogs/currentuser")
    public ResponseEntity<List<Dog>> getCurrentUserDogs() {
        return ResponseEntity.ok(dogService.getAllDogsByUserId(authenticatedUserService.getId()));
    }


    @GetMapping("/api/v1/dogs/{id}")
    public ResponseEntity<Dog> getDogById(@PathVariable Integer id) {
        Optional<Dog> dog = dogService.getDogById(id);

        return dog.isPresent() ? ResponseEntity.ok(dog.get()) : ResponseEntity.notFound().build();
    }

    @PostMapping("/api/v1/dogs")
    public ResponseEntity<Dog> createDog(@RequestBody Dog dog) {
        Integer userId = authenticatedUserService.getId();
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        dog.setId(null);
        DogOwner dogOwner = new DogOwner(userId, null, AccessLevel.PRIMARY_OWNER);

        Dog savedDog = dogService.createDog(dog, dogOwner);
        if (savedDog != null) {
            return ResponseEntity.created(URI.create("/api/v1/dogs/" + savedDog.getId()))
                    .body(savedDog);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/api/v1/dogs/{id}")
    public ResponseEntity<Dog> updateDog(@PathVariable Integer id, @RequestBody Dog dog) {
        Integer userId = authenticatedUserService.getId();
        Optional<Dog> currentDog = dogService.getDogById(id);
        Logger logger = LoggerFactory.getLogger(this.getClass());

        if (currentDog.isPresent()) {
            var requestingOwner = currentDog.get().getOwnerFromOwnerId(userId);
            if (requestingOwner.isEmpty() ||
                    !(requestingOwner.get().getAccessLevel() == AccessLevel.PRIMARY_OWNER ||
                            requestingOwner.get().getAccessLevel() == AccessLevel.SECONDARY_OWNER)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            dog.setOwners(currentDog.get().getOwners());
            Dog savedDog = dogService.updateDog(dog);

            if (savedDog != null) {
                return ResponseEntity.ok(savedDog);
            } else {
                return ResponseEntity.badRequest().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/api/v1/dogs/{id}")
    public ResponseEntity<Dog> deleteDog(@PathVariable Integer id) {
        Integer userId = authenticatedUserService.getId();
        Optional<Dog> dog = dogService.getDogById(id);
        if (dog.isPresent()) {
            if (userId != dog.get().getPrimaryOwnerId()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            dogService.deleteDog(dog.get().getId());
        }

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/api/v1/breeds")
    public ResponseEntity<List<BreedEntry>> getBreedList() {
        return ResponseEntity.ok(dogService.getBreedListResponse());
    }

    @GetMapping("/api/v1/breeds/{id}")
    public ResponseEntity<BreedInfo> getBreedById(@PathVariable Integer id) {
        return ResponseEntity.ok(dogService.getBreedById(id));
    }

    @PostMapping("/api/v1/dogs/{id}/photos")
    public ResponseEntity<Integer> uploadPhoto(@PathVariable Integer id, @RequestParam("file") MultipartFile file) throws IOException {
        Integer userId = authenticatedUserService.getId();
        Optional<Dog> currentDog = dogService.getDogById(id);

        if (currentDog.isPresent()) {
            var requestingOwner = currentDog.get().getOwnerFromOwnerId(userId);
            if (requestingOwner.isEmpty() ||
                    !(requestingOwner.get().getAccessLevel() == AccessLevel.PRIMARY_OWNER ||
                            requestingOwner.get().getAccessLevel() == AccessLevel.SECONDARY_OWNER)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            Photo photo = new Photo(file);
            Dog savedDog = dogService.savePhoto(photo, currentDog.get());

            if (savedDog != null) {
                return ResponseEntity.ok(photo.getId());
            } else {
                return ResponseEntity.badRequest().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
