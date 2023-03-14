package dogbook.repository;

import dogbook.model.DogOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DogOwnerRepo extends JpaRepository<DogOwner,Integer> {
    List<DogOwner> findAllByUserId(Integer userId);
    List<DogOwner> findAllByDogId(Integer dogId);
}
