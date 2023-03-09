package dogbook.service.implementation;

import dogbook.model.Dog;
import dogbook.repository.DogRepo;
import dogbook.service.DogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DogServiceImpl implements DogService {

    @Autowired
    DogRepo dogRepo;

    @Override
    public List<Dog> getAllDogsByUserId(Integer id){
        return dogRepo.findAllByUserId(id);
    }
    @Override
    public Optional<Dog> getDogById(Integer id){
        return dogRepo.findById(id);
    }
    @Override
    public Dog createDog(Dog dog) {
        return dogRepo.save(dog);
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
