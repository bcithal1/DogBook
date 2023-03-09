package dogbook.controller;

import dogbook.model.Dog;
import dogbook.model.Provider;
import dogbook.model.User;
import dogbook.service.DogService;
import dogbook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class DogController {

    @Autowired
    DogService dogService;
    @Autowired
    UserService userService;

    @GetMapping("/api/v1/users/{id}/dogs")
    public ResponseEntity<List<Dog>> getAllDogsByUserId(@PathVariable Integer id){
        Optional<User> user = userService.getUserById(id);
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(dogService.getAllDogsByUserId(id));
    }

    @GetMapping("/api/v1/users/{userId}/dogs/{dogId}")
    public ResponseEntity<Dog> getDogById(@PathVariable Integer userId, @PathVariable Integer dogId){
        Optional<User> user = userService.getUserById(userId);
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Optional<Dog> dog = dogService.getDogById(dogId);

        return dog.isPresent() ? ResponseEntity.ok(dog.get()) : ResponseEntity.notFound().build();
    }

    @PostMapping("/api/v1/users/{id}/dogs")
    @PreAuthorize("@authenticatedUserService.hasId(#id) or hasAuthority('ROLE_next-server')")
    public ResponseEntity<Dog> createDog(@PathVariable Integer id, @RequestBody Dog dog){
        Optional<User> user = userService.getUserById(id);
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        dog.setId(null);
        dog.setUserId(user.get().getId());

        Dog savedDog = dogService.createDog(dog);
        if(savedDog != null){
            return ResponseEntity.created(URI.create("/api/v1/users/" + id + "/dogs/" + savedDog.getId()))
                    .body(savedDog);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/api/v1/users/{userId}/dogs/{dogId}")
    @PreAuthorize("@authenticatedUserService.hasId(#id) or hasAuthority('ROLE_next-server')")
    public ResponseEntity<Dog> updateDog(@PathVariable Integer userId, @PathVariable Integer dogId, @RequestBody Dog dog){
        Optional<User> user = userService.getUserById(userId);
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        dog.setId(dogId);
        dog.setUserId(user.get().getId());

        Dog savedDog = dogService.updateDog(dog);
        if(savedDog != null){
            return ResponseEntity.ok(savedDog);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/api/v1/users/{userId}/dogs/{dogId}")
    @PreAuthorize("@authenticatedUserService.hasId(#id) or hasAuthority('ROLE_next-server')")
    public ResponseEntity<Dog> deleteDog(@PathVariable Integer userId, @PathVariable Integer dogId){
        Optional<User> user = userService.getUserById(userId);
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        dogService.deleteDog(dogId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
