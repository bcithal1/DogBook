package dogbook.service;

import dogbook.model.DogProfile;
import dogbook.repository.DogProfileRepo;
import dogbook.repository.DogRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Optional;

@Service
public class DogProfileService {

    @Autowired
    DogProfileRepo dogProfileRepo;

    @Autowired
    DogRepo dogRepo;


    public DogProfile createDogProfile(DogProfile dogProfile){
        if (dogRepo.findById(dogProfile.getDog().getId()).isPresent()){
            return dogProfileRepo.save(dogProfile);
        }
        else {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Dog not found");
        }
    }

    public DogProfile getDogProfileByProfileId(Integer id){
        Optional<DogProfile> dogProfile = dogProfileRepo.findById(id);
        if(dogProfile.isPresent()){
            return dogProfile.get();
        } else {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Dog profile not found");
        }
    }

    public DogProfile getDogProfileByDogId(Integer id){
        Optional<DogProfile> dogProfile = dogProfileRepo.findByDogId(id);
        if(dogProfile.isPresent()){
            return dogProfile.get();
        } else {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Dog profile not found");
        }
    }

    public DogProfile updateDogProfile(Integer id, DogProfile request ){
        Optional<DogProfile> dogProfile = dogProfileRepo.findById(id);
        if (dogProfile.isPresent()){
            dogProfileRepo.save(request);
            return request;
        } else {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Dog profile not found");
        }
    }


    public void deleteDogProfile(Integer id){
        Optional<DogProfile> dogProfile = dogProfileRepo.findById(id);
        if (dogProfile.isPresent()){
            dogProfileRepo.delete(dogProfile.get());
        } else {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Dog profile not found");
        }
    }
}
