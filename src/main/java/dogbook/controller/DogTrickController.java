package dogbook.controller;

import dogbook.model.DogTrick;
import dogbook.service.DogTrickService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DogTrickController {

    @Autowired
    DogTrickService dogTrickService;

    //create a trick
    @PostMapping("/api/v1/tricks/{dogId}")
    public ResponseEntity<DogTrick> createTrick(@PathVariable Integer dogId, @RequestBody @NotNull DogTrick dogTrick){
        return ResponseEntity.ok(dogTrickService.createTrick(dogId, dogTrick.getTrickName()));
    }

    //display all tricks
    @GetMapping("/api/v1/tricks/{dogId}")
    public ResponseEntity<List<DogTrick>> getAllTricks(@PathVariable Integer dogId) {
        return ResponseEntity.ok(dogTrickService.getAllTricks(dogId));
    }

    //update trick
    @PutMapping("/api/v1/tricks/{trickId}")
    public ResponseEntity<DogTrick> updateTrick(@PathVariable Integer trickId, @RequestBody DogTrick dogTrick ) {
        return ResponseEntity.ok(dogTrickService.updateTrick(trickId, dogTrick));
    }

    //delete a trick
    @DeleteMapping("/api/v1/deleteTricks/{trickId}")
    public ResponseEntity<Void> deleteTrick(@PathVariable Integer trickId) {
        dogTrickService.deleteTrick(trickId);
        return ResponseEntity.noContent().build();
    }
}
