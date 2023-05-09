package dogbook.controller;

import dogbook.model.DogProfile;
import dogbook.service.DogProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
public class DogProfileController {

    @Autowired
    DogProfileService dogProfileService;

    @PreAuthorize("@authenticatedUserService.validateDogOwnership(#dogProfile.getDog().getId())")
    @PostMapping("/api/v1/dogs/profiles")
    public ResponseEntity<DogProfile> createDogProfile(@RequestBody @NotNull DogProfile dogProfile){
        return ResponseEntity.ok(dogProfileService.createDogProfile(dogProfile));
    }

    @GetMapping("/api/v1/dogs/profiles")
    public ResponseEntity<List<DogProfile>> getAllDogProfiles(){
        return ResponseEntity.ok(dogProfileService.getAllDogProfiles());
    }
    @GetMapping("/api/v1/dogs/profiles/{id}")
    public ResponseEntity<DogProfile> getDogProfileByProfileId(@PathVariable Integer id){
        return ResponseEntity.ok(dogProfileService.getDogProfileByProfileId(id));
    }

    @GetMapping("/api/v1/dogs/profiles/dog/{dogId}")
    public ResponseEntity<DogProfile> getDogProfileByDogId(@PathVariable Integer dogId){
        return ResponseEntity.ok(dogProfileService.getDogProfileByDogId(dogId));
    }

    @GetMapping("/api/v1/dogs/profile/picture/{dogId}")
    public ResponseEntity<byte[]> getDogProfilePicture(@PathVariable Integer dogId){
        return dogProfileService.getDogProfilePhoto(dogId);
    }

    @PreAuthorize("@authenticatedUserService.validateDogOwnership(#dogProfileRequest.getDog().getId())")
    @PutMapping("/api/v1/dogs/profiles/{id}")
    public ResponseEntity<DogProfile> updateDogProfile(@PathVariable Integer id, @RequestBody DogProfile dogProfileRequest){
        return ResponseEntity.ok(dogProfileService.updateDogProfile(id, dogProfileRequest));
    }

    @PreAuthorize("@authenticatedUserService.validateDogOwnership(@dogProfileService.getDogProfileByProfileId(#id).getDog().getId())")
    @DeleteMapping("api/v1/dogs/profiles/{id}")
    public ResponseEntity<Void> deleteDogProfile(@PathVariable Integer id){
        dogProfileService.deleteDogProfile(id);
        return ResponseEntity.noContent().build();
    }


}
