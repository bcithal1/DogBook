package dogbook.repository;

import dogbook.model.DogProfile;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface DogProfileRepo extends CrudRepository<DogProfile, Integer> {

    Optional<DogProfile> findByDogId(Integer dogId);

    List<DogProfile> findAll();
}
