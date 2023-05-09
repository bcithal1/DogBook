package dogbook.service;

import dogbook.model.DogProfile;
import dogbook.repository.DogProfileRepo;
import dogbook.repository.DogRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Optional;

@Service
public class DogProfileService {

    @Autowired
    DogProfileRepo dogProfileRepo;

    @Autowired
    DogRepo dogRepo;


    public DogProfile createDogProfile(DogProfile dogProfile){
        Integer dogId = dogProfile.getDog().getId();
        if (dogRepo.findById(dogId).isPresent()){
            if(dogProfileRepo.findByDogId(dogId).isPresent()) {
                throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Dog profile already exists");
            } else {
                return dogProfileRepo.save(dogProfile);
            }
        }
        else {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Dog not found");
        }
    }

    public List<DogProfile> getAllDogProfiles(){
        return dogProfileRepo.findAll();
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
            dogRepo.save(request.getDog());
            return request;
        } else {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Dog profile not found");
        }
    }

    @Transactional
    public void deleteDogProfile(Integer id){
        Optional<DogProfile> dogProfile = dogProfileRepo.findById(id);
        if (dogProfile.isPresent()){
            dogProfileRepo.delete(dogProfile.get());
            dogRepo.delete(dogProfile.get().getDog());
        } else {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Dog profile not found");
        }
    }
}
