package dogbook.service;

import dogbook.model.Dog;
import dogbook.model.DogDogProfDTO;
import dogbook.model.DogProfile;
import dogbook.model.Photo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Service
public class DogDogProfDTOService {

    @Autowired
    DogService dogService;

    @Autowired
    DogProfileService dogProfileService;

    @Autowired
    PhotoService photoService;

    public List<DogDogProfDTO> getDogsForCircus() {
        List<DogDogProfDTO> dogDTOList = new ArrayList<>();

        List<Dog> dogList = dogService.getAllDogs();
        for (Dog dog : dogList) {
            DogProfile dogProfile = dogProfileService.getDogProfileByDogId(dog.getId());
            DogDogProfDTO ddpDTO = new DogDogProfDTO(dog, dogProfile);
            dogDTOList.add(ddpDTO);
        }

        return dogDTOList;
    }

}
