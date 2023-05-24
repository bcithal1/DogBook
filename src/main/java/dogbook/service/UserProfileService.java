package dogbook.service;

import dogbook.model.Photo;
import dogbook.model.UserProfile;
import dogbook.repository.UserProfileRepo;
import dogbook.repository.UserRepo;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Optional;

@Service
public class UserProfileService {

    @Autowired
    UserRepo userRepo;
    @Autowired
    UserProfileRepo userProfileRepo;
    @Autowired
    PhotoService photoService;

    public UserProfile createUserProfile(UserProfile userProfile) {
        Integer userId = userProfile.getId();

        if (userRepo.existsById(userId)) {
            if (userProfileRepo.existsById(userId)) {
                throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "User profile already exists");
            } else {
                userProfile.setProfilePhotoId(15);
                return userProfileRepo.save(userProfile);
            }
        } else {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND,
                    "User account must be registered before a profile can be created.");
        }
    }

    public UserProfile getUserProfile(Integer userId) {

        if (userProfileRepo.existsById(userId)) {
            return userProfileRepo.findById(userId).get();
        } else {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Profile Not Found");
        }
    }

    public UserProfile updateUserProfile(Integer userId, UserProfile updatedProfile){
        Optional<UserProfile> userProfile = userProfileRepo.findById(userId);
        if (userProfile.isPresent()) {
            userProfileRepo.save(updatedProfile);
            return updatedProfile;
        }else{
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "User profile not found");
        }
    }


    public ResponseEntity<byte[]> getUserProfilePhotoByPhotoId(Integer photoId) {
        Optional<Photo> photo = photoService.getPhotoById(photoId);
        if (photo.isPresent()) {
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(photo.get().getType()))
                    .body(Base64.encodeBase64(photo.get().getData()));
        } else {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Profile picture not found");
        }
    }

    public ResponseEntity<byte[]> getUserProfilePhotoByUserId(Integer userId) {
        UserProfile userProfile = getUserProfile(userId);

        Optional<Photo> photo = photoService.getPhotoById(userProfile.getProfilePhotoId());
        if (photo.isPresent()) {
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(photo.get().getType()))
                    .body(Base64.encodeBase64(photo.get().getData()));
        } else {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Profile picture not found");
        }
    }
}