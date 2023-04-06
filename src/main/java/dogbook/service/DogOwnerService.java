package dogbook.service;

import dogbook.model.DogOwner;
import dogbook.repository.DogOwnerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DogOwnerService {
    @Autowired
    DogOwnerRepo dogOwnerRepo;

    public List<DogOwner> getAllByUserId(Integer id){
        return dogOwnerRepo.findAllByUserId(id);
    }

    public List<DogOwner> getAllByDogId(Integer id){
        return dogOwnerRepo.findAllByDogId(id);
    }

    public DogOwner addOwner(DogOwner dog) {
        return dogOwnerRepo.save(dog);
    }

    public DogOwner updateOwner(DogOwner dogOwner) {
        return dogOwnerRepo.save(dogOwner);
    }

    public void removeOwner(DogOwner dogOwner){
        dogOwnerRepo.delete(dogOwner);
    }
}
