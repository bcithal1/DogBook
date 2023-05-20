package dogbook.service;

import dogbook.clients.BreedClient;
import dogbook.model.breedResponse.BreedEntry;
import dogbook.model.breedResponse.BreedInfo;
import dogbook.model.Dog;
import dogbook.model.DogOwner;
import dogbook.model.Photo;
import dogbook.repository.DogOwnerRepo;
import dogbook.repository.DogRepo;
import dogbook.repository.PhotoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class DogService {


    @Autowired
    DogRepo dogRepo;
    @Autowired
    DogOwnerRepo dogOwnerRepo;
    @Autowired
    BreedClient breedClient;
    @Autowired
    PhotoRepo photoRepo;

    public List<Dog> getAllDogsByUserId(Integer id){
        return dogRepo.findAllByOwnerId(id);
    }

    public Optional<Dog> getDogById(Integer id){
        return dogRepo.findById(id);
    }

    @Transactional
    public Dog createDog(Dog dog, DogOwner dogOwner) {
        Dog result = dogRepo.save(dog);
        dogOwner.setDogId(result.getId());
        var savedDogOwner = dogOwnerRepo.save(dogOwner);
        result.setOwners(new ArrayList<>(Arrays.asList(savedDogOwner)));
        return result;
    }

    public Dog updateDog(Dog dog) {
        return dogRepo.save(dog);
    }

    public void deleteDog(Integer id){
        dogRepo.deleteById(id);
    }

    public List<BreedEntry> getBreedListResponse(){
        return breedClient.getBreedList();
    }

    public BreedInfo getBreedById(Integer id){
        return breedClient.getBreedById(id);
    }

    public List<Dog> getAllDogs(){
        return dogRepo.findAll();
    }

    @Transactional
    public Dog savePhoto(Photo photo, Dog dog){
        Photo savedPhoto = photoRepo.save(photo);
        dog.getPhotoIds().add(savedPhoto.getId());
        return dogRepo.save(dog);
    }
}
