package dogbook.controller;

import dogbook.model.UserProfile;
import dogbook.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import javax.validation.constraints.NotNull;

@RestController
public class UserProfileController {

    @Autowired
    UserProfileService userProfileService;

    @PostMapping("/api/v1/users/profile")
    public ResponseEntity<UserProfile> createUserProfile(@RequestBody UserProfile userProfile){
        return ResponseEntity.ok(userProfileService.createUserProfile(userProfile));
    }

    @GetMapping("/api/v1/users/profile/{userId}")
    public ResponseEntity<UserProfile> getUserProfile(@PathVariable Integer userId){
        return ResponseEntity.ok(userProfileService.getUserProfile(userId));
    }

    @GetMapping("/api/v1/users/profilephoto/{photoId}")
    public ResponseEntity<byte[]> getProfilePhotoByPhotoId(@PathVariable Integer photoId){
        return userProfileService.getUserProfilePhotoByPhotoId(photoId);
    }

    @GetMapping("/api/v1/users/ppuid/{userId}")
    public ResponseEntity<byte[]> getProfilePhotoByUserId(@PathVariable Integer userId){
        return userProfileService.getUserProfilePhotoByUserId(userId);
    }

    //update userProfile

}
