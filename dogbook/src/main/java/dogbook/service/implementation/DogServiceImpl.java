package dogbook.service.implementation;

import dogbook.model.Dog;
import dogbook.repository.DogRepo;
import dogbook.service.DogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DogServiceImpl implements DogService {

    @Autowired
    DogRepo dogRepo;

    @Override
    public Dog createDog(Dog dog) {
        return dogRepo.save(dog);
    }
}
