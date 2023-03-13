package dogbook.service.implementation;

import dogbook.model.DogOwner;
import dogbook.repository.DogOwnerRepo;
import dogbook.service.DogOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DogOwnerServiceImpl implements DogOwnerService {

    @Autowired
    DogOwnerRepo dogOwnerRepo;

    @Override
    public List<DogOwner> getAllByUserId(Integer id){
        return dogOwnerRepo.findAllByUserId(id);
    }
    @Override
    public List<DogOwner> getAllByDogId(Integer id){
        return dogOwnerRepo.findAllByDogId(id);
    }
    @Override
    public DogOwner addOwner(DogOwner dog) {
        return dogOwnerRepo.save(dog);
    }
    @Override
    public DogOwner updateOwner(DogOwner dogOwner) {
        return dogOwnerRepo.save(dogOwner);
    }
    @Override
    public void removeOwner(DogOwner dogOwner){
        dogOwnerRepo.delete(dogOwner);
    }
}
