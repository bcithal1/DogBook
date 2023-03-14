package dogbook.service.implementation;

import dogbook.model.Dog;
import dogbook.model.DogOwner;
import dogbook.repository.DogOwnerRepo;
import dogbook.repository.DogRepo;
import dogbook.service.DogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class DogServiceImpl implements DogService {

    @Autowired
    DogRepo dogRepo;
    @Autowired
    DogOwnerRepo dogOwnerRepo;

    @Override
    public List<Dog> getAllDogsByUserId(Integer id){
        return dogRepo.findAllByOwnerId(id);
    }
    @Override
    public Optional<Dog> getDogById(Integer id){
        return dogRepo.findById(id);
    }
    @Override
    @Transactional
    public Dog createDog(Dog dog, DogOwner dogOwner) {
        Dog result = dogRepo.save(dog);
        dogOwner.setDogId(result.getId());
        var savedDogOwner = dogOwnerRepo.save(dogOwner);
        result.setOwners(new ArrayList<>(Arrays.asList(savedDogOwner)));
        return result;
    }
    @Override
    public Dog updateDog(Dog dog) {
        return dogRepo.save(dog);
    }
    @Override
    public void deleteDog(Integer id){
        dogRepo.deleteById(id);
    }
}
