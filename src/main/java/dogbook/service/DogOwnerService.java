package dogbook.service;

import dogbook.model.DogOwner;

import java.util.List;

public interface DogOwnerService {
    List<DogOwner> getAllByUserId(Integer id);
    List<DogOwner> getAllByDogId(Integer id);
    DogOwner addOwner(DogOwner dogOwner);
    DogOwner updateOwner(DogOwner dogOwner);
    void removeOwner(DogOwner dogOwner);
}
